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
import projetoa3.view.Pessoas;
import projetoa3.view.Consultas;
import projetoa3.view.Usuarios;

public class Navbar extends JPanel{
    
    JButton homeButton, consultasButton, pessoasButton, usuariosButton;
    Image houseIcon, calendarIcon, peopleIcon, userIcon;
    
    public Navbar(Principal callingFrame, int currentPanelIndex){
        
        try{
            URL houseUrl = getClass().getResource("../../resources/houseIcon.png");
            URL calendarUrl = getClass().getResource("../../resources/calendarIcon.png");
            URL peopleUrl = getClass().getResource("../../resources/peopleIcon.png");
            URL userUrl = getClass().getResource("../../resources/userIcon.png");
            houseIcon = ImageIO.read(houseUrl);
            calendarIcon = ImageIO.read(calendarUrl);
            peopleIcon = ImageIO.read(peopleUrl);
            userIcon = ImageIO.read(userUrl);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
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
        
        pessoasButton = new JButton("Pessoas");
        pessoasButton.setPreferredSize(new Dimension(200, 50));
        pessoasButton.setMaximumSize(new Dimension(200, 50));
        pessoasButton.setHorizontalAlignment(JButton.LEFT);
        pessoasButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pessoasButton.setBackground(null);
        pessoasButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        pessoasButton.setIcon(new ImageIcon(peopleIcon));
        pessoasButton.setFont(new Font(Font.SANS_SERIF, 1, 18));
        pessoasButton.setIconTextGap(10);
        pessoasButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                callingFrame.setTitle("Pessoas - Cadastro de Consultas");
                callingFrame.currentPanel = new Pessoas();
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
                pessoasButton.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 0),
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
        add(pessoasButton);
        add(consultasButton);
        add(usuariosButton);
        
        setBackground(new java.awt.Color(245, 250, 251));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
