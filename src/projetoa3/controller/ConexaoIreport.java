package projetoa3.controller;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;
import projetoa3.util.database.ConnectionClass;

/**
 *
 * @author Alexandre Soares
 */
public class ConexaoIreport {
    
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/sgh_db";
    private static final String USER = "root";
    private static final String PASS = "alexandre";

    public static Connection getConnection(){
        try{
            Class.forName(DRIVER);
            System.out.println("conexao");
            return DriverManager.getConnection(URL, USER, PASS);
            
        } catch(ClassNotFoundException | SQLException ex){
            throw new RuntimeException("Erro na conexão: ", ex);
        }
    }

    public static void closeConnection(Connection con){
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoIreport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt){

        closeConnection(con);

        try {

            if (stmt != null) {
                stmt.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConexaoIreport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs){

        closeConnection(con, stmt);

        try {

            if (rs != null) {
                rs.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConexaoIreport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimirporId(String id) throws JRException, FileNotFoundException {
        
        Connection conn = ConnectionClass.getConnection();
        
        String jrxml = "projeto.jrxml";
        String jasper = JasperCompileManager.compileReportToFile(jrxml);    
        
        try {
            int idInt = Integer.parseInt(id);
            HashMap filtro = new HashMap();
            
            JasperPrintManager.printReport(jasper, false);
            filtro.put("id", idInt);
            JasperPrint jaspertPrint = JasperFillManager.fillReport(jasper, filtro, conn);
            System.out.println("refg");
            
            JasperViewer view = new JasperViewer(jaspertPrint);
            view.setVisible(true);
            
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }
}