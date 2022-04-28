package projetoa3.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class Atendentes extends JPanel{
    
    JLabel title;
    JButton button;
    JScrollPane tableWrap;
    JTable table;
    
    public Atendentes(){
        
        title = new JLabel("Atendentes");
        title.setFont(new Font(Font.SANS_SERIF, 1, 24));
        button = new JButton("X");
        
        String[] columns = {
            "ID",
            "Nome",
            "Data de Cadastro",
            "Remover"
        };
        
        Object[][] data = {
            {1, "Carlos", "21/12/2003", "x"},
            {2, "Humberto", "21/12/2003", "X"},
            {3, "Marcelo", "21/12/2003", "X"},
            {4, "Mariana", "21/12/2003", "X"},
            {5, "Catarina", "21/12/2003", "X"},
            {6, "Rogerio", "21/12/2003", "X"},
            {7, "Humberto", "21/12/2003", "X"},
            {8, "Marcelo", "21/12/2003", "X"},
            {9, "Rogerio", "21/12/2003", "X"},
            {10, "Catarina", "21/12/2003", "X"},
            {11, "Mariana", "21/12/2003", "X"},
            {12, "Rogerio", "21/12/2003", "X"},
            {13, "Mariana", "21/12/2003", "X"},
            {14, "Carlos", "21/12/2003", "X"},
            {15, "Rogerio", "21/12/2003", "X"},
            {16, "Humberto", "21/12/2003", "X"},
            {17, "Carlos", "21/12/2003", "X"},
            {18, "Marcelo", "21/12/2003", "X"},
            {19, "Mariana", "21/12/2003", "X"},
            {20, "Rogerio", "21/12/2003", "X"},
            {21, "Carlos", "21/12/2003", "X"},
            {22, "Humberto", "21/12/2003", "X"},
        };
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.add(button);
        
        table = new JTable(data, columns);
        table.getColumn("Remover").setCellRenderer(new ButtonRenderer());
        table.getTableHeader().setDefaultRenderer(new HeaderRenderer(table));
        table.getTableHeader().setOpaque(true);
        table.getTableHeader().setFont(new Font(Font.SANS_SERIF, 1, 16));
        table.getTableHeader().setBackground(new java.awt.Color(255, 255, 255));
        
        tableWrap = new JScrollPane(table);
        tableWrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableWrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        tableWrap.setBorder(null);
        
        add(title);
        add(tableWrap);
        
        setBorder(new EmptyBorder(20, 0, 20, 0));
        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {

  public ButtonRenderer() {
    setOpaque(true);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    if (isSelected) {
      setForeground(table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } else {
      setForeground(table.getForeground());
      setBackground(UIManager.getColor("Button.background"));
    }
    setText((value == null) ? "" : value.toString());
    return this;
  }
}

class HeaderRenderer implements TableCellRenderer {

    DefaultTableCellRenderer renderer;

    public HeaderRenderer(JTable table) {        
        renderer = (DefaultTableCellRenderer)
            table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
    }

    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int col) {
        return renderer.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, col);
    }
}