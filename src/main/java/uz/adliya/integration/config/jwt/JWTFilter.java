package uz.adliya.integration.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.adliya.integration.service.AdminService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;
    private final AdminService adminService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && (authorization.startsWith(AppConstants.BEARER_TYPE)))
            setAuthenticationBearer(request, authorization);
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationBearer(HttpServletRequest request, String authorization) {
        String userId = jwtProvider.extractUserId(authorization.substring(AppConstants.BEARER_TYPE.length()).trim());

        if (userId != null) {
            Optional<Admin> optionalUser = adminService.findUserById(Integer.valueOf(userId));
            optionalUser.ifPresent(user -> {
                if (user.allOk()) setAuthentication(request, user);
            });
        }
    }

    private void setAuthentication(HttpServletRequest request, Admin user) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        WebAuthenticationDetails details = new WebAuthenticationDetails(request);
        authentication.setDetails(details);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}