package org.example.service;

import org.apache.log4j.Logger;
import org.example.converter.Converter;
import org.example.converter.MappingUsers;
import org.example.domain.dal.IUserRepo;
import org.example.domain.dal.JDBCUserRepo;
import org.example.domain.entity.User;
import org.example.dto.UserDTO;
import org.example.exception.ServiceException;
import util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class UserService implements IUSerService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private static UserService INSTANCE;

    public static synchronized UserService get() {
        if (INSTANCE == null) {
            INSTANCE = new UserService();
        }
        return INSTANCE;
    }

    private final IUserRepo userRepo = JDBCUserRepo.get();
    private final Converter<User, UserDTO> converter = new MappingUsers();

    @Override
    public UserDTO save(UserDTO user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("User can't be null");
        }
        try (Connection connection = DBConnection.connect()) {
            this.validateUser(connection, user);
            User userToSave = converter.convert(user);
            User persistedUser = userRepo.save(connection, userToSave);
            return converter.convert(persistedUser);
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.error(ex);
            throw new ServiceException("Internal Server Error");
        }
    }

    @Override
    public UserDTO update(UserDTO user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("User can't be null");
        }
        try (Connection connection = DBConnection.connect()) {
            this.validateUser(connection, user);
            User userToUpdate = converter.convert(user);
            User persistedUser = userRepo.update(connection, userToUpdate);
            return converter.convert(persistedUser);
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.error(ex);
            throw new ServiceException("Internal Server Error");
        }
    }

    @Override
    public UserDTO findById(int id) throws ServiceException {
        User user;
        try (Connection connection = DBConnection.connect()) {
            user = userRepo.findById(connection, id);
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.error(ex);
            throw new ServiceException("Internal Server Error");
        }
        if (user == null) {
            throw new ServiceException("User with id: " + id + " Not Found.");
        }
        return converter.convert(user);
    }

    @Override
    public UserDTO findByEmail(String email) throws ServiceException {
        User user;
        try (Connection connection = DBConnection.connect()) {
            user = userRepo.findByEmail(connection, email);
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.error(ex);
            throw new ServiceException("Internal Server Error");
        }
        if (user == null) {
            throw new ServiceException("User with email: " + email + " Not Found.");
        }
        return converter.convert(user);
    }

    @Override
    public List<UserDTO> findUsers() throws ServiceException {
        try (Connection connection = DBConnection.connect()) {
            List<User> users = userRepo.findAll(connection);
            return users.stream().map(converter::convert)
                    .collect(Collectors.toList());
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.error(ex);
            throw new ServiceException("Internal Server Error");
        }
    }

    private void validateUser(Connection connection, UserDTO user) throws ServiceException, SQLException {
        if (this.isUserExists(connection, user)) {
            throw new ServiceException("User with email: " + user.getEmail() + " Already exists.");
        }
    }

    private boolean isUserExists(Connection connection, UserDTO user) throws SQLException {
        return userRepo.findByEmail(connection, user.getEmail()) != null;
    }
}
