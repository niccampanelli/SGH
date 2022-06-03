package projetoa3.view.Consultas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.border.*;
import javax.swing.text.*;
import projetoa3.controller.PacienteController;
import projetoa3.controller.UserController;
import projetoa3.util.Resultado;
import projetoa3.view.Components.CustomButton;
import projetoa3.view.Components.CustomField;
import projetoa3.view.Components.CustomFormatted;

/**
 * Tela de criação de consultas
 * @author Nicholas Campanelli
 */
public class NovoConsulta extends JFrame{
    
    // Componentes da interface
    private final BoxLayout canvasLayout, mainLayout, buttonLayout;
    private final BorderLayout wrapLayout;
    private final JPanel wrapPanel, mainPanel, buttonPanel;
    private final JLabel title, pacienteLabel, especialidadeLabel, medicoLabel, dataLabel, horaLabel;
    private final JComboBox pacienteField, especialidadeField, medicoField;
    private final CustomFormatted dataField, horaField;
    private MaskFormatter dataMask, horaMask;
    private final CustomButton cancelButton, addButton;
    
    // Variáveis de lógica
    private ArrayList<String> especialidades;
    private DefaultComboBoxModel pacienteModel, medicoModel;
    private String paciente, especialidade, medico, data, hora;
    
    private void adicionar(){
        
        paciente = pacienteField.getSelectedItem().toString();
        especialidade = especialidadeField.getSelectedItem().toString();
        medico = medicoField.getSelectedItem().toString();
        data = dataField.getText();
        hora = horaField.getText();
        
        if("".equals(paciente) || paciente == null){
            JOptionPane.showMessageDialog(null, "Escolha um paciente válido.", "Paciente inválido", JOptionPane.WARNING_MESSAGE);
        }
        else if("".equals(especialidade) || especialidade == null){
            JOptionPane.showMessageDialog(null, "Escolha uma especialidade válida.", "Especialidade inválida", JOptionPane.WARNING_MESSAGE);
        }
        else if("".equals(medico) || medico == null){
            JOptionPane.showMessageDialog(null, "Escolha um médico válido.", "Médico inválido", JOptionPane.WARNING_MESSAGE);
        }
        else if("".equals(data) || data.length() != 10 || data.indexOf('/') == -1 || data.endsWith("0000")){
            JOptionPane.showMessageDialog(null, "Insira uma data da consulta válida.", "Data inválida", JOptionPane.WARNING_MESSAGE);
        }
        else if("".equals(hora) || hora.length() != 5 || hora.indexOf(':') == -1){
            JOptionPane.showMessageDialog(null, "Insira uma hora da consulta válida.", "Hora da consulta inválida", JOptionPane.WARNING_MESSAGE);
        }
        else{
            
            String[] pacienteSplit = paciente.split(" - ");
            String pacienteId = pacienteSplit[0].replaceAll("#", "");
            
            String[] medicoSplit = medico.split(" - ");
            String medicoId = medicoSplit[0].replaceAll("#", "");
        }
    }
    
    private void atualizarPacientes(){
        pacienteModel = new DefaultComboBoxModel();
        pacienteField.setModel(pacienteModel);
        
        ArrayList<String> pacientes = PacienteController.readPaciente("id", "1", "1");
        pacienteModel.removeAllElements();
        pacientes.forEach(id -> {
            
            Resultado res = PacienteController.readPaciente(Integer.parseInt(id), "nome");
            String nome = "";
            
            if(res.isSucesso()){
                nome = res.getCorpo().toString();
            }
            
            pacienteModel.addElement("#"+id + " - " + nome);
        });
    }
    
    private void atualizarMedicos(){
        medicoModel = new DefaultComboBoxModel();
        medicoField.setModel(medicoModel);
        
        int especialidadeId = UserController.readEspecialidades(especialidadeField.getSelectedItem().toString());
        
        ArrayList<String> medicos = UserController.readUser("id", "especialidade", String.valueOf(especialidadeId));
        medicoModel.removeAllElements();
        medicos.forEach(id -> {
            
            Resultado res = UserController.readUser(Integer.parseInt(id), "nome");
            String nome = "";
            
            if(res.isSucesso()){
                nome = res.getCorpo().toString();
            }
            
            medicoModel.addElement("#"+id + " - " + nome);
        });
    }
    
