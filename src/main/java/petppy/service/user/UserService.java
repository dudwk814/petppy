package petppy.service.user;

import org.springframework.data.domain.Pageable;
import petppy.domain.user.Type;
import petppy.domain.user.User;
import petppy.domain.user.Rating;
import petppy.dto.user.MembershipDTO;
import petppy.dto.user.UserDTO;

import java.util.List;

public interface UserService {

    public String joinedMember(UserDTO dto);

    public void ModifyUserAddress(UserDTO dto);

    public void deleteMember(String email);

    public UserDTO findByEmail(String email);

    public UserDTO findByEmailAndType(String email, Type type);

    public List<UserDTO> findMemberListByName(String name, Pageable pageable);

    public List<UserDTO> findAll(Pageable pageable);

    public void changeMembership(UserDTO userDTO, Rating rating);

    public MembershipDTO findMembership(Long userId);

    public boolean checkMemberIdExist(UserDTO userDTO);

    default User dtoToEntity(UserDTO dto) {
        User user = User.builder()
                .email(dto.getEmail())
                .picture(dto.getPicture())
                .name(dto.getName())
                .type(dto.getType())
                .role(dto.getRole())
                .password(dto.getPassword())
                .build();

        return user;
    }

    default UserDTO entityToDto(User user) {
        UserDTO userDTO = UserDTO.builder().user(user)

                .build();

        return userDTO;
    }
}
