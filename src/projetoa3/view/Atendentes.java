package projetoa3.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class Atendentes extends JPanel{
    
    BoxLayout titleLayout;
    JPanel titlePanel;
    JLabel title, subtitle;
    JButton button, addButton;
    JScrollPane tableWrap;
    JTable table;
    
    public Atendentes(){
        
        String[] columns = {
            "ID",
            "CPF",
            "Nome",
            "E-mail",
            "Telefone",
            "Data de nascimento",
            "Cadastrado em",
            "X"
        };
        
        Object[][] data = {
            {1, "486.140.698-63", "Nicholas Campanelli de Souza", "nicholasoucampanelli@hotmail.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "x"},
            {2, "486.140.698-63", "Humberto", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {3, "486.140.698-63", "Marcelo", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {4, "486.140.698-63", "Mariana", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {5, "486.140.698-63", "Catarina", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {6, "486.140.698-63", "Rogerio", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {7, "486.140.698-63", "Humberto", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {8, "486.140.698-63", "Marcelo", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {9, "486.140.698-63", "Rogerio", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {10, "486.140.698-63", "Catarina", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {11, "486.140.698-63", "Mariana", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {12, "486.140.698-63", "Rogerio", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {13, "486.140.698-63", "Mariana", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {14, "486.140.698-63", "Carlos", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {15, "486.140.698-63", "Rogerio", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {16, "486.140.698-63", "Humberto", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {17, "486.140.698-63", "Carlos", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {18, "486.140.698-63", "Marcelo", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {19, "486.140.698-63", "Mariana", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {20, "486.140.698-63", "Rogerio", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {21, "486.140.698-63", "Carlos", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {22, "486.140.698-63", "Humberto", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
        };
        
        titlePanel = new JPanel();
        titleLayout = new BoxLayout(titlePanel, BoxLayout.LINE_AXIS);
        titlePanel.setLayout(titleLayout);
        titlePanel.setAlignmentX(0.0f);
        titlePanel.setBackground(null);
        
        title = new JLabel("Atendentes");
        title.setFont(new Font(Font.SANS_SERIF, 1, 24));
        
        if(data.length == 1){
            subtitle = new JLabel("1 atendente cadastrado");
        }
        else{
            subtitle = new JLabel(data.length + " atendentes cadastrados");
        }
        
        addButton = new JButton("Adicionar atendente");
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                NovoAtendente novoAtendente = new NovoAtendente();
                novoAtendente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalGlue());
        titlePanel.add(addButton);
        
        button = new JButton("X");
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.add(button);
        
        table = new JTable(data, columns){            
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(230, 230, 230));
        table.setRowHeight(40);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getColumn("X").setCellRenderer(new ButtonRenderer());
        
        table.getColumn("ID").setMinWidth(40);
        table.getColumn("ID").setMaxWidth(40);
        table.getColumn("CPF").setMinWidth(100);
        table.getColumn("CPF").setMaxWidth(100);
        table.getColumn("Telefone").setMinWidth(110);
        table.getColumn("Telefone").setMaxWidth(110);
        table.getColumn("Data de nascimento").setMinWidth(130);
        table.getColumn("Data de nascimento").setMaxWidth(130);
        table.getColumn("Cadastrado em").setMinWidth(100);
        table.getColumn("Cadastrado em").setMaxWidth(100);
        table.getColumn("X").setMinWidth(45);
        table.getColumn("X").setMaxWidth(45);
        
        table.getTableHeader().setDefaultRenderer(new HeaderRenderer());
        table.getTableHeader().setOpaque(true);
        table.getTableHeader().setFont(new Font(Font.SANS_SERIF, 1, 12));
        table.getTableHeader().setBackground(new Color(255, 255, 255));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        
        tableWrap = new JScrollPane(table);
        tableWrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableWrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        tableWrap.setBorder(new EmptyBorder(0, 0, 0, 0));
        tableWrap.setBackground(new Color(255, 255, 255));
        
        add(titlePanel);
        add(subtitle);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(tableWrap);
        
        setBorder(new EmptyBorder(20, 0, 20, 0));
        setBackground(new Color(255, 255, 255));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setAlignmentY(0.0f);
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {

  public ButtonRenderer() {
    setOpaque(true);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
      setCursor(new Cursor(Cursor.HAND_CURSOR));
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

class HeaderRenderer extends JLabel implements TableCellRenderer {

    public HeaderRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      
        setBorder(new MatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        setBackground(new Color(255, 255, 255));
        setText((value == null) ? "" : value.toString());
        
        return this;
    }
}

/*class HeaderRenderer implements TableCellRenderer {

    DefaultTableCellRenderer renderer;

    public HeaderRenderer(JTable table){
        
        renderer = (DefaultTableCellRenderer)
            table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
    }
}*/