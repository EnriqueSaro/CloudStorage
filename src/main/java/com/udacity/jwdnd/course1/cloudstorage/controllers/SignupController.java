package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/signup")
public class SignupController {

    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView(){
        return "signup";
    }
    @PostMapping
    public String registerUser(@ModelAttribute User user, Model model){
        String signup_error =null;

        if (!userService.isUsernameAvailable(user.getUsername()))
            signup_error = "Username already exists, please choose other.";

        if (signup_error == null){
            Integer rows_added = userService.createUser(user);
            if (rows_added < 0)
                signup_error = "Something happened, try to sign up again";
        }
        if (signup_error == null)
            model.addAttribute("signupSuccess",true);
        else
            model.addAttribute("signupError",signup_error);

        return "signup";
    }

}
