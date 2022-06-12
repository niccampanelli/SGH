package sgh.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.table.DefaultTableModel;
import sgh.model.ConsultaModel;
import sgh.util.Resultado;
import sgh.util.database.ConnectionClass;

/**
 * Controller para os métodos relacionados com a consulta
 * @author Nicholas Campanelli
 */
public class ConsultaController {
    
    /**
     * Método para tratamento de dados para a requisição de criação de consultas
     * @param idMedico - ID do médico da cconsulta em int
     * @param idPaciente - ID do paciente da consulta em int
     * @param data - Data da consulta em formato String no padrão dd/MM/yyyy, desde 01/01/1000
     * @param hora - Hora da consulta em formato String no padrão HH:mm
     * @return Resultado - Objeto de resultado contendo o estado de sucesso, a mensagem e o corpo com o id da consulta criada
     */
    public static Resultado create(
            int idMedico, int idPaciente,
            String data, String hora
    ){
        
        try{
            // Checando se o médico informado existe
            String checkMedicoSql = "SELECT COUNT(*) FROM usuarios WHERE id = '"+idMedico+"';";
            Statement checkMedicoStatement = ConnectionClass.getStatement();
            
            // Executa a query e obtém o resultado
            ResultSet checkMedicoResult = checkMedicoStatement.executeQuery(checkMedicoSql);
            checkMedicoResult.next();
            int medicoCount = checkMedicoResult.getInt("COUNT(*)");
            checkMedicoStatement.close();
            
            // Se o médico existir
            if(medicoCount == 1){
                // Checando se o paciente informado existe
                String checkPacienteSql = "SELECT COUNT(*) FROM pacientes WHERE id = '"+idPaciente+"';";
                Statement checkPacienteStatement = ConnectionClass.getStatement();
                
                // Executa a query e obtém o resultado
                ResultSet checkPacienteResult = checkPacienteStatement.executeQuery(checkPacienteSql);
                checkPacienteResult.next();
                int pacienteCount = checkPacienteResult.getInt("COUNT(*)");
                checkPacienteResult.close();
                
                // Se o paciente existe
                if(pacienteCount == 1){
                    // Cria um model com os dados da consulta
                    ConsultaModel consulta = new ConsultaModel(idMedico, idPaciente, data, hora);
                    return consulta.create(); // Chama o método criar e retorna o resultado do método
                }
                else{
                    System.err.println("Erro ao criar consulta: Paciente não está cadastrado no banco.");
                    return new Resultado(false, "Erro ao criar consulta: Paciente não está cadastrado no banco.");
                }
            }
            else{
                System.err.println("Erro ao criar consulta: Médico não está cadastrado no banco.");
                return new Resultado(false, "Erro ao criar consulta: Médico não está cadastrado no banco.");
            }
        }
        catch(SQLException e){
            System.err.println("Erro criar consulta: "+ e.getMessage());
            return new Resultado(false, "Erro inesperado ao criar consulta: "+e.getMessage());
        }
    }
    
