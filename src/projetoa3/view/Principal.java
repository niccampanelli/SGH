package projetoa3.view;
import projetoa3.view.Components.Navbar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import projetoa3.controller.SessionController;
import projetoa3.util.ProgramDefaults;

/**
 * Frame principal, onde são instanciadas as páginas
 * @author Nicholas Campanelli
 */
public class Principal extends JFrame{
    
    // Componentes da interface
    private BoxLayout canvasLayout;
    private BorderLayout wrapLayout;
    private JMenuBar menu;
    private JMenu arquivoMenu, contaMenu;
    private JMenuItem exportarArquivoMenu, nomeContaMenu, sairContaMenu;
    private JScrollPane mainPanel;
    private JPanel Navbar, wrapPanel;
    private JFileChooser fileChooser;
    private Image appIcon;
    
    // Definem o painel a ser mostrado
    private JPanel currentPanel;
    private int currentPanelIndex;
    
    // Getters e setters --------------------------------
    public JPanel getCurrentPanel(){
        return this.currentPanel;
    }
    
    public void setCurrentPanel(JPanel currentPanel){
        this.currentPanel = currentPanel;
    }
    
    public int getCurrentPanelIndex(){
        return this.currentPanelIndex;
    }
    
    public void setCurrentPanelIndex(int currentPanelIndex){
        this.currentPanelIndex = currentPanelIndex;
    }
    // ---------------------------------------------------
    
    /**
     * Constrói a interface
     */
    public void buildGUI(){
        
        Container canvas = getContentPane();
        canvas.removeAll(); // Remove os elementos do canvas para atualizá-lo
        
        canvasLayout = new BoxLayout(canvas, BoxLayout.PAGE_AXIS);
        wrapLayout = new BorderLayout();
        
        wrapPanel = new JPanel();
        wrapPanel.setLayout(wrapLayout);
        
        // Barra de ações do topo da tela
        menu = new JMenuBar();
        menu.setBackground(new Color(255, 255, 255));
        
            // Menu de ações de "arquivo"
            arquivoMenu = new JMenu("Arquivo");

            // Opção de exportar arquivo
            exportarArquivoMenu = new JMenuItem("Exportar");
            exportarArquivoMenu.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    fileChooser = new JFileChooser();
                    fileChooser.setMultiSelectionEnabled(false);
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivo de texto", "txt", "text"));
                    int option = fileChooser.showSaveDialog(null);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        try {
                            FileWriter writer = new FileWriter(fileChooser.getSelectedFile());
                            writer.write("123dfsdf");
                            writer.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        
            // Menu de ações de "conta"
            contaMenu = new JMenu("Conta");
            
            String tipoNome = "?";
            
            switch(ProgramDefaults.getUserType()){
                case 1:
                    tipoNome = "Administrador(a)";
                    break;
                case 2:
                    tipoNome = "Atendente";
                    break;
                case 3:
                    tipoNome = "Médico(a)";
                    break;
            }
            
            // Exemplos, vão ser modificados depois
            nomeContaMenu = new JMenuItem(ProgramDefaults.getUserName() +" - "+ tipoNome);
            sairContaMenu = new JMenuItem("Sair");
            sairContaMenu.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    
                    if(SessionController.delete()){
                        Login login = new Login();
                        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Não foi possível encerrar a sessão. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        
        // Adiciona os menus no menu superior principal
        arquivoMenu.add(exportarArquivoMenu);
        contaMenu.add(nomeContaMenu);
        contaMenu.add(sairContaMenu);
        menu.add(arquivoMenu);
        menu.add(contaMenu);
        
        // Define o menu do programa como o menu criado
        setJMenuBar(menu);
        
        
        // Se o painel a ser exibido não estiver definido
        // define como "home".
        if(currentPanel == null){
            currentPanel = new Home(this);
        }
        
        // Instancia a barra de navegação lateral
        Navbar = new Navbar(this, currentPanelIndex);
        
        mainPanel = new JScrollPane(currentPanel);
        mainPanel.setBorder(null);
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.getVerticalScrollBar().setUnitIncrement(16);
        
        wrapPanel.add(Navbar, BorderLayout.LINE_START);
        wrapPanel.add(mainPanel, BorderLayout.CENTER);
        
        canvas.add(wrapPanel);
        
        // Atualiza o layout para acomodar os componentes
        validate();
    }
    
    /**
     * Construtor padrão
     */
    public Principal(){
        super("Inicio");
        
        // Tenta pegar o banner do login
        try{
            URL appIconUrl = getClass().getResource("/projetoa3/util/icons/sgh_logo.png");
            appIcon = ImageIO.read(appIconUrl);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        buildGUI();
        
        setSize(1280, 720);
        setLayout(canvasLayout);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setIconImage(appIcon);
    }
    
    /**
     * Construtor para tela maximizada
     * @param extendedState - Estado de maximização da tela
     */
    public Principal(int extendedState){
        super("Inicio");
        
        buildGUI();
        
        setExtendedState(extendedState);
        setLayout(canvasLayout);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }
    
    /**
     * Construtor para tela com tamanho modificado
     * @param size - Tamanho para definir a tela
     */
    public Principal(Dimension size){
        super("Inicio");
        
        buildGUI();
        
        setSize(size);
        setLayout(canvasLayout);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }
    
    // Método principal
    public static void main(String[] args){
        
        Principal home = new Principal();
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
