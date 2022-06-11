package sgh.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.border.*;
import sgh.controller.ConsultaController;
import sgh.controller.PacienteController;
import sgh.controller.SessionController;
import sgh.controller.UserController;
import sgh.util.ProgramDefaults;
import sgh.util.Resultado;
import sgh.view.Consultas.Consultas;
import sgh.view.Pacientes.Pacientes;

/**
 * Inteface de início
 * @author Nicholas Campanelli
 */
public class Home extends JPanel{
    
    // Componentes da interface
    private final GridLayout buttonLayout;
    private final JLabel title;
    private final JPanel buttonPanel;
    private final JPanel pacientesButton, consultasButton, usuariosButton, sairButton;
    private final JLabel pacientesLabel, consultasLabel, usuariosLabel, sairLabel;
    private final JLabel pacientesCount, consultasCount, usuariosCount;
    private final JLabel arrowLabel, calendarLabel, peopleLabel, userLabel;
    private Image arrowIcon, calendarIcon, peopleIcon, userIcon;
    private final BoxLayout pacientesLayout, consultasLayout, usuariosLayout, sairLayout;
    
    /**
     * Construtor padrão
     */
    public Home(Principal callingFrame){
        
        // Tenta pegar os ícones dos botões
        try{
            URL arrowUrl = getClass().getResource("/sgh/util/icons/arrowIcon.png");
            URL calendarUrl = getClass().getResource("/sgh/util/icons/calendarIcon.png");
            URL peopleUrl = getClass().getResource("/sgh/util/icons/peopleIcon.png");
            URL userUrl = getClass().getResource("/sgh/util/icons/userIcon.png");
            arrowIcon = ImageIO.read(arrowUrl);
            calendarIcon = ImageIO.read(calendarUrl);
            peopleIcon = ImageIO.read(peopleUrl);
            userIcon = ImageIO.read(userUrl);
        }
        catch (IOException ex) {
            System.out.print(ex);
        }
        
        title = new JLabel("Bem-vindo, "+ProgramDefaults.getUserName()+"!");
        title.setFont(new Font(Font.SANS_SERIF, 1, 30));
                
        pacientesButton = new JPanel();
        pacientesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pacientesButton.setBorder(new EmptyBorder(20, 20, 20, 20));
        pacientesButton.setBackground(ProgramDefaults.getBaseColorDark());
        pacientesLayout = new BoxLayout(pacientesButton, BoxLayout.Y_AXIS);
        pacientesButton.setLayout(pacientesLayout);
        pacientesButton.addMouseListener(new MouseListener(){
            @Override
            public void mouseEntered(MouseEvent e){
            }
            
            @Override
            public void mouseExited(MouseEvent e){
            }
            
            @Override
            public void mousePressed(MouseEvent e){
            }
            
            @Override
            public void mouseClicked(MouseEvent e){
                
                callingFrame.setTitle("Pacientes - SGH"); // Define titulo do JFrame
                callingFrame.setCurrentPanel(new Pacientes()); // Instancia a tela Pacientes e define como tela principal
                callingFrame.setCurrentPanelIndex(1); // Define o índice da tela
                callingFrame.buildGUI(); // Constrói a tela
            }
            
            @Override
            public void mouseReleased(MouseEvent e){
            }
        });
        
        peopleLabel = new JLabel(new ImageIcon(peopleIcon));
        
        pacientesLabel = new JLabel("Pacientes");
        pacientesLabel.setFont(new Font(Font.SANS_SERIF, 1, 18));
        pacientesLabel.setForeground(new Color(255, 255, 255));
        
        ArrayList<String> pacientesCountRes = PacienteController.readPaciente("COUNT(*)", "1", "1");
        String pacientesCountText = "0";
        
        if(!pacientesCountRes.isEmpty())
            pacientesCountText = pacientesCountRes.get(0);
        
        pacientesCount = new JLabel(pacientesCountText);
        pacientesCount.setFont(new Font(Font.SANS_SERIF, 1, 30));
        pacientesCount.setForeground(new Color(255, 255, 255));
        
        pacientesButton.add(peopleLabel);
        pacientesButton.add(Box.createVerticalGlue());
        pacientesButton.add(pacientesLabel);
        pacientesButton.add(pacientesCount);
        
        consultasButton = new JPanel();
        consultasButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        consultasButton.setBorder(new EmptyBorder(20, 20, 20, 20));
        consultasButton.setBackground(ProgramDefaults.getBaseColorDark());
        consultasLayout = new BoxLayout(consultasButton, BoxLayout.Y_AXIS);
        consultasButton.setLayout(consultasLayout);
        consultasButton.addMouseListener(new MouseListener(){
            @Override
            public void mouseEntered(MouseEvent e){
            }
            
            @Override
            public void mouseExited(MouseEvent e){
            }
            
            @Override
            public void mousePressed(MouseEvent e){
            }
            
            @Override
            public void mouseClicked(MouseEvent e){
                callingFrame.setTitle("Consultas - Cadastro de Consultas"); // Define titulo do JFrame
                callingFrame.setCurrentPanel(new Consultas()); // Instancia a tela Consultas e define como tela principal
                callingFrame.setCurrentPanelIndex(2); // Define o índice da tela
                callingFrame.buildGUI(); // Constrói a tela
            }
            
            @Override
            public void mouseReleased(MouseEvent e){
            }
        });
        
        calendarLabel = new JLabel(new ImageIcon(calendarIcon));
        
        consultasLabel = new JLabel("Consultas");
        consultasLabel.setFont(new Font(Font.SANS_SERIF, 1, 18));
        consultasLabel.setForeground(new Color(255, 255, 255));
        
        ArrayList<String> consultasCountRes = ConsultaController.readConsulta("COUNT(*)", "1", "1");
        String consultasCountText = "0";
        
        if(!consultasCountRes.isEmpty())
            consultasCountText = consultasCountRes.get(0);
        
        consultasCount = new JLabel(consultasCountText);
        consultasCount.setFont(new Font(Font.SANS_SERIF, 1, 30));
        consultasCount.setForeground(new Color(255, 255, 255));
        
        consultasButton.add(calendarLabel);
        consultasButton.add(Box.createVerticalGlue());
        consultasButton.add(consultasLabel);
        consultasButton.add(consultasCount);
        
        usuariosButton = new JPanel();
        usuariosButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        usuariosButton.setBorder(new EmptyBorder(20, 20, 20, 20));
        usuariosButton.setBackground(ProgramDefaults.getBaseColorDark());
        usuariosLayout = new BoxLayout(usuariosButton, BoxLayout.Y_AXIS);
        usuariosButton.setLayout(usuariosLayout);
        usuariosButton.addMouseListener(new MouseListener(){
            @Override
            public void mouseEntered(MouseEvent e){
            }
            
            @Override
            public void mouseExited(MouseEvent e){
            }
            
            @Override
            public void mousePressed(MouseEvent e){
            }
            
            @Override
            public void mouseClicked(MouseEvent e){
                callingFrame.setTitle("Usuários - Cadastro de Consultas"); // Define titulo do JFrame
                callingFrame.setCurrentPanel(new Usuarios()); // Instancia a tela Usuários e define como tela principal
                callingFrame.setCurrentPanelIndex(3); // Define o índice da tela
                callingFrame.buildGUI(); // Constrói a tela
            }
            
            @Override
            public void mouseReleased(MouseEvent e){
            }
        });
        
        userLabel = new JLabel(new ImageIcon(userIcon));
        
        usuariosLabel = new JLabel("Usuarios");
        usuariosLabel.setFont(new Font(Font.SANS_SERIF, 1, 18));
        usuariosLabel.setForeground(new Color(255, 255, 255));
        
        ArrayList<String> usuariosCountRes = UserController.readUser("COUNT(*)", "1", "1");
        String usuariosCountText = "0";
        
        if(!usuariosCountRes.isEmpty())
            usuariosCountText = usuariosCountRes.get(0);
        
        usuariosCount = new JLabel(usuariosCountText);
        usuariosCount.setFont(new Font(Font.SANS_SERIF, 1, 30));
        usuariosCount.setForeground(new Color(255, 255, 255));
        
        usuariosButton.add(userLabel);
        usuariosButton.add(Box.createVerticalGlue());
        usuariosButton.add(usuariosLabel);
        usuariosButton.add(usuariosCount);
        
        sairButton = new JPanel();
        sairButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sairButton.setBorder(new EmptyBorder(20, 20, 20, 20));
        sairButton.setBackground(new Color(255, 100, 100));
        sairLayout = new BoxLayout(sairButton, BoxLayout.Y_AXIS);
        sairButton.setLayout(sairLayout);
        sairButton.addMouseListener(new MouseListener(){
            @Override
            public void mouseEntered(MouseEvent e){
            }
            
            @Override
            public void mouseExited(MouseEvent e){
            }
            
            @Override
            public void mousePressed(MouseEvent e){
            }
            
            @Override
            public void mouseClicked(MouseEvent e){
                    
                if(SessionController.delete()){
                    Login login = new Login();
                    login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    callingFrame.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Não foi possível encerrar a sessão. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e){
            }
        });
        
        sairLabel = new JLabel("Sair");
        sairLabel.setFont(new Font(Font.SANS_SERIF, 1, 18));
        sairLabel.setForeground(new Color(255, 255, 255));
        
        arrowLabel = new JLabel(new ImageIcon(arrowIcon));
        
        sairButton.add(arrowLabel);
        sairButton.add(Box.createVerticalGlue());
        sairButton.add(sairLabel);
        
        buttonPanel = new JPanel();
        buttonLayout = new GridLayout(1, 4, 20, 0);
        buttonPanel.setMaximumSize(new Dimension(buttonPanel.getMaximumSize().width, 200));
        buttonPanel.setBackground(null);
        buttonPanel.setAlignmentX(0.0f);
        buttonPanel.setLayout(buttonLayout);
        buttonPanel.add(pacientesButton);
        buttonPanel.add(consultasButton);
        
        if(ProgramDefaults.getUserType() != 3)
            buttonPanel.add(usuariosButton);
        
        buttonPanel.add(sairButton);
        
        // Adiciona os elementos ao painel
        add(title);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(buttonPanel);
        
        setBorder(new EmptyBorder(40, 40, 40, 40));
        setBackground(new Color(255, 255, 255));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
