import java.io.*;
import java.nio.file.*;
import java.util.*;

public class RubricaDAO {

    private static final String FOLDER = "informazioni";

    public static Vector<Persona> loadFromFolder() throws IOException {
        Vector<Persona> persone = new Vector<>();
        File dir = new File( FOLDER );
        if (!dir.exists()) {
            dir.mkdir();
            return persone;
        }
        File[] files = dir.listFiles( ( d, name ) -> name.toLowerCase().endsWith( ".txt" ) );
        if ( files == null ) return persone;

        for ( File f : files ) {

            try ( BufferedReader br = new BufferedReader( new FileReader( f ) ) ) {
                String nome = br.readLine();
                String cognome = br.readLine();
                String indirizzo = br.readLine();
                String telefono = br.readLine();
                int eta = Integer.parseInt(br.readLine());
                persone.add( new Persona( nome, cognome, indirizzo, telefono, eta) );
            } catch ( IOException e ) {
                    // puoi gestire errore o saltare la riga
                    System.err.println( "Errore nel file " + f.getName() + ": " + e.getMessage() );
            }

        }
        return persone;
    }

    public static void saveAllToFolder( Vector<Persona> persone ) throws IOException {
        File dir = new File( FOLDER );
        if ( !dir.exists() ) {
            dir.mkdir();
        }

        for ( File f : dir.listFiles() ) {
            f.delete();
        }

        Map<String, Integer> contatori = new HashMap<>();

        for ( Persona p : persone ) {
            String baseName = ( p.getNome() + "-" + p.getCognome()).replaceAll("[^a-zA-Z0-9]", "_");
            int count = contatori.getOrDefault(baseName, 0);
            String fileName;
            if (count == 0) {
                fileName = baseName + ".txt";
            } else {
                fileName = baseName + "-" + count + ".txt";
            }
            contatori.put(baseName, count + 1);

            File file = new File( dir, fileName );
            try ( BufferedWriter bw = new BufferedWriter( new FileWriter( file ) ) ) {
                bw.write( p.getNome() );
                bw.newLine();
                bw.write( p.getCognome() );
                bw.newLine();
                bw.write( p.getIndirizzo() );
                bw.newLine();
                bw.write( p.getTelefono() );
                bw.newLine();
                bw.write( String.valueOf( p.getEta() ) );
                bw.newLine();
            }
        }
    }

}
