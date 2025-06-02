import javax.swing.*;
import java.util.List;
import java.awt.event.*;

public class Main {

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

        SwingUtilities.invokeLater(() -> {
            try {
                List<Utente> utenti = UtenteDAO.loadUtenti( "utenti.txt" );

                LoginWindow loginWindow = new LoginWindow( utenti );
                loginWindow.setVisible( true );

                loginWindow.addWindowListener(new WindowAdapter() {
                    @Override //quando clicco login, se va tutto bene è alto il flag e poi c'è la dipose() che chiude
                    public void windowClosed( WindowEvent e ) { //mi onviene event perchè la sola pressione di login può anche essere errata
                        if ( loginWindow.isLoginEffettuato() ) {
                            new MainWindow(); //
                        } else {
                            System.exit(0); //
                        }
                    }
                });

            } catch ( Exception e ) {
                JOptionPane.showMessageDialog( null, "Errore durante il caricamento degli utenti.", "Errore", JOptionPane.ERROR_MESSAGE );
                e.printStackTrace();
            }
        });


    }

}
