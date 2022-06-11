package sgh.view.Administradores;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.time.Year;
import javax.swing.border.*;
import javax.swing.text.*;
import sgh.controller.UserController;
import sgh.util.Resultado;
import sgh.view.Components.CustomButton;
import sgh.view.Components.CustomField;
import sgh.view.Components.CustomFormatted;

/**
 * Tela de criação de administradores
 * @author Nicholas Campanelli
 */
public class NovoAdministrador extends JFrame{
    
    // Componentes da interface
    private final BoxLayout canvasLayout, mainLayout, buttonLayout;
    private final BorderLayout wrapLayout;
    private final JPanel wrapPanel, mainPanel, buttonPanel;
    private final JLabel title, cpfLabel, nomeLabel, emailLabel, telefoneLabel, dataNascLabel;
    private final CustomField nomeField, emailField;
    private final CustomFormatted cpfField, telefoneField, dataNascField;
    private MaskFormatter cpfMask, telefoneMask, dataNascMask;
    private final CustomButton cancelButton, addButton;
    
    // Variáveis de lógica
    private String cpf, nome, email, telefone, dataNasc;
    
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
        email = emailField.getText();
        telefone = telefoneField.getText();
        dataNasc = dataNascField.getText();
        
        if("".equals(cpf) || cpf.length() != 14 || cpf.endsWith("0-00")){
            JOptionPane.showMessageDialog(null, "Insira um CPF válido.", "CPF inválido", JOptionPane.WARNING_MESSAGE);
        }
        else if("".equals(nome) || nome.length() < 10){
            JOptionPane.showMessageDialog(null, "Insira um nome completo com no mínimo 10 caracteres.", "Nome inválido", JOptionPane.WARNING_MESSAGE);
        }
        else if("".equals(email) || email.length() < 7 || !email.contains("@") || !email.contains(".")){
            JOptionPane.showMessageDialog(null, "Insira um endereço de e-mail válido, contendo \"@\" e \".\".", "Endereço de e-mail inválido", JOptionPane.WARNING_MESSAGE);
        }
        else if("".equals(telefone) || telefone.length() != 15 || telefone.startsWith("(00)") || telefone.endsWith("0000")){
            JOptionPane.showMessageDialog(null, "Insira um telefone válido.", "Telefone inválido", JOptionPane.WARNING_MESSAGE);
        }
        else if("".equals(dataNasc) || dataNasc.length() != 10 || dataNasc.endsWith("0000")){
            JOptionPane.showMessageDialog(null, "Insira uma data de nascimento válida.", "Data de nascimento inválida", JOptionPane.WARNING_MESSAGE);
        }
        else{
            
            String cleanCpf = cpf.replaceAll("[^a-zA-Z0-9]", "");
            String cleanTelefone = telefone.replaceAll("[^a-zA-Z0-9]", "");
            String cleanDataNasc = dataNasc.replaceAll("[^a-zA-Z0-9]", "");
            
            Resultado res = UserController.create(
                    1, nome, cleanCpf, dataNasc, cleanTelefone, email, "", cleanDataNasc, "", ""
            );
            
            if(res.isSucesso()){
            
                JOptionPane.showMessageDialog(null, "Administrador adicionado com sucesso!\n"
                        + "ID do administrador adicionado: "+ res.getCorpo().toString(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                dispose();
            }
            else{
                JOptionPane.showMessageDialog(null, res.getMensagem(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
            
    public NovoAdministrador(){
        super("Adicionar administrador");
        
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
        
        title = new JLabel("Adicionar administrador");
        title.setFont(new Font(Font.SANS_SERIF, 1, 24));
        
        cpfLabel = new JLabel("CPF");
        
        nomeLabel = new JLabel("Nome completo");
        
        emailLabel = new JLabel("Endereço de e-mail");
        
        telefoneLabel = new JLabel("Telefone");
        
        dataNascLabel = new JLabel("Data de nascimento");
        
        nomeField = new CustomField("");
        nomeField.setAlignmentX(0.0f);
        nomeField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        nomeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        nomeField.setMargin(new Insets(0, 10, 0, 10));
        
        emailField = new CustomField("");
        emailField.setAlignmentX(0.0f);
        emailField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.setMargin(new Insets(0, 10, 0, 10));
        
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
                    (!emailField.getText().equals("")) ||
                    (!telefoneField.getText().equals("(00) 00000-0000")) ||
                    (!dataNascField.getText().equals("00/00/0000"))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Cancelar a inclusão de um novo administrador?\n"
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
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(telefoneLabel);
        mainPanel.add(telefoneField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(dataNascLabel);
        mainPanel.add(dataNascField);
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
            public void windowClosing(WindowEvent e) {
                
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                
                if((!cpfField.getText().equals("000.000.000-00")) || 
                    (!nomeField.getText().equals("")) ||
                    (!emailField.getText().equals("")) ||
                    (!telefoneField.getText().equals("(00) 00000-0000")) ||
                    (!dataNascField.getText().equals("00/00/0000"))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Cancelar a inclusão de um novo administrador?\n"
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