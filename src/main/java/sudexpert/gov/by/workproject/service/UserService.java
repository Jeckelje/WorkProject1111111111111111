package sudexpert.gov.by.workproject.service;

import org.springframework.stereotype.Service;
import sudexpert.gov.by.workproject.dto.UserDto;

import java.util.List;

@Service
public interface UserService {

    UserDto getUserById(Long id);

    UserDto getUserByUsername(String username);

    UserDto updateUser(UserDto userDto);

    UserDto createUser(UserDto userDto);

    void softDeleteUser(Long id);

    List<UserDto> getAllUsers();

    Boolean checkExistance(Long userId);
}

