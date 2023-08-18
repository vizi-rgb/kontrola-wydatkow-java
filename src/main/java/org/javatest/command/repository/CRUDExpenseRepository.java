package org.javatest.command.repository;

import org.javatest.command.MyDateFormat;

import java.io.File;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
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
                    .prepareStatement("insert into expenses values (NULL, ?, ?, ?, ?, ?);");

            preparedStatement.setDouble(1, expense.money());
            preparedStatement.setString(2, expense.message());
            preparedStatement.setInt(3, expense.date().day());
            preparedStatement.setInt(4, expense.date().month());
            preparedStatement.setInt(5, expense.date().year());
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
                    .prepareStatement("select id, quota, message, day, month, year from expenses where id = ?");

            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            ResultSet resultSet =  preparedStatement.getResultSet();
            expense = new Expense(
                    resultSet.getLong("id"),
                    resultSet.getDouble("quota"),
                    resultSet.getString("message"),

                    new MyDateFormat(
                            resultSet.getInt("day"),
                            resultSet.getInt("month"),
                            resultSet.getInt("year")
                    )
            );


        } catch (SQLException e) {
            System.err.println("Cannot get by id from expenses table");
            System.exit(-1);
        }

        return Optional.of(expense);
    }

    public Collection<Expense> getAll() {
        Collection<Expense> expenses = new ArrayList<>();
        String sqlSelectAll = "select id, quota, message, day, month, year from expenses";
        try {
            ResultSet resultSet = statement.executeQuery(sqlSelectAll);
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                double quota = resultSet.getDouble(2);
                String message = resultSet.getString(3);
                int day = resultSet.getInt(4);
                int month = resultSet.getInt(5);
                int year = resultSet.getInt(6);
                expenses.add(new Expense(id, quota, message, new MyDateFormat(day, month, year)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return expenses;
    }

    private void createTableExpensesIfNotExists() throws SQLException {
        String createUsersTable = "create table if not exists expenses (" +
                "id integer primary key autoincrement," +
                "quota real," +
                "message TEXT," +
                "day integer," +
                "month integer," +
                "year integer);";

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
