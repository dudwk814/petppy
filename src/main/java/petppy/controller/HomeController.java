package petppy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

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

    @GetMapping("/QnA")
    public String QnA() {
        return "QnA";
    }

    @ResponseBody
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }


}
