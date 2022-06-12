package sgh.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.table.DefaultTableModel;
import sgh.model.PacienteModel;
import sgh.util.Resultado;
import sgh.util.database.ConnectionClass;

/**
 * Controller para os métodos relacionados ao paciente
 * @author Nicholas Campanelli
 */
public class PacienteController {
    
    /**
     * Método contolador para verificar e inserir pacientes
     * @param nome - Nome do paciente a ser inserido
     * @param sexo - Sexo em char ("m" ou "f") do paciente a ser inserido
     * @param dataNasc - Data de nascimento do paciente a ser inserido no formato dd/MM/yyyy
     * @param cpf - CPF limpo contendo apenas os números
     * @param telefone - Telefone limpo contendo apenas os números
     * @param endereco - Endereço completo do paciente a ser inserido
     * @return - Resultado - Objeto de resultado contendo se a inserção obteve sucesso, uma mensagem e um objeto com o id do paciente inserido caso seja sucesso
     */
    public static Resultado create(
            String nome, String sexo,
            String dataNasc, String cpf,
            String telefone, String endereco
    ){
        try{
            // Verifica se algum campo está faltando
            if((!"".equals(nome)) && (!"".equals(sexo)) && (!"".equals(dataNasc)) &&
                    (!"".equals(cpf)) && (!"".equals(telefone)) && (!"".equals(endereco))){
                
                // Verifica se o CPF já está cadastrado no banco de dados
                String checkCpfSql = "SELECT COUNT(*) FROM pacientes WHERE cpf = '"+cpf+"';";
                Statement checkCpfStatement = ConnectionClass.getStatement();
                
                // Executa a consulta e obtém o resultado
                ResultSet checkCpfResult = checkCpfStatement.executeQuery(checkCpfSql);
                checkCpfResult.next();
                int cpfCount = checkCpfResult.getInt("COUNT(*)");
                checkCpfStatement.close(); // Fecha a conexão com o banco
                
                // Se a quantidade for 0
                if(cpfCount == 0){
                    
                    // Cria o modelo de paciente com os dados a serem inseridos
                    PacienteModel paciente = new PacienteModel(nome, sexo, dataNasc, cpf, telefone, endereco);
                    return paciente.create(); // Executa o método de inserção e retorna o resultado
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
    
    /**
     * Método controlador para obter todos os pacientes cadastrados no banco de dados
     * @return DefaultTableModel - Modelo de tabela com dados e o cabeçalho da tabela
     */
    public static DefaultTableModel read(){
        
        // Modelo de tabela vazio
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
            // Coloca o cabeçalho da tabela no modelo
            model = new DefaultTableModel(columns, 0);
            String getPacienteSql = "SELECT id, cpf, nome, telefone, endereco, data_nasc FROM pacientes;";
            Statement getPacienteStatement = ConnectionClass.getStatement();

            // Executa a query e obtém os resultados
            ResultSet getPacienteResult = getPacienteStatement.executeQuery(getPacienteSql);

            // Enquanto houver registros, avança para o próximo
            while(getPacienteResult.next()){
                
                // Pega a data do registro, e separa por hífen
                String[] dataNascSplit = getPacienteResult.getDate("data_nasc", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                String newDate = dataNascSplit[2] + "/" + dataNascSplit[1] + "/" + dataNascSplit[0];

                // Adiciona os dados do regístro atual no modelo da tabela
                model.addRow(new Object[]{
                    getPacienteResult.getInt("id"),
                    getPacienteResult.getString("cpf"),
                    getPacienteResult.getString("nome"),
                    getPacienteResult.getString("telefone"),
                    getPacienteResult.getString("endereco"),
                    newDate
                });
            }

            // Fecha a conexão com o banco
            getPacienteStatement.close();
        }
        catch(SQLException e){
            System.err.println("Erro ao obter pacientes: \n");
            e.printStackTrace();
        }
        
        return model;
    }
    
    /**
     * Método controlador para obter um campo específico de um paciente com dado ID
     * @param id - ID do paciente o qual obter o campo
     * @param fieldName - Nome do campo a ser obtido
     * @return Resultado - Objeto de resultado contendo um boolean informado se a consulta foi bem-sucedida ou não, uma mensagem e o campo obtido
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
                
                // Retorna um resultado informando o motivo da falha
                return new Resultado(false, "Campo especificado não existe no banco de dados.");
            }
            else{
                // Insere os dados em um SQL
                String getPacienteSql = "SELECT "+fieldName+" FROM pacientes WHERE id = '"+id+"';";
                Statement getPacienteStatement = ConnectionClass.getStatement();
                
                // Executa a query e obtém os resultados
                ResultSet getPacienteResult = getPacienteStatement.executeQuery(getPacienteSql);
                getPacienteResult.next(); // Avança para o primeiro resultado
                Object obj = getPacienteResult.getObject(fieldName); // Obtem o campo
                getPacienteResult.close(); // Fecha a conexão com o banco de dados
                
                // Retorna o resultado de sucesso com o campo obtido
                return new Resultado(true, "Sucesso", obj);
            }
        }
        catch(SQLException e){
            System.err.println("Ocorreu um erro ao obter o campo: ");
            e.printStackTrace();
            return new Resultado(false, "Ocorreu um erro ao obter o campo: "+ e.getMessage());
        }
    }
    
    /**
     * Método controlador para obter da tabela pacientes um campo específico onde dada condição se faz verdadeira
     * @param fieldName - Nome do campo a ser obtido assim como aparece no banco
     * @param param - Primeiro parâmetro da condição
     * @param paramValue - Segundo parâmetro da condição
     * @return ArrayList<String> - ArrayList de strings contendo os dados obtidos
     */
    public static ArrayList<String> readPaciente(String fieldName, String param, String paramValue){
        
        try{
            // Verifica se os campos informados são campos válidos
            if(!fieldName.equals("id") &&
                    !fieldName.equals("nome") &&
                    !fieldName.equals("sexo") &&
                    !fieldName.equals("data_nasc") &&
                    !fieldName.equals("cpf") &&
                    !fieldName.equals("telefone") &&
                    !fieldName.equals("endereco") &&
                    !fieldName.equals("COUNT(*)")
                    &&
                    !param.equals("id") &&
                    !param.equals("nome") &&
                    !param.equals("sexo") &&
                    !param.equals("data_nasc") &&
                    !param.equals("cpf") &&
                    !param.equals("telefone") &&
                    !param.equals("endereco") &&
                    !param.equals("COUNT(*)")){
                
                // Retorna vazio
                return null;
            }
            else{
                // Coloca os dados informados em um SQL
                String getPacienteSql = "SELECT "+fieldName+" FROM pacientes WHERE "+param+" = '"+paramValue+"';";
                Statement getPacienteStatement = ConnectionClass.getStatement();
                
                // Inicia uma ArrayList vazia
                ArrayList<String> resultado = new ArrayList<>();
                ResultSet getPacienteResult = getPacienteStatement.executeQuery(getPacienteSql);
                
                // Enquanto houver registros, avança para o próximo registro
                while(getPacienteResult.next()){
                    // Adiciona o registro atual na ArrayList
                    resultado.add(getPacienteResult.getObject(fieldName).toString());
                }
                
                // Fecha a conexão com o banco
                getPacienteResult.close();
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
     * Método controlador para modificar os dados de um paciente com dado ID
     * @param id - ID do paciente a ser atualizado
     * @param nome - Nome novo do paciente
     * @param sexo - Novo char de sexo do paciente
     * @param dataNasc - Nova data de nascimento do paciente no padrão dd/MM/yyyy
     * @param telefone - Novo telefone limpo contendo apenas números
     * @param endereco - Novo endereço completo do paciente
     * @return Resultado - Objeto de resultado contendo um boolean informando se a modificação foi bem-sucedida ou não, uma mensagem e um objeto com o id do paciente atualizado caso seja sucesso
     */
    public static Resultado update(
            int id,
            String nome, String sexo,
            String dataNasc, String telefone,
            String endereco
    ){
        
        try{
            // Se nenhum dos campos for vazio
            // Caso ocorra de um dos campos ser vazio, o banco irá remover o dado anterior
            if((!"".equals(nome)) && (!"".equals(sexo)) && (!"".equals(dataNasc)) && (!"".equals(telefone)) && (!"".equals(endereco))){
                // Cria um modelo de paciente com os novos dados
                PacienteModel paciente = new PacienteModel(id, nome, sexo, dataNasc, "00000000000", telefone, endereco);
                return paciente.update(); // Chama o método de atualização e retorna o resultado
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
    
    /**
     * Método controlador para verificar e executar a remoção de um paciente. Verifica por associações desse paciente e as remove também
     * @param id - ID do paciente a ser removido
     * @return Resultado - Objeto de resultado contendo um boolean informando se a remocão foi bem-sucedida, uma mensagem e um objeto caso seja sucesso
     */
    public static Resultado delete(int id){
        
        try{
            // Verifica se existe uma consulta associada com esse paciente
            String checkConsultasSql = "SELECT id FROM consultas WHERE id_paciente = '"+id+"';";
            Statement checkConsultasStatement = ConnectionClass.getStatement();
            
            // Executa a query e obtém o resultado
            ResultSet checkConsultasResult = checkConsultasStatement.executeQuery(checkConsultasSql);
            
            // Enquanto houver registros, avança para o próximo
            while(checkConsultasResult.next()){
                // Chama o método de deletar consultas para deletar a consulta atual
                ConsultaController.delete(checkConsultasResult.getInt("id"));
            }
            //Fecha a conexão com o banco
            checkConsultasStatement.close();
            
            // Coloca o id do paciente no SQL
            String deletePacienteSql = "DELETE FROM pacientes WHERE id = '"+id+"'";
            Statement deletePacienteStatement = ConnectionClass.getStatement();
            
            // Executa a remoção
            deletePacienteStatement.execute(deletePacienteSql);
            deletePacienteStatement.close(); // Fecha a conexão com o banco
            return new Resultado(true, "Sucesso");
            
        }
        catch(SQLException e){
            System.err.println("Erro ao excluir o cadastro do paciente: "+e.getMessage());
            return new Resultado(false, "Erro ao excluir o cadastro do paciente: "+e.getMessage());
        }
    }
}