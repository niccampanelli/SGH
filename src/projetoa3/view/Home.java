package projetoa3.view;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.border.*;
import projetoa3.util.ProgramDefaults;

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
    private final JLabel calendarLabel, peopleLabel, userLabel;
    private Image calendarIcon, peopleIcon, userIcon;
    private final BoxLayout pacientesLayout, consultasLayout, usuariosLayout, sairLayout;
    
    /**
     * Construtor padrão
     */
    public Home(){
        
        // Tenta pegar os ícones dos botões
        try{
            URL calendarUrl = getClass().getResource("/projetoa3/util/icons/calendarIcon.png");
            URL peopleUrl = getClass().getResource("/projetoa3/util/icons/peopleIcon.png");
            URL userUrl = getClass().getResource("/projetoa3/util/icons/userIcon.png");
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
        pacientesButton.setBorder(new EmptyBorder(20, 20, 20, 20));
        pacientesButton.setBackground(ProgramDefaults.getBaseColorDark());
        pacientesLayout = new BoxLayout(pacientesButton, BoxLayout.Y_AXIS);
        pacientesButton.setLayout(pacientesLayout);
        
        peopleLabel = new JLabel(new ImageIcon(peopleIcon));
        
        pacientesLabel = new JLabel("Pacientes");
        pacientesLabel.setFont(new Font(Font.SANS_SERIF, 1, 18));
        pacientesLabel.setForeground(new Color(255, 255, 255));
        
        pacientesCount = new JLabel("05");
        pacientesCount.setFont(new Font(Font.SANS_SERIF, 1, 30));
        pacientesCount.setForeground(new Color(255, 255, 255));
        
        pacientesButton.add(peopleLabel);
        pacientesButton.add(Box.createVerticalGlue());
        pacientesButton.add(pacientesLabel);
        pacientesButton.add(pacientesCount);
        
        consultasButton = new JPanel();
        consultasButton.setBorder(new EmptyBorder(20, 20, 20, 20));
        consultasButton.setBackground(ProgramDefaults.getBaseColorDark());
        consultasLayout = new BoxLayout(consultasButton, BoxLayout.Y_AXIS);
        consultasButton.setLayout(consultasLayout);
        
        calendarLabel = new JLabel(new ImageIcon(calendarIcon));
        
        consultasLabel = new JLabel("Consultas");
        consultasLabel.setFont(new Font(Font.SANS_SERIF, 1, 18));
        consultasLabel.setForeground(new Color(255, 255, 255));
        
        consultasCount = new JLabel("16");
        consultasCount.setFont(new Font(Font.SANS_SERIF, 1, 30));
        consultasCount.setForeground(new Color(255, 255, 255));
        
        consultasButton.add(calendarLabel);
        consultasButton.add(Box.createVerticalGlue());
        consultasButton.add(consultasLabel);
        consultasButton.add(consultasCount);
        
        usuariosButton = new JPanel();
        usuariosButton.setBorder(new EmptyBorder(20, 20, 20, 20));
        usuariosButton.setBackground(ProgramDefaults.getBaseColorDark());
        usuariosLayout = new BoxLayout(usuariosButton, BoxLayout.Y_AXIS);
        usuariosButton.setLayout(usuariosLayout);
        
        userLabel = new JLabel(new ImageIcon(userIcon));
        
        usuariosLabel = new JLabel("Usuarios");
        usuariosLabel.setFont(new Font(Font.SANS_SERIF, 1, 18));
        usuariosLabel.setForeground(new Color(255, 255, 255));
        
        usuariosCount = new JLabel("120");
        usuariosCount.setFont(new Font(Font.SANS_SERIF, 1, 30));
        usuariosCount.setForeground(new Color(255, 255, 255));
        
        usuariosButton.add(userLabel);
        usuariosButton.add(Box.createVerticalGlue());
        usuariosButton.add(usuariosLabel);
        usuariosButton.add(usuariosCount);
        
        sairButton = new JPanel();
        sairButton.setBorder(new EmptyBorder(20, 20, 20, 20));
        sairButton.setBackground(ProgramDefaults.getBaseColorDark());
        sairLayout = new BoxLayout(sairButton, BoxLayout.Y_AXIS);
        sairButton.setLayout(sairLayout);
        
        sairLabel = new JLabel("Sair");
        sairLabel.setFont(new Font(Font.SANS_SERIF, 1, 18));
        sairLabel.setForeground(new Color(255, 255, 255));
        
        sairButton.add(sairLabel);
        
        buttonPanel = new JPanel();
        buttonLayout = new GridLayout(1, 4, 20, 0);
        buttonPanel.setMaximumSize(new Dimension(buttonPanel.getMaximumSize().width, 200));
        buttonPanel.setBackground(null);
        buttonPanel.setAlignmentX(0.0f);
        buttonPanel.setLayout(buttonLayout);
        buttonPanel.add(pacientesButton);
        buttonPanel.add(consultasButton);
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
