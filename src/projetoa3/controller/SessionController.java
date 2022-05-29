package projetoa3.controller;

import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import projetoa3.util.database.ConnectionClass;

/**
 * Controla e armazena sessões
 * @author Nicholas Campanelli
 */
public class SessionController {
    
    public static String create(String address, String key){
        
        String returnValue = "";
        String filebody = "null;null";
                
        try{
            if((!"".equals(address)) && (!"".equals(key))){
                
                String checkEmailSql = "SELECT COUNT(*) FROM usuarios WHERE email = '"+address+"';";
                Statement checkEmailStatement = ConnectionClass.getStatement();
                
                ResultSet checkEmailResult = checkEmailStatement.executeQuery(checkEmailSql);
                checkEmailResult.next();
                int emailCount = checkEmailResult.getInt("COUNT(*)");
                checkEmailStatement.close();
                
                if(emailCount == 1){
                    
                    String validatePasswordSql = "SELECT senha, tipo FROM usuarios WHERE email = '"+address+"' LIMIT 1;";
                    Statement validatePasswordStatement = ConnectionClass.getStatement();
                    
                    ResultSet validatePasswordResult = validatePasswordStatement.executeQuery(validatePasswordSql);
                    validatePasswordResult.next();
                    String passwordResult = validatePasswordResult.getString("senha");
                    int tipoResult = validatePasswordResult.getInt("tipo");
                    validatePasswordStatement.close();
                    
                    BigInteger bigint = BigInteger.ONE;
                    BigInteger salted = BigInteger.ONE;
                    
                    try{
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        bigint = new BigInteger(1, md.digest(key.getBytes()));
                        salted = new BigInteger(1, md.digest((bigint.toString(16) + "segredo").getBytes()));
                    }
                    catch(Exception e){
                        System.out.println("Erro na criptografia:");
                        e.printStackTrace();
                        returnValue = "erro";
                    }
                    
                    if(salted.toString(16).equals(passwordResult)){
                        
                        File directory = new File(".config/");
                        
                        if(directory.mkdir())
                            System.out.println("Pasta config criada");
                        
                        File file = new File(".config\\temp.config");
                        
                        if(file.createNewFile())
                            System.out.println("Arquivo config criado");

                        FileWriter writer = new FileWriter(file);
                        filebody = address + ";" + bigint.toString(16);
                        
                        writer.write(filebody);
                        writer.close();
                        
                        System.out.println("Arquivo config salvo");
                        returnValue = Integer.toString(tipoResult);
                    }
                    else{
                        System.err.println("Erro ao realizar login: Senha inválida.");
                        returnValue = "erro";
                    }
                }
                else{
                    System.err.println("Erro ao realizar login: Email não cadastrado.");
                    returnValue = "erro";
                }
            }
        }
        catch(Exception e){
            System.out.println("Erro no arquivo:");
            e.printStackTrace();
            returnValue = "erro";
        }
        
        return returnValue;
    }
    
    public static String validateTemp(){
        
        String returnValue = "";
        String[] data = new String[2];
        
        try{
            data = SessionController.read();            
            
            if(data.length == 2 && (data[0] != null && !"".equals(data[0])) && (data[1] != null && !"".equals(data[1]))){
                
                String address = data[0];
                String key = data[1];
                
                String checkEmailSql = "SELECT COUNT(*) FROM usuarios WHERE email = '"+address+"';";
                Statement checkEmailStatement = ConnectionClass.getStatement();
                
                ResultSet checkEmailResult = checkEmailStatement.executeQuery(checkEmailSql);
                checkEmailResult.next();
                int emailCount = checkEmailResult.getInt("COUNT(*)");
                checkEmailStatement.close();
                
                if(emailCount == 1){
                    
                    String validatePasswordSql = "SELECT senha, tipo FROM usuarios WHERE email = '"+address+"' LIMIT 1;";
                    Statement validatePasswordStatement = ConnectionClass.getStatement();
                    
                    ResultSet validatePasswordResult = validatePasswordStatement.executeQuery(validatePasswordSql);
                    validatePasswordResult.next();
                    String passwordResult = validatePasswordResult.getString("senha");
                    int tipoResult = validatePasswordResult.getInt("tipo");
                    validatePasswordStatement.close();
                    
                    BigInteger salted = BigInteger.ONE;
                    
                    try{
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        salted = new BigInteger(1, md.digest((key + "segredo").getBytes()));
                    }
                    catch(Exception e){
                        System.out.println("Erro na criptografia:");
                        e.printStackTrace();
                        returnValue = "erro";
                    }
                    
                    if(salted.toString(16).equals(passwordResult)){
                        returnValue = Integer.toString(tipoResult);
                    }
                    else{
                        System.err.println("Erro ao realizar login: Senha inválida.");
                        returnValue = "erro";
                    }
                }
                else{
                    System.err.println("Erro ao realizar login: Email não cadastrado.");
                    returnValue = "erro";
                }
            }
            else{
                returnValue = "erro";
            }
        }
        catch(Exception e){
            System.err.println("Erro no login:");
            e.printStackTrace();
            returnValue = "erro";
        }
        
        return returnValue;
    }
    
    public static String[] read(){
        
        String[] data = new String[2];
        
        try{
            File file = new File(".config\\temp.config");
            Scanner scn = new Scanner(file);
            
            String[] body = scn.nextLine().split(";");
            
            data[0] = body[0];
            data[1] = body[1];
            scn.close();
        }
        catch(Exception e){
            System.out.println("Não foi possível obter o arquivo de config:");
            e.printStackTrace();
        }
        
        return data;
    }
    
}
