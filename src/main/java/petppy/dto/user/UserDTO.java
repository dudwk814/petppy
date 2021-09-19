package petppy.dto.user;

import lombok.*;
import petppy.domain.user.Role;
import petppy.domain.user.Type;
import petppy.domain.user.User;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Long id;
    private String email;
    private String password;
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
                .rating(user.getMembership().getRating())
                .id(user.getMembership().getId()).build();

        if (user.getAddress() != null) {
            this.postcode = user.getAddress().getPostcode();
            this.roadAddress = user.getAddress().getRoadAddress();
            this.jibunAddress = user.getAddress().getJibunAddress();
            this.detailAddress = user.getAddress().getDetailAddress();
            this.extraAddress = user.getAddress().getExtraAddress();
        }

    }

}