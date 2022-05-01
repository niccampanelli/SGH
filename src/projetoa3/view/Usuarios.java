package projetoa3.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import projetoa3.view.Atendentes;
import projetoa3.view.Administradores;

public class Usuarios extends JPanel{
    
    JLabel title;
    
    public Usuarios(){
        
        title = new JLabel("Usuários");
        title.setFont(new Font(Font.SANS_SERIF, 1, 30));
        
        add(title);
        add(new Atendentes());
        add(new Administradores());
        
        setBorder(new EmptyBorder(40, 40, 40, 40));
        setBackground(new Color(255, 255, 255));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setAlignmentY(0.0f);
    }
}
