package petppy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {

        return "home";
    }

    @GetMapping("/loginForm")
    public String loginForm() {

        return "login/loginForm";
    }


    @GetMapping("/gallery")
    public String gallery() {
        return "gallery";
    }

    @GetMapping("/vet")
    public String ver() {
        return "vet";
    }


}
