package sgh.view.Components;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import sgh.util.ProgramDefaults;
import sgh.view.Principal;
import sgh.view.Home;
import sgh.view.Pacientes.Pacientes;
import sgh.view.Consultas.Consultas;
import sgh.view.Usuarios;

/**
 * Barra de navegação lateral
 * @author Nicholas Campanelli
 */
public class Navbar extends JPanel{
    
    // Componentes da interface
    // Os botões são declarados como final para impedir que sejam atribuidas mais de uma vez
    private final JButton logoButton, homeButton, consultasButton, pacientesButton, usuariosButton;
    private Image logoImage, houseIcon, calendarIcon, peopleIcon, userIcon;
    
    /**
     * Construtor padrão
     * @param callingFrame - JFrame onde a navbar está sendo mostrada.
     * @param currentPanelIndex - Indice da tela a ser mostrada.
     */
    public Navbar(Principal callingFrame, int currentPanelIndex){
        
        // Tenta pegar os ícones dos botões
        try{
            URL logoUrl = getClass().getResource("/sgh/util/icons/sgh_logo.png");
            URL houseUrl = getClass().getResource("/sgh/util/icons/houseIcon.png");
            URL calendarUrl = getClass().getResource("/sgh/util/icons/calendarIcon.png");
            URL peopleUrl = getClass().getResource("/sgh/util/icons/peopleIcon.png");
            URL userUrl = getClass().getResource("/sgh/util/icons/userIcon.png");
            logoImage = ImageIO.read(logoUrl);
            houseIcon = ImageIO.read(houseUrl);
            calendarIcon = ImageIO.read(calendarUrl);
            peopleIcon = ImageIO.read(peopleUrl);
            userIcon = ImageIO.read(userUrl);
        }
        catch (IOException ex) {
            System.out.print(ex);
        }
        
        logoButton = new JButton();
        logoButton.setPreferredSize(new Dimension(180, 50));
        logoButton.setMaximumSize(new Dimension(180, 50));
        logoButton.setHorizontalAlignment(JButton.LEFT);
        logoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoButton.setBackground(null);
        logoButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        logoButton.setIcon(new ImageIcon(logoImage.getScaledInstance( 120, 50, Image.SCALE_FAST)));
        logoButton.setHorizontalAlignment(JButton.CENTER);
        logoButton.setIconTextGap(10);
        logoButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                callingFrame.setTitle("Cadastro de Consultas"); // Define titulo do JFrame
                callingFrame.setCurrentPanel(new Home(callingFrame)); // Instancia a tela Home e define como tela principal
                callingFrame.setCurrentPanelIndex(0); // Define o índice da tela
                callingFrame.buildGUI(); // Constrói a tela
            }
        });
        
        homeButton = new JButton("Início");
        homeButton.setPreferredSize(new Dimension(180, 50));
        homeButton.setMaximumSize(new Dimension(180, 50));
        homeButton.setHorizontalAlignment(JButton.LEFT);
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeButton.setBackground(null);
        homeButton.setForeground(new Color(255, 255, 255));
        homeButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        homeButton.setIcon(new ImageIcon(houseIcon));
        homeButton.setFont(new Font(Font.SANS_SERIF, 1, 18));
        homeButton.setIconTextGap(10);
        homeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                callingFrame.setTitle("Cadastro de Consultas"); // Define titulo do JFrame
                callingFrame.setCurrentPanel(new Home(callingFrame)); // Instancia a tela Home e define como tela principal
                callingFrame.setCurrentPanelIndex(0); // Define o índice da tela
                callingFrame.buildGUI(); // Constrói a tela
            }
        });
        
        pacientesButton = new JButton("Pacientes");
        pacientesButton.setPreferredSize(new Dimension(180, 50));
        pacientesButton.setMaximumSize(new Dimension(180, 50));
        pacientesButton.setHorizontalAlignment(JButton.LEFT);
        pacientesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pacientesButton.setBackground(null);
        pacientesButton.setForeground(new Color(255, 255, 255));
        pacientesButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        pacientesButton.setIcon(new ImageIcon(peopleIcon));
        pacientesButton.setFont(new Font(Font.SANS_SERIF, 1, 18));
        pacientesButton.setIconTextGap(10);
        pacientesButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                callingFrame.setTitle("Pacientes - Cadastro de Consultas"); // Define titulo do JFrame
                callingFrame.setCurrentPanel(new Pacientes()); // Instancia a tela Pacientes e define como tela principal
                callingFrame.setCurrentPanelIndex(1); // Define o índice da tela
                callingFrame.buildGUI(); // Constrói a tela
            }
        });
        
        consultasButton = new JButton("Consultas");
        consultasButton.setPreferredSize(new Dimension(180, 50));
        consultasButton.setMaximumSize(new Dimension(180, 50));
        consultasButton.setHorizontalAlignment(JButton.LEFT);
        consultasButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        consultasButton.setBackground(null);
        consultasButton.setForeground(new Color(255, 255, 255));
        consultasButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        consultasButton.setIcon(new ImageIcon(calendarIcon));
        consultasButton.setFont(new Font(Font.SANS_SERIF, 1, 18));
        consultasButton.setIconTextGap(10);
        consultasButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                callingFrame.setTitle("Consultas - Cadastro de Consultas"); // Define titulo do JFrame
                callingFrame.setCurrentPanel(new Consultas()); // Instancia a tela Consultas e define como tela principal
                callingFrame.setCurrentPanelIndex(2); // Define o índice da tela
                callingFrame.buildGUI(); // Constrói a tela
                
            }
        });
        
        usuariosButton = new JButton("Usuários");
        usuariosButton.setPreferredSize(new Dimension(180, 50));
        usuariosButton.setMaximumSize(new Dimension(180, 50));
        usuariosButton.setHorizontalAlignment(JButton.LEFT);
        usuariosButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        usuariosButton.setBackground(null);
        usuariosButton.setForeground(new Color(255, 255, 255));
        usuariosButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        usuariosButton.setIcon(new ImageIcon(userIcon));
        usuariosButton.setFont(new Font(Font.SANS_SERIF, 1, 18));
        usuariosButton.setIconTextGap(10);
        usuariosButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                callingFrame.setTitle("Usuários - Cadastro de Consultas"); // Define titulo do JFrame
                callingFrame.setCurrentPanel(new Usuarios()); // Instancia a tela Usuários e define como tela principal
                callingFrame.setCurrentPanelIndex(3); // Define o índice da tela
                callingFrame.buildGUI(); // Constrói a tela
                
            }
        });
        
        // Define com base no índice da tela a borda
        // indicadora de posição dos botões
        switch(currentPanelIndex){
            case 0:
                homeButton.setBorder(
                            new CompoundBorder(
                                new EmptyBorder(10, 10, 10, 0),
                                new MatteBorder(0, 0, 0, 6, new Color(255, 255, 255))
                            )
                );
                homeButton.setBackground(ProgramDefaults.getBaseColorLight());
                break;
            case 1:
                pacientesButton.setBorder(
                            new CompoundBorder(
                                new EmptyBorder(10, 10, 10, 0),
                                new MatteBorder(0, 0, 0, 6, new Color(255, 255, 255))
                            )
                );
                pacientesButton.setBackground(ProgramDefaults.getBaseColorLight());
                break;
            case 2:
                consultasButton.setBorder(
                            new CompoundBorder(
                                new EmptyBorder(10, 10, 10, 0),
                                new MatteBorder(0, 0, 0, 6, new Color(255, 255, 255))
                            )
                );
                consultasButton.setBackground(ProgramDefaults.getBaseColorLight());
                break;
            case 3:
                usuariosButton.setBorder(
                            new CompoundBorder(
                                new EmptyBorder(10, 10, 10, 0),
                                new MatteBorder(0, 0, 0, 6, new Color(255, 255, 255))
                            )
                );
                usuariosButton.setBackground(ProgramDefaults.getBaseColorLight());
                break;
        }
        
        // Adiciona os botões à barra de navegação
        add(logoButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(homeButton);
        add(pacientesButton);
        add(consultasButton);
        
        // Se o usuário logado não for um médico
        // ele pode ver a aba "usuários"
        if(ProgramDefaults.getUserType() != 3){
            add(usuariosButton);
        }
        
        
        setBorder(new EmptyBorder(20, 10, 10, 10));
        setBackground(ProgramDefaults.getBackgroundColor());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
