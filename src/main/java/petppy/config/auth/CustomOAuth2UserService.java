package petppy.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import petppy.config.auth.dto.OAuthAttributes;
import petppy.domain.user.Membership;
import petppy.domain.user.User;
import petppy.dto.user.UserDTO;
import petppy.repository.user.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

/**
 * 소셜 로그인 Service
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new UserDTO(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        /*User user = userRepository.findByEmailAndType(attributes.getEmail(), attributes.getType()).map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());*/

        Optional<User> result = userRepository.findByEmailAndType(attributes.getEmail(), attributes.getType());

        if (result.isPresent()) {
            User findUser = result.get();
            findUser.update(attributes.getName(), attributes.getPicture());
            return findUser;
        } else {
            User user = attributes.toEntity();
            user.addMembership(Membership.builder().user(user).build());
            User savedUser = userRepository.save(user);

            return savedUser;
        }


    }

}
