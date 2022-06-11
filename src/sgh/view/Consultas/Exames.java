package sgh.view.Consultas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
import sgh.controller.ConsultaController;
import sgh.controller.ExameController;
import sgh.util.Resultado;
import sgh.view.Components.TableDeleteButton;
import sgh.view.Components.TableHeader;

/**
 *
 * @author Nicholas Campanelli
 */
public class Exames extends JFrame{
    
    // Componentes da interface
    private final BoxLayout canvasLayout, mainLayout;
    private final BorderLayout wrapLayout;
    private final JPanel wrapPanel, mainPanel;
    private final JScrollPane tableWrap;
    private final JTable table;
    private DefaultTableModel tableModel;
    private Image trashIcon;
    
    // Variáveis de lógica
    private int idConsulta;
    
    public Exames(int idConsulta){
        super("Exames da consulta #"+idConsulta);
        
        try{
            URL url = getClass().getResource("/sgh/util/icons/trashIcon.png");
            trashIcon = ImageIO.read(url);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        this.idConsulta = idConsulta;
        
        Container canvas = getContentPane();
        canvasLayout = new BoxLayout(canvas, BoxLayout.PAGE_AXIS);
        wrapLayout = new BorderLayout();
        
        wrapPanel = new JPanel();
        wrapPanel.setLayout(wrapLayout);
        
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
                        
                        // Pega o ID e o titulo para serem mostrados
                        String idValue = table.getModel().getValueAt(modelRow, 0).toString();
                        String tituloValue = table.getModel().getValueAt(modelRow, 1).toString();
                        
                        // Opções do painel de confirmação
                        Object[] options = {"Excluir", "Cancelar"};
                        
                        // Mostra um painel de confirmação, para impedir exclusões acidentais
                        int confirmed = JOptionPane.showOptionDialog(null, 
                          "Tem certeza que deseja excluir o exame #"+idValue+" - "+tituloValue+"?\n"
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
                            Resultado res = ExameController.delete(Integer.parseInt(idValue));
                            
                            if(res.isSucesso()){
                                JOptionPane.showMessageDialog(null, "Exame #"+idValue+" removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                                
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
                        String tituloValue = table.getModel().getValueAt(modelRow, 1).toString();
                        String descricaoValue = "";
                        String resultadoValue = "";
                        
                        Resultado descricaoRes = ExameController.readExame(Integer.parseInt(idValue), "descricao");
                        Resultado resultadoRes = ExameController.readExame(Integer.parseInt(idValue), "resultado");
                        
                        if(descricaoRes.isSucesso())
                            descricaoValue = descricaoRes.getCorpo().toString();
                        
                        if(resultadoRes.isSucesso())
                            resultadoValue = resultadoRes.getCorpo().toString();
                        
                        // Instancia uma nova tela de editar consultas
                        EditarExame editarExame = new EditarExame(Integer.parseInt(idValue), tituloValue, descricaoValue, resultadoValue);
                        editarExame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e){
                                atualizarTabela();
                            }
                        });
                        editarExame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        
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
        tableWrap.getViewport().setBackground(new Color(255, 255, 255));
        tableWrap.setBackground(new Color(255, 255, 255));
        
        mainPanel = new JPanel();
        mainLayout = new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS);
        mainPanel.setLayout(mainLayout);
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setAlignmentX(0.0f);
        mainPanel.setAlignmentY(0.0f);
        
        mainPanel.add(tableWrap);
        
        wrapPanel.add(mainPanel);
        canvas.add(wrapPanel);
        
        setSize(400, 400);
        setLayout(canvasLayout);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    
    private void atualizarTabela(){
        tableModel = ExameController.read(idConsulta);
        tableModel.fireTableDataChanged();
        tableModel.addColumn("X");
        
        if(tableModel.getRowCount() == 0){
            dispose();
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
        table.getColumn("X").setMinWidth(40);
        table.getColumn("X").setMaxWidth(40);
    }
}
