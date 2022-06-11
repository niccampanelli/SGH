package sgh.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.table.DefaultTableModel;
import sgh.model.MedicoModel;
import sgh.model.UserModel;
import sgh.util.Resultado;
import sgh.util.database.ConnectionClass;

/**
 * Classe controladora das funções de usuários
 * @author Nicholas Campanelli
 */
public class UserController {
    
    /**
     * Método de criação de usuários
     * @param tipo
     * @param nome
     * @param cpf
     * @param dataNasc
     * @param telefone
     * @param email
     * @param cadastro
     * @param senha
     * @param sexo
     * @param especialidade 
     * @return Resultado - objeto do tipo Resultado definindo se o resultado foi sucesso ou não
     */
    public static Resultado create(
            int tipo, String nome,
            String cpf, String dataNasc,
            String telefone, String email,
            String cadastro, String senha,
            String sexo, String especialidade
    ){
        // Tratativa de erro
        try{
            
            // Verifica se os campos não estão vazios ou com valores inválidos
            if((tipo >= 1 && tipo <= 3) && (!"".equals(nome)) && (!"".equals(cpf)) && (!"".equals(dataNasc))
                    && (!"".equals(telefone)) && (!"".equals(email)) && (!"".equals(senha))){
                
                String checkCpfSql = "SELECT COUNT(*) FROM usuarios WHERE cpf = '"+cpf+"';";
                Statement checkCpfStatement = ConnectionClass.getStatement();
                
                ResultSet checkCpfResult = checkCpfStatement.executeQuery(checkCpfSql);
                checkCpfResult.next();
                int cpfCount = checkCpfResult.getInt("COUNT(*)");
                checkCpfResult.close();
                
                if(cpfCount == 0){

                    // Verifica se o email informado já está em uso por outro registro
                    String checkEmailSql = "SELECT COUNT(*) FROM usuarios WHERE email = '"+email+"';";
                    Statement checkEmailStatement = ConnectionClass.getStatement();

                    ResultSet checkEmailResult = checkEmailStatement.executeQuery(checkEmailSql);
                    checkEmailResult.next();
                    int emailCount = checkEmailResult.getInt("COUNT(*)");
                    checkEmailStatement.close();

                    // Se a quantidade de registros for zero
                    // significa que o email está disponível para uso
                    if(emailCount == 0){
                        
                        // Quando o tipo do usuário a ser cadastrado for 3
                        // significa que ele é do tipo "médico"
                        if(tipo == 3){

                            // Verifica se o cadastro e a especialidade não estão vazios
                            if((!"".equals(cadastro)) && (!"".equals(especialidade))){

                                // Verifica se o CRM está cadastrado
                                String checkCrmSql = "SELECT COUNT(*) FROM usuarios WHERE cadastro = '"+cadastro+"';";
                                Statement checkCrmStatement = ConnectionClass.getStatement();

                                ResultSet checkCrmResult = checkCrmStatement.executeQuery(checkCrmSql);
                                checkCrmResult.next();
                                int crmCount = checkCrmResult.getInt("COUNT(*)");
                                checkCrmStatement.close();

                                if(crmCount == 0){

                                    // Verifica a existência da especialidade indicada
                                    String checkSpecSql = "SELECT COUNT(*) FROM especialidades WHERE nome LIKE '%"+especialidade+"%';";
                                    Statement checkSpecStatement = ConnectionClass.getStatement();

                                    ResultSet checkSpecResult = checkSpecStatement.executeQuery(checkSpecSql);
                                    checkSpecResult.next();
                                    int specCount = checkSpecResult.getInt("COUNT(*)");
                                    checkSpecStatement.close();

                                    // Se a especialidade indicada não existe
                                    if(specCount == 0){
                                        // Insere a especialidade
                                        String insertSpecSql = "INSERT INTO especialidades (nome) VALUES"
                                                + "(?);";
                                        PreparedStatement insertSpecStatement = ConnectionClass.getPreparedStatement(insertSpecSql, Statement.RETURN_GENERATED_KEYS);
                                        insertSpecStatement.setString(1, especialidade);
                                        insertSpecStatement.execute();

                                        ResultSet insertSpecResult = insertSpecStatement.getGeneratedKeys();
                                        insertSpecResult.next();

                                        int key = insertSpecResult.getInt(1);
                                        insertSpecStatement.close();

                                        // Se a chave primária da especialidade adicionada for maior que zero
                                        // significa que criou com sucesso
                                        if(key > 0){
                                            
                                            // Seção de criptografia da senha
                                            BigInteger senharaw = BigInteger.ONE;
                                            BigInteger cripsenha = BigInteger.ONE;

                                            try{
                                                MessageDigest md = MessageDigest.getInstance("MD5");
                                                senharaw = new BigInteger(1, md.digest(senha.getBytes()));
                                                cripsenha = new BigInteger(1, md.digest((senharaw.toString(16) + "segredo").getBytes()));
                                            }
                                            catch(NoSuchAlgorithmException e){
                                                System.err.println("Erro na criptografia da senha: "+e.getMessage());
                                                e.printStackTrace();
                                                return new Resultado(false, "Erro inesperado na criação de médico. Tente novamente.");
                                            }
                                            
                                            // Cria um modelo de médico
                                            MedicoModel medico = new MedicoModel(
                                                    tipo, nome,
                                                    cpf, dataNasc,
                                                    telefone, email,
                                                    cadastro, cripsenha.toString(16),
                                                    sexo, key
                                            );
                                            // Adiciona o médico
                                            return medico.create();
                                        }
                                        else{
                                            System.err.println("Erro inesperado ao criar médico: Especialidade não foi criada corretamente no banco de dados.");
                                            return new Resultado(false, "Erro inesperado ao criar médico: Especialidade não foi criada corretamente no banco de dados.");
                                        }
                                    }
                                    else{

                                        // Pega o id da especialidade com o nome informado pelo usuário
                                        String getSpecSql = "SELECT id FROM especialidades WHERE nome LIKE '%"+especialidade+"%' LIMIT 1;";
                                        Statement getSpecStatement = ConnectionClass.getStatement();

                                        ResultSet getSpecResult = getSpecStatement.executeQuery(getSpecSql);
                                        getSpecResult.next();
                                        int specId = getSpecResult.getInt("id");
                                        getSpecStatement.close();

                                        // Se o id retornado da consulta for maior que zero
                                        // significa que a especialidade foi obtida com sucesso
                                        if(specId > 0){

                                            // Seção de criptografia da senha
                                            BigInteger senharaw = BigInteger.ONE;
                                            BigInteger cripsenha = BigInteger.ONE;

                                            try{
                                                MessageDigest md = MessageDigest.getInstance("MD5");
                                                senharaw = new BigInteger(1, md.digest(senha.getBytes()));
                                                cripsenha = new BigInteger(1, md.digest((senharaw.toString(16) + "segredo").getBytes()));
                                            }
                                            catch(NoSuchAlgorithmException e){
                                                System.err.println("Erro na criptografia da senha: "+e.getMessage());
                                                e.printStackTrace();
                                                return new Resultado(false, "Erro inesperado na criação de médico. Tente novamente.");
                                            }

                                            // Cria um modelo de médico
                                            MedicoModel medico = new MedicoModel(
                                                    tipo, nome,
                                                    cpf, dataNasc,
                                                    telefone, email,
                                                    cadastro, cripsenha.toString(16),
                                                    sexo, specId
                                            );
                                            // Adiciona o médico
                                            return medico.create();
                                        }
                                        else{
                                            System.err.println("Erro ao obter especialidade: A especialidade não foi encontrada.");
                                            return new Resultado(false, "Erro ao obter especialidade: A especialidade não foi encontrada.");
                                        }
                                    }
                                }
                                else{
                                    System.err.println("Erro na criação de médico: CRM já está cadastrado.");
                                    return new Resultado(false, "Erro ao criar médico: CRM já está cadastrado.");
                                }
                            }
                            else{
                                System.err.println("Erro ao cadastrar médico: Cadastro e/ou especialidade faltando.");
                                return new Resultado(false, "Erro ao cadastrar médico: o CRM e/ou especialidade está faltando.");
                            }
                        }
                        else{

                            // Seção de criptografia da senha
                            BigInteger senharaw = BigInteger.ONE;
                            BigInteger cripsenha = BigInteger.ONE;

                            try{
                                MessageDigest md = MessageDigest.getInstance("MD5");
                                senharaw = new BigInteger(1, md.digest(senha.getBytes()));
                                cripsenha = new BigInteger(1, md.digest((senharaw.toString(16) + "segredo").getBytes()));
                            }
                            catch(NoSuchAlgorithmException e){
                                System.err.println("Erro na criptografia da senha: "+e.getMessage());
                                e.printStackTrace();
                                return new Resultado(false, "Erro inesperado ao criar o usuário. Tente novamente.");
                            }

                            // Cria um modelo de usuário
                            UserModel usuario = new UserModel(
                                    tipo, nome, cpf,
                                    dataNasc, telefone, 
                                    email, cripsenha.toString(16)
                            );
                            // Adiciona o usuário
                            return usuario.create();
                        }
                    }
                    else{
                        System.err.println("Erro na criação de usuário: E-mail em uso.");
                        return new Resultado(false, "Erro ao criar usuário: E-mail em uso.");
                    }
                }
                else{
                    System.err.println("Erro na criação de usuário: CPF já está cadastrado.");
                    return new Resultado(false, "Erro ao criar usuário: CPF já está cadastrado.");
                }
            }
            else{
                System.err.println("Erro ao cadastrar usuário: Campos obrigatórios faltando.");
                return new Resultado(false, "Erro ao criar usuário: Campos obrigatórios faltando.");
            }
        }
        catch(SQLException e){
            System.err.println("Não foi possível cadastrar um usuário: "+ e.getMessage());
            return new Resultado(false, "Erro inesperado ao cadastrar usuário. Tente novamente.");
        }
    }
    
