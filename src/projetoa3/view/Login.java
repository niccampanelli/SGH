package projetoa3.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Login extends JFrame{
    
    // Componentes da tela
    private BoxLayout canvasLayout, loginLayout;
    private BorderLayout wrapLayout;
    private JPanel wrapPanel, loginPanel, otherPanel;
    private JLabel title, loginLabel, senhaLabel;
    private JTextField loginField, senhaField;
    private JButton button;
    
    // Variáveis de lógica
    private String login, senha;
    
    // Lógica de login
    private void login(){
        login = loginField.getText();
        senha = senhaField.getText();
        
        if(!"".equals(login) && !"".equals(senha)){
            Principal home = new Principal();
            home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dispose();
        }
        else{
            System.out.println("Erro");
        }
    }
    
    // Construtor
    public Login(){
        super("Login");
        
        Container canvas = getContentPane();
        canvas.setBackground(new Color(255, 255, 255));
        
        canvasLayout = new BoxLayout(canvas, BoxLayout.PAGE_AXIS);
        wrapLayout = new BorderLayout();
        
        wrapPanel = new JPanel();
        wrapPanel.setLayout(wrapLayout);
        
        otherPanel = new JPanel();
        otherPanel.setBackground(new Color(203, 238, 67));
        
        loginPanel = new JPanel();
        loginLayout = new BoxLayout(loginPanel, BoxLayout.Y_AXIS);
        
        loginPanel.setLayout(loginLayout);
        loginPanel.setBackground(new Color(255, 255, 255));
        loginPanel.setBorder(new EmptyBorder(80, 80, 80, 80));

        title = new JLabel("Fazer login");
        title.setPreferredSize(new Dimension(300, 40));
        title.setMaximumSize(new Dimension(300, 40));
        title.setAlignmentX(0.0f);
        title.setHorizontalAlignment(JLabel.LEFT);
        title.setFont(new Font(Font.SANS_SERIF, 1, 30));
        
        loginLabel = new JLabel("Login");
        loginLabel.setAlignmentX(0.0f);
        loginLabel.setPreferredSize(new Dimension(300, 20));
        loginLabel.setMaximumSize(new Dimension(300, 20));
        
        senhaLabel = new JLabel("Senha");
        senhaLabel.setAlignmentX(0.0f);
        senhaLabel.setPreferredSize(new Dimension(300, 20));
        senhaLabel.setMaximumSize(new Dimension(300, 20));
        
        loginField = new JTextField();
        loginField.setAlignmentX(0.0f);
        loginField.setPreferredSize(new Dimension(300, 40));
        loginField.setMaximumSize(new Dimension(300, 40));
        loginField.setMargin(new Insets(0, 10, 0, 10));
        
        senhaField = new JPasswordField();
        senhaField.setAlignmentX(0.0f);
        senhaField.setPreferredSize(new Dimension(300, 40));
        senhaField.setMaximumSize(new Dimension(300, 40));
        senhaField.setMargin(new Insets(0, 10, 0, 10));
        
        button = new JButton("Entrar");
        button.setAlignmentX(0.0f);
        button.setPreferredSize(new Dimension(300, 40));
        button.setMaximumSize(new Dimension(300, 40));
        button.setBackground(new Color(250, 200, 10));
        button.setBorder(null);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                login();
            }
        });
        
        loginPanel.add(title);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(loginLabel);
        loginPanel.add(loginField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(senhaLabel);
        loginPanel.add(senhaField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(button);
        
        wrapPanel.add(loginPanel, BorderLayout.LINE_END);
        wrapPanel.add(otherPanel, BorderLayout.CENTER);
        
        canvas.add(wrapPanel);
        
        setSize(1280, 720);
        setLayout(canvasLayout);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }
    
    public static void main(String[] args){
        
        Login login = new Login();
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
