package projetoa3.view.Atendentes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import javax.swing.border.*;
import javax.swing.text.*;
import projetoa3.view.Components.CustomButton;

public class EditarAtendente extends JFrame{
    
    // Componentes da interface
    private BoxLayout canvasLayout, mainLayout, buttonLayout;
    private BorderLayout wrapLayout;
    private JPanel wrapPanel, mainPanel, buttonPanel;
    private JLabel title, subtitle, cpfLabel, nomeLabel, emailLabel, telefoneLabel, dataNascLabel;
    private JTextField nomeField, emailField;
    private JFormattedTextField cpfField, telefoneField, dataNascField;
    private MaskFormatter cpfMask, telefoneMask, dataNascMask;
    private CustomButton cancelButton, addButton;
    
    // Variáveis de lógica
    private String cpf, nome, email, telefone, dataNasc;
    
    private void editar(){
        
        cpf = cpfField.getText();
        nome = nomeField.getText();
        email = emailField.getText();
        telefone = telefoneField.getText();
        dataNasc = dataNascField.getText();
        
        if("".equals(cpf) || cpf.length() != 14 || cpf.endsWith("0-00")){
            JOptionPane.showMessageDialog(null, "Insira um CPF válido.", "CPF inválido", JOptionPane.WARNING_MESSAGE);
        }
        else if("".equals(nome) || nome.length() < 5){
            JOptionPane.showMessageDialog(null, "Insira um nome completo com no mínimo 5 caracteres.", "Nome inválido", JOptionPane.WARNING_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Atendente modificado com sucesso!\n"
                    + "ID do atentendente modificado: 5415.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
        }
    }
            
    public EditarAtendente(String id, String cpf, String nome, String email, String telefone, String dataNasc){
        super("Editar atendente");
        
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNasc = dataNasc;
        
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
        
        title = new JLabel("Editar atendente");
        title.setFont(new Font(Font.SANS_SERIF, 1, 24));
        
        subtitle = new JLabel("#" + id);
        
        cpfLabel = new JLabel("CPF");
        
        nomeLabel = new JLabel("Nome completo");
        
        emailLabel = new JLabel("Endereço de e-mail");
        
        telefoneLabel = new JLabel("Telefone");
        
        dataNascLabel = new JLabel("Data de nascimento");
        
        nomeField = new JTextField(nome);
        nomeField.setAlignmentX(0.0f);
        nomeField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        nomeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        nomeField.setMargin(new Insets(0, 10, 0, 10));
        
        emailField = new JTextField(email);
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
        
        cpfField = new JFormattedTextField(cpfMask);
        cpfField.setText(cpf);
        cpfField.setAlignmentX(0.0f);
        cpfField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        cpfField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cpfField.setMargin(new Insets(0, 10, 0, 10));
        
        telefoneField = new JFormattedTextField(telefoneMask);
        telefoneField.setText(telefone);
        telefoneField.setAlignmentX(0.0f);
        telefoneField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        telefoneField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        telefoneField.setMargin(new Insets(0, 10, 0, 10));
        
        dataNascField = new JFormattedTextField(dataNascMask);
        dataNascField.setText(dataNasc);
        dataNascField.setAlignmentX(0.0f);
        dataNascField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        dataNascField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        dataNascField.setMargin(new Insets(0, 10, 0, 10));
        
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
                if((!cpfField.getText().equalsIgnoreCase(cpf)) || 
                    (!nomeField.getText().equalsIgnoreCase(nome)) ||
                    (!emailField.getText().equalsIgnoreCase(email)) ||
                    (!telefoneField.getText().equalsIgnoreCase(telefone)) ||
                    (!dataNascField.getText().equalsIgnoreCase(dataNasc))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Tem certeza que deseja cancelar a edição deste atendente?\n"
                      + "Os dados inseridos serão perdidos e o atendente irá permanecer com os dados anteriores.",
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
        
        setSize(400, 570);
        setLayout(canvasLayout);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if((!cpfField.getText().equalsIgnoreCase(cpf)) || 
                    (!nomeField.getText().equalsIgnoreCase(nome)) ||
                    (!emailField.getText().equalsIgnoreCase(email)) ||
                    (!telefoneField.getText().equalsIgnoreCase(telefone)) ||
                    (!dataNascField.getText().equalsIgnoreCase(dataNasc))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Tem certeza que deseja cancelar a edição deste atendente?\n"
                      + "Os dados inseridos serão perdidos e o atendente irá permanecer com os dados anteriores.",
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