package projetoa3.view;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

/**
 * Inteface de início
 * @author Alexandre Soares, Kevin Morais, Nicholas Campanelli, Samuel Vincoletto, Tiago Massao, Victor Carvalho
 */
public class Home extends JPanel{
    
    // Componentes da interface
    private final JLabel title;
    
    /**
     * Construtor padrão
     */
    public Home(){
        
        title = new JLabel("Início");
        title.setFont(new Font(Font.SANS_SERIF, 1, 30));
        
        // Adiciona os elementos ao painel
        add(title);
        
        setBorder(new EmptyBorder(40, 40, 40, 40));
        setBackground(new Color(255, 255, 255));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
