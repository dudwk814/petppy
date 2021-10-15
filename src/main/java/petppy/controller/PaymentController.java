package petppy.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import petppy.dto.payment.PaymentDTO;
import petppy.service.payment.PaymentService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/")
public class PaymentController {

    private final PaymentService paymentService;

    // 아임포트 rest client
    private final IamportClient client = new IamportClient(
            "7733026119266777",
            "d3963eba4c98a0bdb20141af59d4bb89d44b9e6a0ee2ddf6c70dfc2a1104d8aabbfcf68daca9a2bb");

    /**
     * 아임포트 결제 검증 메서드
     * @param model
     * @param locale
     * @param session
     * @param imp_uid
     * @return
     * @throws IamportResponseException
     * @throws IOException
     */
    @RequestMapping(value="/verifyIamport/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(
            Model model
            , Locale locale
            , HttpSession session
            , @PathVariable(value= "imp_uid") String imp_uid) throws IamportResponseException, IOException
    {
        return client.paymentByImpUid(imp_uid);
    }

    /**
     * 결제 메서드
     * @param paymentDTO
     * @return
     */
    @PostMapping("/complete")
    public ResponseEntity<String> createPaymentInfo(@RequestBody PaymentDTO paymentDTO) {

        System.out.println("paymentDTO : " + paymentDTO);

        paymentService.createPaymentInfo(paymentDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // Access 토큰 발급
    @RequestMapping("/accessToken/{imp_uid}")
    public IamportResponse<AccessToken> getAccessToken(@PathVariable("imp_uid") String imp_uid) throws IamportResponseException, IOException {

        System.out.println("imp_uid = " + imp_uid);
        return client.getAuth();
    }

    // 이메일로 결제 정보 조회
    @RequestMapping("/{email}")
    public ResponseEntity<PaymentDTO> findPaymentByEmail(@PathVariable("email") String email) {

        PaymentDTO result = paymentService.findPaymentByEmail(email);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 아임포트 서버에 결제 취소 요청 후 DB에 해당 payment_status cancel로 변경
     * @param imp_uid
     * @param price
     * @return
     * @throws IamportResponseException
     * @throws IOException
     */
    @RequestMapping("/cancel/{imp_uid}")
    public IamportResponse<Payment> cancel(@PathVariable("imp_uid") String imp_uid, long price) throws IamportResponseException, IOException {
        CancelData cancelData = new CancelData(imp_uid, true);
        cancelData.setChecksum(BigDecimal.valueOf(price));

        paymentService.cancel(imp_uid);


        return client.cancelPaymentByImpUid(cancelData);
    }
}
