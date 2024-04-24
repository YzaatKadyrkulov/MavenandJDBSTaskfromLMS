package org.example.dao.daoImpl;

import org.example.config.DatabaseConfig;
import org.example.dao.EmployeeDao;
import org.example.models.Employee;
import org.example.models.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {
    private static final Connection connection = DatabaseConfig.getConnection();

    @Override
    public void createEmployee() {
        String sql = """
                create table if not exists employees(
                id serial primary key, 
                firstName varchar(50),
                last_name varchar(50),
                age int,
                email varchar(50),
                job_id int references job(id)
                                 )
                                """;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Successfully created table: ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        String sqlQuery = """
                create table if not exists employees(
                id serial primary key,
                first_name varchar(50),
                age int,
                email varchar(50),
                job_id int references jobs(id) )
                """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlQuery);
            System.out.println("Successfully added: ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void dropTable() {
        String sql = """
                drop table employees""";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Successfully dropped table: ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanTable() {
        String sql = """
                truncate table employees""";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Successfully cleaned table: ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        String query = """
                update employees
                set first_name = ?
                last_name = ?
                age = ?
                email = ?
                where id = ?""";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setLong(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setLong(5, employee.getId());
            preparedStatement.executeUpdate();
            System.out.println("Successfully updated: ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        String sql = """
                select * from employees""";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return getAllEmployees();
    }

    @Override
    public Employee findByEmail(String email) {
        Employee employee = new Employee();
        String sql = """
                select * from employees where id = ? """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employee;
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Map<Employee, Job> resultMap = new HashMap<>();
        Employee employee = null;
        Job job = null;
        String sql = """
                select * from employees where id = ? """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
            }
            resultMap.put(employee, job);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultMap;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        Employee employee = new Employee();
        List<Employee> employees = new ArrayList<>();
        String sql = """
                select * from employees where position = ?""";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, position);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }
}
