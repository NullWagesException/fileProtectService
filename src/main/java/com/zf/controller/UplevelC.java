package com.zf.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zf.pojo.Uplevel;
import com.zf.pojo.User;
import com.zf.service.IUplevelService;
import com.zf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("uplevel")
public class UplevelC {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUplevelService uplevelService;

    @RequestMapping("uplevel")
    @ResponseBody
    public Object uplevel(Integer filelevel, HttpSession session){
        try {
            Uplevel uplevel = new Uplevel();
            User loginUser = (User) session.getAttribute("loginUser");
            uplevel.setDate(new Date());
            uplevel.setUsername(loginUser.getUsername());
            uplevel.setOldlevel(loginUser.getLevel());
            uplevel.setNewlevel(filelevel);
            uplevel.setAllow(0);
            uplevel.setUserid(loginUser.getId());
            uplevelService.insert(uplevel);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @RequestMapping("checklevel")
    @ResponseBody
    public Object checklevel(HttpSession session,Integer id){
        Uplevel uplevel = new Uplevel();
        uplevel.setId(id);
        uplevel = uplevelService.get(uplevel);
        User loginUser = (User) session.getAttribute("loginUser");
        User checkUser = new User();
        checkUser.setId(uplevel.getUserid());
        checkUser = userService.getID(checkUser);
        Map<String,Object> map = new HashMap<>();
        //如果不是登陆态
        if (loginUser == null){
            map.put("success",false);
            map.put("message","登陆态已失效，请重新登陆");
            return map;
        }
        //如果用户等级比文件等级低
        if (loginUser.getLevel()+1  > checkUser.getLevel()){
            map.put("success",false);
            map.put("message","权限不足");
            return map;
        }
        map.put("success",true);
        return map;

    }

    @RequestMapping("allow")
    @ResponseBody
    public Object allow(Integer id, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        try {
            Uplevel uplevel = new Uplevel();
            uplevel.setId(id);
            uplevel = uplevelService.get(uplevel);
            uplevel.setAllow(1);
            User user = new User();
            user.setId(uplevel.getUserid());
            user = userService.getID(user);
            user.setLevel(uplevel.getNewlevel());
            userService.update(user);
            uplevelService.update(uplevel);
            map.put("success",true);
            map.put("message","同意成功");
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",true);
            map.put("message","同意失败");
            return map;
        }
    }

    @RequestMapping("notallow")
    @ResponseBody
    public Object notallow(Integer id, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        try {
            Uplevel uplevel = new Uplevel();
            uplevel.setId(id);
            uplevel = uplevelService.get(uplevel);
            uplevel.setAllow(2);
            uplevelService.update(uplevel);
            map.put("success",true);
            map.put("message","拒绝成功");
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",true);
            map.put("message","拒绝失败");
            return map;
        }
    }

    @RequestMapping("getAll")
    @ResponseBody
    public Object getAll(Integer page,Integer rows){

        Map<String, Object> data = new HashMap<>();
        if (rows == null)
            rows = 10;
        Page<Object> objects = PageHelper.startPage(page, rows);
        List<Uplevel> all = uplevelService.getAll();
        data.put("total", objects.getTotal());
        data.put("nowPage", page);
        data.put("rows", all);
        return data;
    }

}
