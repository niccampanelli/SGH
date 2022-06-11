package sgh.view.Consultas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.border.*;
import javax.swing.text.*;
import sgh.controller.ExameController;
import sgh.util.ProgramDefaults;
import sgh.util.Resultado;
import sgh.view.Components.CustomButton;
import sgh.view.Components.CustomField;

/**
 * Tela para editar exames
 * @author Nicholas Campanelli
 */
public class EditarExame extends JFrame{
    
    // Componentes da tela
    private final BoxLayout canvasLayout, mainLayout, buttonLayout;
    private final BorderLayout wrapLayout;
    private final JPanel wrapPanel, mainPanel, buttonPanel;
    private final JScrollPane descricaoPanel, resultadoPanel;
    private final JLabel title, subtitle, tituloLabel, descricaoLabel, resultadoLabel;
    private final CustomField tituloField;
    private final JTextArea descricaoField, resultadoField;
    private final CustomButton cancelButton, addButton;
    
    // Variáveis de lógica
    private String id, titulo, descricao, resultado;
    
    private void editar(){
        
        this.descricao = descricaoField.getText();
        this.resultado = resultadoField.getText();
        
        Resultado res = ExameController.update(Integer.parseInt(id), descricao, resultado);

        if(res.isSucesso()){

            JOptionPane.showMessageDialog(null, "Exame modificado com sucesso!\n"
                    + "ID da exame modificado: "+id, "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, res.getMensagem(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public EditarExame(int id, String titulo, String descricao, String resultado){
        super("Editar exame");
        
        this.id = String.valueOf(id);
        this.titulo = titulo;
        this.descricao = descricao;
        this.resultado = resultado;
        
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
        
        title = new JLabel("Editar exame");
        title.setFont(new Font(Font.SANS_SERIF, 1, 24));
        
        subtitle = new JLabel("#" + id);
        
        tituloLabel = new JLabel("Título");
        
        descricaoLabel = new JLabel("Descrição");
        
        resultadoLabel = new JLabel("Resultado");
        
        tituloField = new CustomField(titulo);
        tituloField.setEnabled(false);
        tituloField.setAlignmentX(0.0f);
        tituloField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        tituloField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        tituloField.setMargin(new Insets(0, 10, 0, 10));
        
        descricaoField = new JTextArea(descricao);
        descricaoField.setLineWrap(true);
        descricaoField.setRows(3);
        
        descricaoPanel = new JScrollPane(descricaoField);
        descricaoPanel.setAlignmentX(0.0f);
        descricaoPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));
        descricaoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        descricaoPanel.setBackground(new Color(255, 255, 255));
        descricaoPanel.setBorder(new CompoundBorder(
                    new MatteBorder(0, 0, 3, 0, ProgramDefaults.getBaseColor()),
                    new CompoundBorder(
                        new MatteBorder(2, 2, 0, 2, new Color(0, 0, 0, 10)),
                        new EmptyBorder(10, 10, 0, 0)
        )));
        
        resultadoField = new JTextArea(resultado);
        resultadoField.setLineWrap(true);
        resultadoField.setRows(3);
        
        resultadoPanel = new JScrollPane(resultadoField);
        resultadoPanel.setAlignmentX(0.0f);
        resultadoPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));
        resultadoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        resultadoPanel.setBackground(new Color(255, 255, 255));
        resultadoPanel.setBorder(new CompoundBorder(
                    new MatteBorder(0, 0, 3, 0, ProgramDefaults.getBaseColor()),
                    new CompoundBorder(
                        new MatteBorder(2, 2, 0, 2, new Color(0, 0, 0, 10)),
                        new EmptyBorder(10, 10, 0, 0)
        )));
        
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
                if((!tituloField.getText().equals(titulo)) || 
                    (!descricaoField.getText().equals(descricao)) ||
                    (!resultadoField.getText().equals(resultado))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Tem certeza que deseja cancelar a modificação do exame?\n"
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
        
        addButton = new CustomButton("Salvar");
        addButton.setAlignmentX(0.0f);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                editar();
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
        mainPanel.add(descricaoPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(resultadoLabel);
        mainPanel.add(resultadoPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(buttonPanel);
        
        wrapPanel.add(mainPanel);
        canvas.add(wrapPanel);
        
        setSize(400, 500);
        setLayout(canvasLayout);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                
                if((!tituloField.getText().equals(titulo)) || 
                    (!descricaoField.getText().equals(descricao)) ||
                    (!resultadoField.getText().equals(resultado))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Tem certeza que deseja cancelar a modificação do exame?\n"
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