package petppy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/membership")
public class MembershipController {

    @GetMapping("")
    public String membership() {

        return "/membership/membership";
    }
}
