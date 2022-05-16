package projetoa3.view.components;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import projetoa3.view.Principal;
import projetoa3.view.Home;
import projetoa3.view.Pacientes.Pacientes;
import projetoa3.view.Consultas.Consultas;
import projetoa3.view.Usuarios;

public class Navbar extends JPanel{
    
    JButton homeButton, consultasButton, pacientesButton, usuariosButton;
    Image houseIcon, calendarIcon, peopleIcon, userIcon;
    
    public Navbar(Principal callingFrame, int currentPanelIndex){
        
        try{
            URL houseUrl = getClass().getResource("/projetoa3/resources/houseIcon.png");
            URL calendarUrl = getClass().getResource("/projetoa3/resources/calendarIcon.png");
            URL peopleUrl = getClass().getResource("/projetoa3/resources/peopleIcon.png");
            URL userUrl = getClass().getResource("/projetoa3/resources/userIcon.png");
            houseIcon = ImageIO.read(houseUrl);
            calendarIcon = ImageIO.read(calendarUrl);
            peopleIcon = ImageIO.read(peopleUrl);
            userIcon = ImageIO.read(userUrl);
        }
        catch (IOException ex) {
            System.out.print(ex);
        }
        
        homeButton = new JButton("Início");
        homeButton.setPreferredSize(new Dimension(200, 50));
        homeButton.setMaximumSize(new Dimension(200, 50));
        homeButton.setHorizontalAlignment(JButton.LEFT);
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeButton.setBackground(null);
        homeButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        homeButton.setIcon(new ImageIcon(houseIcon));
        homeButton.setFont(new Font(Font.SANS_SERIF, 1, 18));
        homeButton.setIconTextGap(10);
        homeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                callingFrame.setTitle("Cadastro de Consultas");
                callingFrame.currentPanel = new Home();
                callingFrame.currentPanelIndex = 0;
                callingFrame.buildGUI();
            }
        });
        
        pacientesButton = new JButton("Pacientes");
        pacientesButton.setPreferredSize(new Dimension(200, 50));
        pacientesButton.setMaximumSize(new Dimension(200, 50));
        pacientesButton.setHorizontalAlignment(JButton.LEFT);
        pacientesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pacientesButton.setBackground(null);
        pacientesButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        pacientesButton.setIcon(new ImageIcon(peopleIcon));
        pacientesButton.setFont(new Font(Font.SANS_SERIF, 1, 18));
        pacientesButton.setIconTextGap(10);
        pacientesButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                callingFrame.setTitle("Pacientes - Cadastro de Consultas");
                callingFrame.currentPanel = new Pacientes();
                callingFrame.currentPanelIndex = 1;
                callingFrame.buildGUI();
            }
        });
        
        consultasButton = new JButton("Consultas");
        consultasButton.setPreferredSize(new Dimension(200, 50));
        consultasButton.setMaximumSize(new Dimension(200, 50));
        consultasButton.setHorizontalAlignment(JButton.LEFT);
        consultasButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        consultasButton.setBackground(null);
        consultasButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        consultasButton.setIcon(new ImageIcon(calendarIcon));
        consultasButton.setFont(new Font(Font.SANS_SERIF, 1, 18));
        consultasButton.setIconTextGap(10);
        consultasButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                callingFrame.setTitle("Consultas - Cadastro de Consultas");
                callingFrame.currentPanel = new Consultas();
                callingFrame.currentPanelIndex = 2;
                callingFrame.buildGUI();
                
            }
        });
        
        usuariosButton = new JButton("Usuários");
        usuariosButton.setPreferredSize(new Dimension(200, 50));
        usuariosButton.setMaximumSize(new Dimension(200, 50));
        usuariosButton.setHorizontalAlignment(JButton.LEFT);
        usuariosButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        usuariosButton.setBackground(null);
        usuariosButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        usuariosButton.setIcon(new ImageIcon(userIcon));
        usuariosButton.setFont(new Font(Font.SANS_SERIF, 1, 18));
        usuariosButton.setIconTextGap(10);
        usuariosButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                callingFrame.setTitle("Usuários - Cadastro de Consultas");
                callingFrame.currentPanel = new Usuarios();
                callingFrame.currentPanelIndex = 3;
                callingFrame.buildGUI();
                
            }
        });
        
        switch(currentPanelIndex){
            case 0:
                homeButton.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 0),
                                                        new MatteBorder(0, 0, 0, 5, new Color(15, 140, 190))));
            break;
            case 1:
                pacientesButton.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 0),
                                                        new MatteBorder(0, 0, 0, 5, new Color(15, 140, 190))));
            break;
            case 2:
                consultasButton.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 0),
                                                        new MatteBorder(0, 0, 0, 5, new Color(15, 140, 190))));
            break;
            case 3:
                usuariosButton.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 0),
                                                        new MatteBorder(0, 0, 0, 5, new Color(15, 140, 190))));
            break;
        }
        
        add(homeButton);
        add(pacientesButton);
        add(consultasButton);
        add(usuariosButton);
        
        setBackground(new java.awt.Color(245, 250, 251));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
