package projetoa3.view.Pacientes;
import projetoa3.view.Components.CustomButton;
import projetoa3.view.Components.TableDeleteButton;
import projetoa3.view.Components.TableHeader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * Painel de pacientes
 * @author Nicholas Campanelli
 */
public class Pacientes extends JPanel{
    
    private final BoxLayout titleLayout, descriptionLayout;
    private final JPanel titlePanel, descriptionPanel;
    private final JLabel title, subtitle, description;
    private final CustomButton addButton;
    private final JScrollPane tableWrap;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private Image trashIcon;
    
    public Pacientes(){
        
        try{
            URL url = getClass().getResource("/projetoa3/util/icons/trashIcon.png");
            trashIcon = ImageIO.read(url);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        String[] columns = {
            "ID",
            "CPF",
            "Nome",
            "E-mail",
            "Telefone",
            "Data de nascimento",
            "Cadastrado em"
        };
        
        Object[][] data = {
            {1231, "365.785.234-45", "Nicholas Campanelli de Souza", "nicholasoucampanelli@hotmail.com", "(11) 95846-4236", "21/12/2003", "30/05/2022"},
            {4124, "645.754.425-53", "Charles", "Charles@email.com", "(11) 91424-3454", "20/03/1954", "12/05/2022"},
            {4563, "453.754.578-24", "Eric", "Eric@email.com", "(11) 45363-5353", "10/05/1959", "22/05/2022"},
            {4563, "856.453.424-54", "Gabriel", "Gabriel@email.com", "(11) 46367-4356", "03/04/1962", "13/05/2022"},
            {4251, "235.351.231-63", "Hanna", "Hanna@email.com", "(11) 46235-5647", "17/02/1963", "23/05/2022"},
            {3563, "647.823.555-86", "Ali", "Ali@email.com", "(11) 75674-5647", "27/09/1994", "22/05/2022"},
            {4847, "345.142.453-23", "Beatriz", "Beatriz@email.com", "(11) 64758-6888", "10/08/1986", "23/05/2022"},
            {2763, "867.423.252-22", "Diya", "Diya@email.com", "(11) 53678-6858", "28/07/1980", "31/05/2022"},
            {2746, "565.998.364-35", "Fatima", "Fatima@email.com", "(11) 23412-7657", "22/09/2000", "11/05/2022"},
            {2341, "076.657.312-48", "Eman Glass", "Eman@email.com", "(11) 66743-7657", "28/07/1980", "11/05/2022"},
            {7463, "564.858.467-67", "Ubaid Downs", "Ubaid@email.com", "(11) 23463-4553", "15/02/2002", "22/05/2022"},
            {2451, "234.840.453-88", "Orson Burton", "Orson@email.com", "(11) 13662-4345", "06/01/1994", "31/05/2022"},
            {1245, "535.564.998-40", "Kirby Medina", "Kirby@email.com", "(11) 13455-5523", "08/03/1995", "31/05/2022"},
            {9574, "323.345.564-23", "Dulcie Needham", "Dulcie@email.com", "(11) 23321-9977", "01/03/1986", "21/05/2022"},
            {2356, "414.574.342-53", "Muhamed Guerra", "Muhamed@email.com", "(11) 44768-7655", "21/12/2000", "04/05/2022"},
            {1746, "332.232.356-42", "Glen Hubbard", "Glen@email.com", "(11) 34577-7576", "31/12/2003", "12/05/2022"},
            {2754, "362.216.436-67", "Hebe Stein", "Hebe@email.com", "(11) 46788-6422", "12/05/1992", "04/05/2022"},
            {4675, "637.622.243-12", "Louie Nicholson", "Louie@email.com", "(11) 57554-4333", "04/06/1966", "04/05/2022"},
            {6487, "132.475.518-21", "Dylon Gibson", "Dylon@email.com", "(11) 65466-1123", "15/03/1979", "04/05/2022"}
        };
        
        // Painel que contém o título
        titlePanel = new JPanel();
        titleLayout = new BoxLayout(titlePanel, BoxLayout.LINE_AXIS);
        titlePanel.setLayout(titleLayout);
        titlePanel.setAlignmentX(0.0f);
        titlePanel.setBackground(null);
        
        title = new JLabel("Pacientes");
        title.setFont(new Font(Font.SANS_SERIF, 1, 30));
        
        // Verifica o plural
        if(data.length == 1){
            subtitle = new JLabel("1 paciente cadastrado");
        }
        else{
            subtitle = new JLabel(data.length + " pacientes cadastrados");
        }
        
        // Botão de adicionar pacientes
        addButton = new CustomButton("Cadastrar um paciente");
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                // Instancia uma tela de adicionar administrador
                NovoPaciente novoPaciente = new NovoPaciente();
                novoPaciente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        
        // Adiciona os elementos antetiores no painel do título
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalGlue());
        titlePanel.add(addButton);
        
        descriptionPanel = new JPanel();
        descriptionLayout = new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS);
        descriptionPanel.setBackground(null);
        descriptionPanel.setLayout(descriptionLayout);
        
        description = new JLabel("<html>Aqui você pode ver os pacientes que estão cadastradas no sistema e suas informações. Apenas os pacientes listadas aqui podem ser vinculadas à uma consulta. Para editar um registro de um paciente, clique sobre a linha que deseja editar e então uma tela será aberta onde será possível modificar as informações. Para excluir um paciente, clique no ícone da lixeira na extremidade direita do registro que deseja remover.</html>");
        setPreferredSize(descriptionPanel.getPreferredSize());
        description.setFont(new Font(Font.SANS_SERIF, 0, 12));
        
        descriptionPanel.add(description);
        
        tableModel = new DefaultTableModel(data, columns);
        
        // Cria a tabela com os dados e colunas
        // e desabilita a edição de células
        table = new JTable(tableModel){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tableModel.addColumn("X");
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(230, 230, 230));
        table.setRowHeight(40);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumn("X").setCellRenderer(new TableDeleteButton(trashIcon));
        table.setCursor(new Cursor(Cursor.HAND_CURSOR));
        table.setAutoCreateRowSorter(true);
        
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
        table.getColumn("X").setMinWidth(40);
        table.getColumn("X").setMaxWidth(40);
        
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
                          "Tem certeza que deseja remover o cadastro do paciente #"+idValue+" - "+nomeValue+"?\n"
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
                        
                        // Instancia uma nova tela de editar administrador
                        EditarPaciente editarPaciente = new EditarPaciente(idValue, cpfValue, nomeValue, emailValue, telefoneValue, dataNascValue);
                        editarPaciente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                                
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
        table.getTableHeader().setDefaultRenderer(new TableHeader());
        table.getTableHeader().setOpaque(true);
        table.getTableHeader().setFont(new Font(Font.SANS_SERIF, 1, 12));
        table.getTableHeader().setBackground(new Color(255, 255, 255));
        table.getTableHeader().setCursor(new Cursor(Cursor.HAND_CURSOR));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        
        // Painel que envolve a tabela
        tableWrap = new JScrollPane(table);
        tableWrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableWrap.setBorder(new EmptyBorder(0, 0, 0, 0));
        tableWrap.setBackground(new Color(255, 255, 255));
        
        // Adiciona os elementos na tela
        add(titlePanel);
        add(subtitle);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(descriptionPanel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(tableWrap);
        
        setBorder(new EmptyBorder(40, 40, 40, 40));
        setBackground(new Color(255, 255, 255));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
