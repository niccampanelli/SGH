package projetoa3.view.components;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;

/**
 * Cabeçalho customizado para as tabelas
 * @author Alexandre Soares, Kevin Morais, Nicholas Campanelli, Samuel Vincoletto, Tiago Massao, Victor Carvalho
 */
public class TableHeader extends JLabel implements TableCellRenderer {
    
    /**
     * Construtor padrão
     */
    public TableHeader() {
        setOpaque(true);
    }

    // Sobrescreve o método que renderiza o cabeçalho
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        // Customiza o cabeçalho
        setBorder(new MatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        setBackground(new Color(255, 255, 255));
        setText((value == null) ? "" : value.toString());
        
        // Retorna o cabeçalho
        return this;
    }
}