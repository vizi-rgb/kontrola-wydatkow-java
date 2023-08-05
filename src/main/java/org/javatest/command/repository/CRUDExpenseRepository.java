package org.javatest.command.repository;

import java.io.File;
import java.nio.file.Path;
import java.sql.*;
import java.util.Optional;
import java.util.logging.FileHandler;

public class CRUDExpenseRepository<K extends Number> implements Repository<Expense> {

    private final String url;
    private Connection connection;
    private Statement statement;

    public CRUDExpenseRepository() {
        Path path = Path.of("src", "db", "expense.db");
        url = "jdbc:sqlite:" + String.valueOf(path);

        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }


    @Override
    public void save(Expense expense) {
        try {
            createTableExpensesIfNotExists();
        } catch (SQLException e) {
            System.err.println("Cannot create expenses table");
            System.exit(-1);
        }

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into expenses values (NULL, ?, ?, ?);");

            preparedStatement.setDouble(1, expense.money());
            preparedStatement.setString(2, expense.date());
            preparedStatement.setString(3, expense.message());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("Cannot insert into expenses table");
            System.exit(-1);
        }
    }

    public void deleteById(K id) {
        // TODO
    }

    public Optional<Expense> getById(K id) {
        // TODO
        return null;
    }

    public Optional<Expense> getByMessage(String message) {
        // TODO
        return null;
    }

    public Optional<Expense> getByQuota(double quota) {
        // TODO
        return null;
    }

    private void createTableExpensesIfNotExists() throws SQLException {
        String createUsersTable = "create table if not exists expenses (" +
                "id integer primary key autoincrement," +
                "quota real," +
                "date TEXT," +
                "message TEXT);";

        statement.execute(createUsersTable);
    }
}