    /**
     * Método para obter usuários
     * @param tipo
     * @return DefaultTableModel - modelo de tabela com as linhas dos registros
     */
    public static DefaultTableModel read(int tipo){
        
        DefaultTableModel model = new DefaultTableModel(0, 0);
        String[] columns;
        
        try{
            if(tipo == 3){
                columns = new String[]{
                  "ID",
                  "CPF",
                  "CRM",
                  "Nome",
                  "Especialidade",
                  "E-mail",
                  "Telefone",
                  "Cadastrado em"
                };

                model = new DefaultTableModel(columns, 0);
                String getMedicSql = "SELECT b.id, b.cpf, b.cadastro, b.nome, a.nome as especialidade, b.email, b.telefone, b.data_cadastro "
                        + "FROM especialidades a INNER JOIN usuarios b ON a.id = b.especialidade;";
                Statement getMedicStatement = ConnectionClass.getStatement();

                ResultSet getMedicResult = getMedicStatement.executeQuery(getMedicSql);

                while(getMedicResult.next()){
                    
                    String[] dataCadSplit = getMedicResult.getDate("data_cadastro", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                    String newDataCad = dataCadSplit[2] + "/" + dataCadSplit[1] + "/" + dataCadSplit[0];
                    
                    model.addRow(new Object[]{
                        getMedicResult.getInt("id"),
                        getMedicResult.getString("cpf"),
                        getMedicResult.getString("cadastro"),
                        getMedicResult.getString("nome"),
                        getMedicResult.getString("especialidade"),
                        getMedicResult.getString("email"),
                        getMedicResult.getString("telefone"),
                        newDataCad,
                    });
                }
                
                getMedicStatement.close();
            }
            else if(tipo == 2){
                columns = new String[]{
                  "ID",
                  "CPF",
                  "Nome",
                  "E-mail",
                  "Telefone",
                  "Data de nascimento",
                  "Cadastrado em"
                };

                model = new DefaultTableModel(columns, 0);
                String getAtendenteSql = "SELECT id, cpf, nome, email, telefone, data_nascimento, data_cadastro FROM usuarios WHERE tipo = 2;";
                Statement getAtendenteStatement = ConnectionClass.getStatement();

                ResultSet getAtendenteResult = getAtendenteStatement.executeQuery(getAtendenteSql);

                while(getAtendenteResult.next()){
                    
                    String[] dataNascSplit = getAtendenteResult.getDate("data_nascimento", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                    String[] dataCadSplit = getAtendenteResult.getDate("data_cadastro", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                    
                    String newDataNasc = dataNascSplit[2] + "/" + dataNascSplit[1] + "/" + dataNascSplit[0];
                    String newDataCad = dataCadSplit[2] + "/" + dataCadSplit[1] + "/" + dataCadSplit[0];
                    
                    model.addRow(new Object[]{
                        getAtendenteResult.getInt("id"),
                        getAtendenteResult.getString("cpf"),
                        getAtendenteResult.getString("nome"),
                        getAtendenteResult.getString("email"),
                        getAtendenteResult.getString("telefone"),
                        newDataNasc,
                        newDataCad,
                    });
                }
                
                getAtendenteStatement.close();
            }
            else if(tipo == 1){
                columns = new String[]{
                  "ID",
                  "CPF",
                  "Nome",
                  "E-mail",
                  "Telefone",
                  "Data de nascimento",
                  "Cadastrado em"
                };

                model = new DefaultTableModel(columns, 0);
                String getAdminSql = "SELECT id, cpf, nome, email, telefone, data_nascimento, data_cadastro FROM usuarios WHERE tipo = 1;";
                Statement getAdminStatement = ConnectionClass.getStatement();

                ResultSet getAdminResult = getAdminStatement.executeQuery(getAdminSql);

                while(getAdminResult.next()){
                    
                    String[] dataNascSplit = getAdminResult.getDate("data_nascimento", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                    String[] dataCadSplit = getAdminResult.getDate("data_cadastro", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                    
                    String newDataNasc = dataNascSplit[2] + "/" + dataNascSplit[1] + "/" + dataNascSplit[0];
                    String newDataCad = dataCadSplit[2] + "/" + dataCadSplit[1] + "/" + dataCadSplit[0];
                    
                    model.addRow(new Object[]{
                        getAdminResult.getInt("id"),
                        getAdminResult.getString("cpf"),
                        getAdminResult.getString("nome"),
                        getAdminResult.getString("email"),
                        getAdminResult.getString("telefone"),
                        newDataNasc,
                        newDataCad,
                    });
                }
                
                getAdminStatement.close();
            }
        }
        catch(SQLException e){
            System.err.println("Erro ao obter usuários: ");
            e.printStackTrace();
        }
        
        return model;
    }
    
    /**
     * Método para atualizar dados dos usuários
     * @param tipo
     * @param nome
     * @param cpf
     * @param dataNasc
     * @param telefone
     * @param email
     * @param cadastro
     * @param senha
     * @param sexo
     * @param especialidade 
     * @return Resultado - objeto do tipo Resultado definindo se o resultado foi sucesso ou não
     */
    public static Resultado update(
            int id, int tipo,
            String nome, String dataNasc,
            String telefone, String sexo, 
            String especialidade
    ){
        // Tratativa de erro
        try{
            
            // Verifica se os campos não estão vazios ou com valores inválidos
            if((tipo >= 1 && tipo <= 3) && (!"".equals(nome)) && (!"".equals(dataNasc))
                    && (!"".equals(telefone))){

                // Quando o tipo do usuário a ser cadastrado for 3
                // significa que ele é do tipo "médico"
                if(tipo == 3){

                    // Verifica se o cadastro e a especialidade não estão vazios
                    if(!"".equals(especialidade)){

                        // Verifica a existência da especialidade indicada
                        String checkSpecSql = "SELECT COUNT(*) FROM especialidades WHERE nome LIKE '%"+especialidade+"%';";
                        Statement checkSpecStatement = ConnectionClass.getStatement();

                        ResultSet checkSpecResult = checkSpecStatement.executeQuery(checkSpecSql);
                        checkSpecResult.next();
                        int specCount = checkSpecResult.getInt("COUNT(*)");
                        checkSpecStatement.close();

                        // Se a especialidade indicada não existe
                        if(specCount == 0){
                            // Insere a especialidade
                            String insertSpecSql = "INSERT INTO especialidades (nome) VALUES"
                                    + "(?);";
                            PreparedStatement insertSpecStatement = ConnectionClass.getPreparedStatement(insertSpecSql, Statement.RETURN_GENERATED_KEYS);
                            insertSpecStatement.setString(1, especialidade);
                            insertSpecStatement.execute();

                            ResultSet insertSpecResult = insertSpecStatement.getGeneratedKeys();
                            insertSpecResult.next();

                            int key = insertSpecResult.getInt(1);
                            insertSpecStatement.close();

                            // Se a chave primária da especialidade adicionada for maior que zero
                            // significa que criou com sucesso
                            if(key > 0){
                                
                                // Cria um modelo de médico
                                MedicoModel medico = new MedicoModel(
                                        id,
                                        tipo, nome,
                                        "00000000000", dataNasc,
                                        telefone, "e@e.com",
                                        "00000000", "",
                                        sexo, key
                                );
                                // Adiciona o médico
                                return medico.update();
                            }
                            else{
                                System.err.println("Erro inesperado ao atualizar médico: Especialidade não foi criada corretamente no banco de dados.");
                                return new Resultado(false, "Erro inesperado ao atualizar médico: Especialidade não foi criada corretamente no banco de dados.");
                            }
                        }
                        else{

                            // Pega o id da especialidade com o nome informado pelo usuário
                            String getSpecSql = "SELECT id FROM especialidades WHERE nome LIKE '%"+especialidade+"%' LIMIT 1;";
                            Statement getSpecStatement = ConnectionClass.getStatement();

                            ResultSet getSpecResult = getSpecStatement.executeQuery(getSpecSql);
                            getSpecResult.next();
                            int specId = getSpecResult.getInt("id");
                            getSpecStatement.close();

                            // Se o id retornado da consulta for maior que zero
                            // significa que a especialidade foi obtida com sucesso
                            if(specId > 0){
                                
                                // Cria um modelo de médico
                                MedicoModel medico = new MedicoModel(
                                        id,
                                        tipo, nome,
                                        "00000000000", dataNasc,
                                        telefone, "e@e.com",
                                        "00000000", "",
                                        sexo, specId
                                );
                                // Adiciona o médico
                                return medico.update();
                            }
                            else{
                                System.err.println("Erro ao obter especialidade: A especialidade não foi encontrada.");
                                return new Resultado(false, "Erro ao obter especialidade: A especialidade não foi encontrada.");
                            }
                        }
                    }
                    else{
                        System.err.println("Erro ao atualizar médico: Especialidade faltando.");
                        return new Resultado(false, "Erro ao atualizar médico: Especialidade está faltando.");
                    }
                }
                else{
                    
                    // Cria um modelo de usuário
                    UserModel usuario = new UserModel(
                            id,
                            tipo, nome, "00000000000",
                            dataNasc, telefone, 
                            "e@e.com", ""
                    );
                    // Adiciona o usuário
                    return usuario.update();
                }
            }
            else{
                System.err.println("Erro ao atualizar usuário: Campos obrigatórios faltando.");
                return new Resultado(false, "Erro ao atualizar usuário: Campos obrigatórios faltando.");
            }
        }
        catch(SQLException e){
            System.err.println("Não foi possível atualizar um usuário: "+ e.getMessage());
            return new Resultado(false, "Erro inesperado ao atualizar usuário. Tente novamente.");
        }
    }
    
    /**
     * Método para obter dados específicos de um usuário
     * @param id - id do usuário escolhido para obter os campos
     * @param fieldName - nome do campo a ser obtido da forma em que ele aparece no banco de dados
     * @return Resultado - objeto de resultado. É possível obter o campo desejado com o método <code>Resultado.getCorpo()</code>
     */
    public static Resultado readUser(int id, String fieldName){
        
        try{
            // Verifica se o campo informado é um dos campos válidos
            if(!fieldName.equals("tipo") &&
                    !fieldName.equals("nome") &&
                    !fieldName.equals("cpf") &&
                    !fieldName.equals("data_nascimento") &&
                    !fieldName.equals("telefone") &&
                    !fieldName.equals("email") &&
                    !fieldName.equals("cadastro") &&
                    !fieldName.equals("data_cadastro") &&
                    !fieldName.equals("sexo") &&
                    !fieldName.equals("especialidade")){
                
                return new Resultado(false, "Campo especificado não existe no banco de dados.");
            }
            else{
            
                String getUserSql = "SELECT "+fieldName+" FROM usuarios WHERE id = '"+id+"';";
                Statement getUserStatement = ConnectionClass.getStatement();

                ResultSet getUserResult = getUserStatement.executeQuery(getUserSql);
                getUserResult.next();
                Object obj = getUserResult.getObject(fieldName);
                getUserResult.close();
                
                return new Resultado(true, "Sucesso", obj);
            }
        }
        catch(SQLException e){
            System.err.println("Ocorreu um erro ao obter o campo: ");
            e.printStackTrace();
            return new Resultado(false, "Ocorreu um erro ao obter o campo: "+ e.getMessage());
        }
    }
    
    public static ArrayList<String> readUser(String fieldName, String param, String paramValue){
        
        try{
            // Verifica se o campo informado é um dos campos válidos
            if(!fieldName.equals("id") &&
                    !fieldName.equals("tipo") &&
                    !fieldName.equals("nome") &&
                    !fieldName.equals("cpf") &&
                    !fieldName.equals("data_nascimento") &&
                    !fieldName.equals("telefone") &&
                    !fieldName.equals("email") &&
                    !fieldName.equals("cadastro") &&
                    !fieldName.equals("data_cadastro") &&
                    !fieldName.equals("sexo") &&
                    !fieldName.equals("especialidade") &&
                    !fieldName.equals("COUNT(*)")){
                
                return null;
            }
            else{
            
                String getUserSql = "SELECT "+fieldName+" FROM usuarios WHERE "+param+" = '"+paramValue+"';";
                Statement getUserStatement = ConnectionClass.getStatement();

                ArrayList<String> resultado = new ArrayList<>();
                ResultSet getUserResult = getUserStatement.executeQuery(getUserSql);
                
                while(getUserResult.next()){
                    resultado.add(getUserResult.getObject(fieldName).toString());
                }
                
                getUserResult.close();
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
     * Método para obter especialidades cadastradas no banco de dados
     * @return ArrayList<String> - Array com as especialidades cadastradas.
     */
    public static ArrayList<String> readEspecialidades(){
        
        try{
            
            String getSpecsSql = "SELECT nome FROM especialidades WHERE 1 = 1";
            Statement getSpecsStatement = ConnectionClass.getStatement();
            
            ResultSet getSpecsResult = getSpecsStatement.executeQuery(getSpecsSql);
            ArrayList<String> especialidades = new ArrayList<>();
            
            while(getSpecsResult.next()){
                especialidades.add(getSpecsResult.getString("nome"));
            }
            
            getSpecsStatement.close();
            return especialidades;
        }
        catch(Exception e){
            System.err.println("Falha ao obter especialidades: "+ e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public static int readEspecialidades(String nome){
        
        try{
            
            String getSpecsSql = "SELECT id FROM especialidades WHERE nome = '"+nome+"';";
            Statement getSpecsStatement = ConnectionClass.getStatement();
            
            ResultSet getSpecsResult = getSpecsStatement.executeQuery(getSpecsSql);
            
            getSpecsResult.next();
            int especialidadeId = getSpecsResult.getInt("id");
            getSpecsStatement.close();
            return especialidadeId;
        }
        catch(Exception e){
            System.err.println("Falha ao obter especialidades: "+ e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
    
    public static Resultado delete(int id){
        
        try{
            String deleteUserSql = "DELETE FROM usuarios WHERE id = '"+id+"'";
            Statement deleteUserStatement = ConnectionClass.getStatement();
            
            deleteUserStatement.execute(deleteUserSql);
                        
            return new Resultado(true, "Sucesso");
            
        }
        catch(SQLException e){
            System.err.println("Erro ao excluir usuário: "+e.getMessage());
            return new Resultado(false, "Erro ao excluir usuário: "+e.getMessage());
        }
        
    }
}

