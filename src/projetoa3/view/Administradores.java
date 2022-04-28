package projetoa3.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Administradores extends JPanel{
    
    JLabel title;
    
    public Administradores(){
        
        title = new JLabel("Administradores");
        title.setFont(new Font(Font.SANS_SERIF, 1, 24));
        
        add(title);
        
        setBorder(new EmptyBorder(20, 0, 20, 0));
        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }
}
