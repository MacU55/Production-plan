package org.example.domain.dal;

import org.example.domain.entity.DesignDept;
import org.example.domain.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IDesignDeptRepo {
    DesignDept save(Connection connection, DesignDept designDept) throws SQLException;

    DesignDept update(Connection connection, DesignDept designDept) throws SQLException;

    DesignDept findById(Connection connection, int designId) throws SQLException;

    List<DesignDept> findAll(Connection connection) throws SQLException;
}

