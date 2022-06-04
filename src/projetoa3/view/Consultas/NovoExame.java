package projetoa3.view.Consultas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.border.*;
import javax.swing.text.*;
import projetoa3.controller.ConsultaController;
import projetoa3.controller.ExameController;
import projetoa3.util.Resultado;
import projetoa3.view.Components.CustomButton;
import projetoa3.view.Components.CustomField;

/**
 * Tela para adicionar exames à uma consulta
 * @author Nicholas Campanelli
 */
public class NovoExame extends JFrame{
    
    // Componentes da tela
    private final BoxLayout canvasLayout, mainLayout, buttonLayout;
    private final BorderLayout wrapLayout;
    private final JPanel wrapPanel, mainPanel, buttonPanel;
    private final JLabel title, subtitle, tituloLabel, descricaoLabel, resultadoLabel;
    private final CustomField tituloField;
    private final JTextArea descricaoField, resultadoField;
    private final CustomButton cancelButton, addButton;
    
    // Variáveis de lógica
    private String idConsulta, titulo, descricao, resultado;
    
    private void adicionarExame(){
        
        this.titulo = tituloField.getText();
        this.descricao = descricaoField.getText();
        this.resultado = resultadoField.getText();
            
        Resultado res = ExameController.create(Integer.parseInt(idConsulta), titulo, descricao, resultado);

        if(res.isSucesso()){

            JOptionPane.showMessageDialog(null, "Exame cadastrado com sucesso!\n"
                    + "ID da exame cadastrado: "+res.getCorpo().toString(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, res.getMensagem(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public NovoExame(int idConsulta){
        super("Cadastrar exame");
        
        this.idConsulta = String.valueOf(idConsulta);
        
        Container canvas = getContentPane();
        canvasLayout = new BoxLayout(canvas, BoxLayout.PAGE_AXIS);
        wrapLayout = new BorderLayout();
        
        wrapPanel = new JPanel();
        wrapPanel.setLayout(wrapLayout);
        
        mainPanel = new JPanel();
        mainLayout = new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS);
        mainPanel.setLayout(mainLayout);
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        mainPanel.setAlignmentX(0.0f);
        mainPanel.setAlignmentY(0.0f);
        
        title = new JLabel("Cadastrar exame");
        title.setFont(new Font(Font.SANS_SERIF, 1, 24));
        
        subtitle = new JLabel("para a consulta #" + idConsulta);
        
        tituloLabel = new JLabel("Título");
        
        descricaoLabel = new JLabel("Descrição");
        
        resultadoLabel = new JLabel("Resultado");
        
        tituloField = new CustomField("");
        tituloField.setAlignmentX(0.0f);
        tituloField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        tituloField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        tituloField.setMargin(new Insets(0, 10, 0, 10));
        
        descricaoField = new JTextArea("");
        descricaoField.setLineWrap(true);
        descricaoField.setRows(3);
        descricaoField.setAlignmentX(0.0f);
        descricaoField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        descricaoField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        descricaoField.setMargin(new Insets(0, 10, 0, 10));
        
        resultadoField = new JTextArea("");
        resultadoField.setLineWrap(true);
        resultadoField.setRows(3);
        resultadoField.setAlignmentX(0.0f);
        resultadoField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        resultadoField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        resultadoField.setMargin(new Insets(0, 10, 0, 10));
        
        buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(0.0f);
        buttonLayout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
        buttonPanel.setLayout(buttonLayout);
        buttonPanel.setBackground(null);
        
        cancelButton = new CustomButton("Cancelar");
        cancelButton.setAlignmentX(0.0f);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if((!tituloField.getText().equals("")) || 
                    (!descricaoField.getText().equals("")) ||
                    (!resultadoField.getText().equals(""))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Tem certeza que deseja cancelar o cadastro de um novo exame?\n"
                      + "Os dados inseridos serão perdidos.",
                      "Cancelar",
                      JOptionPane.YES_NO_OPTION);

                    if(confirmed == JOptionPane.YES_OPTION) {
                      dispose();
                    }
                }
                else{
                    dispose();
                }
            }
        });
        
        addButton = new CustomButton("Adicionar");
        addButton.setAlignmentX(0.0f);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                adicionarExame();
            }
        });
        
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(addButton);
        
        mainPanel.add(title);
        mainPanel.add(subtitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(tituloLabel);
        mainPanel.add(tituloField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(descricaoLabel);
        mainPanel.add(descricaoField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(resultadoLabel);
        mainPanel.add(resultadoField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(buttonPanel);
        
        wrapPanel.add(mainPanel);
        canvas.add(wrapPanel);
        
        setSize(400, 440);
        setLayout(canvasLayout);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                
                if((!tituloField.getText().equals("")) || 
                    (!descricaoField.getText().equals("")) ||
                    (!resultadoField.getText().equals(""))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Tem certeza que deseja cancelar o cadastro de um novo exame?\n"
                      + "Os dados inseridos serão perdidos.",
                      "Cancelar",
                      JOptionPane.YES_NO_OPTION);

                    if(confirmed == JOptionPane.YES_OPTION) {
                      dispose();
                    }
                }
                else{
                    dispose();
                }
            }
        });
        
    }
    
}
