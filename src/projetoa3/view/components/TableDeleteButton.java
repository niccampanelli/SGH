package projetoa3.view.components;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TableDeleteButton extends JButton implements TableCellRenderer {
    
    Image icon;
    
    public TableDeleteButton(Image icon){
        setOpaque(true);
        this.icon = icon;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if (isSelected){
            setBackground(new Color(240, 240, 240));
        }
        else{
            setBackground(new Color(255, 255, 255));
            setIcon(new ImageIcon(icon));
            setBorder(null);
        }
        
        setText((value == null) ? "" : value.toString());
        return this;
    }
}