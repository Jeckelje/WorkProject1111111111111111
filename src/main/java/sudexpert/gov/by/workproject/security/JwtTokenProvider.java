package sudexpert.gov.by.workproject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import sudexpert.gov.by.workproject.model.Role;
import sudexpert.gov.by.workproject.props.JwtProperties;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class JwtTokenProvider {

    final JwtProperties jwtProperties;
    final UserDetailsService userDetailsService;

    SecretKey key;

    @PostConstruct
    public void init() {
        log.info("Initializing JWT key for signing and validation.");
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        log.info("JWT key initialized successfully.");
    }

    // Генерация Access токена
    public String createAccessToken( String username, Set<Role> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());

        return Jwts.builder()
                .setSubject(username)               // subject — username
                .claim("roles", resolveRoles(roles)) // кастомный claim: роли
                .setIssuedAt(now)                   // дата создания
                .setExpiration(validity)            // дата истечения
                .signWith(key)                      // подпись HMAC
                .compact();
    }

    public String createRefreshToken( Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getRefresh());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    private List<String> resolveRoles(Set<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    // Проверка валидности токена
    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
        return !claims.getPayload().getExpiration().before(new Date());
    }

    private String getId(String token) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getId();
    }

    private String getUsername(String token) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String createToken(Authentication authentication) {
        // Получаем UserDetails
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Получаем роли пользователя
        Set<Role> roles = authentication.getAuthorities().stream()
                .map(auth -> Role.valueOf(auth.getAuthority()))
                .collect(Collectors.toSet());

        // Генерируем JWT access token
        return createAccessToken(userDetails.getUsername(), roles);
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

    }

}
