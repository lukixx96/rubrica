import java.sql.*;
import java.util.Vector;

public class RubricaMySQLAdapter {
    private final String url, user, password;

    public RubricaMySQLAdapter(DatabaseConfig config) {
        this.url = config.getUrl();
        this.user = config.getUsername();
        this.password = config.getPassword();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public Vector<Persona> caricaPersone() {
        Vector<Persona> persone = new Vector<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM persone")) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String indirizzo = rs.getString("indirizzo");
                String telefono = rs.getString("telefono");
                int eta = rs.getInt("eta");
                Persona p = new Persona(nome, cognome, indirizzo, telefono, eta);
                persone.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persone;
    }

    public void salvaPersone(Vector<Persona> persone) {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM persone");
            for (Persona p : persone) {
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO persone (nome, cognome, indirizzo, telefono, eta) VALUES (?, ?, ?, ?, ?)");
                ps.setString(1, p.getNome());
                ps.setString(2, p.getCognome());
                ps.setString(3, p.getIndirizzo());
                ps.setString(4, p.getTelefono());
                ps.setInt(5, p.getEta());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
