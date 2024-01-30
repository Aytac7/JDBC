package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Connection connection = null;

    public static void main(String[] args) throws SQLException {
        connection = connectToDb("postgres", "postgres", "root");
        EmployeesEntity employeesEntity=new EmployeesEntity();
        Scanner scanner=new Scanner(System.in);
        System.out.println("Choices:");
        System.out.println("1.To create table(attention! you can only press the button once)");
        System.out.println("2.To enter data");
        System.out.println("3.To get all data");
        System.out.println("4.To get data about specific ID");
        System.out.println("5.To update");
        System.out.println(" ");
        System.out.println("Please enter your choice:");
        int choice=scanner.nextInt();

        switch (choice){
            case 1->EmployeesDAO.createTable("employees");
            case 2 -> {
                System.out.println("Please write how many times you want to enter data: ");
                int numberOfInsert=scanner.nextInt();
                while(numberOfInsert!=0){
                    EmployeesDAO.insertRow("employees");
                    numberOfInsert--;
                }
            }
            case 3->EmployeesDAO.getAllData("employees");
            case 4->{
                System.out.println("Which id do you want to get data about?");
                EmployeesDAO.getById("employees", employeesEntity.setId(scanner.nextInt()));
            }
            case 5->{
                System.out.println("Which id do you want to change data?");
                EmployeesDAO.updateData("employees");
            }
            default -> {
                System.out.println("There is no such choice");
            }
        }

        connection.close();
    }

    public static Connection connectToDb(String dbname, String user, String password) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/";
        connection = DriverManager.getConnection(url + dbname, user, password);
        if (connection != null) {
            System.out.println("Connection  successfully !");
        } else
            System.out.println("Connection failed");

        return connection;
    }
}

