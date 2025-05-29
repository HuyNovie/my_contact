//Chứa các file cấu hình, như bảo mật (SecurityConfig), Swagger, CORS, các Bean.

package com.example.mycontact.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //đánh dấu class là nơi khai báo các @Bean – Spring sẽ scan và khởi tạo khi chạy.
@EnableWebSecurity// kích hoạt Spring Security, thay thế cấu hình mặc định.
@EnableMethodSecurity(jsr250Enabled = true)
public class WebConfiguration implements WebMvcConfigurer {



    //Cấu hình hỗ trợ đa ngôn ngữ: load file message.yaml hoặc message_vi.properties,...
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private CustomSuccessHandler customSuccessHandler;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    //Lấy AuthenticationManager từ AuthenticationConfiguration, để hệ thống dùng khi xử lý đăng nhập.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);// Tắt CSRF nếu không dùng form POST truyền token

        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        // Các trang không yêu cầu login
                        .requestMatchers("/", "/login", "/logout", "/register").permitAll()
                        // Trang /userInfo yêu cầu phải login với vai trò ROLE_USER hoặc ROLE_ADMIN.
                        // Nếu chưa login, nó sẽ redirect tới trang /login.
                        //.requestMatchers("/userInfo").hasAnyRole("USER", "ADMIN")
                        // Trang chỉ dành cho ADMIN
//                        .requestMatchers("/contact/add", "/contact/save", "/contact/delete/**", "/contact/*/edit").hasRole("ADMIN")
//                        .requestMatchers("/contact", "/contact/search").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers("/admin").hasRole("ADMIN")

                        .anyRequest().authenticated()

                )

                .formLogin(form -> form
                        .loginProcessingUrl("/login") // URL xử lý POST login
                        .loginPage("/login")// URL hiển thị trang login
                        .successHandler(customSuccessHandler)
                        .failureUrl("/login?error=true")
                        .usernameParameter("email")
                        .passwordParameter("password")
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                                .successHandler(customSuccessHandler)
                        )



                .logout(logout -> logout
                        // URL mà client sẽ gọi để đăng xuất (mặc định là POST /logout)
                        .logoutUrl("/logout")

                        // Trang đích sau khi đăng xuất thành công
                        .logoutSuccessUrl("/login?logout")

                        // Xoá session, xoá JSESSIONID trong cookie & dọn dẹp thông tin xác thực
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")

                        // Cho phép mọi người gọi URL /logout mà không cần đăng nhập
                        .permitAll()
                )

                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/403") // <-- Phải gắn trong cùng chuỗi
                );


        return http.build();
    }
}
