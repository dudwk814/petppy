package petppy.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import petppy.service.user.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/membership")
public class MembershipController {

    private final UserService userService;

    @GetMapping("")
    public String membership() {

        return "membership/membership";
    }

    @ResponseBody
    @PatchMapping("/change/{userId}")
    public ResponseEntity<String> changeRating(@PathVariable("userId") Long userId, String rating) {

        userService.changeMembership(userId, rating);


        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
