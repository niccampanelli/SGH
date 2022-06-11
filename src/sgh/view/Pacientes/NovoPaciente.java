package sgh.view.Pacientes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.time.Year;
import javax.swing.border.*;
import javax.swing.text.*;
import sgh.controller.PacienteController;
import sgh.util.Resultado;
import sgh.view.Components.CustomButton;
import sgh.view.Components.CustomField;
import sgh.view.Components.CustomFormatted;

/**
 * Tela de criação de pacientes
 * @author Nicholas Campanelli
 */
public class NovoPaciente extends JFrame{
    
    // Componentes da interface
    private final BoxLayout canvasLayout, mainLayout, doubleFieldLayout, sexoLayout, dataNascLayout, buttonLayout;
    private final BorderLayout wrapLayout;
    private final JPanel wrapPanel, mainPanel, doubleField, sexoPanel, dataNascPanel, buttonPanel;
    private final JLabel title, cpfLabel, nomeLabel, telefoneLabel, sexoLabel, dataNascLabel, enderecoLabel;
    private final CustomField nomeField, enderecoField;
    private final CustomFormatted cpfField, telefoneField, dataNascField;
    private final JComboBox sexoField;
    private MaskFormatter cpfMask, telefoneMask, dataNascMask;
    private final CustomButton cancelButton, addButton;
    
    // Variáveis de lógica
    private String cpf, nome, telefone, sexo, dataNasc, endereco;
    
