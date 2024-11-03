import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionExample {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/db"; // Change 'db' to your database name
    private static final String USERNAME = "ram";
    private static final String PASSWORD = "Rammi9492";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.println("Connected to the database!");

            // Create a scrollable and updatable ResultSet
            try (Statement stmt = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE)) {

                // Execute a query to retrieve all employees
                String sql = "SELECT id, name, position, salary FROM employees";
                ResultSet rs = stmt.executeQuery(sql);

                // Scroll to the last row
                if (rs.last()) {
                    System.out.println("Last Employee: ID=" + rs.getInt("id") +
                                       ", Name=" + rs.getString("name") +
                                       ", Position=" + rs.getString("position") +
                                       ", Salary=" + rs.getDouble("salary"));
                }

                // Move to the first row
                if (rs.first()) {
                    System.out.println("First Employee: ID=" + rs.getInt("id") +
                                       ", Name=" + rs.getString("name") +
                                       ", Position=" + rs.getString("position") +
                                       ", Salary=" + rs.getDouble("salary"));
                }

                // Scroll to the second row (absolute positioning)
                if (rs.absolute(2)) {
                    System.out.println("Second Employee: ID=" + rs.getInt("id") +
                                       ", Name=" + rs.getString("name") +
                                       ", Position=" + rs.getString("position") +
                                       ", Salary=" + rs.getDouble("salary"));

                    // Update the salary of the second employee
                    rs.updateDouble("salary", 70000);
                    rs.updateRow(); // Apply the update to the database
                    System.out.println("Updated Salary of Second Employee to 70000.");
                }

                // Scroll backward to the previous row
                if (rs.previous()) {
                    System.out.println("Previous Employee: ID=" + rs.getInt("id") +
                                       ", Name=" + rs.getString("name") +
                                       ", Position=" + rs.getString("position") +
                                       ", Salary=" + rs.getDouble("salary"));
                }

                // Insert a new row
                rs.moveToInsertRow();
                rs.updateString("name", "David");
                rs.updateString("position", "Intern");
                rs.updateDouble("salary", 45000);
                rs.insertRow();
                System.out.println("Inserted new employee: David, Position=Intern, Salary=45000.");

                // Verify the inserted row by scrolling back to the last row
                if (rs.last()) {
                    System.out.println("Last Employee After Insertion: ID=" + rs.getInt("id") +
                                       ", Name=" + rs.getString("name") +
                                       ", Position=" + rs.getString("position") +
                                       ", Salary=" + rs.getDouble("salary"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(50),
    salary DECIMAL(10, 2)
);

-- Insert sample data
INSERT INTO employees (name, position, salary) VALUES
('Alice', 'Developer', 75000),
('Bob', 'Designer', 65000),
('Charlie', 'Manager', 85000);
