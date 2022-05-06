package projetoa3.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import projetoa3.view.components.*;

public class Principal extends JFrame{
    
    BoxLayout canvasLayout;
    BorderLayout wrapLayout;
    JMenuBar menu;
    JMenu arquivoMenu, contaMenu;
    JMenuItem exportarArquivoMenu, nomeContaMenu, sairContaMenu;
    JScrollPane mainPanel;
    JPanel Navbar, wrapPanel, Pessoas, Consultas;
    public JPanel currentPanel;
    public int currentPanelIndex;
    
    public void buildGUI(){
        
        Container canvas = getContentPane();
        canvas.removeAll();
        
        canvasLayout = new BoxLayout(canvas, BoxLayout.PAGE_AXIS);
        wrapLayout = new BorderLayout();
        
        wrapPanel = new JPanel();
        wrapPanel.setLayout(wrapLayout);
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        
        menu = new JMenuBar();
        menu.setBackground(new Color(255, 255, 255));
        arquivoMenu = new JMenu("Arquivo");
        exportarArquivoMenu = new JMenuItem("Exportar");
        exportarArquivoMenu.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int userSelection = fileChooser.showSaveDialog(menu);
            }
        });
        
        contaMenu = new JMenu("Conta");
        nomeContaMenu = new JMenuItem("Fulano de Souza - Administrador");
        sairContaMenu = new JMenuItem("Sair");
        sairContaMenu.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Login login = new Login();
                login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dispose();
            }
        });
        
        arquivoMenu.add(exportarArquivoMenu);
        contaMenu.add(nomeContaMenu);
        contaMenu.add(sairContaMenu);
        menu.add(arquivoMenu);
        menu.add(contaMenu);
        
        setJMenuBar(menu);
        
        if(currentPanel == null){
            currentPanel = new Home();
        }
        
        Navbar = new Navbar(this, currentPanelIndex);
        
        mainPanel = new JScrollPane(currentPanel);
        mainPanel.setBorder(null);
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.getVerticalScrollBar().setUnitIncrement(16);
        
        wrapPanel.add(Navbar, BorderLayout.LINE_START);
        wrapPanel.add(mainPanel, BorderLayout.CENTER);
        
        canvas.add(wrapPanel);
        
        validate();
    }
    
    public Principal(){
        super("Inicio");
        
        buildGUI();
        
        setSize(1280, 720);
        setLayout(canvasLayout);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }
    
    public Principal(int extendedState){
        super("Inicio");
        
        buildGUI();
        
        setExtendedState(extendedState);
        setLayout(canvasLayout);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }
    
    public Principal(Dimension size){
        super("Inicio");
        
        buildGUI();
        
        setSize(size);
        setLayout(canvasLayout);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }
    
    public static void main(String[] args){
        
        Principal home = new Principal();
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
