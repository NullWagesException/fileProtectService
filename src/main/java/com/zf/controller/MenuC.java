package com.zf.controller;

import com.zf.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("menu")
public class MenuC {

    @RequestMapping("getMenuTrees")
    @ResponseBody
    public Object getMenuTrees(HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        Map<String,Object> map = new HashMap<>();
        map.put("type",loginUser.getLevel());
        return map;
    }

}
