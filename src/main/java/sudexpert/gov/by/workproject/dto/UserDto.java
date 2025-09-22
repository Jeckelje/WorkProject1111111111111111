package sudexpert.gov.by.workproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.dto.validation.OnUpdate;
import sudexpert.gov.by.workproject.model.Role;
import sudexpert.gov.by.workproject.model.User;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(implementation = User.class)
public class UserDto {

    @NotNull(message = "Id must be not null", groups = OnUpdate.class)
    Long id;

    @NotEmpty(message = "Username must be not null", groups = {OnUpdate.class, OnCreate.class})
    @Length(min = 4, max = 255, message = "Username length mus be smaller then 255")
    String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "Password must be not null", groups = {OnUpdate.class, OnCreate.class})
    @Length(min = 4, max = 255)
    String password;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "Password confirmation must be not null", groups = OnCreate.class)
    String passwordConfirmation;

    @NotEmpty(message = "Roles must be not empty", groups = {OnUpdate.class})
    Set<Role> roles;
}