package projetoa3.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class Atendentes extends JPanel{
    
    // Componentes da Tela
    BoxLayout titleLayout;
    JPanel titlePanel;
    JLabel title, subtitle;
    JButton button, addButton;
    JScrollPane tableWrap;
    JTable table;
    
    // Construtor
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
        };
        
        // Painel que contém o título
        titlePanel = new JPanel();
        titleLayout = new BoxLayout(titlePanel, BoxLayout.LINE_AXIS);
        titlePanel.setLayout(titleLayout);
        titlePanel.setAlignmentX(0.0f);
        titlePanel.setBackground(null);
        
        title = new JLabel("Atendentes");
        title.setFont(new Font(Font.SANS_SERIF, 1, 24));
        
        // Verifica o plural
        if(data.length == 1){
            subtitle = new JLabel("1 atendente cadastrado");
        }
        else{
            subtitle = new JLabel(data.length + " atendentes cadastrados");
        }
        
        // Botão de adicionar atendente
        addButton = new JButton("Adicionar atendente");
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                // Instancia uma tela de adicionar atendente
                NovoAtendente novoAtendente = new NovoAtendente();
                novoAtendente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        
        // Adiciona os elementos antetiores no painel do título
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalGlue());
        titlePanel.add(addButton);
        
        button = new JButton("X");
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.add(button);
        
        // Cria a tabela com os dados e colunas
        // e desabilita a edição de células
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
        
        // Adiciona um event listener para tornar as linhas clicáveis
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e){
                
                // Índice da linha e da coluna selecionados
                int viewRow = table.getSelectedRow();
                int viewColumn = table.getSelectedColumn();
                
                // Previne as instruções de serem chamadas duas vezes
                if (!e.getValueIsAdjusting() && viewRow != -1) {
                    
                    // Converte o índice da tabela para um índice de modelo
                    // previne de obter a célula errada ao ordenar a tabela
                    int modelRow = table.convertRowIndexToModel(viewRow);
                    int modelColumn = table.convertColumnIndexToModel(viewColumn);
                    
                    // Se a coluna selecionada for a última (onde estão os botões de excluir)
                    if(modelColumn == table.getColumnCount()-1){
                        
                        // Pega o ID e o nome para serem mostrados
                        String idValue = table.getModel().getValueAt(modelRow, 0).toString();
                        String nomeValue = table.getModel().getValueAt(modelRow, 2).toString();
                        
                        // Opções do painel de confirmação
                        Object[] options = {"Excluir", "Cancelar"};
                        
                        // Mostra um painel de confirmação, para impedir exclusões acidentais
                        int confirmed = JOptionPane.showOptionDialog(null, 
                          "Tem certeza que deseja excluir o atendente #"+idValue+" - "+nomeValue+"?\n"
                          + "Esta ação não poderá ser desfeita.",
                          "Excluir",
                          JOptionPane.YES_NO_OPTION,
                          JOptionPane.QUESTION_MESSAGE,
                          null,
                          options,
                          options[1]
                        );
                        
                        // Se a opção for "sim"
                        if(confirmed == JOptionPane.YES_OPTION) {
                            table.repaint();
                        }
                        
                        // Limpa a seleção da tabela
                        // Se a linha continuar selecionada, não é possível
                        // clicá-la novamente
                        table.clearSelection();
                        table.getSelectionModel().clearSelection();
                    }
                    else{
                        // Obtém os dados da linha selecionada
                        String idValue = table.getModel().getValueAt(modelRow, 0).toString();
                        String cpfValue = table.getModel().getValueAt(modelRow, 1).toString();
                        String nomeValue = table.getModel().getValueAt(modelRow, 2).toString();
                        String emailValue = table.getModel().getValueAt(modelRow, 3).toString();
                        String telefoneValue = table.getModel().getValueAt(modelRow, 4).toString();
                        String dataNascValue = table.getModel().getValueAt(modelRow, 5).toString();
                        
                        // Instancia uma nova tela de editar atendente
                        EditarAtendente editarAtendente = new EditarAtendente(idValue, cpfValue, nomeValue, emailValue, telefoneValue, dataNascValue);
                        editarAtendente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                                
                        // Limpa a seleção da tabela
                        // Se a linha continuar selecionada, não é possível
                        // clicá-la novamente
                        table.clearSelection();
                        table.getSelectionModel().clearSelection();
                    }
                }
            }
        });
        
        // Faz modificações no cabeçalho da tabela
        table.getTableHeader().setDefaultRenderer(new HeaderRenderer());
        table.getTableHeader().setOpaque(true);
        table.getTableHeader().setFont(new Font(Font.SANS_SERIF, 1, 12));
        table.getTableHeader().setBackground(new Color(255, 255, 255));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        
        // Painel que envolve a tabela
        tableWrap = new JScrollPane(table);
        tableWrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableWrap.setPreferredSize(new Dimension(Integer.MIN_VALUE, 300));
        tableWrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        tableWrap.setBorder(new EmptyBorder(0, 0, 0, 0));
        tableWrap.setBackground(new Color(255, 255, 255));
        
        // Adiciona os elementos na tela
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