package com.phung.clothshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.phung.clothshop.service.account.IAccountService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IAccountService iAccountService;

    // Phân quyền trước đăng nhập --> Nhưng vẫn cố gắng truy cập
    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    // Phân quyền sau đăng nhập --> Nhưng token hết hạn
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    // Băm mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(iAccountService).passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/**").disable();

        http.httpBasic().authenticationEntryPoint(restServicesEntryPoint());
        // http.authorizeRequests()
        // .anyRequest().permitAll() // Cho phép tất cả các request mà không cần xác
        // thực
        http.authorizeRequests()
                .antMatchers(
                        "/api/auth/login",
                        "/api/auth/forget-password",
                        "/api/users/update-password/*",
                        "/api/auth/register",
                        "/api/auth/register",
                        "/api/customers/**")
                .permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login") // Đường dẫn tới trang đăng nhập tùy chỉnh
                .loginProcessingUrl("/perform_login") // Đường dẫn xử lý quá trình đăng nhập khi form được gửi
                .usernameParameter("username") // Tham số tên người dùng trong form đăng nhập
                .passwordParameter("password") // Tham số mật khẩu trong form đăng nhập
                .defaultSuccessUrl("/home") // URL mặc định sau khi đăng nhập thành công
                .failureUrl("/login?error=true"); // URL khi đăng nhập thất bại

        http.logout()
                .logoutUrl("/logout") // URL để đăng xuất
                .logoutSuccessUrl("/login?logout") // URL sau khi đăng xuất thành công
                .invalidateHttpSession(true) // Hủy phiên HttpSession
                .deleteCookies("JWT") // Xóa cookie khi đăng xuất
                .permitAll();

        // http.addFilterBefore(jwtAuthenticationFilter(),
        // UsernamePasswordAuthenticationFilter.class)
        // .exceptionHandling().accessDeniedPage("/error/403");
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());

        http.exceptionHandling().authenticationEntryPoint(new MyAuthenticationEntryPoint());

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Đặt cách quản lý session (ví dụ: STATELESS,
                                                                         // IF_REQUIRED, ALWAYS, NEVER)
        // .invalidSessionUrl("/invalidSession") // Đặt URL để xử lý khi session không
        // hợp lệ
        // .maximumSessions(1) // Đặt số lượng phiên tối đa mà một người dùng có thể có
        // cùng một lúc
        // .maxSessionsPreventsLogin(true) // Nếu true, ngăn chặn đăng nhập khi đạt đến
        // số lượng phiên tối đa
        // .sessionFixation().newSession() // Xử lý vấn đề session fixation bằng cách
        // tạo session mới sau khi xác
        // // thực
        // .sessionConcurrency() // Cấu hình đồng thời phiên, xử lý đăng nhập từ nhiều
        // phiên cùng một tài khoản
        // .sessionAuthenticationErrorUrl("/sessionError") // Đặt URL khi có lỗi xác
        // thực session
        // .sessionRegistry(sessionRegistry()); // Cung cấp sessionRegistry để quản lý
        // thông tin phiên

        http.cors();

    }

}
