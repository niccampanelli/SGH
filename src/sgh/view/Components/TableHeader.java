package sgh.view.Components;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;
import sgh.util.ProgramDefaults;

/**
 * Cabeçalho customizado para as tabelas
 * @author Nicholas Campanelli
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
        setForeground(ProgramDefaults.getBaseColorDark());
        setText((value == null) ? "" : value.toString());
        
        // Retorna o cabeçalho
        return this;
    }
}