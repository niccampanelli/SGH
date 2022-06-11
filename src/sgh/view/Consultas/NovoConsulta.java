package sgh.view.Consultas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.time.Year;
import java.util.ArrayList;
import javax.swing.border.*;
import javax.swing.text.*;
import sgh.controller.ConsultaController;
import sgh.controller.PacienteController;
import sgh.controller.UserController;
import sgh.util.Resultado;
import sgh.view.Components.CustomButton;
import sgh.view.Components.CustomField;
import sgh.view.Components.CustomFormatted;

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
    
    private String checarData(String data){
        String[] dataSplit = data.split("/");
        int dia = Integer.parseInt(dataSplit[0]);
        int mes = Integer.parseInt(dataSplit[1]);
        int ano = Integer.parseInt(dataSplit[2]);

        for(int i = dataSplit.length-1; i >= 0; i--){
            if(i == 2){
                if(Integer.parseInt(dataSplit[2]) < Year.now().getValue() || Integer.parseInt(dataSplit[2]) > Year.now().getValue()+10){
                    ano = Year.now().getValue();
                }
            }
            else if(i == 1){
                if(Integer.parseInt(dataSplit[1]) < 1 || Integer.parseInt(dataSplit[1]) > 12){
                    mes = 12;
                }
            }
            else if(i == 0){
                if(Integer.parseInt(dataSplit[1]) < 8){
                    if(Integer.parseInt(dataSplit[1]) % 2 == 0){
                        if(Integer.parseInt(dataSplit[1]) == 2){
                            if(Integer.parseInt(dataSplit[0]) < 1 || Integer.parseInt(dataSplit[0]) > 28){
                                dia = 28;
                            }
                        }
                        else{
                            if(Integer.parseInt(dataSplit[0]) < 1 || Integer.parseInt(dataSplit[0]) > 30){
                                dia = 30;
                            }
                        }
                    }
                    else{
                        if(Integer.parseInt(dataSplit[0]) < 1 || Integer.parseInt(dataSplit[0]) > 31){
                            dia = 31;
                        }
                    }
                }
                else{
                    if(Integer.parseInt(dataSplit[1]) % 2 == 0){
                        if(Integer.parseInt(dataSplit[0]) < 1 || Integer.parseInt(dataSplit[0]) > 31){
                            dia = 31;
                        }
                    }
                    else{
                        if(Integer.parseInt(dataSplit[0]) < 1 || Integer.parseInt(dataSplit[0]) > 30){
                            dia = 30;
                        }
                    }
                }
            }
        }

        String diaStr = "01";
        String mesStr = "01";
        String anoStr = String.valueOf(ano);

        if(dia < 10)
            diaStr = "0"+dia;
        else
            diaStr = String.valueOf(dia);

        if(mes < 10)
            mesStr = "0"+mes;
        else
            mesStr = String.valueOf(mes);

        if(!data.replaceAll("/", "").equals(diaStr+""+mesStr+""+anoStr))
            JOptionPane.showMessageDialog(null, "Data substituida por uma data válida.", "Data inválida", JOptionPane.WARNING_MESSAGE);

        return diaStr+""+mesStr+""+anoStr;
    }
    
    private String checarHora(String texto){
        String[] horaSplit = texto.split(":");
        int hora = Integer.parseInt(horaSplit[0]);
        int min = Integer.parseInt(horaSplit[1]);
        
        for(int i = 0; i < horaSplit.length; i++){
            if(i == 0){
                if(hora > 23){
                    hora = 23;
                }
            }
            else{
                if(min > 59){
                    min = 59;
                }
            }
        }
        
        String horaStr = "01";
        String minStr = "01";
        
        if(hora < 10)
            horaStr = "0" + hora;
        else
            horaStr = String.valueOf(hora);
        
        if(min < 10)
            minStr = "0" + min;
        else
            minStr = String.valueOf(min);

        if(!texto.replaceAll(":", "").equals(horaStr+""+minStr))
            JOptionPane.showMessageDialog(null, "Hora substituida por uma hora válida.", "Hora inválida", JOptionPane.WARNING_MESSAGE);

        return horaStr+""+minStr;
    }
    
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
            
            Resultado res = ConsultaController.create(Integer.parseInt(medicoId), Integer.parseInt(pacienteId), data, hora);
            
            if(res.isSucesso()){
            
                JOptionPane.showMessageDialog(null, "Consulta adicionada com sucesso!\n"
                        + "ID da consulta adicionada: "+ res.getCorpo().toString(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                dispose();
            }
            else{
                JOptionPane.showMessageDialog(null, res.getMensagem(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
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
        
        String spec = "";
        
        if(especialidadeField.getSelectedItem() != null)
            spec = especialidadeField.getSelectedItem().toString();
        
        int especialidadeId = UserController.readEspecialidades(spec);
        
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
        dataField.addFocusListener(new FocusListener(){

            @Override  
            public void focusGained(FocusEvent e){
                
            }

            @Override  
            public void focusLost(FocusEvent e){
                dataField.setText(checarData(dataField.getText()));
            }
        });
        
        horaField = new CustomFormatted(horaMask);
        horaField.setAlignmentX(0.0f);
        horaField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        horaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        horaField.setMargin(new Insets(0, 10, 0, 10));
        horaField.addFocusListener(new FocusListener(){

            @Override  
            public void focusGained(FocusEvent e){
                
            }

            @Override  
            public void focusLost(FocusEvent e){
                horaField.setText(checarHora(horaField.getText()));
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