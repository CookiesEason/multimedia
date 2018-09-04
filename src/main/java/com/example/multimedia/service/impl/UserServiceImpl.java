package com.example.multimedia.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.multimedia.domian.User;
import com.example.multimedia.domian.UserInfo;
import com.example.multimedia.domian.UserRole;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.Article;
import com.example.multimedia.domian.maindomian.Video;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.dto.SimpleUserDTO;
import com.example.multimedia.dto.UsersDTO;
import com.example.multimedia.repository.*;
import com.example.multimedia.service.FileService;
import com.example.multimedia.service.MailService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.EmailUtil;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.util.UserUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

/**
 * @author CookiesEason
 * 2018/07/23 15:18
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private TopicLikeRepository topicLikeRepository;

    @Override
    @Cacheable(value = "user", key = "#id",unless = "#result==null ")
    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    @Override
    public ResultVo save(User user,String role) {
        if (findByUsername(user.getUsername())==null){
            UserInfo userInfo = new UserInfo();
            user.setPassword(encryptPassword(user.getPassword()));
            userInfo.setNickname(user.getUsername());
            user.setUserInfo(userInfo);
            String activateCode = EmailUtil.generateActivateCode(user.getUsername());
            user.setRoleList(Arrays.asList(userRoleRepository.findByRole(role)));
            userRepository.save(user);
            mailService.sendEmail(user.getUsername(),activateCode);
            return ResultVoUtil.success();
        }
        return ResultVoUtil.error(0,"邮箱已经存在");
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @CacheEvict(value = "user",allEntries = true)
    public ResultVo signature(String signature) {
        User user = findByUsername(UserUtil.getUserName());
        user.getUserInfo().setSignature(signature);
        userRepository.save(user);
        return ResultVoUtil.success();
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public ResultVo announcement(String announcement) {
        User user = findByUsername(UserUtil.getUserName());
        user.getUserInfo().setAnnouncement(announcement);
        userRepository.save(user);
        return ResultVoUtil.success();
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public ResultVo save(UserInfo userInfo) {
        // TODO: 2018/07/30 用户个人中心
        User user = findByUsername(UserUtil.getUserName());
        String originalName = user.getUserInfo().getNickname();
        if (checkNickName(userInfo) || userInfo.getNickname().equals(originalName)) {
            userInfo.setHeadImgUrl(user.getUserInfo().getHeadImgUrl());
            userInfo.setAnnouncement(user.getUserInfo().getAnnouncement());
            userInfo.setSignature(user.getUserInfo().getSignature());
            user.setUserInfo(userInfo);
            userRepository.save(user);
            return ResultVoUtil.success();
        }
        return ResultVoUtil.error(0, "用户名已经存在");
    }

    @Override
    public ResultVo simpleInfo(Long userId) {
        User user = findById(userId);
        SimpleUserDTO simpleUserDTO = new SimpleUserDTO(user);
        return ResultVoUtil.success(simpleUserDTO);
    }

    @Override
    public User findByUserInfoNickname(String nickname) {
        return userRepository.findByUserInfoNickname(nickname);
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public ResultVo updatePassword(String code,String password,String passwordAgain) {
        User user = userRepository.findByUsername(UserUtil.getUserName());
        if (code.equals(getCode(user.getUsername()))){
            if (password.length()<8){
                return ResultVoUtil.error(0,"密码长度至少为8位");
            }
            if (password.equals(passwordAgain)){
                user.setPassword(encryptPassword(password));
                userRepository.save(user);
                return ResultVoUtil.success();
            }
            return ResultVoUtil.error(0,"两次密码输入不相同");
        }
        return ResultVoUtil.error(0,"验证码不正确");
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public ResultVo updateHead(MultipartFile multipartFile) {
        ResultVo resultVo = fileService.uploadFile(multipartFile);
        if (resultVo.getCode()==1){
            User user = findByUsername(UserUtil.getUserName());
            user.getUserInfo().setHeadImgUrl(resultVo.getData().toString());
            userRepository.save(user);
        }
        return resultVo;
    }

    @Override
    public ResultVo activateEmail(String username, String activeCode) {
        User user = userRepository.findByUsername(username);
        if (user!=null){
            if (user.isActive()){
                return ResultVoUtil.error(1,"已激活成功");
            }
            String code = template.opsForValue().get(user.getUsername());
            if (code!=null&&code.equals(activeCode)){
                user.setActive(true);
                userRepository.save(user);
                template.opsForValue().getOperations().delete(user.getUsername());
                return ResultVoUtil.success();
            }else {
                String activateCode = EmailUtil.generateActivateCode(user.getUsername());
                mailService.sendEmail(user.getUsername(),activateCode);
                return ResultVoUtil.error(0,"激活失败,链接已经失效,已重新发送邮件");
            }
        }
        return ResultVoUtil.error(0,"激活失败,请确认邮箱信息");
    }



    @Override
    public ResultVo findUsers(int page,String role) {
        int size=10;
        Pageable pageable = PageRequest.of(page,size);
        List<UserRole> roles = userRoleRepository.findAllByRoleLike(role);
        Page<User> users = userRepository.findAllByRoleListIn(pageable,roles);
        UsersDTO usersDTO = new UsersDTO(users.getContent(),(int) users.getTotalElements(),users.getTotalPages());
        return ResultVoUtil.success(usersDTO);
    }

    @Override
    public ResultVo findByUsernameOrUserInfoNickname(String username,String nickname) {
        return ResultVoUtil.success(userRepository.findByUsernameOrUserInfoNickname(username,nickname));
    }

    @Override
    @CacheEvict(value = "user",key = "#userId")
    public ResultVo enableUserByUserId(Long userId) {
        User user = userRepository.findUserById(userId);
        user.setEnable(!user.isEnable());
        userRepository.save(user);
        return ResultVoUtil.success();
    }

    @Override
    @CacheEvict(value = "user", key = "#userId")
    public ResultVo deleteByUserId(Long userId) {
        userRepository.deleteById(userId);
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo getRoles() {
        return ResultVoUtil.success(userRoleRepository.findAll());
    }

    @Override
    @CacheEvict(value = "user", key = "#userId")
    public ResultVo changeRole(Long userId,String role) {
        UserRole userRole  = userRoleRepository.findByRole(role);
        User user = userRepository.findUserById(userId);
        List<UserRole> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoleList(roles);
        userRepository.save(user);
        return ResultVoUtil.success();
    }

    @Override
    public List<User> findAllByIdIn(List<Long> ids) {
        return userRepository.findUsersByIdIn(ids);
    }

    @Override
    public Long getUserHot(Long userId) {
        return userRepository.getUserWorkHot(userId);
    }

    @Override
    public ResultVo findHotUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<SimpleUserDTO> simpleUserDTOS = new ArrayList<>();
        Page<User> users = userRepository.getHotUsers(pageable);
        users.getContent().forEach(user -> {
            SimpleUserDTO simpleUserDTO = new SimpleUserDTO(user,(long)getUserHot(user.getId()));
            simpleUserDTOS.add(simpleUserDTO);
        });
        PageDTO<SimpleUserDTO> userDTOPageDTO = new PageDTO<>(simpleUserDTOS,users.getTotalElements(),
                (long)users.getTotalPages());
        return ResultVoUtil.success(userDTOPageDTO);
    }

    @Override
    public ResultVo checkAccessComment() {
        User user = userRepository.findByUsername(UserUtil.getUserName());
        if (user!=null){
            List<UserRole> roles  = user.getRoleList();
            boolean isAccess = false;
            for (UserRole role : roles) {
                if (!"ROLE_PRIMARY_USER".equals(role.getRole())) {
                    isAccess = true;
                }
            }
            return ResultVoUtil.success(isAccess);
        }
       return ResultVoUtil.error(0,"发生错误,您还未登录");
    }

    @Override
    public int newRegisterCountForDays(int day) {
        return userRepository.countNewRegister(day);
    }

    @Override
    public ResultVo likeUsers(Long userId) {
        Pageable pageable = PageRequest.of(0,5);
        List<User> users = userRepository.likeUserIds(userId,pageable)
                .getContent();
        List<SimpleUserDTO> simpleUserDTOS = new ArrayList<>();
        users.forEach(user -> {
            SimpleUserDTO simpleUserDTO = new SimpleUserDTO(user.getId(),
                    user.getUserInfo().getNickname(),user.getUserInfo().getHeadImgUrl(),
                    user.getUserInfo().getSignature(),getUserHot(user.getId()));
            simpleUserDTOS.add(simpleUserDTO);
        });
        return ResultVoUtil.success(simpleUserDTOS);
    }

    @Override
    public ResultVo likeWorksProportion(Long userId) {
        Map<String,Integer> kv = new HashMap<>();
        List<Article> articles = articleRepository.findAllByIdIn(topicLikeRepository.likeIds(Topic.ARTICLE.toString(),userId));
        articles.forEach(article -> article.getSmallTags().forEach(smallTags -> {
            if (kv.containsKey(smallTags.getSmallTag())){
                kv.put(smallTags.getSmallTag(),kv.get(smallTags.getSmallTag())+1);
            }else {
                kv.put(smallTags.getSmallTag(),1);
            }
        }));
        List<Video> videos = videoRepository.findAllByIdIn(topicLikeRepository.likeIds(Topic.VIDEO.toString(),userId));
        videos.forEach(video -> video.getSmallTags().forEach(smallTags -> {
            if (kv.containsKey(smallTags.getSmallTag())){
                kv.put(smallTags.getSmallTag(),kv.get(smallTags.getSmallTag())+1);
            }else {
                kv.put(smallTags.getSmallTag(),1);
            }
        }));
        Map<String,Integer> result;
        result = kv.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(
                toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new)
        );
        JSONArray jsonArray=new JSONArray();
        List<Integer> values = new ArrayList<>();
        JSONObject rs =new JSONObject();
        result.forEach((k, v) -> {
            if (values.size()>4){
                return;
            }
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",k);
            jsonObject.put("max",100);
            values.add(v*10);
            jsonArray.add(jsonObject);
        });
        rs.put("indicator",jsonArray);
        rs.put("value",values);
        return ResultVoUtil.success(rs);
    }

    private String encryptPassword(String password){
       return new BCryptPasswordEncoder().encode(password);
    }

    private String getCode(String email){
        return template.opsForValue().get(email);
    }

    private boolean checkNickName(UserInfo userInfo){
        return findByUserInfoNickname(userInfo.getNickname()) == null;
    }

}
