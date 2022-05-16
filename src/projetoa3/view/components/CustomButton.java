package projetoa3.view.components;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class CustomButton extends JButton {
    
    public CustomButton(){
        this(null);
    }
    
    public CustomButton(String label){
        super(label.toUpperCase());
        super.setContentAreaFilled(false);
        
        setBorderPainted(false);
        setFocusPainted(false);
        setForeground(new Color(255, 255, 255));
        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(10, 15, 10, 15));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(new Color(5, 130, 180));
        } else if (getModel().isRollover()) {
            g.setColor(new Color(25, 150, 200));
        } else {
            g.setColor(new Color(15, 140, 190));
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
