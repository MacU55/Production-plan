package org.example.service;

import org.example.dto.LoginDTO;
import org.example.dto.UserDTO;

public interface IAuthenticationService {
    UserDTO login(LoginDTO loginDTO);
}
