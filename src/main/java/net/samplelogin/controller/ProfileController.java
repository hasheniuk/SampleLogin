package net.samplelogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    //TODO logout

    @GetMapping
    public String getView() {
        return Views.PROFILE;
    }
}
