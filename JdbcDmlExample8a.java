import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnectionExample {
    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/db";
    private static final String USERNAME = "ram";
    private static final String PASSWORD = "Rammi9492";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.println("Connected to the database!");

            // Perform DML operations
            insertEmployee(connection, "Alice", "Developer", 70000);
            insertEmployee(connection, "Bob", "Designer", 60000);
            updateEmployeeSalary(connection, 1, 75000);
            deleteEmployee(connection, 2);

            // Call stored procedure
            callStoredProc(connection, 65000);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to insert an employee using a PreparedStatement
    public static void insertEmployee(Connection connection, String name, String position, double salary) throws SQLException {
        String insertSQL = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setDouble(3, salary);
            int rowsInserted = pstmt.executeUpdate();
            System.out.println("Inserted " + rowsInserted + " row(s) into the employees table.");
        }
    }

    // Method to update an employee's salary using a PreparedStatement
    public static void updateEmployeeSalary(Connection connection, int id, double newSalary) throws SQLException {
        String updateSQL = "UPDATE employees SET salary = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setDouble(1, newSalary);
            pstmt.setInt(2, id);
            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("Updated " + rowsUpdated + " row(s) in the employees table.");
        }
    }

    // Method to delete an employee using a PreparedStatement
    public static void deleteEmployee(Connection connection, int id) throws SQLException {
        String deleteSQL = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            System.out.println("Deleted " + rowsDeleted + " row(s) from the employees table.");
        }
    }

    // Method to call a stored procedure using a CallableStatement
    public static void callStoredProc(Connection connection, double minSalary) throws SQLException {
        String procSQL = "{CALL get_high_salary_employees(?)}";
        try (CallableStatement cstmt = connection.prepareCall(procSQL)) {
            cstmt.setDouble(1, minSalary);
            ResultSet rs = cstmt.executeQuery();
            System.out.println("Employees with salary above " + minSalary + ":");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String position = rs.getString("position");
                double salary = rs.getDouble("salary");
                System.out.println("ID: " + id + ", Name: " + name + ", Position: " + position + ", Salary: " + salary);
            }
        }
    }
}



  CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(100) NOT NULL, position VARCHAR(50),   salary DECIMAL(10, 2) );

   SHOW PROCEDURE STATUS WHERE Db = 'db';

   DELIMITER //

CREATE PROCEDURE get_high_salary_employees(IN min_salary DECIMAL(10, 2))
BEGIN
    SELECT * FROM employees WHERE salary > min_salary;
END //

DELIMITER ;
