package petppy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petppy.dto.payment.PaymentDTO;
import petppy.service.payment.PaymentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/complete")
    public ResponseEntity<String> createPaymentInfo(@RequestBody PaymentDTO paymentDTO) {

        System.out.println("paymentDTO : " + paymentDTO);

        paymentService.createPaymentInfo(paymentDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
