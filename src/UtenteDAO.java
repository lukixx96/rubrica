import java.io.*;
import java.util.*;

public class UtenteDAO {

    public static List<Utente> loadUtenti( String path ) throws IOException {
        List<Utente> utenti = new ArrayList<>();
        try ( BufferedReader br = new BufferedReader( new FileReader( path ) ) ) {
            String line;
            while ( ( line = br.readLine() ) != null ) {
                String[] credentials = line.split( "," );
                if ( credentials.length == 2 ) {
                    utenti.add( new Utente( credentials[0].trim(), credentials[1].trim() ) );
                }
            }
        }
        return utenti;
    }

    public static boolean verificaCredenziali( String username, String password, List<Utente> utenti ) {
        for ( Utente u : utenti ) {
            if ( u.getUsername().equals( username ) && u.getPassword().equals( password ) ) {
                return true;
            }
        }
        return false;
    }

}
