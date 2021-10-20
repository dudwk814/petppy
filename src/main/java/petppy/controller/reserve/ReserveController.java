package petppy.controller.reserve;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import petppy.domain.reserve.ReserveType;
import petppy.dto.PageRequestDTO;
import petppy.dto.reserve.ReserveDTO;
import petppy.dto.user.UserDTO;
import petppy.service.reserve.ReserveService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reserve/")
public class ReserveController {

    private final ReserveService reserveService;

    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/create", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReserveDTO> create(@RequestBody ReserveDTO reserveDTO) {
        ReserveDTO result = reserveService.createReserve(reserveDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/reserveList")
    public String reserveList(ReserveDTO reserveDTO, PageRequestDTO pageRequestDTO,  HttpSession session, Model model) {
        String userEmail = (String)session.getAttribute("userEmail");

        reserveDTO.setEmail(userEmail);

        model.addAttribute("reserveList", reserveService.findReserveList(reserveDTO, pageRequestDTO));

        return "/reserve/reserveList";
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @PostMapping("/cancel")
    public ResponseEntity<Boolean> cancel(ReserveDTO reserveDTO) {

        reserveService.cancelReserve(reserveDTO);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
