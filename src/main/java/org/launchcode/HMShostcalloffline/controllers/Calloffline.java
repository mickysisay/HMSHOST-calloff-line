package org.launchcode.HMShostcalloffline.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="login")
public class Calloffline {
    static HashMap<String,String> loginInfo =new HashMap<>();
    @RequestMapping(value="",method = RequestMethod.GET)
    public String index(Model model){
      loginInfo.put("betty","password");
      loginInfo.put("michael","123456789");

      return "calloffline/index";
    };
    @RequestMapping(value="welcome")
    public String  handleLogin(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam String username, @RequestParam String password){
      String  whatToDisplay ="calloffline/index";
        Cookie[] cookies = request.getCookies();
      //for(Map.Entry<String,String> entry : loginInfo.entrySet()){
            if(loginInfo.get(username).equals(password)){
                if(cookies != null){
                    for(Cookie cookie : cookies){
                        if(cookie.getName().equals(username)){

                        }
                    }
                }else{
                    response.addCookie(new Cookie("user",username));

                }
                model.addAttribute("name",username);
                whatToDisplay = "callOffline/firstpage";
            }
        //}
       model.addAttribute("noLoginData","sorry,username or password is incorrect");
        return whatToDisplay;
    }

}
