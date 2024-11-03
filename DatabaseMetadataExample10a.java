import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DatabaseMetadataExample {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/db"; // Replace 'db' with your database name
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.println("Connected to the database!");

            // Retrieve database metadata
            DatabaseMetaData dbMetaData = connection.getMetaData();

            // Database product information
            System.out.println("Database Product Name: " + dbMetaData.getDatabaseProductName());
            System.out.println("Database Product Version: " + dbMetaData.getDatabaseProductVersion());
            System.out.println("JDBC Driver Name: " + dbMetaData.getDriverName());
            System.out.println("JDBC Driver Version: " + dbMetaData.getDriverVersion());

            // Retrieve and list tables in the database
            System.out.println("\nTables in the database:");
            ResultSet tables = dbMetaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);

                // Retrieve columns metadata for each table
                ResultSet columns = dbMetaData.getColumns(null, null, tableName, "%");
                System.out.println("Columns in " + tableName + ":");
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    int columnSize = columns.getInt("COLUMN_SIZE");
                    System.out.println("\t" + columnName + " - " + columnType + "(" + columnSize + ")");
                }
            }

            // Retrieving metadata for a specific query
            System.out.println("\nMetadata for the 'employees' table:");
            String query = "SELECT * FROM employees";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            // Display column metadata for the 'employees' table
            int columnCount = rsMetaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Column " + i + ": " + rsMetaData.getColumnName(i) +
                                   " - " + rsMetaData.getColumnTypeName(i) +
                                   " (" + rsMetaData.getColumnDisplaySize(i) + ")");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    position VARCHAR(50),
    salary DECIMAL(10, 2)
);

INSERT INTO employees (name, position, salary) VALUES
('Alice', 'Developer', 75000),
('Bob', 'Designer', 65000),
('Charlie', 'Manager', 85000);