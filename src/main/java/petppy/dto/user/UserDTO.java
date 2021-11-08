package petppy.dto.user;

import lombok.*;
import petppy.domain.user.Role;
import petppy.domain.user.Type;
import petppy.domain.user.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Long id;

    @Email(message = "이메일 형식을 맞춰주세요.")
    @NotBlank(message = "필수 입력 항목입니다.")
    private String email;

    @Size(min = 4, max = 16, message = "비밀번호를 4 ~ 16글자 사이로 입력해주세요.")
    private String password;

    @Size(min = 2, max = 10, message = "이름을 2 ~ 10글자 사이로 입력해주세요.")
    private String name;

    private String picture;
    private LocalDateTime joinedDate;
    private LocalDateTime modifiedDate;
    private Integer postcode;   // 우편번호
    private String roadAddress; // 도로명 주소
    private String jibunAddress;    // 지번 주소
    private String detailAddress;   // 상세 주소
    private String extraAddress;    // 참고사항
    private Role role;
    private Type type;
    private MembershipDTO membershipDTO;

    @Builder
    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.picture = user.getPicture();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.joinedDate = user.getCreatedDate();
        this.type = user.getType();
        this.membershipDTO = MembershipDTO.builder()
                .createdDate(user.getMembership().getCreatedDate())
                .modifiedDate(user.getMembership().getModifiedDate())
                .rating(user.getMembership().getRating())
                .id(user.getMembership().getId())
                .dogWalkCount(user.getMembership().getDogWalkCount())
                .petGroomingCount(user.getMembership().getPetGroomingCount())
                .vetVisitCount(user.getMembership().getVetVisit())
                .build();

        if (user.getAddress() != null) {
            this.postcode = user.getAddress().getPostcode();
            this.roadAddress = user.getAddress().getRoadAddress();
            this.jibunAddress = user.getAddress().getJibunAddress();
            this.detailAddress = user.getAddress().getDetailAddress();
            this.extraAddress = user.getAddress().getExtraAddress();
        }

    }

}
