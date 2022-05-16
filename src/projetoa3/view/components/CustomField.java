package projetoa3.view.components;
import java.awt.*;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CustomField extends JTextField {
    
    public CustomField(int size){
        super(size);
        
        setForeground(new Color(255, 255, 255));
        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(10, 15, 10, 15));
    }
}