package sgh.view.Atendentes;
import sgh.view.Components.CustomButton;
import sgh.view.Components.TableDeleteButton;
import sgh.view.Components.TableHeader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
import sgh.controller.UserController;
import sgh.util.ProgramDefaults;
import sgh.util.Resultado;

/**
 * Painel de atendentes
 * @author Nicholas Campanelli
 */
public class Atendentes extends JPanel{
    
    // Componentes da Tela
    private final BoxLayout titleLayout, descriptionLayout;
    private final JPanel titlePanel, descriptionPanel;
    private final JLabel title, subtitle, description;
    private final CustomButton addButton;
    private final JScrollPane tableWrap;
    private final JTable table;
    private DefaultTableModel tableModel;
    private Image trashIcon;
    
    // Construtor
    public Atendentes(){
        
        try{
            URL url = getClass().getResource("/sgh/util/icons/trashIcon.png");
            trashIcon = ImageIO.read(url);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        // Painel que contém o título
        titlePanel = new JPanel();
        titleLayout = new BoxLayout(titlePanel, BoxLayout.LINE_AXIS);
        titlePanel.setLayout(titleLayout);
        titlePanel.setAlignmentX(0.0f);
        titlePanel.setBackground(null);
        
        title = new JLabel("Atendentes");
        title.setFont(new Font(Font.SANS_SERIF, 1, 24));
        subtitle = new JLabel("1 atendente cadastrado");
        
        // Botão de adicionar atendente
        addButton = new CustomButton("Adicionar atendente");
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                // Instancia uma tela de adicionar atendente
                NovoAtendente novoAtendente = new NovoAtendente();
                novoAtendente.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e){
                        atualizarTabela();
                    }
                });
                novoAtendente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        
        description = new JLabel("<html>Estes são os atendentes cadastrados. Os atendentes possuem um nível de acesso menor do que os administradores e não podem cadastrar outros usuários.</html>");
        setPreferredSize(descriptionPanel.getPreferredSize());
        description.setFont(new Font(Font.SANS_SERIF, 0, 12));
        
        descriptionPanel.add(description);
        
        tableModel = new DefaultTableModel(0, 0);
        
        // Cria a tabela com os dados e colunas
        // e desabilita a edição de células
        table = new JTable(tableModel){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        atualizarTabela();
        
        // Adiciona um event listener para tornar as linhas clicáveis
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e){
                
                if(ProgramDefaults.getUserType() == 1){
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
                                Resultado res = UserController.delete(Integer.parseInt(idValue));

                                if(res.isSucesso()){
                                    JOptionPane.showMessageDialog(null, "Atendente #"+idValue+" excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                                    atualizarTabela();
                                }
                                else{
                                    JOptionPane.showMessageDialog(null, res.getMensagem(), "Erro", JOptionPane.ERROR_MESSAGE);
                                }
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
                            String dataNascValue = table.getModel().getValueAt(modelRow, 5).toString().replaceAll("[^a-zA-Z0-9]", "");

                            // Instancia uma nova tela de editar atendente
                            EditarAtendente editarAtendente = new EditarAtendente(idValue, cpfValue, nomeValue, emailValue, telefoneValue, dataNascValue);
                            editarAtendente.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosed(WindowEvent e){
                                    atualizarTabela();
                                }
                            });
                            editarAtendente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            
                            // Limpa a seleção da tabela
                            // Se a linha continuar selecionada, não é possível
                            // clicá-la novamente
                            table.clearSelection();
                            table.getSelectionModel().clearSelection();
                        }
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
        tableWrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        tableWrap.setBorder(new EmptyBorder(0, 0, 0, 0));
        tableWrap.getViewport().setBackground(new Color(255, 255, 255));
        tableWrap.setBackground(new Color(255, 255, 255));
        
        // Adiciona os elementos na tela
        add(titlePanel);
        add(subtitle);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(description);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(tableWrap);
        
        setBackground(new Color(255, 255, 255));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setAlignmentY(0.0f);
    }
    
    private void atualizarTabela(){
        tableModel = UserController.read(2);
        tableModel.fireTableDataChanged();
        table.setModel(tableModel);
        
        if(ProgramDefaults.getUserType() == 1){
            tableModel.addColumn("X");
            table.getColumn("X").setCellRenderer(new TableDeleteButton(trashIcon));
            table.getColumn("X").setMinWidth(40);
            table.getColumn("X").setMaxWidth(40);
        }
        
        // Verifica o plural
        if(tableModel.getRowCount() == 1){
            subtitle.setText("1 atendente cadastrado");
        }
        else{
            subtitle.setText(tableModel.getRowCount() + " atendentes cadastrados");
        }
        
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(230, 230, 230));
        table.setRowHeight(40);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
    }
}