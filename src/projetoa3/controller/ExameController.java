package projetoa3.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import projetoa3.model.ExameModel;
import projetoa3.util.Resultado;
import projetoa3.util.database.ConnectionClass;

/**
 *
 * @author Nicholas Campanelli
 */
public class ExameController {
    
    public static Resultado create(
            int idConsulta, String titulo,
            String descricao, String resultado
    ){
        
        try{
            if(!"".equals(titulo)){
                
                ExameModel exame = new ExameModel(
                        idConsulta, titulo, descricao, resultado
                );
                return exame.create();
            }
            else{
                System.err.println("Erro ao cadastrar exame: Campos obrigatórios faltando");
                return new Resultado(false, "Erro ao cadastrar exame: Campos obrigatórios faltando");
            }
        }
        catch(Exception e){
            System.err.println("Erro inesperado ao cadastrar exame.");
            e.printStackTrace();
            return new Resultado(false, "Erro inesperado ao cadastrar exame. Tente novamente.");
        }
    }
    
    public static DefaultTableModel read(int id){
        
        DefaultTableModel model = new DefaultTableModel(0, 0);
        String[] columns = {
            "ID",
            "Titulo"
        };
        
        try{
            
            model = new DefaultTableModel(columns, 0);
            String getExameSql = "SELECT id, titulo FROM exames WHERE id_consulta = '"+id+"';";
            Statement getExameStatement = ConnectionClass.getStatement();
            
            ResultSet getExameResult = getExameStatement.executeQuery(getExameSql);
            
            while(getExameResult.next()){
                model.addRow(new Object[]{
                    getExameResult.getInt("id"),
                    getExameResult.getString("titulo"),
                });
            }
            
            getExameStatement.close();
        }
        catch(SQLException e){
            System.err.println("Erro ao obter exames da consulta: "+id+". "+e.getMessage());
            e.printStackTrace();
        }
        
        return model;
    }
    
    public static Resultado readExame(int id, String fieldName){
        
        try{
            if(!fieldName.equals("id_consulta") &&
                    !fieldName.equals("titulo") &&
                    !fieldName.equals("descricao") &&
                    !fieldName.equals("resultado")){
                
                return new Resultado(false, "Erro ao obter campo do exame: Campo não existe no banco de dados.");
            }
            else{
            
                String getExameSql = "SELECT "+fieldName+" FROM exames WHERE id = '"+id+"';";
                Statement getExameStatement = ConnectionClass.getStatement();
                
                ResultSet getExameResult = getExameStatement.executeQuery(getExameSql);
                getExameResult.next();
                Object obj = getExameResult.getObject(fieldName);
                getExameResult.close();
                
                return new Resultado(true, "Sucesso", obj);
            }
        }
        catch(SQLException e){
            System.err.println("Erro inesperado ao obter campo do exame: "+e.getMessage());
            e.printStackTrace();
            return new Resultado(false, "Erro inesperado ao obter campo do exame: "+e.getMessage());
        }
    }
    
    public static ArrayList<String> readExame(String fieldName, String param, String paramValue){
        
        try{
            // Verifica se o campo informado é um dos campos válidos
            if(!fieldName.equals("id") &&
                    !fieldName.equals("id_consulta") &&
                    !fieldName.equals("titulo") &&
                    !fieldName.equals("descricao") &&
                    !fieldName.equals("resultado") &&
                    !fieldName.equals("COUNT(*)")){
                
                return null;
            }
            else{
            
                String getExameSql = "SELECT "+fieldName+" FROM exames WHERE "+param+" = '"+paramValue+"';";
                Statement getExameStatement = ConnectionClass.getStatement();
                
                ArrayList<String> resultado = new ArrayList<>();
                ResultSet getExameResult = getExameStatement.executeQuery(getExameSql);
                
                while(getExameResult.next()){
                    resultado.add(getExameResult.getObject(fieldName).toString());
                }
                
                getExameResult.close();
                return resultado;
            }
        }
        catch(SQLException e){
            System.err.println("Ocorreu um erro ao obter o campo: ");
            e.printStackTrace();
            return null;
        }
        
    }
    
    public static Resultado update(
            int id,
            String descricao, String resultado
    ){
        try{
            ExameModel exame = new ExameModel(id, 0, "", descricao, resultado);
            return exame.update();
        }
        catch(Exception e){
            System.err.println("Erro inesperado ao atualizar exame. " + e.getMessage());
            e.printStackTrace();
            return new Resultado(false, "Erro inesperado ao atualizar exame. "+e.getMessage());
        }
    }
    
    public static Resultado delete(int id){
        
        try{
            String deleteExameSql = "DELETE FROM exames WHERE id = '"+id+"'";
            Statement deleteExameStatement = ConnectionClass.getStatement();
            
            deleteExameStatement.execute(deleteExameSql);
            deleteExameStatement.close();
            return new Resultado(true, "Sucesso");
            
        }
        catch(SQLException e){
            System.err.println("Erro ao excluir o exame da consulta: "+e.getMessage());
            return new Resultado(false, "Erro ao excluir o exame da consulta: "+e.getMessage());
        }
        
    }
}
