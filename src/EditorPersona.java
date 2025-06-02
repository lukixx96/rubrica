import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditorPersona extends JDialog {

    private Persona  persona;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField indirizzoField;
    private JTextField telefonoField;
    private JTextField etaField;

    private boolean confermato = false;

    public boolean isConfermato() {
        return this.confermato;
    }

    public EditorPersona( JFrame parent, Persona persona ) {
        super( parent, "Editor Persona", true );
        this.persona = persona;

        setLayout( new BorderLayout() );

        // Primo pannello: campi
        JPanel fieldPanel = new JPanel( new GridLayout( 5, 2, 10, 10 ) );

        fieldPanel.add( new JLabel( "Nome:" ) );
        nomeField = new JTextField( persona.getNome() );
        fieldPanel.add( nomeField );
        fieldPanel.add( new JLabel( "Cognome:" ) );
        cognomeField = new JTextField( persona.getCognome() );
        fieldPanel.add( cognomeField );
        fieldPanel.add( new JLabel( "Indirizzo:" ) );
        indirizzoField = new JTextField( persona.getIndirizzo() );
        fieldPanel.add( indirizzoField );
        fieldPanel.add( new JLabel( "Teleono:" ) );
        telefonoField = new JTextField( persona.getTelefono() );
        fieldPanel.add( telefonoField );
        fieldPanel.add( new JLabel( "Eta:" ) );
        etaField = new JTextField( String.valueOf( persona.getEta() ) );
        fieldPanel.add( etaField );

        // Secondo pannello: bottoni
        JPanel buttonPanel = new JPanel( new FlowLayout ( FlowLayout.CENTER, 20, 20 ) );
        JButton salvaBtn = new JButton( "Salva" );
        JButton annullaBtn = new JButton( "Annulla" );

        salvaBtn.setPreferredSize(new Dimension(200, 50));
        annullaBtn.setPreferredSize(new Dimension(200, 50));

        buttonPanel.add( salvaBtn );
        buttonPanel.add( annullaBtn );

        salvaBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                persona.setNome( nomeField.getText() );
                persona.setCognome( cognomeField.getText() );
                persona.setIndirizzo( indirizzoField.getText() );
                persona.setTelefono( telefonoField.getText() );
                String etaText = etaField.getText().trim();
                while ( true ) {
                    try {
                        persona.setEta( Integer.parseInt( etaText ) );
                        break;
                    } catch ( NumberFormatException ex ) {
                        etaText = JOptionPane.showInputDialog(EditorPersona.this,
                                "Età non valida.\nInserisci un numero intero:",
                                etaText);
                        if ( etaText == null ) {
                            return;
                        }
                    }
                }
                // System.out.println( "Persona salvata: " + persona );
                confermato = true;
                JOptionPane.showMessageDialog( EditorPersona.this, "Hai salvato!" );
                dispose(); // chiudi la finestra
            }
        });

        annullaBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // chiudi senza fare nulla
            }
        });

        // Composizione pannelli
        add( fieldPanel, BorderLayout.CENTER );
        add( buttonPanel, BorderLayout.SOUTH );

        pack(); // ridimensiona secondo contenuto, poi:
        setSize(800, 600); // oppure tutto schermo se preferisci
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setLocationRelativeTo(parent); // centrata rispetto alla mainWindow
    }
}
