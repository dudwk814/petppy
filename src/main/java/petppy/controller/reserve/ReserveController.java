package petppy.controller.reserve;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import petppy.domain.reserve.ReserveType;
import petppy.dto.reserve.ReserveDTO;
import petppy.dto.user.UserDTO;
import petppy.service.reserve.ReserveService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reserve/")
public class ReserveController {

    private final ReserveService reserveService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/create", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReserveDTO> create(@RequestBody ReserveDTO reserveDTO) {
        ReserveDTO result = reserveService.createReserve(reserveDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*@PreAuthorize("isAuthenticated()")
    @ResponseBody
    @GetMapping(value = "/reserveList")
    public ResponseEntity<List<ReserveDTO>> reserveList(@RequestBody ReserveDTO reserveDTO, HttpSession session) {
        String userEmail = (String)session.getAttribute("userEmail");


    }*/
}
