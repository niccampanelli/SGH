package sgh.view.Medicos;
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
import sgh.util.Resultado;

/**
 * Painel de médicos
 * @author Nicholas Campanelli
 */
public class Medicos extends JPanel{
    
    // Componentes da Tela
    private final BoxLayout titleLayout, descriptionLayout;
    private final JPanel titlePanel, descriptionPanel;
    private final JLabel title, description;
    private final JLabel subtitle;
    private final CustomButton addButton;
    private final JScrollPane tableWrap;
    private final JTable table;
    private DefaultTableModel tableModel;
    private Image trashIcon;
    
    // Construtor
    public Medicos(){
        
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
        
        title = new JLabel("Medicos");
        title.setFont(new Font(Font.SANS_SERIF, 1, 24));
        subtitle = new JLabel("1 médico cadastrado");
        
        // Botão de adicionar médico
        addButton = new CustomButton("Adicionar médico");
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                // Instancia uma tela de adicionar médico
                NovoMedico novoMedico = new NovoMedico();
                novoMedico.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e){
                        atualizarTabela();
                    }
                });
                novoMedico.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        
        description = new JLabel("<html>Os médicos cadastrados no sistema estão listados abaixo. Apenas administradores podem adicionar médicos na plataforma. Estes médicos serão listados ao iniciar a criação de uma consulta, habilitando o gerente do sistema (atendentes, médicos ou administradores) a atribuição de médicos na consulta a ser criada.</html>");
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
                        String nomeValue = table.getModel().getValueAt(modelRow, 3).toString();
                        
                        // Opções do painel de confirmação
                        Object[] options = {"Excluir", "Cancelar"};
                        
                        // Mostra um painel de confirmação, para impedir exclusões acidentais
                        int confirmed = JOptionPane.showOptionDialog(null, 
                          "Tem certeza que deseja excluir o médico #"+idValue+" - "+nomeValue+"?\n"
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
                                JOptionPane.showMessageDialog(null, "Médico #"+idValue+" excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                                
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
                        String crmValue = table.getModel().getValueAt(modelRow, 2).toString();
                        String nomeValue = table.getModel().getValueAt(modelRow, 3).toString();
                        String especialidadeValue = table.getModel().getValueAt(modelRow, 4).toString();
                        String emailValue = table.getModel().getValueAt(modelRow, 5).toString();
                        String telefoneValue = table.getModel().getValueAt(modelRow, 6).toString();
                        String sexoValue = "Masculino";
                        String dataNascValue = "21/12/2003";
                        
                        Resultado sexoRes =  UserController.readUser(Integer.parseInt(idValue), "sexo");
                        Resultado dataNascRes =  UserController.readUser(Integer.parseInt(idValue), "data_nascimento");
                        
                        if(sexoRes.isSucesso()){
                            if(sexoRes.getCorpo().equals("m")){
                                sexoValue = "Masculino";
                            }
                            else if(sexoRes.getCorpo().equals("f")){
                                sexoValue = "Feminino";
                            }
                        }
                        
                        if(dataNascRes.isSucesso()){
                            String[] dataNascSplit = dataNascRes.getCorpo().toString().split("-");
                            dataNascValue = dataNascSplit[2] + "/" + dataNascSplit[1] + "/" + dataNascSplit[0];
                        }
                        
                        // Instancia uma nova tela de editar médico
                        EditarMedico editarMedico = new EditarMedico(idValue, cpfValue, nomeValue, crmValue, especialidadeValue, emailValue, telefoneValue, sexoValue, dataNascValue);
                        editarMedico.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e){
                                atualizarTabela();
                            }
                        });
                        editarMedico.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        
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
        tableModel = UserController.read(3);
        tableModel.fireTableDataChanged();
        tableModel.addColumn("X");
        
        // Verifica o plural
        if(tableModel.getRowCount() == 1){
            subtitle.setText("1 médico cadastrado");
        }
        else{
            subtitle.setText(tableModel.getRowCount() + " médicos cadastrados");
        }
        
        table.setModel(tableModel);
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
        table.getColumn("CRM").setMinWidth(80);
        table.getColumn("CRM").setMaxWidth(80);
        table.getColumn("Especialidade").setMinWidth(130);
        table.getColumn("Especialidade").setMaxWidth(130);
        table.getColumn("E-mail").setMinWidth(180);
        table.getColumn("E-mail").setMaxWidth(180);
        table.getColumn("Telefone").setMinWidth(100);
        table.getColumn("Telefone").setMaxWidth(100);
        table.getColumn("Cadastrado em").setMinWidth(100);
        table.getColumn("Cadastrado em").setMaxWidth(100);
        table.getColumn("X").setMinWidth(40);
        table.getColumn("X").setMaxWidth(40);
    }
}