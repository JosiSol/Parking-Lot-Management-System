package App;

import java.io.*;
import java.util.*;

public class Employee {
    private String name;
    private String gender;
    private String username;
    private String password;

    // Constructor to initialize an Employee object
    public Employee(String name, String gender, String username, String password) {
        this.name = name;
        this.gender = gender;
        this.username = username;
        this.password = password;
    }
    
    // Method to validate employee login credentials
    public static boolean validateEmployeeLogin(String username, String password) {
        List<Employee> employees = getAllEmployees(); 

        // Check if any employee's username and password match the input
        for (Employee employee : employees) {
            if (employee.getUsername().equals(username) && employee.getPassword().equals(password)) {
                return true; // Valid login
            }
        }
        return false; // Invalid login
    }

    // Method to save employee information to a file
    public static void addEmployeeToFile(Employee employee) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employees.txt", true))) {
            writer.write(employee.name + "," + employee.gender + "," + employee.username + "," + employee.password);
            writer.newLine();
            System.out.println("Employee added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to list all employees from the file
    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Employee employee = new Employee(data[0], data[1], data[2], data[3]);
                employees.add(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // Method to display employee information
    public static void displayEmployees() {
        List<Employee> employees = getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee employee : employees) {
                System.out.println("Name: " + employee.name);
                System.out.println("Gender: " + employee.gender);
                System.out.println("Username: " + employee.username);
                System.out.println("Password: " + employee.password);
                System.out.println("-----------------------------");
            }
        }
    }

    // Method to search for an employee by username
    public static Employee getEmployeeByUsername(String username) {
        List<Employee> employees = getAllEmployees();
        for (Employee employee : employees) {
            if (employee.username.equals(username)) {
                return employee;
            }
        }
        return null;  // Return null if the employee is not found
    }
    // Check if employee exists in the file
    public static boolean checkEmployeeExists(String username) {
        List<Employee> employees = getAllEmployees(); 

        // Iterate through all employees to check if the username exists
        for (Employee employee : employees) {
            if (employee.getUsername().equals(username)) {
                return true; // Employee found
            }
        }
        return false; // Employee not found
    }


    // Method to remove an employee by username (remove from the file)
    public static void removeEmployeeByUsername(String username) {
        List<Employee> employees = getAllEmployees();
        boolean removed = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employees.txt"))) {
            for (Employee employee : employees) {
                if (!employee.username.equals(username)) {
                    writer.write(employee.name + "," + employee.gender + "," + employee.username + "," + employee.password);
                    writer.newLine();
                } else {
                    removed = true;
                }
            }

            if (removed) {
                System.out.println("Employee removed successfully.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

