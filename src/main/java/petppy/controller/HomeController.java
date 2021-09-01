package petppy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import petppy.dto.UserDTO;

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
