package sgh.view.Components;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Botão de deletar para as tabelas
 * @author Nicholas Campanelli
 */
public class TableDeleteButton extends JButton implements TableCellRenderer {
    
    // Ícone da lixeira
    private final Image icon;
    
    /**
     * Construtor que recebe o ícone de lixeira
     * @param icon - Ícone pro botão de excluir
     */
    public TableDeleteButton(Image icon){
        setOpaque(true);
        this.icon = icon;
    }
    
    // Sobrescreve o método que renderiza a célula da tabela.
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Muda a cor do botão quando a linha for selecionada
        if (isSelected){
            setBackground(new Color(240, 240, 240));
        }
        else{
            setBackground(new Color(255, 255, 255));
            setIcon(new ImageIcon(icon));
            setBorder(null);
        }
        
        // Verifica se a célula recebe algum valor
        setText((value == null) ? "" : value.toString());
        return this;
    }
}