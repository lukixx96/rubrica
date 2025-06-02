import java.sql.*;

public class TestMySQL {
    public static void main(String[] args) {
        try {
            // Parametri di connessione
            String url = "jdbc:mysql://localhost:3306/rubrica";
            String username = "root";
            String password = "root";

            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connessione riuscita!");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Errore di connessione:");
            e.printStackTrace();
        }
    }
}