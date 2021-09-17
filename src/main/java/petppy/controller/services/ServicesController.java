package petppy.controller.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/services")
public class ServicesController {

    @GetMapping("")
    public String services() {

        return "services/serviceList";
    }
}
