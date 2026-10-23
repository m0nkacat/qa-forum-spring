package org.example.pr4.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute
    public void addUserInfo(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAuth = auth != null && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getName());

        model.addAttribute("isAuth", isAuth);
        model.addAttribute("currentUsername", isAuth ? auth.getName() : null);

        boolean isAdmin = false, isModerator = false, isUser = false;
        if (isAuth) {
            for (var a : auth.getAuthorities()) {
                switch (a.getAuthority()) {
                    case "ADMIN"     -> isAdmin = true;
                    case "MODERATOR" -> isModerator = true;
                    case "USER"      -> isUser = true;
                }
            }
        }
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isModerator", isModerator);
        model.addAttribute("isUser", isUser);


        String roles = isAuth
                ? auth.getAuthorities().toString().replaceAll("[\\[\\]]", "")
                : "";
        model.addAttribute("currentRoles", roles);
    }
}
