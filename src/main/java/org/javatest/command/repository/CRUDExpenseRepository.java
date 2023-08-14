package org.javatest.command.repository;

import java.io.File;
import java.nio.file.Path;
import java.sql.*;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.FileHandler;

public class CRUDExpenseRepository implements Repository<Expense> {

    private final String url;
    private Connection connection;
    private Statement statement;

    public CRUDExpenseRepository() {
        File file = new File("src/db/expense.db");
        url = "jdbc:sqlite:" + file.getAbsolutePath();

        establishConnectionWithDatabase();
    }
    public CRUDExpenseRepository(String repositoryName) {
        File file = new File("src/db/" + repositoryName);
        url = "jdbc:sqlite:" + file.getAbsolutePath();

        establishConnectionWithDatabase();
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

    public void deleteById(long id) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from expenses where id = ?");

            preparedStatement.setLong(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            System.err.println("Cannot delete from expenses table");
            System.exit(-1);
        }
    }

    public Optional<Expense> getById(long id) {
        Expense expense = null;

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select id, quota, message, date from expenses where id = ?");

            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            ResultSet resultSet =  preparedStatement.getResultSet();
            expense = new Expense(
                    resultSet.getLong("id"),
                    resultSet.getDouble("quota"),
                    resultSet.getString("message"),
                    resultSet.getString("date")
            );


        } catch (SQLException e) {
            System.err.println("Cannot get by id from expenses table");
            System.exit(-1);
        }

        return Optional.of(expense);
    }

    public Optional<Expense> getByMessage(String message) {
        // TODO
        return null;
    }

    public Collection<Expense> getAllByQuota(double quota) {
        // TODO
        Collection<Expense> expenses = null;
        return expenses;
    }

    private void createTableExpensesIfNotExists() throws SQLException {
        String createUsersTable = "create table if not exists expenses (" +
                "id integer primary key autoincrement," +
                "quota real," +
                "date TEXT," +
                "message TEXT);";

        statement.execute(createUsersTable);
    }

    private void establishConnectionWithDatabase() {
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
