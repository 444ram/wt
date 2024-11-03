import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseConnectionExample {
    public static void main(String[] args) {
        // Step 1: Define connection parameters
        String jdbcUrl = "jdbc:mysql://localhost:3306/db"; // Replace with your DB URL
        String username = "ram"; // Replace with your DB username
        String password = "Rammi9492"; // Replace with your DB password

        // Step 2: Establish a connection
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Connected to the database successfully!");

            // Step 3: Create a statement
            Statement statement = connection.createStatement();

            // Step 4: Execute a SQL query
            String sql = "SELECT * FROM emp"; // Replace with your table name
            ResultSet resultSet = statement.executeQuery(sql);

            // Step 5: Process the results
            while (resultSet.next()) {
                int id = resultSet.getInt("id");          // Replace with your column names
                String name = resultSet.getString("name"); // Replace with your column names
                System.out.println("ID: " + id + ", Name: " + name);
            }

            // Clean up
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


CREATE TABLE emp (
    ->     id INT PRIMARY KEY AUTO_INCREMENT,
    ->     name VARCHAR(50) NOT NULL
    -> );

    INSERT INTO emp (name) VALUES ('Alice'), ('Bob'), ('Charlie');