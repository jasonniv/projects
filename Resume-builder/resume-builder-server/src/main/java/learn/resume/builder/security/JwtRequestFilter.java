package learn.resume.builder.security;

import learn.resume.builder.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class JwtRequestFilter extends BasicAuthenticationFilter {
    private final JwtConverter converter;
    private AppUserService service;

    public JwtRequestFilter(AuthenticationManager authenticationManager, JwtConverter converter, AppUserService service) {
        super(authenticationManager);
        this.converter = converter;
        this.service = service;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {

            UserDetails user = converter.getUserFromToken(authorization);
            if (user == null) {
                response.setStatus(403); // Forbidden
            } else {

                UserDetails appUser = service.loadUserByUsername(user.getUsername());

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        appUser, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }

        chain.doFilter(request, response);
    }
}
