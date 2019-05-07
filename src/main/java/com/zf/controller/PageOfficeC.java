package com.zf.controller;

import com.zf.pojo.File;
import com.zf.pojo.User;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PageOfficeC {

    private Integer filelevel;

    @RequestMapping("word")
    public Object word(HttpServletRequest request, HttpSession session, String filepath,Integer filelevel){
        this.filelevel = filelevel;
        User loginUser = (User) session.getAttribute("loginUser");
        Map<String,Object> map = new HashMap<>();
        //如果不是登陆态
        if (loginUser == null){
            map.put("success",false);
            map.put("message","登陆态已失效，请重新登陆");
            return map;
        }
        PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
        request.setAttribute("poCtrl",poCtrl);
        //设置服务器页面
        poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");
         //添加自定义按钮
        poCtrl.addCustomToolButton("保存", "Save", 1);
        poCtrl.addCustomToolButton("关闭", "Close", 21);

        poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
        //设置保存页面
        poCtrl.setSaveFilePage("save");
        //打开Word文档
        String replace = filepath.replace(" ", "+");
        request.getSession().setAttribute("path",replace);
        poCtrl.webOpen("/dataResourceImages/" + replace, OpenModeType.docNormalEdit, loginUser.getUsername());
        return "/word.jsp";
    }
    @RequestMapping("save")
    public void save(HttpServletRequest request, HttpServletResponse response){

        FileSaver fs=new FileSaver(request,response);
        String path = (String) request.getSession().getAttribute("path");
        fs.saveToFile("D://KDR//"+path);
        fs.close();

    }

}
