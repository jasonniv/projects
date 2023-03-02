package learn.resume.builder.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final JwtConverter converter;

    @Autowired
    private AppUserService service;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {
        http.csrf().disable();
        //allowing cors related requests
        http.cors();

        http.authorizeRequests()
                .antMatchers("/auth/authenticate").permitAll()
                .antMatchers("/auth/create").permitAll()
                .antMatchers("/auth/refresh_token").authenticated()
                .antMatchers(HttpMethod.GET, "/api/resume/user" ).hasRole("Job Seeker")
                .antMatchers(HttpMethod.GET, "/api/resume/*" ).permitAll()
                .antMatchers(HttpMethod.POST, "/api/resume" ).hasRole("Job Seeker")
                .antMatchers(HttpMethod.PUT, "/api/resume/*" ).hasRole("Job Seeker")
                .antMatchers(HttpMethod.DELETE, "/api/resume/*" ).hasRole("Job Seeker")

                //denying everything from here
                .antMatchers("/**").denyAll()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(authConfig), converter, service))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
