package sudexpert.gov.by.workproject.mapper;


import org.mapstruct.Mapper;
import sudexpert.gov.by.workproject.dto.UserDto;
import sudexpert.gov.by.workproject.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto dto);
    List<UserDto> toDto(List<User> users);
}
