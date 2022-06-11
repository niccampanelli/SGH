package sgh.view.Components;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JPasswordField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import sgh.util.ProgramDefaults;

/**
 * Text Field com aparência personalizada
 * @author Nicholas Campanelli
 */

// Classe de field customizado herdando o JTextField original
public class CustomPasswordField extends JPasswordField {
    
    /**
     * Construtor padrão
     */
    public CustomPasswordField(){
        super();
        
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
    
    /**
     * Construtor com tamanho
     * @param size - Constrói o o text field com a largura necessária para o tamanho de texto especificado
     */
    public CustomPasswordField(int size){
        super(size); // Passa para a superclasse o tamanho
        
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
    
    /**
     * Construtor com placeholder definido
     * @param placeholder - Exibe uma dica de valor no text field
     */
    public CustomPasswordField(String placeholder){
        super(placeholder); // Passa para a superclasse o placeholder
        
        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        setForeground(new Color(180, 180, 180));
        setDisabledTextColor(new Color(100, 100, 100));
        setBackground(new Color(250, 250, 250));
        // Borda com linha na base, sombra e padding
        setBorder(new CompoundBorder(
                    new MatteBorder(0, 0, 3, 0, ProgramDefaults.getBaseColor()),
                    new CompoundBorder(
                        new MatteBorder(2, 2, 0, 2, new Color(0, 0, 0, 10)),
                        new EmptyBorder(0, 10, 0, 10)
        )));
        
        // Adiciona efeito de focus e mostra o placeholder
        // ao perder o foco
        addFocusListener(new FocusListener(){

            @Override  
            public void focusGained(FocusEvent e){
                setBackground(new Color(255, 255, 255));
                if(getText().equals(placeholder)){
                    setText("");
                    setForeground(new Color(100, 100, 100));
                }
            }  

            @Override  
            public void focusLost(FocusEvent e){
                setBackground(new Color(250, 250, 250));
                if(getText().isEmpty()){
                    setText(placeholder);
                    setForeground(new Color(180, 180, 180));
                }
            }
        });
    }
}