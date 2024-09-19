package awsGroup.awsPractice.com.sikdan.config.auth;

import awsGroup.awsPractice.com.sikdan.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) //h2 console을 사용해서 disable
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll() //해당 url 및 static파일은 전체 열람권한
                        .requestMatchers("/api/v1/**").hasRole(Role.USER.name()) //해당 url은 USER권한을 가진 사람만 접근가능
                        .anyRequest().authenticated() //그 외의 url은 인증된(Authenticated) 사람만 가능
                )
                .logout(logout -> logout.logoutSuccessUrl("/")
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService))
                );
        return http.build();
    }}
