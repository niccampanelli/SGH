package projetoa3.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import projetoa3.model.MedicoModel;
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
            // Quando o tipo do usuário a ser cadastrado for 3
            // significa que ele é do tipo "médico"
            if(tipo == 3){
                
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
                    
                    // Se a chave primária da especialidade adicionada não for zero
                    // significa que criou com sucesso
                    if(key != 0){
                        
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
                }
            }
            else{
                
                /*String insertUserSql = "INSERT INTO usuarios (tipo, nome, cpf, data_nascimento, "
                        + "telefone, email, cadastro, senha, data_cadastro, sexo, especialidade) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertUserStatement = ConnectionClass.getPreparedStatement(cpf);*/
            }
            
        }
        catch(SQLException e){
            System.err.println("Não foi possível cadastrar um usuário: "+ e.getMessage());
        }
    }
    
}
