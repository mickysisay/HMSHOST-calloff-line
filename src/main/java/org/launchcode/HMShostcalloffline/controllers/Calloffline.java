package org.launchcode.HMShostcalloffline.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="login")
public class Calloffline {
    static HashMap<String,String> loginInfo =new HashMap<>();
    static HashMap<String, String[]> peopleInfo = new HashMap<>();
    @RequestMapping(value="welcome",method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response,Model model){
        loginInfo.put("betty","password");
      loginInfo.put("michael","123456789");

      peopleInfo.put("betty",new String[] {"Employee","no"});
      peopleInfo.put("michael",new String[] {"Manager","no"});
      String whatToDisplay = "calloffline/index";
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie : cookies){

                if(cookie.getName().equals("user")){
                    model.addAttribute("name", cookie.getValue());
                    model.addAttribute("calloffs" , "hello");
                    whatToDisplay = "calloffline/firstpage";
                    break;
                }
            }
        }
      return whatToDisplay;
    };
    @RequestMapping(value="welcome" , method = RequestMethod.POST)
    public String  handleLogin(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam String username, @RequestParam String password){
      String  whatToDisplay ="redirect:/login/welcome";
        Cookie[] cookies = request.getCookies();
      //for(Map.Entry<String,String> entry : loginInfo.entrySet()){
        try{
        if(loginInfo.get(username).equals(password)){
                Cookie newUser = new Cookie ("user",username);
                    response.addCookie(newUser);
                    newUser.setPath("/login");
                    model.addAttribute("name",username);

                if(peopleInfo.get(username)[0].equals("manager")){
                whatToDisplay = "calloffline/manager";
                }else if(peopleInfo.get(username)[0].equals("employee")){
                whatToDisplay ="calloffline/employee";
                model.addAttribute("calloffs",peopleInfo.get(username)[1]);
                }
                    //whatToDisplay = "calloffline/firstpage";
           }}catch(NullPointerException e){

        }

        //}
       model.addAttribute("noLoginData","sorry,username or password is incorrect");
        return whatToDisplay;
    }
    @RequestMapping(value = "logout",method = RequestMethod.POST)

    public String logout(Model model,HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();

        Cookie userNameCookieRemove = new Cookie("user", "hello");
        userNameCookieRemove.setPath("/login");
        userNameCookieRemove.setMaxAge(0);

        response.addCookie(userNameCookieRemove);
        model.addAttribute("name","gfg");
        return "redirect:/login/welcome";
    }
    @RequestMapping(value = "calloff", method = RequestMethod.POST)
    public String callOff(){

        return "redirect:/login/welcome";
    }
}
