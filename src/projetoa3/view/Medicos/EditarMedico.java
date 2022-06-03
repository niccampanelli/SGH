package projetoa3.view.Medicos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import javax.swing.border.*;
import javax.swing.text.*;
import projetoa3.controller.UserController;
import projetoa3.util.Resultado;
import projetoa3.view.Components.CustomButton;
import projetoa3.view.Components.CustomField;
import projetoa3.view.Components.CustomFormatted;

/**
 * Tela de criação de médico
 * @author Nicholas Campanelli
 */
public class EditarMedico extends JFrame{
    
    // Componentes da interface
    private final BoxLayout canvasLayout, mainLayout, doubleFieldLayout1, crmLayout, especialidadeLayout, doubleFieldLayout2, sexoLayout, dataNascLayout, buttonLayout;
    private final BorderLayout wrapLayout;
    private final JPanel wrapPanel, mainPanel, doubleField1, crmPanel, especialidadePanel, doubleField2, sexoPanel, dataNascPanel, buttonPanel;
    private final JLabel title, subtitle, cpfLabel, nomeLabel, crmLabel, especialidadeLabel, emailLabel, telefoneLabel, sexoLabel, dataNascLabel;
    private final CustomField nomeField, crmField, emailField;
    private final CustomFormatted cpfField, telefoneField, dataNascField;
    private final JComboBox especialidadeField, sexoField;
    private MaskFormatter cpfMask, telefoneMask, dataNascMask;
    private final CustomButton cancelButton, addButton;
    
    // Variáveis de lógica
    private String id, nome, especialidade, telefone, sexo, dataNasc;
    
