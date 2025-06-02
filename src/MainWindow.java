import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow extends JFrame {

    private JTable tabellaRubrica;
    private DefaultTableModel model;
    private Rubrica rubrica;
    private DatabaseConfig config;
    private RubricaMySQLAdapter sqlAdapter;

    public MainWindow() {

        rubrica = Rubrica.getInstance();
        try {
            //rubrica.setRubrica(RubricaDAO.loadFromFolder());
            config = new DatabaseConfig( "credenziali_database.properties" );
            //config = new DatabaseConfig( "C:\\Users\\Lukixx\\IdeaProjects\\Rubrica\\credenziali_database.properties" );
            sqlAdapter = new RubricaMySQLAdapter( config );
            rubrica.caricaDaDB( sqlAdapter );
        } catch (IOException e) {
            return;
        }

        setTitle("Gestione Rubrica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // tutto schermo

        // SETTING TABELLA
        String[] fields = { "Nome", "Cognome", "Indirizzo", "Telefono", "Età" };
        model = new DefaultTableModel(fields, 0);
        tabellaRubrica = new JTable(model);
        tabellaRubrica.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabellaRubrica.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(tabellaRubrica);

        // TOOLBAR (barra in alto)
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false); // fissa la barra in alto

        JButton nuovoBtn = new JButton("Nuovo");
        JButton modificaBtn = new JButton("Modifica");
        JButton eliminaBtn = new JButton("Elimina");

        // (Opzionale) Aggiungi icone se disponibili
        try {
            nuovoBtn.setIcon(new ImageIcon("icons/nuovo.png"));
            modificaBtn.setIcon(new ImageIcon("icons/modifica.png"));
            eliminaBtn.setIcon(new ImageIcon("icons/elimina.png"));
        } catch (Exception e) {
            System.out.println("Icone non trovate, continuando senza.");
        }

        // Aggiungi bottoni alla toolbar
        toolBar.add(nuovoBtn);
        toolBar.add(modificaBtn);
        toolBar.add(eliminaBtn);

        // LISTENERS
        nuovoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Persona nuovaPersona = new Persona("", "", "", "", 0);
                EditorPersona editor = new EditorPersona(MainWindow.this, nuovaPersona);
                editor.setVisible(true);
                if (editor.isConfermato()) {
                    rubrica.aggiungiPersona(nuovaPersona);
                    aggiornaTabella();
                }
            }
        });

        modificaBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabellaRubrica.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(MainWindow.this,
                            "Seleziona una persona da modificare.",
                            "Nessuna selezione",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Persona modificaPersona = rubrica.getRubrica().get(selectedRow);
                EditorPersona editor = new EditorPersona(MainWindow.this, modificaPersona);
                editor.setVisible(true);
                aggiornaTabella();
            }
        });

        eliminaBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabellaRubrica.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(MainWindow.this,
                            "Seleziona una persona da eliminare.",
                            "Nessuna selezione",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Persona eliminaPersona = rubrica.getRubrica().get(selectedRow);
                int conferma = JOptionPane.showConfirmDialog(
                        MainWindow.this,
                        "Eliminare la persona " + eliminaPersona.getNome() + " " + eliminaPersona.getCognome() + "?",
                        "Conferma eliminazione",
                        JOptionPane.YES_NO_OPTION
                );
                if (conferma == JOptionPane.YES_OPTION) {
                    Rubrica.getInstance().rimuoviPersona(eliminaPersona);
                    aggiornaTabella();
                }
            }
        });

        // LAYOUT
        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH);       // 👈 NUOVO
        add(scrollPane, BorderLayout.CENTER);   // tabella centrale
        aggiornaTabella();

        setVisible(true);
    }

    public void aggiornaTabella() {
        model.setRowCount(0);
        for (Persona p : Rubrica.getInstance().getRubrica()) {
            Object[] row = {
                    p.getNome(),
                    p.getCognome(),
                    p.getIndirizzo(),
                    p.getTelefono(),
                    p.getEta()
            };
            model.addRow(row);
        }/*
        try {
            RubricaDAO.saveAllToFolder(rubrica.getRubrica());
        } catch (IOException e) {
            return;
        }*/
        rubrica.salvaSuDB( sqlAdapter );

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
    }
    */
}
