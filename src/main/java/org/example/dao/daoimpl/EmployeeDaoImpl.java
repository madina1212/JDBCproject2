package org.example.dao.daoimpl;

import cenfig.Hibernatiprojec;
import org.example.dao.EmployeeDao;
import org.example.model.Employee;
import org.example.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {
    private Connection connection;

    public EmployeeDaoImpl() {
        this.connection = Hibernatiprojec.connectionToDatabase();
    }
    public void createEmployee() {
        String query = "create table employees (id serial primary key ," +
                "first_name varchar," +
                "last_name varchar," +
                "age smallint," +
                " email varchar ," +
                "job_id int references employees(id))";
        System.out.println("papka achyldy");
        try (Statement statement = connection.createStatement()){
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addEmployee(Employee employee) {
       String sql = " insert into employees (first_name, last_name, age, email, job_id)" +
               " values(?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, employee.getFirst_name());
            preparedStatement.setString(2, employee.getLast_name());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setLong(5, employee.getJob_id());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void dropTable() {
        String quer = " drop table if exists employees;";
        try (Statement statement = connection.createStatement()){
            statement.execute(quer);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void cleanTable() {
        String query = "truncate table employees cascade";
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            System.out.println("Table is truncate on database!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void updateEmployee(Long id, Employee employee){
         String qeur = " update employees set first_name = ?, last_name = ?, age = ?, email = ?, job_id = ?" +
                      " where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(qeur)){
            preparedStatement.setString(1, employee.getFirst_name());
            preparedStatement.setString(2, employee.getLast_name());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setLong(5, employee.getJob_id());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Employee> getAllEmployees() {
        String sql = "select * from employees";
        List<Employee> users = new ArrayList<>();
        try (Connection connection = Hibernatiprojec.connectionToDatabase();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet= statement.executeQuery(sql);
            while (resultSet.next()){
                users.add(new Employee(
                        resultSet.getLong("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getInt("job_id")
                ));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return users;
    }

    public Employee findByEmail(String email) {
        Employee employee = new Employee();
        String sql = "SELECT * FROM employees WHERE email = ?";
        try(Connection connection=Hibernatiprojec.connectionToDatabase();
            PreparedStatement preparedStatement= connection.prepareStatement(sql)){
            preparedStatement.setString(1,email);
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()) {
                employee.setId( resultSet.getLong("id"));
                employee.setFirst_name( resultSet.getString("first_name"));
                employee.setLast_name( resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJob_id(resultSet.getInt("job_id"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return  employee ;

    }
    public Map<Employee, Job> getEmployeeById(Long employeeId){
        Map<Employee, Job> employeeJobMap = new HashMap<>();
        String sql = " select * from employees full join job j on employees.job_id = j.id where employees.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Employee employee = new Employee();
                Job job = new Job();
                employee.setId(resultSet.getLong("id"));
                employee.setFirst_name(resultSet.getString(2));
                employee.setLast_name(resultSet.getString(3));
                employee.setAge(resultSet.getInt(4));
                employee.setEmail(resultSet.getString(5));
                employee.setJob_id(resultSet.getInt(6));
                job.setId(resultSet.getLong(7));
                job.setPosition(resultSet.getString(8));
                job.setProfession(resultSet.getString(9));
                job.setDescription(resultSet.getString(10));
                job.setExperience(resultSet.getInt(10));
                employeeJobMap.put(employee, job);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeJobMap;

    }

    public List<Employee> getEmployeeByPosition(String position){
        List<Employee> employees = new ArrayList<>();
        String query ="select * from employees join job j on employees.job_id = j.id where j.position = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, position);
            ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirst_name(resultSet.getString(2));
                employee.setLast_name(resultSet.getString(3));
                employee.setAge(resultSet.getInt(4));
                employee.setEmail(resultSet.getString(5));
                employee.setJob_id(resultSet.getInt(6));
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }
    }
