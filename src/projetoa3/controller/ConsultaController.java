package projetoa3.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import projetoa3.model.ConsultaModel;
import projetoa3.util.Resultado;
import projetoa3.util.database.ConnectionClass;

/**
 * Controller para os métodos relacionados com a consulta
 * @author Nicholas Campanelli
 */
public class ConsultaController {
    
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
}
