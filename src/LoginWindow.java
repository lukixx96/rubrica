import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class LoginWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private boolean loginEffettuato = false;

    public LoginWindow( List<Utente> utenti ) {

        setTitle("Login");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        usernameField = new JTextField( 15 );
        passwordField = new JPasswordField( 15 );
        loginButton = new JButton( "LOGIN" );

        loginButton.addActionListener( e -> {
            String username = usernameField.getText().trim();
            String password = new String( passwordField.getPassword() ).trim();

            if ( UtenteDAO.verificaCredenziali( username, password, utenti ) ) {
                loginEffettuato = true;
                dispose(); // chiude la finestra di login
            } else {
                JOptionPane.showMessageDialog( this, "Login errato", "Errore", JOptionPane.ERROR_MESSAGE );
            }
        } );

        JPanel panel = new JPanel( new GridLayout( 3, 2 ) );
        panel.add( new JLabel( "Utente:" ) );
        panel.add( usernameField );
        panel.add( new JLabel( "Password:" ) );
        panel.add( passwordField );
        panel.add( new JLabel() );
        panel.add( loginButton );

        add( panel );
    }

    public boolean isLoginEffettuato() {
        return loginEffettuato;
    }
}
