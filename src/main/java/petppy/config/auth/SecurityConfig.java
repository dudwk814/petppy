package petppy.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.
                csrf()
                    .disable()
                .headers()
                    .frameOptions()
                        .disable()
                .and()

                .authorizeRequests()
                    .antMatchers("/")
                        .permitAll()
                    .antMatchers(
                            "/user",
                            "/user/modifyForm",
                            "/user/dashboard",
                            "/board/edit")
                        .hasAnyRole("MEMBER", "ADMIN")
                .and()
                .formLogin()
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .failureHandler(new LoginFailureHandler())
                    .loginPage("/loginForm")
                    .loginProcessingUrl("/login")
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService);

    }
}