    public NovoConsulta(){
        super("Abrir consulta");
        
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
        
        title = new JLabel("Abrir consulta");
        title.setFont(new Font(Font.SANS_SERIF, 1, 24));
        
        pacienteLabel = new JLabel("Escolha um paciente");
        
        especialidadeLabel = new JLabel("Especialidade");
        
        medicoLabel = new JLabel("Médico da consulta");
        
        dataLabel = new JLabel("Data");
        
        horaLabel = new JLabel("Hora");
        
        pacienteField = new JComboBox();
        pacienteField.setAlignmentX(0.0f);
        pacienteField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        pacienteField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        pacienteField.setEditable(false);
        atualizarPacientes();
        
        especialidades = UserController.readEspecialidades();
        
        especialidadeField = new JComboBox(especialidades.toArray());
        especialidadeField.setAlignmentX(0.0f);
        especialidadeField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        especialidadeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        especialidadeField.setEditable(false);
        especialidadeField.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarMedicos();
            }
        });
        
        medicoField = new JComboBox();
        medicoField.setAlignmentX(0.0f);
        medicoField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        medicoField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        medicoField.setEditable(false);
        atualizarMedicos();
        
        try{
            dataMask = new MaskFormatter("##/##/####");
            dataMask.setPlaceholderCharacter('0');
            horaMask = new MaskFormatter("##:##");
            horaMask.setPlaceholderCharacter('0');
        }
        catch(ParseException ex){
            System.out.println("Erro na máscara");
        }
        
        dataField = new CustomFormatted(dataMask);
        dataField.setAlignmentX(0.0f);
        dataField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        dataField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        dataField.setMargin(new Insets(0, 10, 0, 10));
        
        horaField = new CustomFormatted(horaMask);
        horaField.setAlignmentX(0.0f);
        horaField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        horaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        horaField.setMargin(new Insets(0, 10, 0, 10));
        
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
                if((pacienteField.getSelectedItem() != null || (pacienteField.getSelectedItem() != null && pacienteField.getSelectedItem() != "")) || 
                    (especialidadeField.getSelectedItem() != null || (especialidadeField.getSelectedItem() != null && especialidadeField.getSelectedItem() != "")) ||
                    (medicoField.getSelectedItem() != null || (medicoField.getSelectedItem() != null && medicoField.getSelectedItem() != "")) ||
                    (!dataField.getText().equals("00/00/0000")) ||
                    (!horaField.getText().equals("00:00"))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Cancelar a abertura de uma nova consulta?\n"
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
        
        addButton = new CustomButton("Concluir");
        addButton.setAlignmentX(0.0f);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                adicionar();
            }
        });
        
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(addButton);
        
        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(pacienteLabel);
        mainPanel.add(pacienteField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(especialidadeLabel);
        mainPanel.add(especialidadeField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(medicoLabel);
        mainPanel.add(medicoField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(dataLabel);
        mainPanel.add(dataField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(horaLabel);
        mainPanel.add(horaField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(buttonPanel);
        
        wrapPanel.add(mainPanel);
        canvas.add(wrapPanel);
        
        setSize(400, 550);
        setLayout(canvasLayout);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                
                if((pacienteField.getSelectedItem() != null || (pacienteField.getSelectedItem() != null && pacienteField.getSelectedItem() != "")) || 
                    (especialidadeField.getSelectedItem() != null || (especialidadeField.getSelectedItem() != null && especialidadeField.getSelectedItem() != "")) ||
                    (medicoField.getSelectedItem() != null || (medicoField.getSelectedItem() != null && medicoField.getSelectedItem() != "")) ||
                    (!dataField.getText().equals("00/00/0000")) ||
                    (!horaField.getText().equals("00:00"))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Cancelar a abertura de uma nova consulta?\n"
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