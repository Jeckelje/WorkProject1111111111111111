package sudexpert.gov.by.workproject.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class RefreshRequest {
    @NotEmpty
    private String token;
}
