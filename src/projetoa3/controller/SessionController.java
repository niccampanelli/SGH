package projetoa3.controller;

import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Scanner;

/**
 * Controla e armazena sessões
 * @author Nicholas Campanelli
 */
public class SessionController {
    
    public static void create(String address, String key){
        
        String filebody = "null;null";
        
        try{
            File directory = new File(".config/");
            
            if(directory.mkdir())
                System.out.println("Pasta config criada");
            
            File file = new File(".config\\temp.config");
            
            if(file.createNewFile())
                System.out.println("Arquivo config criado");
            
            FileWriter writer = new FileWriter(file);
            
            try{
                MessageDigest md = MessageDigest.getInstance("MD5");
                BigInteger bigint = new BigInteger(1, md.digest(key.getBytes()));
                filebody = address + ";" + bigint.toString(16);
            }
            catch(Exception e){
                System.out.println("Erro na criptografia:");
                e.printStackTrace();
            }
            
            writer.write(filebody);
            writer.close();
            
            System.out.println("Arquivo config salvo");
            
        }
        catch(Exception e){
            System.out.println("Erro no arquivo:");
            e.printStackTrace();
        }
        
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
