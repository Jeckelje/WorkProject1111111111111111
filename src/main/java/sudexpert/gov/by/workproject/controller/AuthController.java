package sudexpert.gov.by.workproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sudexpert.gov.by.workproject.security.JwtTokenProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    // Показ формы логина
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        model.addAttribute("error", error != null);
        return "login"; // Thymeleaf-шаблон login.html
    }

    // Обработка POST-запроса с формы логина
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletResponse response,
                        Model model) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Генерация JWT
            String token = jwtTokenProvider.createToken(authentication);
            String refreshToken = jwtTokenProvider.createRefreshToken(authentication);

            // Можно положить JWT в cookie (HTTP-only, чтобы JS не читал)
            Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(60 * 60); // 1 час
            response.addCookie(jwtCookie);

            // Refresh token cookie (живет дольше, например 7 дней)
            Cookie refreshCookie = new Cookie("REFRESH_TOKEN", refreshToken);
            refreshCookie.setHttpOnly(true);
            refreshCookie.setPath("/");
            refreshCookie.setMaxAge(60 * 60 * 24 * 3); // 7 дней
            response.addCookie(refreshCookie);

            // Или передать токен через JS в HTML
           // model.addAttribute("token", token);

            // Перенаправляем на домашнюю страницу
            return "redirect:/workers/view"; // Thymeleaf-шаблон home.html

        } catch (AuthenticationException e) {
            model.addAttribute("error", true);
            return "login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();

        // Получаем текущий контекст и протокол
        String contextPath = request.getContextPath();
        String domain = request.getServerName();
        boolean isSecure = request.isSecure(); // true для HTTPS

        // Используем RAW Set-Cookie заголовки
        String cookieTemplate = "%s=; Path=%s; HttpOnly; Max-Age=0; %sSameSite=Strict";

        // Удаляем JWT_TOKEN
        String accessCookie = String.format(cookieTemplate,
                "JWT_TOKEN",
                contextPath.isEmpty() ? "/" : contextPath,
                isSecure ? "Secure; " : ""
        );
        response.addHeader("Set-Cookie", accessCookie);

        // Удаляем REFRESH_TOKEN
        String refreshCookie = String.format(cookieTemplate,
                "REFRESH_TOKEN",
                contextPath.isEmpty() ? "/" : contextPath,
                isSecure ? "Secure; " : ""
        );
        response.addHeader("Set-Cookie", refreshCookie);

        // Дополнительно удаляем через Cookie объект
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT_TOKEN") ||
                        cookie.getName().equals("REFRESH_TOKEN")) {
                    Cookie deleteCookie = new Cookie(cookie.getName(), null);
                    deleteCookie.setPath(cookie.getPath());
                    deleteCookie.setMaxAge(0);
                    if (cookie.getDomain() != null) {
                        deleteCookie.setDomain(cookie.getDomain());
                    }
                    deleteCookie.setHttpOnly(cookie.isHttpOnly());
                    deleteCookie.setSecure(cookie.getSecure());
                    response.addCookie(deleteCookie);
                }
            }
        }

        return "redirect:/login?logout";
    }
}
