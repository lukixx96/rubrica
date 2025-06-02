import java.util.*;

public class Rubrica {

    private static Rubrica instance;
    private Vector<Persona> rubrica;


    private Rubrica() {
        this.rubrica = new Vector<>();
        Persona p1 = new Persona("11","2","3","4",5 );
        Persona p2 = new Persona("12","2","3","4",5 );
        Persona p3 = new Persona("13","2","3","4",5 );
        Persona p4 = new Persona("14","2","3","4",5 );
        Persona p5 = new Persona("15","2","3","4",5 );

        this.rubrica.add( p1 );
        this.rubrica.add( p2 );
        this.rubrica.add( p3 );
        this.rubrica.add( p4 );
        this.rubrica.add( p5 );
    }

    public static synchronized Rubrica getInstance() {
        if( instance == null ) {
            instance = new Rubrica();
        }
        return instance;
    }

    public Vector<Persona> getRubrica() {
        return instance.rubrica;
    }

    public void setRubrica( Vector<Persona> rubrica ) {
        this.rubrica = rubrica;
    }

    public int getIndexPersona( Persona p ) {
        return instance.rubrica.indexOf( p );
    }

    public void aggiungiPersona( Persona p ) {
        this.rubrica.add( p );
    }

    public void rimuoviPersona( Persona p ) {
        this.rubrica.remove( p );
    };

    public void modificaPersona( Persona p, String nome, String cognome, String indirizzo, String telefono, int eta ) {
        int index = this.rubrica.indexOf( p );
        Persona temp = this.rubrica.elementAt( index );
        temp.setNome( nome );
        temp.setCognome( cognome );
        temp.setIndirizzo( indirizzo );
        temp.setTelefono( telefono );
        temp.setEta( eta );
    };

    public void caricaDaDB(RubricaMySQLAdapter adapter) {
        this.rubrica = adapter.caricaPersone();
    }

    public void salvaSuDB(RubricaMySQLAdapter adapter) {
        adapter.salvaPersone(this.rubrica);
    }

}
