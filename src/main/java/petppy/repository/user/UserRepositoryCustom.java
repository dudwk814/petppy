package petppy.repository.user;

import org.springframework.data.domain.Page;
import petppy.domain.user.User;
import petppy.dto.PageRequestDTO;
import petppy.dto.user.UserDTO;

public interface UserRepositoryCustom {

    Page<User> searchUser(PageRequestDTO pageRequestDTO);
}
