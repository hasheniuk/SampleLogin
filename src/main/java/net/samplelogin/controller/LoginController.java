package net.samplelogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/")
    public ModelAndView getLoginView() {
        return new ModelAndView("login");
    }
}
