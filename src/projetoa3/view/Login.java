package projetoa3.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame{
    
    JLabel title, loginLabel, senhaLabel;
    JTextField login, senha;
    JButton button;
    
    public Login(){
        super("Login");
        
        Container canvas = getContentPane();
        canvas.setBackground(new java.awt.Color(255, 255, 255));
        
        title = new JLabel("Fazer login");
        title.setBounds(100, 120, 200, 100);
        title.setAlignmentY(JLabel.CENTER_ALIGNMENT);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, 1, 30));
        
        loginLabel = new JLabel("Login");
        loginLabel.setBounds(100, 220, 200, 40);
        
        senhaLabel = new JLabel("Senha");
        senhaLabel.setBounds(100, 300, 200, 40);
        
        login = new JTextField("");
        login.setBounds(100, 260, 200, 40);
        login.setMargin(new Insets(0, 10, 0, 10));
        
        senha = new JPasswordField("");
        senha.setBounds(100, 340, 200, 40);
        senha.setMargin(new Insets(0, 10, 0, 10));
        
        button = new JButton("Entrar");
        button.setBounds(100, 400, 200, 40);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Principal home = new Principal();
                home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dispose();
            }
        });
        
        canvas.add(title);
        canvas.add(loginLabel);
        canvas.add(login);
        canvas.add(senhaLabel);
        canvas.add(senha);
        canvas.add(button);
        
        setSize(400, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    
    public static void main(String[] args){
        
        Login login = new Login();
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
