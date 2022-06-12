package sgh.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import sgh.model.ExameModel;
import sgh.util.Resultado;
import sgh.util.database.ConnectionClass;

/**
 * Controller para os métodos relacionados ao exame
 * @author Nicholas Campanelli
 */
public class ExameController {
    
    /**
     * Método de verificação e inserção de exames
     * @param idConsulta - ID da consulta a qual o exame será associado
     * @param titulo - Título informativo do exame
     * @param descricao - Descrição opcional detalhando o procedimento do exame
     * @param resultado - Resultado opcional do exame
     * @return Resultado - Objeto de resultado contendo um boolean se a inserção foi bem-sucedida ou não, uma mensagem e o corpo contendo o id do exame inserido caso seja sucesso
     */
    public static Resultado create(
            int idConsulta, String titulo,
            String descricao, String resultado
    ){
        
        try{
            // Verifica se o título, o único campo obrigatório, foi infomado
            if(!"".equals(titulo)){
                // Cria um modelo de exame com os dados
                ExameModel exame = new ExameModel(
                        idConsulta, titulo, descricao, resultado
                );
                return exame.create(); // Executa a inserção e retorna o resultado
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
    
    /**
     * Método controlador para obter todos os exames de uma consulta
     * @param id - ID da consulta associada com os exames a serem obtidos
     * @return DefaultTableModel - Modelo de tabela com os dados obtidos e o cabeçalho da tabela
     */
    public static DefaultTableModel read(int id){
        
        // Inicia um modelo de tabela vazio
        DefaultTableModel model = new DefaultTableModel(0, 0);
        String[] columns = {
            "ID",
            "Titulo"
        };
        
        try{
            // Insere as colunas ao modelo de tabela
            model = new DefaultTableModel(columns, 0);
            String getExameSql = "SELECT id, titulo FROM exames WHERE id_consulta = '"+id+"';";
            Statement getExameStatement = ConnectionClass.getStatement();
            
            // Executa a query e obtém os resultados
            ResultSet getExameResult = getExameStatement.executeQuery(getExameSql);
            
            // Enquanto houver registros, percorre-os
            while(getExameResult.next()){
                // Adiciona o registro atual no modelo da tabela
                model.addRow(new Object[]{
                    getExameResult.getInt("id"),
                    getExameResult.getString("titulo"),
                });
            }
            
            // Fecha a coneção com o banco
            getExameStatement.close();
        }
        catch(SQLException e){
            System.err.println("Erro ao obter exames da consulta: "+id+". "+e.getMessage());
            e.printStackTrace();
        }
        
        // Retorna o modelo preenchido
        return model;
    }
    
    /**
     * Método para obter um campo específico de um exame com dado ID
     * @param id - ID do exame para obter o campo
     * @param fieldName -  Nome do campo do exame assim como aparece no banco
     * @return Resultado - Objeto de resultado com um boolean informando se foi sucesso ou não, uma mensagem e um objeto com o dado solicitado no caso de sucesso
     */
    public static Resultado readExame(int id, String fieldName){
        
        try{
            // Verifica se o campo especificado é válido
            if(!fieldName.equals("id_consulta") &&
                    !fieldName.equals("titulo") &&
                    !fieldName.equals("descricao") &&
                    !fieldName.equals("resultado")){
                
                // Retorna o objeto de resultado com a mensagem de erro
                return new Resultado(false, "Erro ao obter campo do exame: Campo não existe no banco de dados.");
            }
            else{
                // Coloca os dados informados no SQL
                String getExameSql = "SELECT "+fieldName+" FROM exames WHERE id = '"+id+"';";
                Statement getExameStatement = ConnectionClass.getStatement();
                
                // Executa a query e obtém o resultado
                ResultSet getExameResult = getExameStatement.executeQuery(getExameSql);
                getExameResult.next(); // Avança para o primeiro resultado
                Object obj = getExameResult.getObject(fieldName); // Obtém o campo
                getExameResult.close(); // Fecha a conexão com o banco
                
                // Retorna o resultado com o campo obtido
                return new Resultado(true, "Sucesso", obj);
            }
        }
        catch(SQLException e){
            System.err.println("Erro inesperado ao obter campo do exame: "+e.getMessage());
            e.printStackTrace();
            return new Resultado(false, "Erro inesperado ao obter campo do exame: "+e.getMessage());
        }
    }
    
    /**
     * Método controlador para obter um dado caso uma condição seja satisfeita
     * @param fieldName - Campo do banco de exames para ser obtido
     * @param param - Primeiro campo da condição
     * @param paramValue - Segundo campo da condição
     * @return ArrayList<Striung> - ArrayList de strings contendo todos os dados obtidos sob dada condição
     */
    public static ArrayList<String> readExame(String fieldName, String param, String paramValue){
        
        try{
            // Verifica se o campo informado é um dos campos válidos
            if(!fieldName.equals("id") &&
                    !fieldName.equals("id_consulta") &&
                    !fieldName.equals("titulo") &&
                    !fieldName.equals("descricao") &&
                    !fieldName.equals("resultado") &&
                    !fieldName.equals("COUNT(*)")){
                
                // Retorna vazio
                return null;
            }
            else{
                // Coloca os dados informados em um SQL
                String getExameSql = "SELECT "+fieldName+" FROM exames WHERE "+param+" = '"+paramValue+"';";
                Statement getExameStatement = ConnectionClass.getStatement();
                
                // Inicia uma ArrayList vazia
                ArrayList<String> resultado = new ArrayList<>();
                ResultSet getExameResult = getExameStatement.executeQuery(getExameSql);
                
                // Enquanto houver registros, avança para o próximo
                while(getExameResult.next()){
                    // Adiciona o registro atual na ArrayList
                    resultado.add(getExameResult.getObject(fieldName).toString());
                }
                
                // Fecha a conexão com o banco
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
    
    /**
     * Método controlador para atualizar os dados de um exame com dado ID
     * @param id - ID do exame a ser modificado
     * @param descricao - Nova descrição do exame
     * @param resultado - Novo texto de resultado do exame
     * @return Resultado - Objeto de resultado contendo um boolean informando se a modificação foi bem-sucedida ou não, uma mensagem e um objeto com o id do exame modificado caso seja sucesso
     */
    public static Resultado update(
            int id,
            String descricao, String resultado
    ){
        try{
            // Cria um modelo de exame com os novos dados
            ExameModel exame = new ExameModel(id, 0, "", descricao, resultado);
            return exame.update(); // Chama o método de atualização e retorna o resultado
        }
        catch(Exception e){
            System.err.println("Erro inesperado ao atualizar exame. " + e.getMessage());
            e.printStackTrace();
            return new Resultado(false, "Erro inesperado ao atualizar exame. "+e.getMessage());
        }
    }
    
    /**
     * Método controlador para deletar um exame com dado ID
     * @param id - ID do exame a ser removido
     * @return Resultado - Objeto de resultado com um boolean informando se a remoção foi bem-sucedida ou não, uma mensagem e um objeto caso seja sucesso
     */
    public static Resultado delete(int id){
        
        try{
            // Coloca o id informado em um SQL
            String deleteExameSql = "DELETE FROM exames WHERE id = '"+id+"'";
            Statement deleteExameStatement = ConnectionClass.getStatement();
            
            // Executa a remoção
            deleteExameStatement.execute(deleteExameSql);
            deleteExameStatement.close(); // Fecha a conexão com o banco
            return new Resultado(true, "Sucesso");
            
        }
        catch(SQLException e){
            System.err.println("Erro ao excluir o exame da consulta: "+e.getMessage());
            return new Resultado(false, "Erro ao excluir o exame da consulta: "+e.getMessage());
        }
    }
}