    /**
     * Método para obter todas as consultas cadastradas
     * @return DefaultTableModel - Modelo de tabela com os dados e colunas
     */
    public static DefaultTableModel read(){
        
        // Cria um modelo de tabela vazio
        DefaultTableModel model = new DefaultTableModel(0, 0);
        // Define as colunas da tabela
        String[] columns = {
            "ID",
            "Nome do paciente",
            "Especialista",
            "Nome do médico",
            "Data",
            "Hora",
            "Cadastrada em"
        };
        
        try{
            // Preenche o modelo com as colunas criadas
            model = new DefaultTableModel(columns, 0);
            String getConsultasSql = "SELECT c.id, e.nome as especialidade, m.id as id_medico, m.nome as nome_medico, "
                                        + "p.id as id_paciente, p.nome as nome_paciente, c.data, c.hora, c.data_cadastrada "
                                        + "FROM consultas c INNER JOIN pacientes p ON c.id_paciente = p.id "
                                        + "INNER JOIN usuarios m ON c.id_medico = m.id "
                                        + "INNER JOIN especialidades e ON e.id = m.especialidade;";
            Statement getConsultasStatement = ConnectionClass.getStatement();
            
            // Executa a query e obtém os resultados
            ResultSet getConsultasResult = getConsultasStatement.executeQuery(getConsultasSql);
            
            // Enquanto existirem registros
            while(getConsultasResult.next()){
                
                // Pega a data do registro no fuso horário atual e separa por hífen
                String[] dataSplit = getConsultasResult.getDate("data", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                String newData = dataSplit[2] + "/" + dataSplit[1] + "/" + dataSplit[0];
                
                // Pega a data do registro no fuso horário atual e separa por hífen
                String[] dataCadSplit = getConsultasResult.getDate("data_cadastrada", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                String newDataCad = dataCadSplit[2] + "/" + dataCadSplit[1] + "/" + dataCadSplit[0];
                
                // Adiciona o registro no modelo da tabela
                model.addRow(new Object[]{
                    getConsultasResult.getInt("id"),
                    "#"+getConsultasResult.getInt("id_paciente") + " - " + getConsultasResult.getString("nome_paciente"),
                    getConsultasResult.getString("especialidade"),
                    "#"+getConsultasResult.getInt("id_medico") + " - " + getConsultasResult.getString("nome_medico"),
                    newData,
                    getConsultasResult.getString("hora"),
                    newDataCad
                });
            }
            
            // Fecha a conexão com o banco
            getConsultasStatement.close();
        }
        catch(SQLException e){
            System.err.println("Erro ao obter consultas: \n");
            e.printStackTrace();
        }
        
        // Retorna o modelo da tabela
        return model;
    }
    
    /**
     * Método para obter dados específicos quando uma comparação no banco é verdadeira
     * @param fieldName - Nome do campo a ser obtido da consulta
     * @param param - Primeiro parâmetro da comparação da consulta
     * @param paramValue - Segundo parâmetro da comparação da consulta
     * @return ArrayList<String> - ArrayList de strings com os resultados obtidos
     */
    public static ArrayList<String> readConsulta(String fieldName, String param, String paramValue){
        
        try{
            // Verifica se os campos informados são campos válidos
            if(!fieldName.equals("id") &&
                    !fieldName.equals("id_medico") &&
                    !fieldName.equals("id_paciente") &&
                    !fieldName.equals("data") &&
                    !fieldName.equals("hora") &&
                    !fieldName.equals("data_atualizada") &&
                    !fieldName.equals("data_cadastrada") &&
                    !fieldName.equals("COUNT(*)")
                    &&
                    !param.equals("id") &&
                    !param.equals("id_medico") &&
                    !param.equals("id_paciente") &&
                    !param.equals("data") &&
                    !param.equals("hora") &&
                    !param.equals("data_atualizada") &&
                    !param.equals("data_cadastrada") &&
                    !param.equals("COUNT(*)")){
                
                return null;
            }
            else{
                // Coloca os campos informados em um SQL
                String getConsultaSql = "SELECT "+fieldName+" FROM consultas WHERE "+param+" = '"+paramValue+"';";
                Statement getConsultaStatement = ConnectionClass.getStatement();
                
                // Cria uma ArrayList vazia e executa a query
                ArrayList<String> resultado = new ArrayList<>();
                ResultSet getConsultaResult = getConsultaStatement.executeQuery(getConsultaSql);
                
                // Enquanto houver dados no resultado
                while(getConsultaResult.next()){
                    resultado.add(getConsultaResult.getObject(fieldName).toString());
                }
                
                // Fecha a conexão com o banco
                getConsultaResult.close();
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
     * Método controlador para atualizar os dados de uma consulta
     * @param id - ID da consulta a ser modificada
     * @param idMedico - ID do novo médico da consulta
     * @param data - Nova data da consulta no formato dd/MM/yyyy
     * @param hora - Nova hora da consulta no formato HH:mm
     * @return Resultado - Objeto de resultado contendo se foi sucesso ou não, a mensagem e o objeto com o id da consulta modificada caso seja sucesso
     */
    public static Resultado update(
            int id,
            int idMedico,
            String data, String hora
    ){
        try{
            // Checando se o médico informado existe
            String checkMedicoSql = "SELECT COUNT(*) FROM usuarios WHERE id = '"+idMedico+"';";
            Statement checkMedicoStatement = ConnectionClass.getStatement();
            
            // Executa a query e obtém o resultado
            ResultSet checkMedicoResult = checkMedicoStatement.executeQuery(checkMedicoSql);
            checkMedicoResult.next(); // Passa para o primeiro resultado
            int medicoCount = checkMedicoResult.getInt("COUNT(*)"); // Obtém a quantidade
            checkMedicoStatement.close(); // Fecha a conexão com o banco
            
            // Se a quantidade de médicos for igual a 1
            if(medicoCount == 1){
                // Cria um modelo de consulta com os novos dados
                ConsultaModel consulta = new ConsultaModel(id, idMedico, 0, data, hora);
                return consulta.update(); // Chama o método de atualizar e retorna o resultado
            }
            else{
                System.err.println("Erro ao atualizar consulta: Médico não está cadastrado no banco.");
                return new Resultado(false, "Erro ao atualizar consulta: Médico não está cadastrado no banco.");
            }
        }
        catch(SQLException e){
            System.err.println("Erro ao atualizar consulta: "+ e.getMessage());
            return new Resultado(false, "Erro inesperado ao atualizar consulta: "+e.getMessage());
        }
    }
    
    /**
     * Método controlador para verificar e realizar a exclusão da consulta. Verifica por associações dessa consulta e as remove também
     * @param id - ID da consulta a ser removida
     * @return Resultado - Objeto de resultado contendo um boolean se é sucesso ou não, uma mensagem e o objeto caso seja sucesso
     */
    public static Resultado delete(int id){
        
        try{
            // Verifica e obtém o id de um exame associado a essa consulta
            String checkExameSql = "SELECT id FROM exames WHERE id_consulta = '"+id+"';";
            Statement checkExameStatement = ConnectionClass.getStatement();
            
            // Executa a query e obtém o resultado
            ResultSet checkExameResult = checkExameStatement.executeQuery(checkExameSql);
            
            // Enquanto existirem registros
            while(checkExameResult.next()){
                // Chama o método de remover exames e remove o exame com o id obtido
                ExameController.delete(checkExameResult.getInt("id"));
            }
            // Fecha a conexão com o banco
            checkExameStatement.close();
            
            // Então realiza a remoção da consulta informada
            String deleteConsultasSql = "DELETE FROM consultas WHERE id = '"+id+"'";
            Statement deleteConsultasStatement = ConnectionClass.getStatement();
            
            // Executa a chamada
            deleteConsultasStatement.execute(deleteConsultasSql);
            deleteConsultasStatement.close();
            return new Resultado(true, "Sucesso");
            
        }
        catch(SQLException e){
            System.err.println("Erro ao excluir consulta: "+e.getMessage());
            return new Resultado(false, "Erro ao excluir consulta: "+e.getMessage());
        }
    }
}