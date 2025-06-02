import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnessione {
    public static void main(String[] args) {
        try {
            DatabaseConfig config = new DatabaseConfig("credenziali_database.properties");

            Connection conn = DriverManager.getConnection(
                    config.getUrl(), config.getUsername(), config.getPassword()
            );
            System.out.println("Connessione al database riuscita.");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
