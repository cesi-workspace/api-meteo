package fr.cesi.divers.mysql.connector;

import lombok.SneakyThrows;

import java.sql.*;
import java.util.Optional;

public class SQLConnectionAdapter {

    private final Connection connection;

    private SQLConnectionAdapter(Connection connection) {
        this.connection = connection;
    }

    @SneakyThrows
    public static Optional<SQLConnectionAdapter> from(String jdbcUrl, String username, String password) {
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        SQLConnectionAdapter sqlConnectionAdapter = new SQLConnectionAdapter(connection);

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
