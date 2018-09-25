package com.example.multimedia.controller;

import com.example.multimedia.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author CookiesEason
 * 2018/09/22 14:22
 */
@Controller
public class AdminWebController {

    @Autowired
    private AdminNoticeService adminNoticeService;

    @Autowired
    private UserService userService;

    @Autowired
    private SmallTagsService smallTagsService;

    @Autowired
    private CommandHistoryService commandHistoryService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SearchService searchService;

    @RequestMapping("/adminLogin")
    public String adminLoginIndex(){
        return "login";
    }

    @RequestMapping("/admin/report")
    public String adminReportIndex(@RequestParam(defaultValue = "1") int page, Model model){
        model.addAttribute("reports",adminNoticeService.getReportNotice(page,"report"));
        return "adminReport";
    }

    @RequestMapping("/admin/users")
    public String adminUsers(@RequestParam(defaultValue = "1") int page, Model model){
        model.addAttribute("users",userService.findUsers(page));
        return "adminUser";
    }

    @RequestMapping("/admin/users/search")
    public String adminUsersSearch(@RequestParam String searchContent, Model model){
        model.addAttribute("users",userService.findByUsernameOrUserInfoNickname(searchContent));
        return "adminUser";
    }

    @RequestMapping("/admin/labels")
    public String adminLabels(Model model){
        model.addAttribute("labels",smallTagsService.findAll(1));
        return "adminLabel";
    }

    @RequestMapping("/admin/records")
    public String adminRecords(@RequestParam(defaultValue = "1") int page,Model model){
        model.addAttribute("records",commandHistoryService.findAll(page,16));
        return "adminRecord";
    }

    @RequestMapping("/admin/examine")
    public String adminExamine(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "16") int size,
                            @RequestParam(defaultValue = "desc") String order,
                            @RequestParam(defaultValue = "createDate") String sort,
                            @RequestParam(defaultValue = "true") Boolean enable,
                            @RequestParam(defaultValue = "false") Boolean auditing,Model model){
        model.addAttribute("videos",videoService.findVideos(page-1, size, order, sort, enable, auditing));
        return "adminExamine";
    }


    @RequestMapping("/admin/imgTxt")
    public String adminImgTxt(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "16") int size,
                              @RequestParam(defaultValue = "desc") String order,
                              @RequestParam(defaultValue = "createDate") String sort,
                              @RequestParam(defaultValue = "true") Boolean enable,Model model){
        model.addAttribute("articles",articleService.findAll(page-1, size, order, sort, enable));
        return "adminImgTxt";
    }

    @RequestMapping("/admin/imgTxt/search")
    public String adminImgTxtSearch(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "desc") String order,
                                    @RequestParam(defaultValue = "create_date") String sort,
                                    @RequestParam String searchContent,Model model){
        model.addAttribute("articles",searchService.searchArticle(page-1, order, sort, searchContent,null));
        return "adminImgTxt";
    }
}
