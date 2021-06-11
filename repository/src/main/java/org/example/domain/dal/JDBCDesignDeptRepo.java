package org.example.domain.dal;

import org.example.domain.entity.Order;
import org.example.domain.entity.DesignDept;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JDBCDesignDeptRepo implements IDesignDeptRepo {
    private static final String INSERT_STATEMENT = "INSERT INTO designdept(order_id, date_design_completed, drawing_name)" +
            " values (?,?,?)";
    private static final String UPDATE_STATEMENT = "UPDATE designdept SET order_id=?,date_design_completed=?,drawing_name=?" +
             "WHERE design_id=?";
    private static final String FIND_BY_ID_STATEMENT = "SELECT * FROM designdept WHERE design_id=?";
    private static final String FIND_ALL_STATEMENT = "SELECT * FROM designdept";

    private static JDBCDesignDeptRepo INSTANCE;

    private JDBCDesignDeptRepo() {
    }

    public static synchronized JDBCDesignDeptRepo get() {
        if (INSTANCE == null) {
            INSTANCE = new JDBCDesignDeptRepo();
        }
        return INSTANCE;
    }

    @Override
    public DesignDept save(Connection connection, DesignDept designDept) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, designDept.getOrderId());

        Date utilDateDesignCompleted = designDept.getDateDesignCompleted();
        java.sql.Date sqlDateDesignCompleted = new java.sql.Date(utilDateDesignCompleted.getTime());
        statement.setDate(2, sqlDateDesignCompleted);

        statement.setString(3, designDept.getDesignName());

        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Save designDept Failed");
        }
        return this.setIdAndReturn(statement, designDept);
    }

    @Override
    public DesignDept update(Connection connection, DesignDept designDept) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_STATEMENT);
        statement.setInt(1, designDept.getOrderId());
        Date utilDateDesignCompleted = designDept.getDateDesignCompleted();
        java.sql.Date sqlDateDesignCompleted = new java.sql.Date(utilDateDesignCompleted.getTime());
        statement.setDate(2, sqlDateDesignCompleted);
        statement.setString(3, designDept.getDesignName());


        statement.setInt(4, designDept.getId());
        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Update designdept Failed");
        }
        return designDept;
    }

    @Override
    public DesignDept findById(Connection connection, int designId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_STATEMENT);
        statement.setInt(1, designId);
        ResultSet resultSet = statement.executeQuery();
        DesignDept designDept = null;

        if (resultSet.next()) {
            designDept = loadFromResult(resultSet);
        }

        return designDept;
    }

    @Override
    public List<DesignDept> findAll(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_ALL_STATEMENT);
        ResultSet resultSet = statement.executeQuery();
        List<DesignDept> designDepts = new ArrayList<>();

        while (resultSet.next()) {
            DesignDept designDept = loadFromResult(resultSet);
            designDepts.add(designDept);
        }
        return designDepts;
    }

    public DesignDept setIdAndReturn(PreparedStatement statement, DesignDept designDept) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                designDept.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating designDept failed, no ID obtained.");
            }
        }
        return designDept;
    }

    private static DesignDept loadFromResult(ResultSet resultSet) throws SQLException {
        DesignDept designDept = new DesignDept();
        designDept.setId(resultSet.getInt("design_id"));
        designDept.setOrderId(resultSet.getInt("order_id"));
        designDept.setDateDesignCompleted(resultSet.getDate("date_design_completed"));
        designDept.setDesignName(resultSet.getString("drawing_name"));

        return designDept;
    }
}