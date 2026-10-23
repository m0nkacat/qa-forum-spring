package org.example.pr4.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pr4.model.AppUser;
import org.example.pr4.repository.AppUserRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final AppUserRepository userRepository;

    public CustomAuthenticationFailureHandler(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                         HttpServletResponse response,
                                         AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");

        if (exception instanceof DisabledException && username != null) {
            AppUser user = userRepository.findByUsername(username).orElse(null);
            String reason = user != null ? user.getBlockReason() : null;

            if (reason == null || reason.isBlank()) {
                reason = "Причина блокировки не указана администратором.";
            }

            String encodedReason = URLEncoder.encode(reason, StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath()
                    + "/login?blocked=true&reason=" + encodedReason);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/login?error=true");
    }
}
