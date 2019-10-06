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
   // static HashMap<String,String> loginInfo =new HashMap<>();
    static HashMap<String, String[]> peopleInfo = new HashMap<>();
    static String error;
    static{
//        loginInfo.put("000000","password");
//        loginInfo.put("111111","123456789");
//        loginInfo.put("222222","1");
        peopleInfo.put("000000",new String[] {"Betty","password","employee","no"});
        peopleInfo.put("111111",new String[] {"Michael","123456789","manager","no"});
        peopleInfo.put("222222",new String[]{"Rob","1","employee","yes"});
    }

    @RequestMapping(value="welcome",method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response,Model model){

      String whatToDisplay = "calloffline/index";
        Cookie[] cookies = request.getCookies();
      String usernameCookie="";
      String passwordCookie="";

        if(cookies != null){
            for(Cookie cookie : cookies){

                if(cookie.getName().equals("hmshostuser")){
                    usernameCookie = cookie.getValue();

                   //  whatToDisplay = "calloffline/firstpage";

                }
                if(cookie.getName().equals("hmshostpassword")){
                    passwordCookie = cookie.getValue();
                    model.addAttribute("noLoginData",passwordCookie);
                }
            }try{
            if(peopleInfo.get(usernameCookie)[1].equals(passwordCookie)){
                model.addAttribute("name", peopleInfo.get(usernameCookie)[0]);
                model.addAttribute("calloffs" , peopleInfo.get(usernameCookie)[3]);

                if(peopleInfo.get(usernameCookie)[2].equals("manager")){
                    model.addAttribute("employee" , getCalloffs());
                    whatToDisplay = "calloffline/manager";
                }else if(peopleInfo.get(usernameCookie)[2].equals("employee")){
                    whatToDisplay = "calloffline/employee";
                }
            }}catch(NullPointerException e){

            }
        }
      return whatToDisplay;
    };
    public static ArrayList<String[]> getCalloffs(){
        ArrayList<String[]> info = new ArrayList<>();
        for(Map.Entry<String,String[]> me:peopleInfo.entrySet()){
            if(me.getValue()[2].equals("employee")) {
                info.add(new String[] {me.getValue()[0],me.getValue()[3]});
            }
        }
        return info;
    }
    @RequestMapping(value="welcome" , method = RequestMethod.POST)
    public String  handleLogin(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam String username, @RequestParam String password){
      String  whatToDisplay ="redirect:/login/welcome";
        Cookie[] cookies = request.getCookies();
      //for(Map.Entry<String,String> entry : loginInfo.entrySet()){
        try{
        if(peopleInfo.get(username)[1].equals(password)){
                Cookie newUser = new Cookie ("hmshostuser",username);
                    response.addCookie(newUser);
                    newUser.setPath("/login");
            Cookie newUserPassword = new Cookie ("hmshostpassword",password);
            response.addCookie(newUserPassword);
            newUserPassword.setPath("/login");
                    model.addAttribute("name",peopleInfo.get(username)[0]);

                if(peopleInfo.get(username)[2].equals("manager")){

                   model.addAttribute("employee" , getCalloffs());
                    whatToDisplay = "calloffline/manager";
                }else if(peopleInfo.get(username)[2].equals("employee")){
                whatToDisplay ="calloffline/employee";
                model.addAttribute("calloffs",peopleInfo.get(username)[3]);
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

        Cookie userNameCookieRemove = new Cookie("hmshostuser", "hello");
        userNameCookieRemove.setPath("/login");
        userNameCookieRemove.setMaxAge(0);
        Cookie userNameCookieRemove2 = new Cookie("hmshostpassword", "remove");
        userNameCookieRemove2.setPath("/login");
        userNameCookieRemove2.setMaxAge(0);
        response.addCookie(userNameCookieRemove);
        model.addAttribute("name","gfg");
        return "redirect:/login/welcome";
    }
    @RequestMapping(value = "calloff", method = RequestMethod.POST)
    public String callOff(Model model,HttpServletRequest request, HttpServletResponse response){
         Cookie[] cookies = request.getCookies();
         for(Cookie cookie : cookies){

             if(cookie.getName().equals("hmshostuser")){
                 peopleInfo.get(cookie.getValue())[3] =  peopleInfo.get(cookie.getValue())[3].equals("yes") ? "no" : "yes";
             }
         }
        return "redirect:/login/welcome";
    }
}
