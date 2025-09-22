package sudexpert.gov.by.workproject.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class JwtRequest {

    @NotEmpty(message = "Username must be not null")
    private String username;

    @NotEmpty(message = "Password must be not null")
    private String password;
}
