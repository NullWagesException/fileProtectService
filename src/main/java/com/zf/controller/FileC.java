package com.zf.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zf.pojo.File;
import com.zf.pojo.User;
import com.zf.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;


@Controller
@RequestMapping("file")
public class FileC {

    @Autowired
    private IFileService fileService;

    @RequestMapping(value = "upload",method = RequestMethod.POST)
    @ResponseBody
    public Object upload(MultipartFile file, Integer filelevel, HttpSession session){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        User loginUser = (User) session.getAttribute("loginUser");
        Map<String,Object> map = new HashMap<>();
        //如果不是登陆态
        if (loginUser == null){
            map.put("success",false);
            map.put("message","登陆态已失效，请重新登陆");
            return map;
        }
        try {
            inputStream = file.getInputStream();
            byte[] bytes = new byte[1024];
            Date date = new Date();
            //加入时间戳，保证唯一性并加密文件名
            String filepath = "D:\\KDR\\" + "___" + date.getTime() + "___" + file.getOriginalFilename();
            outputStream = new FileOutputStream(filepath);
            int len = -1;
            while ((len = inputStream.read(bytes)) != -1) {
                //写入磁盘
                outputStream.write(bytes);
            }
            //写入完成后存入文件信息到数据库
            File filemessage = new File();
            filemessage.setDate(new Date());
            filemessage.setFilelevel(filelevel);
            filemessage.setFilepath(filepath);

            filemessage.setUsername(loginUser.getUsername());
            fileService.insert(filemessage);

        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("message","文件写入失败");
            return map;
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        map.put("success",true);
        map.put("message","文件提交成功");
        return map;
    }

    @RequestMapping("getAll")
    @ResponseBody
    public Object getAll(Integer page,Integer rows){

        Map<String, Object> data = new HashMap<>();
        if (rows == null)
            rows = 10;
        Page<Object> objects = PageHelper.startPage(page, rows);
        List<File> all = fileService.getAll();
        for (File file : all) {
            file.setFilename(file.getFilepath().split("___")[2]);
        }
        data.put("total", objects.getTotal());
        data.put("nowPage", page);
        data.put("rows", all);
        return data;
    }

    @RequestMapping("check")
    @ResponseBody
    public Object check(HttpSession session,Integer id){

        User loginUser = (User) session.getAttribute("loginUser");
        File file = new File();
        file.setId(id);
        File file1 = fileService.get(file);
        Map<String,Object> map = new HashMap<>();
        //如果不是登陆态
        if (loginUser == null){
            map.put("success",false);
            map.put("message","登陆态已失效，请重新登陆");
            return map;
        }
        //如果用户等级比文件等级低
        if (loginUser.getLevel() > file1.getFilelevel()){
            map.put("success",false);
            map.put("message","权限不足");
            return map;
        }
        map.put("success",true);
        map.put("filepath",file1.getFilepath().split("___")[1] + "___"+ file1.getFilepath().split("___")[2]);
        return map;

    }

    @RequestMapping("delete")
    @ResponseBody
    public Object delete(HttpSession session,Integer id){

        User loginUser = (User) session.getAttribute("loginUser");
        File file = new File();
        file.setId(id);
        File file1 = fileService.get(file);
        Map<String,Object> map = new HashMap<>();
        //如果不是登陆态
        if (loginUser == null){
            map.put("success",false);
            map.put("message","登陆态已失效，请重新登陆");
            return map;
        }
        //如果用户等级比文件等级低
        if (loginUser.getLevel() > file1.getFilelevel()){
            map.put("success",false);
            map.put("message","权限不足");
            return map;
        }

        java.io.File Dfile = new java.io.File(file1.getFilepath());
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (Dfile.exists() && Dfile.isFile()) {
            if (Dfile.delete()) {
                fileService.delete(id);
                map.put("success",false);
                map.put("message","删除成功");
                return map;
            } else {
                map.put("success",false);
                map.put("message","删除失败！");
                return map;
            }
        } else {
            map.put("success",false);
            map.put("message","文件不存在！");
            return map;
        }

    }


    @RequestMapping("upfile")
    @ResponseBody
    public Object upfile(Integer filelevel,Integer id){
        try {
            File file = new File();
            file.setId(id);
            file = fileService.get(file);
            file.setFilelevel(filelevel);
            fileService.update(file);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
