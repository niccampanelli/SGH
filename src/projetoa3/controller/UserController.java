package projetoa3.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import projetoa3.model.MedicoModel;
import projetoa3.model.UserModel;
import projetoa3.util.database.ConnectionClass;

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
     */
    public static void create(
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
                    && (!"".equals(telefone)) && (!"".equals(email)) && (!"".equals(senha)) && (!"".equals(sexo))){
                
                // Quando o tipo do usuário a ser cadastrado for 3
                // significa que ele é do tipo "médico"
                if(tipo == 3){
                    
                    // Verifica se o cadastro e a especialidade não estão vazios
                    if((!"".equals(cadastro)) && (!"".equals(especialidade))){
                        
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
                                BigInteger cripsenha = BigInteger.ONE;

                                try{
                                    MessageDigest md = MessageDigest.getInstance("MD5");
                                    cripsenha = new BigInteger(1, md.digest((senha + "segredo").getBytes()));
                                }
                                catch(NoSuchAlgorithmException e){
                                    System.err.println("Erro na criptografia da senha: "+e.getMessage());
                                    e.printStackTrace();
                                }

                                // Cria um modelo de médico
                                MedicoModel medico = new MedicoModel(
                                        tipo, nome,
                                        cpf, dataNasc,
                                        telefone, email,
                                        cadastro, cripsenha.toString(),
                                        sexo, key
                                );
                                // Adiciona o médico
                                medico.create();
                            }
                            else{
                                System.err.println("Erro inesperado ao criar médico: Especialidade não foi criada corretamente no banco de dados.");
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

                                    // Seção de criptografia da senha
                                    BigInteger cripsenha = BigInteger.ONE;

                                    try{
                                        MessageDigest md = MessageDigest.getInstance("MD5");
                                        cripsenha = new BigInteger(1, md.digest((senha + "segredo").getBytes()));
                                    }
                                    catch(NoSuchAlgorithmException e){
                                        System.err.println("Erro na criptografia da senha: "+e.getMessage());
                                        e.printStackTrace();
                                    }

                                    // Cria um modelo de médico
                                    MedicoModel medico = new MedicoModel(
                                            tipo, nome,
                                            cpf, dataNasc,
                                            telefone, email,
                                            cadastro, cripsenha.toString(),
                                            sexo, specId
                                    );
                                    // Adiciona o médico
                                    medico.create();
                                }
                                else{
                                    System.err.println("Erro na criação de usuário: Email em uso.");
                                }
                            }
                            else{
                                System.err.println("Erro ao obter especialidade: A especialidade não foi enconrada.");
                            }
                        }
                    }
                    else{
                        System.err.println("Erro ao cadastrar médico: Cadastro e/ou especialidade faltando.");
                    }
                }
                else{
                    
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
                        
                        // Seção de criptografia da senha
                        BigInteger cripsenha = BigInteger.ONE;

                        try{
                            MessageDigest md = MessageDigest.getInstance("MD5");
                            cripsenha = new BigInteger(1, md.digest((senha + "segredo").getBytes()));
                        }
                        catch(NoSuchAlgorithmException e){
                            System.err.println("Erro na criptografia da senha: "+e.getMessage());
                            e.printStackTrace();
                        }

                        // Cria um modelo de usuário
                        UserModel usuario = new UserModel(
                                tipo, nome, 
                                dataNasc, telefone, 
                                email, senha
                        );
                        // Adiciona o usuário
                        usuario.create();
                    }
                    else{
                        System.err.println("Erro na criação de usuário: Email em uso.");
                    }
                }
            }
            else{
                System.err.println("Erro ao cadastrar usuário: Campos obrigatórios faltando.");
            }
        }
        catch(SQLException e){
            System.err.println("Não foi possível cadastrar um usuário: "+ e.getMessage());
        }
    }
    
}
