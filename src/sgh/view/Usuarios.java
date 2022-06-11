package sgh.view;
import sgh.view.Medicos.Medicos;
import sgh.view.Atendentes.Atendentes;
import sgh.view.Administradores.Administradores;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import sgh.util.ProgramDefaults;

/**
 * Tela de usuários onde serão acomodadas as diferentes tabelas dos usuários
 * @author Nicholas Campanelli
 */
public class Usuarios extends JPanel{
    
    private final JLabel title;
    
    /**
     * Construtor padrão
     */
    public Usuarios(){
        
        // Cria o título da página
        title = new JLabel("Usuários");
        title.setFont(new Font(Font.SANS_SERIF, 1, 30));
        
        add(title);
        add(Box.createRigidArea(new Dimension(0, 20))); // Cria um espaçamento entre os elementos
        add(new Medicos());
        add(Box.createRigidArea(new Dimension(0, 20))); // Cria um espaçamento entre os elementos
        add(new Atendentes());
        
        // Se o usuário for do tipo administrador
        if(ProgramDefaults.getUserType() == 1){
            add(Box.createRigidArea(new Dimension(0, 20))); // Cria um espaçamento entre os elementos
            add(new Administradores());
        }
        
        
        setBorder(new EmptyBorder(40, 40, 40, 40));
        setBackground(new Color(255, 255, 255));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(0.0f);
        setAlignmentY(0.0f);
        
        // Define o tamanho como o tamanho máximo.
        // Utilizado para eliminar espaço desnecessário criado pelo layout
        setPreferredSize(new Dimension(this.getPreferredSize().width, this.getMaximumSize().height));
    }
}
