package projetoa3.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Consultas extends JPanel{
    
    JLabel title;
    
    public Consultas(){
        
        title = new JLabel("Consultas");
        title.setFont(new Font(Font.SANS_SERIF, 1, 30));
        
        add(title);
        
        setBorder(new EmptyBorder(40, 40, 40, 40));
        setBackground(new Color(255, 255, 255));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
