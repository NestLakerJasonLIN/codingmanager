package com.yanwenl.codingmanager.controller;

import com.yanwenl.codingmanager.model.User;
import com.yanwenl.codingmanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@Slf4j
public class LoginController extends BaseController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public String registration(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createNewUser(Model model,
                                @Valid User user,
                                BindingResult bindingResult) {
        User userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "User name already used");
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            user.setId(0);
            userService.saveUser(user);
            model.addAttribute("successMessage", "Registered successfully");
            model.addAttribute("user", new User());
            return "registration";
        }
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public String adminHome(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getUserName());
        model.addAttribute("adminMessage","Content Available Only for Users with Admin Role");
        return "admin/home";
    }

    @RequestMapping(value="/access-denied", method = RequestMethod.GET)
    public String showAccessDenied() {
        return "access-denied";
    }
}
