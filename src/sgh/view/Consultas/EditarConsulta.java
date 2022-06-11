package sgh.view.Consultas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.text.ParseException;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.border.*;
import javax.swing.text.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import sgh.controller.ConsultaController;
import sgh.controller.ExameController;
import sgh.controller.UserController;
import sgh.util.Resultado;
import sgh.util.database.ConnectionClass;
import sgh.view.Components.CustomButton;
import sgh.view.Components.CustomFormatted;

/**
 * Tela de edição de consultas
 * @author Nicholas Campanelli, Alexandre Soares
 */
public class EditarConsulta extends JFrame{
    
    // Componentes da interface
    private BoxLayout canvasLayout, mainLayout, titleLayout, buttonLayout;
    private BorderLayout wrapLayout;
    private JPanel wrapPanel, mainPanel, titlePanel, buttonPanel;
    private JLabel title, subtitle, pacienteLabel, especialidadeLabel, medicoLabel, dataLabel, horaLabel;
    private JComboBox pacienteField, especialidadeField, medicoField;
    private CustomFormatted dataField, horaField;
    private MaskFormatter dataMask, horaMask;
    private CustomButton seeExamsButton, examButton, cancelButton, addButton, imprimir;
    
    // Variáveis de lógica
    private ArrayList<String> especialidades;
    private DefaultComboBoxModel medicoModel;
    private int quantidadeExames;
    private String id, paciente, especialidade, medico, data, hora;
    
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
        
        titlePanel = new JPanel();
        titleLayout = new BoxLayout(titlePanel, BoxLayout.X_AXIS);
        titlePanel.setLayout(titleLayout);
        titlePanel.setAlignmentX(0.0f);
        titlePanel.setBackground(null);
        
        title = new JLabel("Editar consulta");
        title.setFont(new Font(Font.SANS_SERIF, 1, 24));
        
        imprimir = new CustomButton("imprimir");
        imprimir.setAlignmentX(0.0f);
        imprimir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        imprimir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                // Especifica o modelo a ser seguido para gerar o arquivo
                String jrxml = "consulta.jrxml";

                try {
                    // Pega a conexão com o banco
                    Connection conn = ConnectionClass.getConnection();

                    // Gera um arquivo do tipo Jasper com base no modelo passado
                    String jasper = JasperCompileManager.compileReportToFile(jrxml);
                    int intId = Integer.parseInt(id); 
                    
                    // Cria um objeto do tipo Map
                    HashMap filtro = new HashMap();
                    // Insere os dados ao Map
                    filtro.put("id", paciente.split(" - ")[0].replaceAll("#", ""));
                    filtro.put("especialidade", especialidade);
                    filtro.put("medico", medico.split(" - ")[1]);
                    filtro.put("data", data);
                    // Preenche o arquivo Jasper com os dados do map criado acima e passa a classe
                    // de conexão com o banco, para serem puxados os dados do banco
                    JasperPrint jaspertPrint = JasperFillManager.fillReport(jasper, filtro, conn);

                    // Cria uma tela da biblioteca Jasper para visualizar o arquivo
                    JasperViewer view = new JasperViewer(jaspertPrint, false);
                    view.setVisible(true);
                    
                } catch (JRException ex) {
                    System.err.println(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalGlue());
        titlePanel.add(imprimir);
        
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
        horaField.setText(hora);
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
        
        mainPanel.add(titlePanel);
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