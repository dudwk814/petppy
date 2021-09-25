package petppy.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import petppy.config.auth.dto.LoginUserDTO;
import petppy.domain.user.Role;
import petppy.domain.user.User;
import petppy.repository.user.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import static petppy.domain.user.Type.NORMAL;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> result = userRepository.findByEmailAndType(username, NORMAL);

        if (!result.isPresent()) {
            throw new UsernameNotFoundException("Check Email or Type");
        }

        User user = result.get();

        log.info(user);

        HashSet<Role> roles = new HashSet<>();
        roles.add(user.getRole());

        LoginUserDTO loginUserDTO = new LoginUserDTO(
                user.getEmail(),
                user.getPassword(),
                user.getId(),
                user.getType(),
                user.getRole(),
                roles.stream().map(role -> new SimpleGrantedAuthority(user.getRoleKey())).collect(Collectors.toList())
        );

        loginUserDTO.setName(user.getName());
        loginUserDTO.setAddress(user.getAddress());

        httpSession.setAttribute("user", loginUserDTO);
        httpSession.setAttribute("userEmail", user.getEmail());

        return loginUserDTO;
    }
}
