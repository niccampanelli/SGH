package projetoa3.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import projetoa3.model.PacienteModel;
import projetoa3.util.Resultado;
import projetoa3.util.database.ConnectionClass;

/**
 * Controlador de funções do paciente
 * @author Nicholas Campanelli
 */
public class PacienteController {
    
    /**
     * Método para criar pacientes
     * @param nome
     * @param sexo
     * @param dataNasc
     * @param cpf
     * @param telefone
     * @param endereco
     * @return - objeto Resultado contendo se a inserção obteve sucesso e a mensagem
     */
    public static Resultado create(
            String nome, String sexo,
            String dataNasc, String cpf,
            String telefone, String endereco
    ){
        // Tratativa de erro
        try{
            // Verifica se algum campo está faltando
            if((!"".equals(nome)) && (!"".equals(sexo)) && (!"".equals(dataNasc)) &&
                    (!"".equals(cpf)) && (!"".equals(telefone)) && (!"".equals(endereco))){
                
                // Verifica se o CPF já está cadastrado no banco de dados
                String checkCpfSql = "SELECT COUNT(*) FROM pacientes WHERE cpf = '"+cpf+"';";
                Statement checkCpfStatement = ConnectionClass.getStatement();
                
                ResultSet checkCpfResult = checkCpfStatement.executeQuery(checkCpfSql);
                checkCpfResult.next();
                int cpfCount = checkCpfResult.getInt("COUNT(*)");
                checkCpfStatement.close();
                
                // Se a quantidade for 0
                if(cpfCount == 0){
                    
                    // Criando modelo de paciente com os dados a serem inseridos
                    PacienteModel paciente = new PacienteModel(nome, sexo, dataNasc, cpf, telefone, endereco);
                    return paciente.create();
                }
                else{
                    System.err.println("Erro ao cadastrar paciente: CPF informado já cadastrado.");
                    return new Resultado(false, "Erro ao cadastrar paciente: CPF informado já está cadastrado no banco de dados.");
                }
            }
            else{
                System.err.println("Erro ao cadastrar paciente: Campos obrigatórios faltando.");
                return new Resultado(false, "Erro ao cadastrar paciente: Campos obrigatórios faltando.");
            }
        }
        catch(SQLException e){
            System.err.println("Erro ao cadastrar paciente: "+e.getMessage());
            e.printStackTrace();
            return new Resultado(false, "Erro ao cadastrar paciente: "+e.getMessage());
        }
    }
}
