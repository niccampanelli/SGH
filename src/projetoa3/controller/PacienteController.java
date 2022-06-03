package projetoa3.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.table.DefaultTableModel;
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
    
    public static DefaultTableModel read(){
        
        DefaultTableModel model = new DefaultTableModel(0, 0);
        String[] columns = {
            "ID",
            "CPF",
            "Nome",
            "Telefone",
            "Endereco",
            "Data de nascimento"
        };
        
        try{

            model = new DefaultTableModel(columns, 0);
            String getPacienteSql = "SELECT id, cpf, nome, telefone, endereco, data_nasc FROM pacientes;";
            Statement getPacienteStatement = ConnectionClass.getStatement();

            ResultSet getPacienteResult = getPacienteStatement.executeQuery(getPacienteSql);

            while(getPacienteResult.next()){

                String[] dataNascSplit = getPacienteResult.getDate("data_nasc", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                String newDate = dataNascSplit[2] + "/" + dataNascSplit[1] + "/" + dataNascSplit[0];

                model.addRow(new Object[]{
                    getPacienteResult.getInt("id"),
                    getPacienteResult.getString("cpf"),
                    getPacienteResult.getString("nome"),
                    getPacienteResult.getString("telefone"),
                    getPacienteResult.getString("endereco"),
                    newDate
                });
            }

            getPacienteStatement.close();
        }
        catch(SQLException e){
            System.err.println("Erro ao obter pacientes: \n");
            e.printStackTrace();
        }
        
        return model;
    }
    
    /**
     * Método para obter um campo específico do banco de dados
     * @param id
     * @param fieldName
     * @return Resultado - objeto contendo a informação requisitada
     */
    public static Resultado readPaciente(int id, String fieldName){
        
        try{
            // Verifica se o campo informado é um dos campos válidos
            if(!fieldName.equals("nome") &&
                    !fieldName.equals("sexo") &&
                    !fieldName.equals("data_nasc") &&
                    !fieldName.equals("cpf") &&
                    !fieldName.equals("telefone") &&
                    !fieldName.equals("endereco")){
                
                return new Resultado(false, "Campo especificado não existe no banco de dados.");
            }
            else{
            
                String getPacienteSql = "SELECT "+fieldName+" FROM pacientes WHERE id = '"+id+"';";
                Statement getPacienteStatement = ConnectionClass.getStatement();
                
                ResultSet getPacienteResult = getPacienteStatement.executeQuery(getPacienteSql);
                getPacienteResult.next();
                Object obj = getPacienteResult.getObject(fieldName);
                getPacienteResult.close();
                
                return new Resultado(true, "Sucesso", obj);
            }
        }
        catch(SQLException e){
            System.err.println("Ocorreu um erro ao obter o campo: ");
            e.printStackTrace();
            return new Resultado(false, "Ocorreu um erro ao obter o campo: "+ e.getMessage());
        }
        
    }
    
    public static Resultado update(
            int id,
            String nome, String sexo,
            String dataNasc, String telefone,
            String endereco
    ){
        
        try{
            
            if((!"".equals(nome)) && (!"".equals(sexo)) && (!"".equals(dataNasc)) && (!"".equals(telefone)) && (!"".equals(endereco))){
                System.out.println(telefone);
                PacienteModel paciente = new PacienteModel(id, nome, sexo, dataNasc, "00000000000", telefone, endereco);
                return paciente.update();
            }
            else{
                System.err.println("Erro ao atualizar paciente: Campos obrigatórios faltando.");
                return new Resultado(false, "Erro ao atualizar paciente: Campos obrigatórios faltando.");
            }
        }
        catch(Exception e){
            System.err.println("Não foi possível atualizar paciente: \n");
            e.printStackTrace();
            return new Resultado(false, "Não foi possível atualizar paciente: "+e.getMessage());
        
        }
    }
    
    public static Resultado delete(int id){
        
        try{
            String deletePacienteSql = "DELETE FROM pacientes WHERE id = '"+id+"'";
            Statement deletePacienteStatement = ConnectionClass.getStatement();
            
            deletePacienteStatement.execute(deletePacienteSql);
                        
            return new Resultado(true, "Sucesso");
            
        }
        catch(SQLException e){
            System.err.println("Erro ao excluir o cadastro do paciente: "+e.getMessage());
            return new Resultado(false, "Erro ao excluir o cadastro do paciente: "+e.getMessage());
        }
        
    }
}
