package petppy.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@ToString
public class Address {

    private Integer postcode;   // 우편번호
    private String roadAddress; // 도로명 주소
    private String jibunAddress;    // 지번 주소
    private String detailAddress;   // 상세 주소
    private String extraAddress;    // 참고사항

    @Builder
    public Address(Integer postcode, String roadAddress, String jibunAddress, String detailAddress, String extraAddress) {
        this.postcode = postcode;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.detailAddress = detailAddress;
        this.extraAddress = extraAddress;
    }
}
