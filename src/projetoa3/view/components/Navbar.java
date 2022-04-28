package projetoa3.view.components;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import projetoa3.view.Principal;
import projetoa3.view.Home;
import projetoa3.view.Pessoas;
import projetoa3.view.Consultas;
import projetoa3.view.Usuarios;

public class Navbar extends JPanel{
    
    JButton homeButton, consultasButton, pessoasButton, usuariosButton;
    
    public Navbar(Principal callingFrame){
        
        homeButton = new JButton("Início");
        homeButton.setPreferredSize(new Dimension(200, 40));
        homeButton.setMaximumSize(new Dimension(200, 40));
        homeButton.setHorizontalAlignment(JButton.LEFT);
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                callingFrame.setTitle("Cadastro de Consultas");
                callingFrame.currentPanel = new Home();
                callingFrame.buildGUI();
            }
        });
        
        pessoasButton = new JButton("Pessoas");
        pessoasButton.setPreferredSize(new Dimension(200, 40));
        pessoasButton.setMaximumSize(new Dimension(200, 40));
        pessoasButton.setHorizontalAlignment(JButton.LEFT);
        pessoasButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pessoasButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                callingFrame.setTitle("Pessoas - Cadastro de Consultas");
                callingFrame.currentPanel = new Pessoas();
                callingFrame.buildGUI();
            }
        });
        
        consultasButton = new JButton("Consultas");
        consultasButton.setPreferredSize(new Dimension(200, 40));
        consultasButton.setMaximumSize(new Dimension(200, 40));
        consultasButton.setHorizontalAlignment(JButton.LEFT);
        consultasButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        consultasButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                callingFrame.setTitle("Consultas - Cadastro de Consultas");
                callingFrame.currentPanel = new Consultas();
                callingFrame.buildGUI();
                
            }
        });
        
        usuariosButton = new JButton("Usuários");
        usuariosButton.setPreferredSize(new Dimension(200, 40));
        usuariosButton.setMaximumSize(new Dimension(200, 40));
        usuariosButton.setHorizontalAlignment(JButton.LEFT);
        usuariosButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        usuariosButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                callingFrame.setTitle("Usuários - Cadastro de Consultas");
                callingFrame.currentPanel = new Usuarios();
                callingFrame.buildGUI();
                
            }
        });
        
        add(homeButton);
        add(pessoasButton);
        add(consultasButton);
        add(usuariosButton);
        
        setBackground(new java.awt.Color(250, 250, 250));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
    }
}
