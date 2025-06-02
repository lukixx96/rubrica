import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow extends JFrame {

    private JTable tabellaRubrica;
    private DefaultTableModel model;
    private JPanel buttonPanel;
    private Rubrica rubrica;
    //private static final String FILE_PATH = "informazioni.txt";

    public MainWindow() {

        rubrica = Rubrica.getInstance();
        try{
            rubrica.setRubrica( RubricaDAO.loadFromFolder(  ) );
        } catch (IOException e) {
            return;
        }

        setTitle( "Gestione Rubrica" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setExtendedState( JFrame.MAXIMIZED_BOTH ); // tutto schermo

        // SETTING TABELLA
        String[] fields = { "Nome", "Cognome", "Indirizzo", "Telefono", "Età" };
        model = new DefaultTableModel( fields, 0 );
        tabellaRubrica = new JTable( model );
        tabellaRubrica.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        tabellaRubrica.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane( tabellaRubrica );

        // PANNELLO BOTTONI
        buttonPanel = new JPanel();
        buttonPanel.setLayout( new FlowLayout( FlowLayout.CENTER, 20, 10 ) );

        JButton nuovoBtn = new JButton( "Nuovo" );
        JButton modificaBtn = new JButton( "Modifica" );
        JButton eliminaBtn = new JButton( "Elimina" );

        nuovoBtn.setPreferredSize( new Dimension( 200, 50 ) );
        modificaBtn.setPreferredSize( new Dimension( 200, 50 ) );
        eliminaBtn.setPreferredSize( new Dimension( 200, 50 ) );

        buttonPanel.add( nuovoBtn );
        buttonPanel.add( modificaBtn );
        buttonPanel.add( eliminaBtn );

        nuovoBtn.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                Persona nuovaPersona = new Persona("", "", "", "", 0);
                EditorPersona editor = new EditorPersona( MainWindow.this, nuovaPersona );
                editor.setVisible( true );
                if (editor.isConfermato()) {
                    rubrica.aggiungiPersona(nuovaPersona);
                    aggiornaTabella();
                }
                // System.out.println( "Nuova persona: " + nuovaPersona + "\nid: " + rubrica.getIndexPersona( nuovaPersona ) );
            }
        }
        );

        modificaBtn.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                int selectedRow = tabellaRubrica.getSelectedRow();
                if ( selectedRow == -1 ) {
                    JOptionPane.showMessageDialog(MainWindow.this,
                            "Seleziona una persona da modificare.",
                            "Nessuna selezione",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Persona modificaPersona = modificaPersona = rubrica.getRubrica().get( selectedRow );
                EditorPersona editor = new EditorPersona( MainWindow.this, modificaPersona );
                editor.setVisible( true );
                aggiornaTabella();
            }
        }
        );

        eliminaBtn.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                int selectedRow = tabellaRubrica.getSelectedRow();
                if ( selectedRow == -1 ) {
                    JOptionPane.showMessageDialog(MainWindow.this,
                            "Seleziona una persona da eliminare.",
                            "Nessuna selezione",
                            JOptionPane.WARNING_MESSAGE );
                    return;
                }
                Persona eliminaPersona = rubrica.getRubrica().get( selectedRow );
                int conferma = JOptionPane.showConfirmDialog(
                        MainWindow.this,
                        "Eliminare la persona " + eliminaPersona.getNome() + " " + eliminaPersona.getCognome() + "?",
                        "Conferma eliminazione",
                        JOptionPane.YES_NO_OPTION
                );
                if ( conferma == JOptionPane.YES_OPTION ) {
                    Rubrica.getInstance().rimuoviPersona( eliminaPersona );
                    aggiornaTabella();
                }
            }
        }
        );

        setLayout( new BorderLayout() );
        add( scrollPane, BorderLayout.CENTER );
        add( buttonPanel, BorderLayout.SOUTH );
        aggiornaTabella();

        setVisible( true );
    }

    public void aggiornaTabella() {
        model.setRowCount(0);
        for ( Persona p : Rubrica.getInstance().getRubrica() ) {
            Object[] row = {
                    p.getNome(),
                    p.getCognome(),
                    p.getIndirizzo(),
                    p.getTelefono(),
                    p.getEta()
            };
            model.addRow( row );
        }
        try{
            RubricaDAO.saveAllToFolder( rubrica.getRubrica() );
        } catch (IOException e) {
            return;
        }
    }
/*
    public static void main(String[] args) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new MainWindow());
    }*/
}
