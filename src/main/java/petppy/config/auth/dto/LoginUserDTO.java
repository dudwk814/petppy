package petppy.config.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import petppy.domain.Address;
import petppy.domain.user.Type;

import java.util.Collection;

@Getter
@Setter
@ToString
public class LoginUserDTO extends User {

    private String email;
    private String name;
    private Address address;
    private Type type;
    private Long id;

    public LoginUserDTO(String username, String password, Long id, Type type, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.type = type;
        this.id = id;
    }

}
