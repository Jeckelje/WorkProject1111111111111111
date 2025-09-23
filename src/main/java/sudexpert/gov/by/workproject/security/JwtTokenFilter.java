package sudexpert.gov.by.workproject.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import sudexpert.gov.by.workproject.dto.error.AppError;
import sudexpert.gov.by.workproject.exception.ResourceNotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JwtTokenFilter extends GenericFilterBean {

    JwtTokenProvider jwtTokenProvider;
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String bearerToken = ((HttpServletRequest) servletRequest).getHeader("Authorization");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getServletPath();
        String method = request.getMethod();

        String token = null;
        String refreshToken = null;

        if (("/login".equals(path) && "POST".equalsIgnoreCase(method)) || path.equals("/login") || path.equals("/favicon.ico") || path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/api/v1/auth")) {
            filterChain.doFilter(servletRequest, servletResponse);
            log.info("Login POST request");
            return;
        }

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
            log.info("Extracted Bearer token from Authorization header");
        } else if (request.getCookies() != null) for (Cookie cookie : request.getCookies()) {
            if ("JWT_TOKEN".equals(cookie.getName())) {
                token = cookie.getValue();

            } else if (("REFRESH_TOKEN".equals(cookie.getName()))) {
                refreshToken = cookie.getValue();
            }
        }
        try {
            if (bearerToken != null && jwtTokenProvider.validateToken(bearerToken)) {
                log.info("Token is valid. Proceeding to authenticate...");

                try {
                    Authentication authentication = jwtTokenProvider.getAuthentication(bearerToken);
                    if (authentication != null) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        log.info("Authentication successful for token.");
                    }
                } catch (ResourceNotFoundException ignored) {
                    log.warn("Authentication failed, user not found for token.");
                }
            } else if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Authentication successful for token.");
            }
        } catch (MalformedJwtException e) {
            // Игнорируем, не устанавливаем аутентификацию
            logger.debug("Ignoring malformed JWT: {}");
        } catch (Exception e) {
            log.error("Error occurred while processing the token: {}", e.getMessage(), e);
            exceptionHandler((HttpServletResponse) servletResponse, e);
            return;
        }
        try {
            log.warn("Invalid or expired token. Trying refresh...");
            if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Генерируем новый access token
                String newAccessToken = jwtTokenProvider.createToken(authentication);

                Cookie newJwtCookie = new Cookie("JWT_TOKEN", newAccessToken);
                newJwtCookie.setHttpOnly(true);
                newJwtCookie.setPath("/");
                newJwtCookie.setMaxAge(60 * 60); // 15 минут
                response.addCookie(newJwtCookie);

                log.info("Access token refreshed via refresh token");
            } else {
                log.error("Refresh token invalid or missing.");
                AppError appError = new AppError(401, "expired or invalid refresh token", LocalDateTime.now().toString());
                response.getWriter().write(objectMapper.writeValueAsString(appError));
                return;
            }
        }catch (Exception e){
            log.error("Exception occurred while processing refresh token");
            AppError appError = new AppError(401, "expired or invalid refresh token", LocalDateTime.now().toString());
            response.getWriter().write(objectMapper.writeValueAsString(appError));
        }


        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void exceptionHandler(HttpServletResponse response, Exception exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();

        AppError appError;
        if (exception instanceof ExpiredJwtException) {
            appError = new AppError(401, "Expired JWT", LocalDateTime.now().toString());
            log.error("JWT has expired.");
        } else {
            appError = new AppError(401, "Invalid JWT", LocalDateTime.now().toString());
            log.error("JWT is invalid.");
        }

        response.getWriter().write(objectMapper.writeValueAsString(appError));
    }
}