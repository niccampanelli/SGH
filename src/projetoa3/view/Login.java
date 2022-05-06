package projetoa3.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.border.*;

public class Login extends JFrame{
    
    // Componentes da tela
    private BoxLayout canvasLayout, loginLayout;
    private BorderLayout wrapLayout;
    private JPanel wrapPanel, loginPanel, otherPanel;
    private JLabel bannerLabel, title, loginLabel, senhaLabel;
    private JTextField loginField, senhaField;
    private JButton button;
    private Image loginBanner;
    
    // Variáveis de lógica
    private String login, senha;
    
    // Lógica de login
    private void login(){
        
        login = loginField.getText();
        senha = senhaField.getText();
        
        if(!"".equals(login) && !"".equals(senha)){
            if(getExtendedState() == JFrame.MAXIMIZED_BOTH || getExtendedState() == JFrame.MAXIMIZED_HORIZ || getExtendedState() == JFrame.MAXIMIZED_VERT){
                Principal home = new Principal(getExtendedState());
            home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
            else{
                Principal home = new Principal(getSize());
                home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
            dispose();
        }
        else{
            System.out.println("Erro");
        }
    }
    
    // Construtor
    public Login(){
        super("Login");
        
        try{
            URL url = getClass().getResource("../resources/loginBanner.jpg");
            loginBanner = ImageIO.read(url);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        Container canvas = getContentPane();
        canvas.setBackground(new Color(255, 255, 255));
        
        canvasLayout = new BoxLayout(canvas, BoxLayout.PAGE_AXIS);
        wrapLayout = new BorderLayout();
        
        wrapPanel = new JPanel();
        wrapPanel.setLayout(wrapLayout);
        
        otherPanel = new LoginBannerPanel(loginBanner);
        
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
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(0.0f);
        button.setPreferredSize(new Dimension(300, 40));
        button.setMaximumSize(new Dimension(300, 40));
        button.setBackground(new Color(15, 140, 190));
        button.setForeground(new Color(255, 255, 255));
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
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
        
        setMinimumSize(new Dimension(854, 480));
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

class LoginBannerPanel extends JPanel {

    private Image backgroundImage;
    
    public LoginBannerPanel(Image img){
        backgroundImage = img;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(this.getWidth() >= this.getHeight()){
            g.drawImage(backgroundImage,
                        0,
                        -200,
                        this.getWidth(),
                        (int) (this.getWidth()*1.6),
                        this
            );
        }
        else{
            g.drawImage(backgroundImage,
                        0,
                        -200,
                        this.getHeight(),
                        (int) (this.getHeight()*1.6),
                        this
            );
        }
        
    }
}
