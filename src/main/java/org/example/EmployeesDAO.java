package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeesDAO {
    static PreparedStatement preparedStatement;
    static Connection connection=Main.connection;
    public static void createTable( String tableName) {

        try {
            String query = "create table " + tableName +
                    "(id serial primary key," +
                    "name varchar(64) not null," +
                    "surname varchar(64)," +
                    "phone varchar(32)," +
                    "department varchar(64)," +
                    "hire_date date," +
                    "salary int)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            System.out.println("Table has been created successfully!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertRow(String tableName) throws SQLException {
        String query = "insert into employees (name, surname, phone, department, hire_date, salary) " +
                "values (?, ?, ?, ?, to_date(?,'yyyy-MM-dd'), ?)";
        Scanner scanner=new Scanner(System.in);
        System.out.println("Name:");
        String name=scanner.nextLine();
        System.out.println("Surname:");
        String surname=scanner.nextLine();
        System.out.println("Phone:");
        String phone=scanner.nextLine();
        System.out.println("Department:");
        String department=scanner.nextLine();
        System.out.println("HireDate(Format->'yyyy-mm-dd'):");
        LocalDate hireDate=LocalDate.parse(scanner.nextLine());
        System.out.println("Salary:");
        Double salary= scanner.nextDouble();


        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, phone);
        preparedStatement.setString(4, department);
        preparedStatement.setString(5, hireDate.toString());
        preparedStatement.setDouble(6, salary);
        preparedStatement.execute();
    }

    public static void getAllData( String tableName) throws SQLException {
        List<EmployeesEntity> list = new ArrayList<>();
        ResultSet resultSet;
        String query = "select * from " + tableName;
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            EmployeesEntity employeesEntity =new EmployeesEntity();
            employeesEntity.setId(resultSet.getInt("id"));
            employeesEntity.setName(resultSet.getString("name"));
            employeesEntity.setSurname(resultSet.getString("surname"));
            employeesEntity.setPhone(resultSet.getString("phone"));
            employeesEntity.setDepartment(resultSet.getString("department"));
            employeesEntity.setHireDate(resultSet.getDate("hire_date").toLocalDate());
            employeesEntity.setSalary(resultSet.getDouble("salary"));
            list.add(employeesEntity);
        }
        for (EmployeesEntity listEmp : list) {
            System.out.println(listEmp);
        }
    }

    public static void getById(String tableName, int id) throws SQLException {
        List<EmployeesEntity> list = new ArrayList<>();
        ResultSet resultSet;
        String query = "select * from " + tableName + " where id=" + id;
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            EmployeesEntity employeesEntity =new EmployeesEntity();
            employeesEntity.setId(resultSet.getInt("id"));
            employeesEntity.setName(resultSet.getString("name"));
            employeesEntity.setSurname(resultSet.getString("surname"));
            employeesEntity.setPhone(resultSet.getString("phone"));
            employeesEntity.setDepartment(resultSet.getString("department"));
            employeesEntity.setHireDate(resultSet.getDate("hire_date").toLocalDate());
            employeesEntity.setSalary(resultSet.getDouble("salary"));

            list.add(employeesEntity);


        }
        for (EmployeesEntity listEmp : list) {
            System.out.println(listEmp);
        }
    }

    public static void updateData(String tableName) throws SQLException {
        Scanner scanner=new Scanner(System.in);

            int id = scanner.nextInt();
            scanner.nextLine();

            String query = "update " + tableName + " set name=?, surname=?, phone=?, department=?, hire_date=?, salary=? WHERE id=" + id;


            System.out.println("Name:");
            String name = scanner.nextLine();

            System.out.println("Surname:");
            String surname = scanner.nextLine();

            System.out.println("Phone:");
            String phone = scanner.nextLine();

            System.out.println("Department:");
            String department = scanner.nextLine();

            System.out.println("HireDate(Format->'yyyy-mm-dd'):");
            LocalDate hireDate = LocalDate.parse(scanner.nextLine());

            System.out.println("Salary:");
            Double salary = scanner.nextDouble();

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, department);
            preparedStatement.setString(5, hireDate.toString());
            preparedStatement.setDouble(6, salary);
            preparedStatement.executeUpdate();


    }

}
