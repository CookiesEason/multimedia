package com.example.multimedia.filter;

/**
 * @author CookiesEason
 * 2018/09/20 11:02
 */
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SensitiveWordFiler {
    private StringBuilder replaceAll;
    private String encoding = "UTF-8";
    private String replceStr = "*";
    private int replceSize = 500;
    private String fileName = "CensorWords.txt";
    private List<String> arrayList;
    public static Set<String> sensitiveWordSet;
    public static List<String> sensitiveWordList;

    /**
     * 文件要求路径在src或resource下，默认文件名为CensorWords.txt
     * @param fileName 词库文件名(含后缀)
     */
    public SensitiveWordFiler(String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * @param replceStr 敏感词被转换的字符
     * @param replceSize 初始转义容量
     */
    public SensitiveWordFiler(String replceStr,int replceSize)
    {
        this.replceStr = fileName;
        this.replceSize = replceSize;
    }

    public SensitiveWordFiler()
    {
    }

    /**
     * @param str 将要被过滤信息
     * @return 过滤后的信息
     */
    public String filterInfo(String str)
    {  	sensitiveWordSet = new HashSet<String>();
        sensitiveWordList= new ArrayList<>();
        StringBuilder buffer = new StringBuilder(str);
        HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>(arrayList.size());
        String temp;
        for(int x = 0; x < arrayList.size();x++)
        {
            temp = arrayList.get(x);
            int findIndexSize = 0;
            for(int start = -1;(start=buffer.indexOf(temp,findIndexSize)) > -1;)
            {
                //System.out.println("###replace="+temp);
                findIndexSize = start+temp.length();//从已找到的后面开始找
                Integer mapStart = hash.get(start);//起始位置
                if(mapStart == null || (mapStart != null && findIndexSize > mapStart))//满足1个，即可更新map
                {
                    hash.put(start, findIndexSize);
                    //System.out.println("###敏感词："+buffer.substring(start, findIndexSize));
                }
            }
        }
        Collection<Integer> values = hash.keySet();
        for(Integer startIndex : values)
        {
            Integer endIndex = hash.get(startIndex);
            //获取敏感词，并加入列表，用来统计数量
            String sensitive = buffer.substring(startIndex, endIndex);
            //System.out.println("###敏感词："+sensitive);
            if (!sensitive.contains("*")) {//添加敏感词到集合
                sensitiveWordSet.add(sensitive);
                sensitiveWordList.add(sensitive);
            }
            buffer.replace(startIndex, endIndex, replaceAll.substring(0,endIndex-startIndex));
        }
        hash.clear();
        return buffer.toString();
    }
    /**
     *   初始化敏感词库
     */
    public void InitializationWork()
    {
        replaceAll = new StringBuilder(replceSize);
        for(int x=0;x < replceSize;x++)
        {
            replaceAll.append(replceStr);
        }
        //加载词库
        arrayList = new ArrayList<String>();
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        try {
            Resource resource = new ClassPathResource("CensorWords.txt");
            read = new InputStreamReader(resource.getInputStream(),encoding);
            bufferedReader = new BufferedReader(read);
            for(String txt = null;(txt = bufferedReader.readLine()) != null;){
                if(!arrayList.contains(txt)){
                    arrayList.add(txt);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(null != bufferedReader) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null != read) {
                    read.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public StringBuilder getReplaceAll() {
        return replaceAll;
    }
    public void setReplaceAll(StringBuilder replaceAll) {
        this.replaceAll = replaceAll;
    }
    public String getReplceStr() {
        return replceStr;
    }
    public void setReplceStr(String replceStr) {
        this.replceStr = replceStr;
    }
    public int getReplceSize() {
        return replceSize;
    }
    public void setReplceSize(int replceSize) {
        this.replceSize = replceSize;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public List<String> getArrayList() {
        return arrayList;
    }
    public void setArrayList(List<String> arrayList) {
        this.arrayList = arrayList;
    }
    public String getEncoding() {
        return encoding;
    }
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public static void main(String[] args){
        long startNumer = System.currentTimeMillis();
        SensitiveWordFiler sw = new SensitiveWordFiler();
        sw.InitializationWork();
        //System.out.println("敏感词的数量：" + arrayList.size());
        String str = "sb";
        System.out.println("被检测字符串长度:"+str.length());
        str = sw.filterInfo(str);
        long endNumber = System.currentTimeMillis();
        sw.setArrayList(sensitiveWordList);
        System.out.println("语句中包含敏感词的个数为：" + sensitiveWordSet.size() + "。包含：" + sensitiveWordSet);
        System.out.println("语句中包含敏感词的个数为：" + sensitiveWordList.size() + "。包含：" + sensitiveWordList);
        System.out.println("总共耗时:"+(endNumber-startNumer)+"ms");
        System.out.println("替换后的字符串为:\n"+str);
    }
}
