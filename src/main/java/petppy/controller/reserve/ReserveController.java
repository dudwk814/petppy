package petppy.controller.reserve;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petppy.dto.reserve.ReserveDTO;
import petppy.service.reserve.ReserveService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reserve/")
public class ReserveController {

    private final ReserveService reserveService;

    @PostMapping("/create")
    public ResponseEntity<ReserveDTO> create(@RequestBody ReserveDTO reserveDTO) {
        ReserveDTO result = reserveService.createReserve(reserveDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
