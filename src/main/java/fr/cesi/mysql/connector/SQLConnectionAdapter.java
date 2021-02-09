package fr.cesi.mysql.connector;

import java.sql.*;
import java.util.Optional;

public class SQLConnectionAdapter {

    private final Connection connection;

    private SQLConnectionAdapter(Connection connection) {
        this.connection = connection;
    }

    public static Optional<SQLConnectionAdapter> from(String jdbcUrl, String username, String password) {
        SQLConnectionAdapter sqlConnectionAdapter = null;
        try {
            DriverManager.setLoginTimeout(10);
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            sqlConnectionAdapter = new SQLConnectionAdapter(connection);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return Optional.ofNullable(sqlConnectionAdapter);
    }

    public Optional<ResultSet> query(String query, Object... parameters) {
        ResultSet resultSet = null;

        try {
            resultSet = preparedStatement(query, parameters).executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return Optional.ofNullable(resultSet);
    }

    public int update(String query, Object... parameters) {
        try {
            return preparedStatement(query, parameters).executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return -1;
    }

    private PreparedStatement preparedStatement(String query, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        int index = 1;
        for (Object parameter : parameters)
            preparedStatement.setObject(index++, parameter);

        System.out.println(preparedStatement);
        return preparedStatement;
    }

}
