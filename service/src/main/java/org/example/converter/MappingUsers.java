package org.example.converter;

import org.example.domain.entity.User;
import org.example.dto.UserDTO;

public class MappingUsers implements Converter<User, UserDTO> {

    @Override
    public User convert(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setSecondName(dto.getSecondName());
        return user;
    }

    @Override
    public UserDTO convert(User entity) {
        UserDTO user = new UserDTO();
        user.setId(entity.getId());
        user.setEmail(entity.getEmail());
        user.setFirstName(entity.getFirstName());
        user.setSecondName(entity.getSecondName());
        return user;
    }
}
