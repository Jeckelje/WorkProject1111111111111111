package sudexpert.gov.by.workproject.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import sudexpert.gov.by.workproject.model.Role;
import sudexpert.gov.by.workproject.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JwtEntityFactory {


    public static JwtEntity createJwtEntity(User user) {
        log.info("Creating JWT entity for user with username: {}", user.getUsername());

        JwtEntity jwtEntity = new JwtEntity(user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())));

        log.info("JWT entity successfully created for user: {}", user.getUsername());
        return jwtEntity;
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        log.info("Mapping roles to granted authorities...");

        List<GrantedAuthority> authorities = roles.stream()
                .map(Enum::name) // Mapping role to its string name
                .map(SimpleGrantedAuthority::new) // Creating granted authority for each role
                .collect(Collectors.toList());

        log.info("Mapped {} roles to granted authorities.", authorities.size());
        return authorities;
    }
}