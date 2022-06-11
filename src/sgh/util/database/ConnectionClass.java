package sgh.util.database;
import java.sql.*;

/**
 * Classe de conexão com o banco de dados.
 * @author Nicholas Campanelli
 */
public class ConnectionClass {
    
    private static String url;
    private static String host = "localhost";
    private static String port = "3306";
    private static String dbname = "sgh_db";
    private static String user = "root";
    private static String password = "";
    
    private static Connection conn;
    
    /**
     * Método responsável por abrir uma conexão com o servidor do banco de dados.
     */
    public static void connect(){
        
        // Define o endereço do servidor com os atributos necessários
        ConnectionClass.url = "jdbc:mysql://"+host+":"+port+"/"+dbname;
        
        try{
            // Tenta pegar uma conexão por meio do driver JDBC
            ConnectionClass.conn = DriverManager.getConnection(url, user, password);
            System.out.println("Servidor do banco de dados conectado com sucesso!");
        }
        catch(SQLException e){
            System.err.println("Erro na conexão com o servidor do banco de dados.");
            System.err.println(e.getCause());
        }
    }
    
    /**
     * Método responsável pela desconexão do servidor do banco de dados.
     */
    public static void disconnect(){
        
        try{
            // Se a conexão existir
            if(ConnectionClass.conn != null){
                // Fecha a conexão
                ConnectionClass.conn.close();
                System.out.println("Servidor do banco de dados desconectado.");
            }
            else{
                System.out.println("Conexão com o servidor do banco de dados é nula e não pode ser fechada.");
            }
        }
        catch(SQLException e){
            System.err.println("Não foi possível encerrar a conexão com o servidor do banco de dados: "+e.getMessage());
        }
    }
    
    public static Statement getStatement(){
        try{
            Statement stmt = ConnectionClass.conn.createStatement();
            return stmt;
        }
        catch(SQLException e){
            System.err.println("Não foi possível criar um Statement: "+ e.getMessage());
            return null;
        }
    }
    
    public static PreparedStatement getPreparedStatement(String sql){
        try{
            PreparedStatement pstmt = ConnectionClass.conn.prepareStatement(sql);
            return pstmt;
        }
        catch(SQLException e){
            System.err.println("Não foi possível criar uma PreparedStatement: "+ e.getMessage());
            return null;
        }
    }
    
    public static PreparedStatement getPreparedStatement(String sql, int getGeneratedKeys){
        try{
            PreparedStatement pstmt = ConnectionClass.conn.prepareStatement(sql, getGeneratedKeys);
            return pstmt;
        }
        catch(SQLException e){
            System.err.println("Não foi possível criar uma PreparedStatement: "+ e.getMessage());
            return null;
        }
    }
    
    public static Connection getConnection(){
        
        try{
            // Se a conexão existir
            if(ConnectionClass.conn != null){
                return ConnectionClass.conn;
            }
            else{
                ConnectionClass.connect();
                return conn;
            }
        }
        catch(Exception e){
            System.err.println("Não foi possível encerrar a conexão com o servidor do banco de dados: "+e.getMessage());
            return null;
        }
    }
}
