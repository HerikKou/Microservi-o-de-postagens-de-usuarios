package Usuarios.Config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class SecurityFilter extends OncePerRequestFilter{
    private final TokenConfig tokenConfig;

    public SecurityFilter(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
    }
    @Override
    public void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws IOException, ServletException{
        String authorizeHeader = request.getHeader("Authorization");
        if(Strings.isNotEmpty(authorizeHeader) && authorizeHeader.startsWith("Bearer")){
            String token = authorizeHeader.substring("Bearer".length()).trim();
            Optional<JWTUserData> optional = tokenConfig.validateToken(token);
            if(optional.isPresent()){
                JWTUserData userdata = optional.get();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userdata, null,List.of());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            
        }
           filterChain.doFilter(request, response);    
}
}