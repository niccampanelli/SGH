package sgh.view.Components;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import sgh.util.ProgramDefaults;

/**
 * Botão com aparência personalizada
 * @author Nicholas Campanelli
 */

// Classe de botão customizado herdando o JButton original
public class CustomButton extends JButton {
    
    /**
     * Construtor padrão
     */
    public CustomButton(){
        this(null);
    }
    
    /**
     * Construtor com texto no botão
     * @param label 
     */
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
    
    // Sobrescreve o método do swing que renderiza
    // o componente a fim de aplicar um efeito de
    // hover e focus
    @Override
    public void paintComponent(Graphics graphic) {
        
        // Se pressionado
        if(getModel().isPressed()){
            graphic.setColor(ProgramDefaults.getBaseColorDark());
        }
        // Ao passar o mouse
        else if(getModel().isRollover()){
            graphic.setColor(ProgramDefaults.getBaseColorLight());
        }
        // Padrão
        else{
            graphic.setColor(ProgramDefaults.getBaseColor());
        }
        
        // Define o tamanho do componente como o tamanho do botão
        graphic.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(graphic);
    }
}