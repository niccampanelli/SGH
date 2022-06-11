package sgh.view;
import sgh.view.Components.CustomButton;
import sgh.view.Components.CustomField;
import sgh.view.Components.CustomPasswordField;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import javax.imageio.ImageIO;
import javax.swing.border.*;
import sgh.controller.SessionController;
import sgh.controller.UserController;
import sgh.util.ProgramDefaults;
import sgh.util.Resultado;
import sgh.util.database.ConnectionClass;

/**
 * Inteface de login
 * @author Nicholas Campanelli
 */
public class Login extends JFrame{
    
    // Componentes da interface
    private final BoxLayout canvasLayout, loginLayout;
    private final BorderLayout wrapLayout;
    private final JPanel wrapPanel, loginPanel, otherPanel;
    private final JLabel title, loginLabel, senhaLabel, erroLabel;
    private final JTextField loginField;
    private final JPasswordField senhaField;
    private final CustomButton button;
    private Image loginBanner, appIcon;
    
    // Variáveis de lógica
    private String login;
    private char[] senha;
    
    /**
     * Lógica de verificação login (pode ser substituida depois)
     */
    private void login(){
        
        erroLabel.setText("");
        
        // Pega o texto dos campos
        login = loginField.getText();
        senha = senhaField.getPassword();
        
        // Verifica se os valores existem
        if(!"".equals(login) && senha != null && senha.length != 0){
            
            Resultado res = SessionController.create(login, String.valueOf(senha));
            
            if(res.isSucesso() == true){
                
                Resultado nomeRes = UserController.readUser(Integer.parseInt(res.getCorpo().toString()), "nome");
                Resultado tipoRes = UserController.readUser(Integer.parseInt(res.getCorpo().toString()), "tipo");

                if(tipoRes.isSucesso())
                    ProgramDefaults.setUserType(Integer.parseInt(tipoRes.getCorpo().toString()));

                if(nomeRes.isSucesso())
                    ProgramDefaults.setUserName(nomeRes.getCorpo().toString());
                
                ProgramDefaults.setUserType(Integer.parseInt(res.getCorpo().toString()));
                
                // Verifica se a tela está maximizada (questão de estética)
                if(getExtendedState() == JFrame.MAXIMIZED_BOTH || getExtendedState() == JFrame.MAXIMIZED_HORIZ || getExtendedState() == JFrame.MAXIMIZED_VERT){

                    // Instancia a tela principal e passa como parâmetro se a tela está maximizada
                    Principal principal = new Principal(getExtendedState());
                    principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                else{

                    // Instancia a tela principal e passa como parâmetro o tamanho da tela
                    Principal principal = new Principal(getSize());
                    principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                
                // Fecha a tela de login
                dispose();
            }
            else{
                erroLabel.setText(res.getMensagem());
            }
        }
        else{
            erroLabel.setText("E-mail senha devem ser preenchidos.");
        }
    }
    
    /**
     * Construtor padrão
     */
    public Login(){
        super("Login");
        
        // Tenta pegar o banner do login
        try{
            URL url = getClass().getResource("/sgh/util/icons/loginBanner.jpg");
            URL appIconUrl = getClass().getResource("/sgh/util/icons/sgh_logo.png");
            loginBanner = ImageIO.read(url);
            appIcon = ImageIO.read(appIconUrl);
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
        
        erroLabel = new JLabel("");
        erroLabel.setAlignmentX(0.0f);
        erroLabel.setHorizontalAlignment(JLabel.LEFT);
        erroLabel.setForeground(new Color(255, 110, 130));
        
        loginLabel = new JLabel("Login");
        loginLabel.setAlignmentX(0.0f);
        loginLabel.setPreferredSize(new Dimension(300, 20));
        loginLabel.setMaximumSize(new Dimension(300, 20));
        
        senhaLabel = new JLabel("Senha");
        senhaLabel.setAlignmentX(0.0f);
        senhaLabel.setPreferredSize(new Dimension(300, 20));
        senhaLabel.setMaximumSize(new Dimension(300, 20));
        
        loginField = new CustomField("Insira seu login");
        loginField.setAlignmentX(0.0f);
        loginField.setPreferredSize(new Dimension(300, 40));
        loginField.setMaximumSize(new Dimension(300, 40));
        
        senhaField = new CustomPasswordField("Insira sua senha");
        senhaField.setAlignmentX(0.0f);
        senhaField.setPreferredSize(new Dimension(300, 40));
        senhaField.setMaximumSize(new Dimension(300, 40));
        
        button = new CustomButton("Entrar");
        button.setAlignmentX(0.0f);
        button.setPreferredSize(new Dimension(300, 40));
        button.setMaximumSize(new Dimension(300, 40));
        
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                login();
            }
        });
        
        loginPanel.add(title);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(erroLabel);
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
        setIconImage(appIcon);
    }
    
    public static void main(String[] args){
        // Inicia a conexão com o banco de dados
        ConnectionClass.connect();
        
        Resultado tempRes = SessionController.validateTemp();
        
        if(tempRes.isSucesso()){
            Resultado nomeRes = UserController.readUser(Integer.parseInt(tempRes.getCorpo().toString()), "nome");
            Resultado tipoRes = UserController.readUser(Integer.parseInt(tempRes.getCorpo().toString()), "tipo");
            
            if(tipoRes.isSucesso())
                ProgramDefaults.setUserType(Integer.parseInt(tipoRes.getCorpo().toString()));
            
            if(nomeRes.isSucesso())
                ProgramDefaults.setUserName(nomeRes.getCorpo().toString());
            
            
            Principal principal = new Principal();
            principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        else{
            Login login = new Login();
            login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
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
