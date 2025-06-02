import java.io.*;
import java.util.Vector;

public class RubricaDAO {

    public static Vector<Persona> loadFromFile( String filePath ) throws IOException {
        Vector<Persona> persone = new Vector<>();

        try ( BufferedReader br = new BufferedReader( new FileReader( filePath ) ) ) {
            String line;
            while ( ( line = br.readLine() ) != null ) {
                String[] campi = line.split(";");
                if (campi.length == 5) {
                    String nome = campi[0];
                    String cognome = campi[1];
                    String indirizzo = campi[2];
                    String telefono = campi[3];
                    int eta;
                    try {
                        eta = Integer.parseInt(campi[4]);
                    } catch (NumberFormatException e) {
                        eta = 0; // valore di default o gestisci diversamente
                    }

                    Persona p = new Persona( nome, cognome, indirizzo, telefono, eta );
                    persone.add( p );
                } else {
                    // puoi gestire errore o saltare la riga
                    System.err.println("Formato errato riga: " + line);
                }
            }
        }

        return persone;
    }

    public static void saveToFile( String filePath, Vector<Persona> persone ) throws IOException {
        try ( BufferedWriter bw = new BufferedWriter( new FileWriter( filePath ) ) ) {
            for ( Persona p : persone ) {
                String line = p.getNome() + ";" + p.getCognome() + ";" + p.getIndirizzo() + ";" + p.getTelefono() + ";" + p.getEta();
                bw.write( line );
                bw.newLine();
            }
        }
    }
}
