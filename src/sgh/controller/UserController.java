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
 * Controller com os métodos relacionados aos usuários
 * @author Nicholas Campanelli
 */
public class UserController {
    
    /**
     * Método controlador de verificação e inserção de usuários
     * @param tipo - Tipo do usuário a ser inserido
     * @param nome - Nome completo do usuário
     * @param cpf - CPF limpo contendo apenas números
     * @param dataNasc - Data de nascrimento no padrão dd/MM/yyyy
     * @param telefone - Telefone limpo contendo apenas os números
     * @param email - Endereço de e-mail do usuário. Deve ser único pois é utilizado para login
     * @param cadastro - CRM limpo contendo apenas números para caso o usuário seja um médico
     * @param senha - Senha do usuário a ser cadastrado
     * @param sexo - Char de sexo ("m" ou "f") do usuário a ser inserido
     * @param especialidade -  Nome da especialidade caso o usuário seja um médico
     * @return Resultado - Ojeto de Resultado definindo se o resultado foi sucesso ou não, uma mensagem e o id do usuário inserido
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
            // Verifica se os campos não estão vazios
            if((tipo >= 1 && tipo <= 3) && (!"".equals(nome)) && (!"".equals(cpf)) && (!"".equals(dataNasc))
                    && (!"".equals(telefone)) && (!"".equals(email)) && (!"".equals(senha))){
                
                // Verifica a quantidade de usuários estão cadastrados com o CPF fornecido
                String checkCpfSql = "SELECT COUNT(*) FROM usuarios WHERE cpf = '"+cpf+"';";
                Statement checkCpfStatement = ConnectionClass.getStatement();
                
                // Executa a query e obtém os resultados
                ResultSet checkCpfResult = checkCpfStatement.executeQuery(checkCpfSql);
                checkCpfResult.next(); // Avança para o primeiro registro
                int cpfCount = checkCpfResult.getInt("COUNT(*)"); // Obtém a quantidade
                checkCpfResult.close(); // Fecha a conexão com o banco
                
                // Se não existir usuário com o mesmo CPF
                if(cpfCount == 0){

                    // Verifica se o email informado já está em uso por outro registro
                    String checkEmailSql = "SELECT COUNT(*) FROM usuarios WHERE email = '"+email+"';";
                    Statement checkEmailStatement = ConnectionClass.getStatement();

                    // Executa a query e obtém os resultados
                    ResultSet checkEmailResult = checkEmailStatement.executeQuery(checkEmailSql);
                    checkEmailResult.next(); // Avança para o primeiro registro
                    int emailCount = checkEmailResult.getInt("COUNT(*)"); // Obtém a quantidade
                    checkEmailStatement.close(); // Fecha a conexão com o banco

                    // Se a quantidade de registros for zero
                    // significa que o email está disponível para uso
                    if(emailCount == 0){
                        
                        // Quando o tipo do usuário a ser cadastrado for 3
                        // significa que ele é do tipo "médico"
                        if(tipo == 3){

                            // Verifica se o CRM e a especialidade não estão vazios
                            if((!"".equals(cadastro)) && (!"".equals(especialidade))){

                                // Verifica se o CRM já está cadastrado
                                String checkCrmSql = "SELECT COUNT(*) FROM usuarios WHERE cadastro = '"+cadastro+"';";
                                Statement checkCrmStatement = ConnectionClass.getStatement();

                                // Executa a query e obtém os resultados
                                ResultSet checkCrmResult = checkCrmStatement.executeQuery(checkCrmSql);
                                checkCrmResult.next(); // Avança para o primeiro registro
                                int crmCount = checkCrmResult.getInt("COUNT(*)"); // Obtém a quantidade
                                checkCrmStatement.close(); // Fecha a conexão com o banco

                                // Se não existir outro usuário com o mesmo CRM
                                if(crmCount == 0){

                                    // Verifica a existência da especialidade indicada
                                    String checkSpecSql = "SELECT COUNT(*) FROM especialidades WHERE nome LIKE '%"+especialidade+"%';";
                                    Statement checkSpecStatement = ConnectionClass.getStatement();

                                    // Executa a query e obtém o resultado
                                    ResultSet checkSpecResult = checkSpecStatement.executeQuery(checkSpecSql);
                                    checkSpecResult.next(); // Avança para o primeiro registro
                                    int specCount = checkSpecResult.getInt("COUNT(*)"); // Obtém a quantidade
                                    checkSpecStatement.close(); // Fecha a conexão com o banco

                                    // Se a especialidade indicada não existe
                                    if(specCount == 0){
                                        // Insere a especialidade
                                        String insertSpecSql = "INSERT INTO especialidades (nome) VALUES (?);";
                                        PreparedStatement insertSpecStatement = ConnectionClass.getPreparedStatement(insertSpecSql, Statement.RETURN_GENERATED_KEYS);
                                        insertSpecStatement.setString(1, especialidade);
                                        insertSpecStatement.execute();

                                        // Pega o id da especialidade inserida
                                        ResultSet insertSpecResult = insertSpecStatement.getGeneratedKeys();
                                        insertSpecResult.next(); // Avança para o primeiro registro

                                        // Pega o id
                                        int key = insertSpecResult.getInt(1);
                                        insertSpecStatement.close(); // Fecha a conexão com o banco

                                        // Se a chave primária da especialidade adicionada for maior que zero
                                        // significa que criou com sucesso
                                        if(key > 0){
                                            
                                            // Seção de criptografia da senha
                                            BigInteger senharaw = BigInteger.ONE;
                                            BigInteger cripsenha = BigInteger.ONE;

                                            try{
                                                // Pega uma instância do algoritimo de criptografia MD5
                                                MessageDigest md = MessageDigest.getInstance("MD5");
                                                senharaw = new BigInteger(1, md.digest(senha.getBytes())); // Criptografa a senha fornecida
                                                cripsenha = new BigInteger(1, md.digest((senharaw.toString(16) + "segredo").getBytes())); // Salt a senha criptografada e criptografa de novo
                                            }
                                            catch(NoSuchAlgorithmException e){
                                                System.err.println("Erro na criptografia da senha: "+e.getMessage());
                                                e.printStackTrace();
                                                return new Resultado(false, "Erro inesperado na criação de médico. Tente novamente.");
                                            }
                                            
                                            // Cria um modelo de médico com os dados fornecidos
                                            MedicoModel medico = new MedicoModel(
                                                    tipo, nome,
                                                    cpf, dataNasc,
                                                    telefone, email,
                                                    cadastro, cripsenha.toString(16),
                                                    sexo, key
                                            );
                                            return medico.create(); // Chama o método de inserção e retorna o resultado
                                        }
                                        else{
                                            System.err.println("Erro inesperado ao criar médico: Especialidade não foi criada corretamente no banco de dados.");
                                            return new Resultado(false, "Erro inesperado ao criar médico: Especialidade não foi criada corretamente no banco de dados.");
                                        }
                                    }
                                    // Quando a especialidade existe
                                    else{
                                        
                                        // Pega o id da especialidade com o nome informado pelo usuário
                                        String getSpecSql = "SELECT id FROM especialidades WHERE nome LIKE '%"+especialidade+"%' LIMIT 1;";
                                        Statement getSpecStatement = ConnectionClass.getStatement();

                                        // Executa a query e obtém o resultado
                                        ResultSet getSpecResult = getSpecStatement.executeQuery(getSpecSql);
                                        getSpecResult.next(); // Avança para o primeiro registro
                                        int specId = getSpecResult.getInt("id"); // Obtém o id
                                        getSpecStatement.close(); // Fecha a conexão com o banco

                                        // Se o id retornado da consulta for maior que zero
                                        // significa que a especialidade foi obtida com sucesso
                                        if(specId > 0){

                                            // Seção de criptografia da senha
                                            BigInteger senharaw = BigInteger.ONE;
                                            BigInteger cripsenha = BigInteger.ONE;

                                            try{
                                                // Pega uma instância do algoritimo de criptografia MD5
                                                MessageDigest md = MessageDigest.getInstance("MD5");
                                                senharaw = new BigInteger(1, md.digest(senha.getBytes())); // Criptografa a senha inserida
                                                cripsenha = new BigInteger(1, md.digest((senharaw.toString(16) + "segredo").getBytes())); // Salt a senha criptografada e criptografa de novo
                                            }
                                            catch(NoSuchAlgorithmException e){
                                                System.err.println("Erro na criptografia da senha: "+e.getMessage());
                                                e.printStackTrace();
                                                return new Resultado(false, "Erro inesperado na criação de médico. Tente novamente.");
                                            }

                                            // Cria um modelo de médico com os dados fornecidos
                                            MedicoModel medico = new MedicoModel(
                                                    tipo, nome,
                                                    cpf, dataNasc,
                                                    telefone, email,
                                                    cadastro, cripsenha.toString(16),
                                                    sexo, specId
                                            );
                                            return medico.create(); // Chama o método de inserção e retorna o resultado
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
                        // Se o usuário não for do tipo médico
                        else{

                            // Seção de criptografia da senha
                            BigInteger senharaw = BigInteger.ONE;
                            BigInteger cripsenha = BigInteger.ONE;

                            try{
                                // Pega uma instância do algoritimo de criptografia MD5
                                MessageDigest md = MessageDigest.getInstance("MD5");
                                senharaw = new BigInteger(1, md.digest(senha.getBytes())); // Criptografa a senha inserida
                                cripsenha = new BigInteger(1, md.digest((senharaw.toString(16) + "segredo").getBytes())); // Salt a senha criptografada e criptografa de novo
                            }
                            catch(NoSuchAlgorithmException e){
                                System.err.println("Erro na criptografia da senha: "+e.getMessage());
                                e.printStackTrace();
                                return new Resultado(false, "Erro inesperado ao criar o usuário. Tente novamente.");
                            }

                            // Cria um modelo de usuário com os dados fornecidos
                            UserModel usuario = new UserModel(
                                    tipo, nome, cpf,
                                    dataNasc, telefone, 
                                    email, cripsenha.toString(16)
                            );
                            return usuario.create(); // Chama o método de inserção e retorna o resultado
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
     * Método controlador para obter todos os usuários do banco com o tipo informado
     * @param tipo - Tipo dos usuários a serem listados
     * @return DefaultTableModel - Modelo de tabela com os dados dos registros obtidos e o cabeçalho da tabela
     */
    public static DefaultTableModel read(int tipo){
        
        // Instancia um novo modelo de tabela vazio
        DefaultTableModel model = new DefaultTableModel(0, 0);
        String[] columns;
        
        try{
            switch (tipo) {
                // Se o usuário for médico
                case 3:
                    // Define o cabeçalho da tabela
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
                    
                    // Insere o cabeçalho ao modelo
                    model = new DefaultTableModel(columns, 0);
                    
                    // Obtém o médico e utiliza um inner join para obter o nome da especialidade
                    String getMedicSql = "SELECT b.id, b.cpf, b.cadastro, b.nome, a.nome as especialidade, b.email, b.telefone, b.data_cadastro "
                            + "FROM especialidades a INNER JOIN usuarios b ON a.id = b.especialidade;";
                    Statement getMedicStatement = ConnectionClass.getStatement();
                    
                    // Executa a query e obtém o resultado
                    ResultSet getMedicResult = getMedicStatement.executeQuery(getMedicSql);
                    
                    // Enquanto houver registros, avança para o próximo
                    while(getMedicResult.next()){
                        
                        // Pega a data de cadastro e separa por hífen
                        String[] dataCadSplit = getMedicResult.getDate("data_cadastro", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                        String newDataCad = dataCadSplit[2] + "/" + dataCadSplit[1] + "/" + dataCadSplit[0];
                        
                        // Insere o registro atual no modelo da tabela
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
                    
                    // Fecha a conexão com o banco
                    getMedicStatement.close();
                    
                    break;
                
                // Caso o usuário seja um atendente
                case 2:
                    // Define o cabeçalho da tabela
                    columns = new String[]{
                        "ID",
                        "CPF",
                        "Nome",
                        "E-mail",
                        "Telefone",
                        "Data de nascimento",
                        "Cadastrado em"
                    };
                    
                    // Insere o cabeçalho ao modelo
                    model = new DefaultTableModel(columns, 0);
                    
                    // Obtém os dados do usuário
                    String getAtendenteSql = "SELECT id, cpf, nome, email, telefone, data_nascimento, data_cadastro FROM usuarios WHERE tipo = 2;";
                    Statement getAtendenteStatement = ConnectionClass.getStatement();
                    
                    // Executa a query e obtém o resultado
                    ResultSet getAtendenteResult = getAtendenteStatement.executeQuery(getAtendenteSql);
                    
                    // Enquanto houver registros, avança para o próximo
                    while(getAtendenteResult.next()){
                        
                        // Pega as datas e separa por hífen
                        String[] dataNascSplit = getAtendenteResult.getDate("data_nascimento", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                        String[] dataCadSplit = getAtendenteResult.getDate("data_cadastro", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                        
                        // Formata as datas no padrão dd/MM/yyyy
                        String newDataNasc = dataNascSplit[2] + "/" + dataNascSplit[1] + "/" + dataNascSplit[0];
                        String newDataCad = dataCadSplit[2] + "/" + dataCadSplit[1] + "/" + dataCadSplit[0];
                        
                        // Adiciona o registro atual no modelo da tabela
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
                    
                    // Fecha a conexão com o banco
                    getAtendenteStatement.close();
                    
                    break;
                    
                // Caso o usuário seja um administrador
                case 1:
                    // Define o cabeçalho da tabela
                    columns = new String[]{
                        "ID",
                        "CPF",
                        "Nome",
                        "E-mail",
                        "Telefone",
                        "Data de nascimento",
                        "Cadastrado em"
                    };
                    
                    // Insere o cabeçalho no modelo
                    model = new DefaultTableModel(columns, 0);
                    
                    // Obtém os dados do administrador
                    String getAdminSql = "SELECT id, cpf, nome, email, telefone, data_nascimento, data_cadastro FROM usuarios WHERE tipo = 1;";
                    Statement getAdminStatement = ConnectionClass.getStatement();
                    
                    // Executa a query e obtém o resultado
                    ResultSet getAdminResult = getAdminStatement.executeQuery(getAdminSql);
                    
                    // Enquanto houver registros, avança para o próximo
                    while(getAdminResult.next()){
                        
                        // Obtém as datas e separa por hífen
                        String[] dataNascSplit = getAdminResult.getDate("data_nascimento", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                        String[] dataCadSplit = getAdminResult.getDate("data_cadastro", Calendar.getInstance(TimeZone.getDefault())).toString().split("-");
                        
                        // Formata as datas no padrão dd/MM/yyyy
                        String newDataNasc = dataNascSplit[2] + "/" + dataNascSplit[1] + "/" + dataNascSplit[0];
                        String newDataCad = dataCadSplit[2] + "/" + dataCadSplit[1] + "/" + dataCadSplit[0];
                        
                        // Adiciona o registro atual no modelo da tabela
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
                    
                    // Fecha a conexão com o banco
                    getAdminStatement.close();
                    
                    break;
            }
        }
        catch(SQLException e){
            System.err.println("Erro ao obter usuários: ");
            e.printStackTrace();
        }
        
        // Retorna o modelo da tabela
        return model;
    }
    
    /**
     * Método controlador para atualizar os dados de um usuário
     * @param tipo - Tipo do usuário que será atualizado
     * @param nome - Novo nome completo do usuário
     * @param dataNasc - Nova data de nascimento do usuário no padrão dd/MM/yyyy
     * @param telefone - Novo telefone do usuário limpo com apenas os números
     * @param sexo - Novo char de sexo ("m" ou "f") do usuário
     * @param especialidade - Nome da nova especialidade do usuário
     * @return Resultado - Objeto do tipo Resultado definindo se o resultado foi sucesso ou não, uma mensagem e o id do usuário atualizado
     */
    public static Resultado update(
            int id, int tipo,
            String nome, String dataNasc,
            String telefone, String sexo, 
            String especialidade
    ){
        try{
            // Verifica se os campos não estão vazios ou com valores inválidos
            if((tipo >= 1 && tipo <= 3) && (!"".equals(nome)) && (!"".equals(dataNasc))
                    && (!"".equals(telefone))){

                // Quando o tipo do usuário a ser atualizado for 3
                // significa que ele é do tipo "médico"
                if(tipo == 3){

                    // Verifica se a especialidade não estão vazios
                    if(!"".equals(especialidade)){

                        // Verifica a existência da especialidade indicada
                        String checkSpecSql = "SELECT COUNT(*) FROM especialidades WHERE nome LIKE '%"+especialidade+"%';";
                        Statement checkSpecStatement = ConnectionClass.getStatement();

                        // Executa a query e obtém o resultado
                        ResultSet checkSpecResult = checkSpecStatement.executeQuery(checkSpecSql);
                        checkSpecResult.next(); // Avança para o primeiro registro
                        int specCount = checkSpecResult.getInt("COUNT(*)"); // Obtém a quantidade
                        checkSpecStatement.close(); // Fecha a conexão com o banco

                        // Se a especialidade indicada não existe
                        if(specCount == 0){
                            // Insere a especialidade
                            String insertSpecSql = "INSERT INTO especialidades (nome) VALUES (?);";
                            PreparedStatement insertSpecStatement = ConnectionClass.getPreparedStatement(insertSpecSql, Statement.RETURN_GENERATED_KEYS);
                            
                            // Define o nome da especialidade 
                            insertSpecStatement.setString(1, especialidade);
                            insertSpecStatement.execute();

                            // Obtém a chave do usuário atualizado
                            ResultSet insertSpecResult = insertSpecStatement.getGeneratedKeys();
                            insertSpecResult.next(); // Avança para o primeiro registro

                            int key = insertSpecResult.getInt(1); // Obtém a chave
                            insertSpecStatement.close();

                            // Se a chave primária da especialidade adicionada for maior que zero
                            // significa que criou com sucesso
                            if(key > 0){
                                
                                // Cria um modelo de médico com os dados fornecidos
                                MedicoModel medico = new MedicoModel(
                                        id,
                                        tipo, nome,
                                        "00000000000", dataNasc,
                                        telefone, "e@e.com",
                                        "00000000", "",
                                        sexo, key
                                );
                                
                                return medico.update(); // Chama o método de atualizar e retorna o resultado
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

                            // Executa a query e obtém o resultado
                            ResultSet getSpecResult = getSpecStatement.executeQuery(getSpecSql);
                            getSpecResult.next(); // Avança para o primeiro registro
                            int specId = getSpecResult.getInt("id"); // Obtém o id da especialidade
                            getSpecStatement.close(); // Fecha a conexão com o banco

                            // Se o id retornado da consulta for maior que zero
                            // significa que a especialidade foi obtida com sucesso
                            if(specId > 0){
                                
                                // Cria um modelo de médico com os dados fornecidos
                                MedicoModel medico = new MedicoModel(
                                        id,
                                        tipo, nome,
                                        "00000000000", dataNasc,
                                        telefone, "e@e.com",
                                        "00000000", "",
                                        sexo, specId
                                );
                                return medico.update(); // Chama o método de atualizar e retorna o resultado
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
                    // Cria um modelo de usuário com os dados fornecidos
                    UserModel usuario = new UserModel(
                            id,
                            tipo, nome, "00000000000",
                            dataNasc, telefone, 
                            "e@e.com", ""
                    );
                    return usuario.update(); // Chama o método de atualizar e retorna o resultado
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
     * Método controlador para obter um dado específico de um usuário com dado ID
     * @param id - ID do usuário escolhido para obter o campo
     * @param fieldName - Nome do campo a ser obtido da forma em que ele aparece no banco de dados
     * @return Resultado - Objeto de resultado. É possível obter o campo desejado com o método <code>Resultado.getCorpo()</code>
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
            
                // Coloca os dados informados no SQL
                String getUserSql = "SELECT "+fieldName+" FROM usuarios WHERE id = '"+id+"';";
                Statement getUserStatement = ConnectionClass.getStatement();

                // Executa a query e obtém o resultado
                ResultSet getUserResult = getUserStatement.executeQuery(getUserSql);
                getUserResult.next(); // Avança para o primeiro registro
                Object obj = getUserResult.getObject(fieldName); // Obtém o campo
                getUserResult.close(); // Fecha a conexão com o banco
                
                // Retorna o campo obtido
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
     * Método controlador para obter um campo de um determinado registro onde dada condição for satisfeita
     * @param fieldName - Nome do campo a ser obtido
     * @param param - Primeiro parâmetro da condição
     * @param paramValue - Segundo parâmentro da condição
     * @return ArrayList<String> - ArrayList de strings com os dados obtidos
     */
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
                    !fieldName.equals("COUNT(*)")
                    &&
                    !param.equals("id") &&
                    !param.equals("tipo") &&
                    !param.equals("nome") &&
                    !param.equals("cpf") &&
                    !param.equals("data_nascimento") &&
                    !param.equals("telefone") &&
                    !param.equals("email") &&
                    !param.equals("cadastro") &&
                    !param.equals("data_cadastro") &&
                    !param.equals("sexo") &&
                    !param.equals("especialidade") &&
                    !param.equals("COUNT(*)")){
                
                // Retorna vazio
                return null;
            }
            else{
            
                // Coloca os dados inseridos no SQL
                String getUserSql = "SELECT "+fieldName+" FROM usuarios WHERE "+param+" = '"+paramValue+"';";
                Statement getUserStatement = ConnectionClass.getStatement();

                // Cria um ArrayList vazio
                ArrayList<String> resultado = new ArrayList<>();
                ResultSet getUserResult = getUserStatement.executeQuery(getUserSql);
                
                // Enquanto houver registros, avança para o próximo
                while(getUserResult.next()){
                    // Adiciona o registro atual na ArrayList
                    resultado.add(getUserResult.getObject(fieldName).toString());
                }
                
                // Fecha a conexão com o banco
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
     * Método controlador para obter especialidades cadastradas no banco de dados
     * @return ArrayList<String> - Array com as especialidades cadastradas
     */
    public static ArrayList<String> readEspecialidades(){
        
        try{
            // Obtém o nome das especialidades
            String getSpecsSql = "SELECT nome FROM especialidades WHERE 1 = 1";
            Statement getSpecsStatement = ConnectionClass.getStatement();
            
            // Executa a query e obtém o resultado
            ResultSet getSpecsResult = getSpecsStatement.executeQuery(getSpecsSql);
            ArrayList<String> especialidades = new ArrayList<>();
            
            // Enquanto houver registros, avança para o próximo
            while(getSpecsResult.next()){
                // Adiciona o registro atual na ArrayList
                especialidades.add(getSpecsResult.getString("nome"));
            }
            
            // Fecha a conexão com o banco
            getSpecsStatement.close();
            return especialidades;
        }
        catch(Exception e){
            System.err.println("Falha ao obter especialidades: "+ e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Sobrescrita do método controlador de obter as especialidades. Obtém o id de uma especialidade com o nome informado. Retorna -1 caso ocorra erro ou não encontre
     * @param nome - Nome da especialidade a qual se deseja obter o ID
     * @return int - ID da especialidade com o nome informado. Retorna -1 caso não encontre ou ocorra um erro
     */
    public static int readEspecialidades(String nome){
        
        try{
            // Pega o id da especialidade
            String getSpecsSql = "SELECT id FROM especialidades WHERE nome = '"+nome+"';";
            Statement getSpecsStatement = ConnectionClass.getStatement();
            
            // Executa a query e obtém o resultado
            ResultSet getSpecsResult = getSpecsStatement.executeQuery(getSpecsSql);
            getSpecsResult.next(); // Avança para o próximo registro
            int especialidadeId = getSpecsResult.getInt("id"); // Pega o id
            getSpecsStatement.close(); // Fecha a conexão com o banco
            
            return especialidadeId;
        }
        catch(Exception e){
            System.err.println("Falha ao obter especialidades: "+ e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
    
    /**
     * Método controlador para remover um usuário do banco
     * @param id - ID do usuário a ser deletado
     * @return Resultado - Objeto de resultado com um boolean definindo se houve sucesso ou não, uma mensagem e um objeto
     */
    public static Resultado delete(int id){
        
        try{
            // Deleta o usuário com o id informado
            String deleteUserSql = "DELETE FROM usuarios WHERE id = '"+id+"'";
            Statement deleteUserStatement = ConnectionClass.getStatement();
            
            // Executa a remoção
            deleteUserStatement.execute(deleteUserSql);
            
            // Retorna o resultado com sucesso
            return new Resultado(true, "Sucesso");
            
        }
        catch(SQLException e){
            System.err.println("Erro ao excluir usuário: "+e.getMessage());
            return new Resultado(false, "Erro ao excluir usuário: "+e.getMessage());
        }
    }
}