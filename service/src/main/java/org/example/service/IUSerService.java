package org.example.service;

import org.example.dto.UserDTO;
import org.example.exception.ServiceException;

import java.util.List;

public interface IUSerService {
    UserDTO save(UserDTO user) throws ServiceException;

    UserDTO update(UserDTO user) throws ServiceException;

    UserDTO findById(int id) throws ServiceException;

    UserDTO findByEmail(String email) throws ServiceException;

    List<UserDTO> findUsers() throws ServiceException;
}