    private String checarData(String data){
        String[] dataSplit = data.split("/");
        int dia = Integer.parseInt(dataSplit[0]);
        int mes = Integer.parseInt(dataSplit[1]);
        int ano = Integer.parseInt(dataSplit[2]);

        for(int i = dataSplit.length-1; i >= 0; i--){
            if(i == 2){
                if(Integer.parseInt(dataSplit[2]) < 1900 || Integer.parseInt(dataSplit[2]) > Year.now().getValue()-18){
                    ano = Year.now().getValue()-18;
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
    
    private void adicionar(){
        
        cpf = cpfField.getText();
        nome = nomeField.getText();
        telefone = telefoneField.getText();
        sexo = sexoField.getSelectedItem().toString();
        dataNasc = dataNascField.getText();
        endereco = enderecoField.getText();
        
        if("".equals(cpf) || cpf.length() != 14 || cpf.endsWith("0-00")){
            JOptionPane.showMessageDialog(null, "Insira um CPF válido.", "CPF inválido", JOptionPane.WARNING_MESSAGE);
        }
        else if("".equals(nome) || nome.length() < 10){
            JOptionPane.showMessageDialog(null, "Insira um nome completo com no mínimo 10 caracteres.", "Nome inválido", JOptionPane.WARNING_MESSAGE);
        }
        else if("".equals(telefone) || telefone.length() != 15 || telefone.startsWith("(00)") || telefone.endsWith("0000")){
            JOptionPane.showMessageDialog(null, "Insira um telefone válido.", "Telefone inválido", JOptionPane.WARNING_MESSAGE);
        }
        else if("".equals(sexo) || (!"Masculino".equals(sexo) && !"Feminino".equals(sexo)) || sexo == null){
            JOptionPane.showMessageDialog(null, "Escolha um sexo válido.", "Sexo inválido", JOptionPane.WARNING_MESSAGE);
        }
        else if("".equals(dataNasc) || dataNasc.length() != 10 || dataNasc.endsWith("0000")){
            JOptionPane.showMessageDialog(null, "Insira uma data de nascimento válida.", "Data de nascimento inválida", JOptionPane.WARNING_MESSAGE);
        }
        else if("".equals(endereco) || endereco.length() < 20 || endereco.length() > 100){
            JOptionPane.showMessageDialog(null, "Insira um endereco completo possuindo logradouro, número, bairro e cidade e complemento se necessário.", "Endereco inválido", JOptionPane.WARNING_MESSAGE);
        }
        else{
            
            String cleanCpf = cpf.replaceAll("[^a-zA-Z0-9]", "");
            String cleanTelefone = telefone.replaceAll("[^a-zA-Z0-9]", "");
            String charSexo = "m";
            
            if(sexo.equals("Masculino")){
                charSexo = "m";
            }
            else{
                charSexo = "f";
            }
            
            Resultado res = PacienteController.create(nome, charSexo, dataNasc, cleanCpf, cleanTelefone, endereco);
            
            if(res.isSucesso()){
            
                JOptionPane.showMessageDialog(null, "Paciente cadastrado com sucesso!\n"
                        + "ID do paciente cadastrado: "+ res.getCorpo().toString(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                dispose();
            }
            else{
                JOptionPane.showMessageDialog(null, res.getMensagem(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
            
    public NovoPaciente(){
        super("Adicionar um paciente");
        
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
        
        title = new JLabel("Adicionar um paciente");
        title.setFont(new Font(Font.SANS_SERIF, 1, 24));
        
        cpfLabel = new JLabel("CPF");
        
        nomeLabel = new JLabel("Nome completo");
                
        telefoneLabel = new JLabel("Telefone");
        
        sexoLabel = new JLabel("Sexo");
        
        dataNascLabel = new JLabel("Data de nascimento");
        
        enderecoLabel = new JLabel("Endereço");
        
        nomeField = new CustomField("");
        nomeField.setAlignmentX(0.0f);
        nomeField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        nomeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        nomeField.setMargin(new Insets(0, 10, 0, 10));
        
        try{
            cpfMask = new MaskFormatter("###.###.###-##");
            cpfMask.setPlaceholderCharacter('0');
            telefoneMask = new MaskFormatter("(##) #####-####");
            telefoneMask.setPlaceholderCharacter('0');
            dataNascMask = new MaskFormatter("##/##/####");
            dataNascMask.setPlaceholderCharacter('0');
        }
        catch(ParseException ex){
            System.out.println("Erro na máscara");
        }
        
        cpfField = new CustomFormatted(cpfMask);
        cpfField.setAlignmentX(0.0f);
        cpfField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        cpfField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cpfField.setMargin(new Insets(0, 10, 0, 10));
        
        telefoneField = new CustomFormatted(telefoneMask);
        telefoneField.setAlignmentX(0.0f);
        telefoneField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        telefoneField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        telefoneField.setMargin(new Insets(0, 10, 0, 10));
        
        doubleField = new JPanel();
        doubleField.setAlignmentX(0.0f);
        doubleFieldLayout = new BoxLayout(doubleField, BoxLayout.X_AXIS);
        doubleField.setLayout(doubleFieldLayout);
        doubleField.setBackground(null);
        
        sexoPanel = new JPanel();
        sexoPanel.setAlignmentX(0.0f);
        sexoLayout = new BoxLayout(sexoPanel, BoxLayout.Y_AXIS);
        sexoPanel.setLayout(sexoLayout);
        sexoPanel.setBackground(null);
        
        String[] sexoList = {
            "Masculino", "Feminino"
        };
        
        sexoField = new JComboBox(sexoList);
        sexoField.setAlignmentX(0.0f);
        sexoField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        sexoField.setEditable(false);
        
        sexoPanel.add(sexoLabel);
        sexoPanel.add(sexoField);
        
        dataNascPanel = new JPanel();
        dataNascPanel.setAlignmentX(0.0f);
        dataNascLayout = new BoxLayout(dataNascPanel, BoxLayout.Y_AXIS);
        dataNascPanel.setLayout(dataNascLayout);
        dataNascPanel.setBackground(null);
        
        dataNascField = new CustomFormatted(dataNascMask);
        dataNascField.setAlignmentX(0.0f);
        dataNascField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        dataNascField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        dataNascField.setMargin(new Insets(0, 10, 0, 10));
        dataNascField.addFocusListener(new FocusListener(){

            @Override  
            public void focusGained(FocusEvent e){
                
            }

            @Override  
            public void focusLost(FocusEvent e){
                dataNascField.setText(checarData(dataNascField.getText()));
            }
        });
        
        dataNascPanel.add(dataNascLabel);
        dataNascPanel.add(dataNascField);
        
        doubleField.add(sexoPanel);
        doubleField.add(Box.createRigidArea(new Dimension(10, 0)));
        doubleField.add(dataNascPanel);
        
        enderecoField = new CustomField();
        enderecoField.setAlignmentX(0.0f);
        enderecoField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        enderecoField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        enderecoField.setMargin(new Insets(0, 10, 0, 10));
        
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
                if((!cpfField.getText().equals("000.000.000-00")) || 
                    (!nomeField.getText().equals("")) ||
                    (!telefoneField.getText().equals("(00) 00000-0000")) ||
                    (!enderecoField.getText().equals("")) ||
                    (!dataNascField.getText().equals("00/00/0000"))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Cancelar o cadastro de paciente?\n"
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
                adicionar();
            }
        });
        
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(addButton);
        
        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(cpfLabel);
        mainPanel.add(cpfField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(nomeLabel);
        mainPanel.add(nomeField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(telefoneLabel);
        mainPanel.add(telefoneField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(doubleField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(enderecoLabel);
        mainPanel.add(enderecoField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(buttonPanel);
        
        wrapPanel.setBorder(null);
        wrapPanel.add(mainPanel);
        canvas.add(wrapPanel);
        
        setSize(400, 550);
        setLayout(canvasLayout);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                
                if((!cpfField.getText().equals("000.000.000-00")) || 
                    (!nomeField.getText().equals("")) ||
                    (!telefoneField.getText().equals("(00) 00000-0000")) ||
                    (!enderecoField.getText().equals("")) ||
                    (!dataNascField.getText().equals("00/00/0000"))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Cancelar o cadastro de um paciente?\n"
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