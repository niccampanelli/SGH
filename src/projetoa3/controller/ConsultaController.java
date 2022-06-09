package projetoa3.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.table.DefaultTableModel;
import projetoa3.model.ConsultaModel;
import projetoa3.util.Resultado;
import projetoa3.util.database.ConnectionClass;

/**
 * Controller para os métodos relacionados com a consulta
 * @author Nicholas Campanelli
 */
public class ConsultaController {
    
    /**
     * Método para criar consultas
     * @param idMedico
     * @param idPaciente
     * @param data
     * @param hora
     * @return 
     */
    public static Resultado create(
            int idMedico, int idPaciente,
            String data, String hora
    ){
        
        try{
            // Checando se o médico informado existe
            String checkMedicoSql = "SELECT COUNT(*) FROM usuarios WHERE id = '"+idMedico+"';";
            Statement checkMedicoStatement = ConnectionClass.getStatement();
            
            ResultSet checkMedicoResult = checkMedicoStatement.executeQuery(checkMedicoSql);
            checkMedicoResult.next();
            int medicoCount = checkMedicoResult.getInt("COUNT(*)");
            checkMedicoStatement.close();
            
            if(medicoCount == 1){
                // Checando se o paciente informado existe
                String checkPacienteSql = "SELECT COUNT(*) FROM pacientes WHERE id = '"+idPaciente+"';";
                Statement checkPacienteStatement = ConnectionClass.getStatement();
                
                ResultSet checkPacienteResult = checkPacienteStatement.executeQuery(checkPacienteSql);
                checkPacienteResult.next();
                int pacienteCount = checkPacienteResult.getInt("COUNT(*)");
                checkPacienteResult.close();
                
                if(pacienteCount == 1){
                    
                    ConsultaModel consulta = new ConsultaModel(idMedico, idPaciente, data, hora);
                    return consulta.create();
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
     * Método para obter as consultas
     * @return DefaultTableModel - modelo de tabela com os dados e colunas
     */
    public static DefaultTableModel read(){
        
        DefaultTableModel model = new DefaultTableModel(0, 0);
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

            model = new DefaultTableModel(columns, 0);
            String getConsultasSql = "SELECT c.id, e.nome as especialidade, m.id as id_medico, m.nome as nome_medico, "
                                        + "p.id as id_paciente, p.nome as nome_paciente, c.data, c.hora, c.data_cadastrada "
                                        + "FROM consultas c INNER JOIN pacientes p ON c.id_paciente = p.id "
                                        + "INNER JOIN usuarios m ON c.id_medico = m.id "
                                        + "INNER JOIN especialidades e ON e.id = m.especialidade;";
            Statement getConsultasStatement = ConnectionClass.getStatement();
            
            ResultSet getConsultasResult = getConsultasStatement.executeQuery(getConsultasSql);
            
            while(getConsultasResult.next()){
                
                String[] dataSplit = getConsultasResult.getDate("data", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                String newData = dataSplit[2] + "/" + dataSplit[1] + "/" + dataSplit[0];
                
                String[] dataCadSplit = getConsultasResult.getDate("data_cadastrada", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                String newDataCad = dataCadSplit[2] + "/" + dataCadSplit[1] + "/" + dataCadSplit[0];
                
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
            
            getConsultasStatement.close();
        }
        catch(SQLException e){
            System.err.println("Erro ao obter consultas: \n");
            e.printStackTrace();
        }
        
        return model;
    }
    
    public static ArrayList<String> readConsulta(String fieldName, String param, String paramValue){
        
        try{
            // Verifica se o campo informado é um dos campos válidos
            if(!fieldName.equals("id") &&
                    !fieldName.equals("id_medico") &&
                    !fieldName.equals("id_paciente") &&
                    !fieldName.equals("data") &&
                    !fieldName.equals("hora") &&
                    !fieldName.equals("data_atualizada") &&
                    !fieldName.equals("data_cadastrada") &&
                    !fieldName.equals("COUNT(*)")){
                
                return null;
            }
            else{
            
                String getConsultaSql = "SELECT "+fieldName+" FROM consultas WHERE "+param+" = '"+paramValue+"';";
                Statement getConsultaStatement = ConnectionClass.getStatement();
                
                ArrayList<String> resultado = new ArrayList<>();
                ResultSet getConsultaResult = getConsultaStatement.executeQuery(getConsultaSql);
                
                while(getConsultaResult.next()){
                    resultado.add(getConsultaResult.getObject(fieldName).toString());
                }
                
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
    
    public static Resultado update(
            int id,
            int idMedico,
            String data, String hora
    ){
        
        try{
            // Checando se o médico informado existe
            String checkMedicoSql = "SELECT COUNT(*) FROM usuarios WHERE id = '"+idMedico+"';";
            Statement checkMedicoStatement = ConnectionClass.getStatement();
            
            ResultSet checkMedicoResult = checkMedicoStatement.executeQuery(checkMedicoSql);
            checkMedicoResult.next();
            int medicoCount = checkMedicoResult.getInt("COUNT(*)");
            checkMedicoStatement.close();
            
            if(medicoCount == 1){
                ConsultaModel consulta = new ConsultaModel(id, idMedico, 0, data, hora);
                return consulta.update();
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
    
    public static Resultado delete(int id){
        
        try{
            String checkExameSql = "SELECT id FROM exames WHERE id_consulta = '"+id+"';";
            Statement checkExameStatement = ConnectionClass.getStatement();
            
            ResultSet checkExameResult = checkExameStatement.executeQuery(checkExameSql);
            
            while(checkExameResult.next()){
                ExameController.delete(checkExameResult.getInt("id"));
            }
            checkExameStatement.close();
            
            String deleteConsultasSql = "DELETE FROM consultas WHERE id = '"+id+"'";
            Statement deleteConsultasStatement = ConnectionClass.getStatement();
            
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
