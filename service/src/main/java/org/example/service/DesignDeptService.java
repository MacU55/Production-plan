package org.example.service;

import org.apache.log4j.Logger;
import org.example.converter.MappingDesignDept;
import org.example.domain.dal.IDesignDeptRepo;
import org.example.domain.dal.JDBCDesignDeptRepo;
import org.example.domain.entity.DesignDept;
import org.example.dto.DesignDeptDTO;
import org.example.exception.ServiceException;
import util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class DesignDeptService implements IDesignDeptService {

    private static final Logger LOGGER = Logger.getLogger(DesignDeptService.class.getName());

    private static DesignDeptService INSTANCE;

    public static synchronized DesignDeptService get() {
        if (INSTANCE == null) {
            INSTANCE = new DesignDeptService();
        }
        return INSTANCE;
    }

    private final IDesignDeptRepo designDeptRepo = JDBCDesignDeptRepo.get();
    private final MappingDesignDept converter = new MappingDesignDept();

    @Override
    public DesignDeptDTO save(DesignDeptDTO designDeptDTO) throws ServiceException {
        if (designDeptDTO == null) {
            throw new ServiceException("designDeptDTO can't be null");
        }
        try (Connection connection = DBConnection.connect()) {
            DesignDept designDeptToSave = converter.convert(designDeptDTO);
            DesignDept persistedDesignDept = designDeptRepo.save(connection, designDeptToSave);
            return converter.convert(persistedDesignDept);
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.error(ex);
            throw new ServiceException("Internal Server Error");
        }
    }

    @Override
    public DesignDeptDTO update(DesignDeptDTO designDeptDTO) throws ServiceException {
        if (designDeptDTO == null) {
            throw new ServiceException("designDept can't be null");
        }
        try (Connection connection = DBConnection.connect()) {
            DesignDept designDeptToUpdate = converter.convert(designDeptDTO);
            DesignDept persistedDesignDept = designDeptRepo.update(connection, designDeptToUpdate);
            return converter.convert(persistedDesignDept);
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.error(ex);
            throw new ServiceException("Internal Server Error");
        }
    }

    @Override
    public DesignDeptDTO findByDesignId(int designId) throws ServiceException {
        DesignDept designDept;
        try (Connection connection = DBConnection.connect()) {
            designDept = designDeptRepo.findById(connection, designId);
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.error(ex);
            throw new ServiceException("Internal Server Error");
        }
        if (designDept == null) {
            throw new ServiceException("designdept with id: " + designId + " Not Found.");
        }
        return converter.convert(designDept);
    }

    @Override
    public List<DesignDeptDTO> findDesignDepts() throws ServiceException {
        try (Connection connection = DBConnection.connect()) {
            List<DesignDept> designDepts = designDeptRepo.findAll(connection);
            return designDepts.stream().map(converter::convert)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new ServiceException("Internal Server Error");
        }
    }
}


