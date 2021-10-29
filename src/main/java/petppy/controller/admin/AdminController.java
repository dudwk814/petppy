package petppy.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    @GetMapping("")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public String adminPage() {

        return "admin/adminPage";
    }
}
