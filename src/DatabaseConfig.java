import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {
    private String host;
    private String porta;
    private String database;
    private String username;
    private String password;

    public DatabaseConfig(String pathProperties) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(pathProperties)) {
            System.out.println("File .properties caricato da: " + new File(pathProperties).getAbsolutePath());
            if( !new File(pathProperties).exists() ) { return;}
            props.load(fis);
        }

        host = props.getProperty("host");
        porta = props.getProperty("porta");
        database = props.getProperty("database");
        username = props.getProperty("username");
        password = props.getProperty("password");
    }

    public String getUrl() {
        return "jdbc:mysql://" + host + ":" + porta + "/" + database;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