    private void editar(){
        
        nome = nomeField.getText();
        especialidade = (String) especialidadeField.getSelectedItem();
        telefone = telefoneField.getText();
        sexo = (String) sexoField.getSelectedItem();
        dataNasc = dataNascField.getText();
        
        if("".equals(nome) || nome.length() < 10){
            JOptionPane.showMessageDialog(null, "Insira um nome completo com no mínimo 10 caracteres.", "Nome inválido", JOptionPane.WARNING_MESSAGE);
        }
        else if("".equals(especialidade)){
            JOptionPane.showMessageDialog(null, "Indique uma especialidade válida. Se a especialidade desejada não constar na lista, digite-a no campo.", "Especialidade inválida", JOptionPane.WARNING_MESSAGE);
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
        else{
            
            String cleanTelefone = telefone.replaceAll("[^a-zA-Z0-9]", "");
            String charSexo = "m";
            
            if(sexo.equals("Masculino")){
                charSexo = "m";
            }
            else{
                charSexo = "f";
            }
            
            Resultado res = UserController.update(Integer.parseInt(id), 3, nome, dataNasc, cleanTelefone, charSexo, especialidade);
            
            if(res.isSucesso()){
            
                JOptionPane.showMessageDialog(null, "Médico atualizado com sucesso!\n"
                        + "ID do médico atualizado: "+id, "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                dispose();
            }
            else{
                JOptionPane.showMessageDialog(null, res.getMensagem(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }
            
    public EditarMedico(String id, String cpf, String nome, String crm, String especialidade, String email, String telefone, String sexo, String dataNasc){
        super("Editar médico");
        
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
        this.telefone = telefone;
        this.sexo = sexo;
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
        
        title = new JLabel("Editar médico");
        title.setFont(new Font(Font.SANS_SERIF, 1, 24));
        
        subtitle = new JLabel("#"+id);
        
        cpfLabel = new JLabel("CPF");
        
        nomeLabel = new JLabel("Nome completo");
        
        crmLabel = new JLabel("CRM");
        
        especialidadeLabel = new JLabel("Especialidade");
        
        emailLabel = new JLabel("Endereço de e-mail");
        
        telefoneLabel = new JLabel("Telefone");
        
        sexoLabel = new JLabel("Sexo");
        
        dataNascLabel = new JLabel("Data de nascimento");
        
        nomeField = new CustomField(nome);
        nomeField.setAlignmentX(0.0f);
        nomeField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        nomeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        nomeField.setMargin(new Insets(0, 10, 0, 10));
        
        doubleField1 = new JPanel();
        doubleField1.setAlignmentX(0.0f);
        doubleFieldLayout1 = new BoxLayout(doubleField1, BoxLayout.X_AXIS);
        doubleField1.setLayout(doubleFieldLayout1);
        doubleField1.setBackground(null);
        
        crmPanel = new JPanel();
        crmPanel.setAlignmentX(0.0f);
        crmLayout = new BoxLayout(crmPanel, BoxLayout.Y_AXIS);
        crmPanel.setLayout(crmLayout);
        crmPanel.setBackground(null);
        
        crmField = new CustomField(crm);
        crmField.setAlignmentX(0.0f);
        crmField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        crmField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        crmField.setMargin(new Insets(0, 10, 0, 10));
        crmField.setEnabled(false);
        
        crmPanel.add(crmLabel);
        crmPanel.add(crmField);
        
        especialidadePanel = new JPanel();
        especialidadePanel.setAlignmentX(0.0f);
        especialidadeLayout = new BoxLayout(especialidadePanel, BoxLayout.Y_AXIS);
        especialidadePanel.setLayout(especialidadeLayout);
        especialidadePanel.setBackground(null);
        
        especialidadeField = new JComboBox(new String[]{especialidade});
        especialidadeField.setAlignmentX(0.0f);
        especialidadeField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        especialidadeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        especialidadeField.setEditable(true);
        
        especialidadePanel.add(especialidadeLabel);
        especialidadePanel.add(especialidadeField);
        
        emailField = new CustomField(email);
        emailField.setAlignmentX(0.0f);
        emailField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.setMargin(new Insets(0, 10, 0, 10));
        emailField.setEnabled(false);
        
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
        cpfField.setText(cpf);
        cpfField.setAlignmentX(0.0f);
        cpfField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        cpfField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cpfField.setMargin(new Insets(0, 10, 0, 10));
        cpfField.setEnabled(false);
        
        telefoneField = new CustomFormatted(telefoneMask);
        telefoneField.setText(telefone);
        telefoneField.setAlignmentX(0.0f);
        telefoneField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        telefoneField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        telefoneField.setMargin(new Insets(0, 10, 0, 10));
        
        doubleField2 = new JPanel();
        doubleField2.setAlignmentX(0.0f);
        doubleFieldLayout2 = new BoxLayout(doubleField2, BoxLayout.X_AXIS);
        doubleField2.setLayout(doubleFieldLayout2);
        doubleField2.setBackground(null);
        
        sexoPanel = new JPanel();
        sexoPanel.setAlignmentX(0.0f);
        sexoLayout = new BoxLayout(sexoPanel, BoxLayout.Y_AXIS);
        sexoPanel.setLayout(sexoLayout);
        sexoPanel.setBackground(null);
        
        String[] sexoList = {
            "Masculino", "Feminino"
        };
        
        sexoField = new JComboBox(sexoList);
        sexoField.setSelectedItem(sexo);
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
        dataNascField.setText(dataNasc);
        dataNascField.setAlignmentX(0.0f);
        dataNascField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        dataNascField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        dataNascField.setMargin(new Insets(0, 10, 0, 10));
        
        dataNascPanel.add(dataNascLabel);
        dataNascPanel.add(dataNascField);
        
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
                    (!crmField.getText().equalsIgnoreCase(crm)) ||
                    (!especialidadeField.getSelectedItem().toString().equalsIgnoreCase(especialidade)) ||
                    (!emailField.getText().equalsIgnoreCase(email)) ||
                    (!telefoneField.getText().equalsIgnoreCase(telefone)) ||
                    (!sexoField.getSelectedItem().toString().equalsIgnoreCase(sexo)) ||
                    (!dataNascField.getText().equalsIgnoreCase(dataNasc))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Tem certeza que deseja cancelar a edição deste médico?\n"
                      + "Os dados inseridos serão perdidos e o médico irá permanecer com os dados anteriores.",
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
        
        doubleField1.add(crmPanel);
        doubleField1.add(Box.createRigidArea(new Dimension(10, 0)));
        doubleField1.add(especialidadePanel);
        
        doubleField2.add(sexoPanel);
        doubleField2.add(Box.createRigidArea(new Dimension(10, 0)));
        doubleField2.add(dataNascPanel);
        
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
        mainPanel.add(doubleField1);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(telefoneLabel);
        mainPanel.add(telefoneField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(doubleField2);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(buttonPanel);
        
        wrapPanel.add(mainPanel);
        canvas.add(wrapPanel);
        
        setSize(480, 600);
        setLayout(canvasLayout);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                
                if((!cpfField.getText().equalsIgnoreCase(cpf)) || 
                    (!nomeField.getText().equalsIgnoreCase(nome)) ||
                    (!crmField.getText().equalsIgnoreCase(crm)) ||
                    (!especialidadeField.getSelectedItem().toString().equalsIgnoreCase(especialidade)) ||
                    (!emailField.getText().equalsIgnoreCase(email)) ||
                    (!telefoneField.getText().equalsIgnoreCase(telefone)) ||
                    (!sexoField.getSelectedItem().toString().equalsIgnoreCase(sexo)) ||
                    (!dataNascField.getText().equalsIgnoreCase(dataNasc))){
                    
                    int confirmed = JOptionPane.showConfirmDialog(null, 
                      "Tem certeza que deseja cancelar a edição deste médico?\n"
                      + "Os dados inseridos serão perdidos e o médico irá permanecer com os dados anteriores.",
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