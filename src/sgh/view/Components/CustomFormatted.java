package sgh.view.Components;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.FocusManager;
import javax.swing.JFormattedTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;
import sgh.util.ProgramDefaults;

/**
 * Formatted Text Field com aparência personalizada
 * @author Nicholas Campanelli
 */

// Classe de field customizado herdando o JTextField formatado
public class CustomFormatted extends JFormattedTextField {
    
    /**
     * Construtor padrão
     */
    public CustomFormatted(MaskFormatter msk){
        super(msk);
        
        // Define o visual do field
        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        setForeground(new Color(100, 100, 100));
        setDisabledTextColor(new Color(100, 100, 100));
        setBackground(new Color(250, 250, 250));
        // Borda com linha na base, sombra e padding
        setBorder(new CompoundBorder(
                    new MatteBorder(0, 0, 3, 0, ProgramDefaults.getBaseColor()),
                    new CompoundBorder(
                        new MatteBorder(2, 2, 0, 2, new Color(0, 0, 0, 10)),
                        new EmptyBorder(0, 10, 0, 10)
        )));
        
        // Adiciona efeito de focus
        addFocusListener(new FocusListener(){

            @Override  
            public void focusGained(FocusEvent e){
                setBackground(new Color(255, 255, 255));
            }

            @Override  
            public void focusLost(FocusEvent e){
                setBackground(new Color(250, 250, 250));
            }
        });
    }
}