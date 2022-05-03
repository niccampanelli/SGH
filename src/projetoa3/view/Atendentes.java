package projetoa3.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
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
            {1231, "486.140.698-63", "Nicholas Campanelli de Souza", "nicholasoucampanelli@hotmail.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "x"},
            {2421, "486.140.698-63", "Humberto", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {3355, "486.140.698-63", "Marcelo", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {4547, "486.140.698-63", "Mariana", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {5456, "486.140.698-63", "Catarina", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {6685, "486.140.698-63", "Rogerio", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {7563, "486.140.698-63", "Humberto", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {8543, "486.140.698-63", "Marcelo", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {9234, "486.140.698-63", "Rogerio", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {1023, "486.140.698-63", "Catarina", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {1157, "486.140.698-63", "Mariana", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {1226, "486.140.698-63", "Rogerio", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {1533, "486.140.698-63", "Mariana", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {1454, "486.140.698-63", "Carlos", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {1523, "486.140.698-63", "Rogerio", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {1641, "486.140.698-63", "Humberto", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {1357, "486.140.698-63", "Carlos", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {1428, "486.140.698-63", "Marcelo", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {1219, "486.140.698-63", "Mariana", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {2401, "486.140.698-63", "Rogerio", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {2351, "486.140.698-63", "Carlos", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
            {2122, "486.140.698-63", "Humberto", "email@email.com", "(11) 95846-4236", "21/12/2003", "21/12/2003", "X"},
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
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumn("X").setCellRenderer(new ButtonRenderer());
        table.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e){
                                
                int viewRow = table.getSelectedRow();
                int viewColumn = table.getSelectedColumn();
                        
                if (!e.getValueIsAdjusting() && viewRow != -1) {
                                        
                    int modelRow = table.convertRowIndexToModel(viewRow);
                    int modelColumn = table.convertColumnIndexToModel(viewColumn);
                    
                    if(modelColumn == table.getColumnCount()-1){
                        int confirmed = JOptionPane.showConfirmDialog(null, 
                          "Tem certeza que deseja cancelar a edição deste atendente?\n"
                          + "Os dados inseridos serão perdidos e o atendente irá permanecer com os dados anteriores.",
                          "Cancelar",
                          JOptionPane.YES_NO_OPTION);

                        if(confirmed == JOptionPane.YES_OPTION) {
                            table.repaint();
                        }
                        
                        table.clearSelection();
                        table.getSelectionModel().clearSelection();
                    }
                    else{
                        String idValue = table.getModel().getValueAt(modelRow, 0).toString();
                        String cpfValue = table.getModel().getValueAt(modelRow, 1).toString();
                        String nomeValue = table.getModel().getValueAt(modelRow, 2).toString();
                        String emailValue = table.getModel().getValueAt(modelRow, 3).toString();
                        String telefoneValue = table.getModel().getValueAt(modelRow, 4).toString();
                        String dataNascValue = table.getModel().getValueAt(modelRow, 5).toString();

                        EditarAtendente editarAtendente = new EditarAtendente(idValue, cpfValue, nomeValue, emailValue, telefoneValue, dataNascValue);
                        editarAtendente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        
                        table.clearSelection();
                        table.getSelectionModel().clearSelection();
                    }
                }
            }
        });
        
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
      addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                NovoAtendente novoAtendente = new NovoAtendente();
                novoAtendente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
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