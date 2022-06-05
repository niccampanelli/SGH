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
import projetoa3.controller.UserController;
import projetoa3.util.Resultado;
import projetoa3.view.Components.CustomButton;
import projetoa3.view.Components.CustomFormatted;

/**
 * Tela de edição de consultas
 * @author Nicholas Campanelli
 */
public class EditarConsulta extends JFrame{
    
    // Componentes da interface
    private BoxLayout canvasLayout, mainLayout, buttonLayout;
    private BorderLayout wrapLayout;
    private JPanel wrapPanel, mainPanel, buttonPanel;
    private JLabel title, subtitle, pacienteLabel, especialidadeLabel, medicoLabel, dataLabel, horaLabel;
    private JComboBox pacienteField, especialidadeField, medicoField;
    private CustomFormatted dataField, horaField;
    private MaskFormatter dataMask, horaMask;
    private CustomButton seeExamsButton, examButton, cancelButton, addButton;
    
    // Variáveis de lógica
    private ArrayList<String> especialidades;
    private DefaultComboBoxModel medicoModel;
    private int quantidadeExames;
    private String id, paciente, especialidade, medico, data, hora;
    
    private void editar(){
        
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
            
            String[] medicoSplit = medico.split(" - ");
            String medicoId = medicoSplit[0].replaceAll("#", "");
            
            Resultado res = ConsultaController.update(Integer.parseInt(id), Integer.parseInt(medicoId), data, hora);
            
            if(res.isSucesso()){
            
                JOptionPane.showMessageDialog(null, "Consulta modificada com sucesso!\n"
                        + "ID da consulta modificada: "+id, "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                dispose();
            }
            else{
                JOptionPane.showMessageDialog(null, res.getMensagem(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void contarExames(){
        ArrayList<String> res = ExameController.readExame("COUNT(*)", "id_consulta", id);
        
        if(!res.isEmpty()){
            quantidadeExames = Integer.parseInt(res.get(0));
        }
        else{
            System.err.println("Erro ao obter quantidade de exames da consulta #"+id+".");
        }
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
    
    private void buildGUI(){
        
        Container canvas = getContentPane();
        canvas.removeAll(); // Remove os elementos do canvas para atualizá-lo
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
        
        title = new JLabel("Editar consulta");
        title.setFont(new Font(Font.SANS_SERIF, 1, 24));
        
        subtitle = new JLabel("#" + id);
        
        pacienteLabel = new JLabel("Escolha um paciente");
        
        especialidadeLabel = new JLabel("Especialidade");
        
        medicoLabel = new JLabel("Médico da consulta");
        
        dataLabel = new JLabel("Data");
        
        horaLabel = new JLabel("Hora");
        
        pacienteField = new JComboBox(new Object[]{ paciente });
        pacienteField.setAlignmentX(0.0f);
        pacienteField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        pacienteField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        pacienteField.setEditable(false);
        pacienteField.setEnabled(false);
        pacienteField.setSelectedItem(paciente);
        
        especialidades = UserController.readEspecialidades();
        
        especialidadeField = new JComboBox(especialidades.toArray());
        especialidadeField.setAlignmentX(0.0f);
        especialidadeField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        especialidadeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        especialidadeField.setEditable(false);
        especialidadeField.setSelectedItem(especialidade);
        especialidadeField.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarMedicos();
            }
        });
        
        medicoField = new JComboBox();
        atualizarMedicos();
        medicoField.setAlignmentX(0.0f);
        medicoField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        medicoField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        medicoField.setEditable(false);
        medicoField.setSelectedItem(medico);
        
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
        dataField.setText(data);
        dataField.setAlignmentX(0.0f);
        dataField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        dataField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        dataField.setMargin(new Insets(0, 10, 0, 10));
        
        horaField = new CustomFormatted(horaMask);
        horaField.setText(hora);
        horaField.setAlignmentX(0.0f);
        horaField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        horaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        horaField.setMargin(new Insets(0, 10, 0, 10));
        
        seeExamsButton = new CustomButton("Ver exames");
        seeExamsButton.setAlignmentX(0.0f);
        seeExamsButton.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        seeExamsButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        seeExamsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        seeExamsButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Exames exames = new Exames(Integer.parseInt(id));
                exames.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e){
                        contarExames();
                        buildGUI();
                    }
                });
                exames.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                contarExames();
                buildGUI();
            }
        });
        
        examButton = new CustomButton("Adicionar Exame");
        examButton.setAlignmentX(0.0f);
        examButton.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        examButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        examButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        examButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                NovoExame novoExame = new NovoExame(Integer.parseInt(id));
                novoExame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e){
                        contarExames();
                        buildGUI();
                    }
                });
                novoExame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                contarExames();
                buildGUI();
            }
        });
        
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
                if((!pacienteField.getSelectedItem().toString().equals(paciente)) || 
                    (!especialidadeField.getSelectedItem().toString().equals(especialidade)) ||
                    (!medicoField.getSelectedItem().toString().equals(medico)) ||
                    (!dataField.getText().equals(data)) ||
                    (!horaField.getText().equals(hora))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Tem certeza que deseja cancelar a edição desta consulta?\n"
                      + "Os dados inseridos serão perdidos e a consulta irá permanecer com os dados anteriores.",
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
        
        addButton = new CustomButton("Concluido");
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
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        if(quantidadeExames > 0){
            mainPanel.add(seeExamsButton);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        mainPanel.add(examButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(buttonPanel);
        
        wrapPanel.add(mainPanel);
        canvas.add(wrapPanel);
        
        if(quantidadeExames > 0)
            setSize(400, 670);
        else
            setSize(400, 620);
        
        // Atualiza o layout para acomodar os componentes
        validate();
    }
            
    public EditarConsulta(String id, String paciente, String especialidade, String medico, String data, String hora){
        super("Editar consulta");
        
        this.id = id;
        this.paciente = paciente;
        this.especialidade = especialidade;
        this.medico = medico;
        this.data = data;
        this.hora = hora;
        
        contarExames();
        buildGUI();
        
        setLayout(canvasLayout);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                
                if((!pacienteField.getSelectedItem().toString().equals(paciente)) || 
                    (!especialidadeField.getSelectedItem().toString().equals(especialidade)) ||
                    (!medicoField.getSelectedItem().toString().equals(medico)) ||
                    (!dataField.getText().equals(data)) ||
                    (!horaField.getText().equals(hora))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Tem certeza que deseja cancelar a edição desta consulta?\n"
                      + "Os dados inseridos serão perdidos e a consulta irá permanecer com os dados anteriores.",
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