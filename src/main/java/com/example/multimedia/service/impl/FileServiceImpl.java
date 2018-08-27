package com.example.multimedia.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.multimedia.properties.TengXunProperties;
import com.example.multimedia.service.FileService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author CookiesEason
 * 2018/07/25 14:21
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private TengXunProperties tengXunProperties;

    @Override
    public ResultVo uploadFile(MultipartFile multipartFile){
        if (multipartFile.isEmpty()){
            return ResultVoUtil.error(0,"文件不能为空");
        }
        String oldFileName = multipartFile.getOriginalFilename();
        String eName = oldFileName.substring(oldFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID()+eName;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH)+1;
        int day=cal.get(Calendar.DATE);
        COSCredentials cred = new BasicCOSCredentials(tengXunProperties.getAccessKey(), tengXunProperties.getSecretKey());
        ClientConfig clientConfig = new ClientConfig(new Region(tengXunProperties.getBucket()));
        COSClient cosClient = new COSClient(cred,clientConfig);
        String bucketName = tengXunProperties.getBucketName();
        try {
            File localFile = File.createTempFile("temp", null);
            multipartFile.transferTo(localFile);
            String key = "/"+year+"/"+month+"/"+day+"/"+newFileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            return ResultVoUtil.success(tengXunProperties.getPath()+putObjectRequest.getKey());
        }catch (IOException e){
           e.printStackTrace();
        }
        finally {
            cosClient.shutdown();
        }
        return ResultVoUtil.error(0,"发生错误,请稍后重试");
    }

    @Override
    public JSONObject uploadWangFile(MultipartFile multipartFile) {
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        if (multipartFile.isEmpty()){
            jsonObject.put("errno",1);
            return jsonObject;
        }
        String oldFileName = multipartFile.getOriginalFilename();
        String eName = oldFileName.substring(oldFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID()+eName;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH)+1;
        int day=cal.get(Calendar.DATE);
        COSCredentials cred = new BasicCOSCredentials(tengXunProperties.getAccessKey(), tengXunProperties.getSecretKey());
        ClientConfig clientConfig = new ClientConfig(new Region(tengXunProperties.getBucket()));
        COSClient cosClient = new COSClient(cred,clientConfig);
        String bucketName = tengXunProperties.getBucketName();
        try {
            File localFile = File.createTempFile("temp", null);
            multipartFile.transferTo(localFile);
            String key = "/"+year+"/"+month+"/"+day+"/"+newFileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            jsonObject.put("errno",0);
            jsonArray.add(tengXunProperties.getPath()+putObjectRequest.getKey());
            jsonObject.put("data",jsonArray);
            return jsonObject;
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            cosClient.shutdown();
        }
        jsonObject.put("errno",1);
        return jsonObject;
    }
}
