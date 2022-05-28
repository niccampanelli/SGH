package projetoa3.controller;

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
    
    public static void create(
            int tipo, String nome,
            String cpf, String dataNasc,
            String telefone, String email,
            String cadastro, String senha,
            String sexo, String especialidade
    ){
        
        try{
            if(tipo == 3){
                String checkSpecSql = "SELECT COUNT(*) FROM especialidades WHERE nome LIKE '%"+especialidade+"%';";
                Statement checkSpecStatement = ConnectionClass.getStatement();
                
                ResultSet checkSpecResult = checkSpecStatement.executeQuery(checkSpecSql);
                checkSpecResult.next();
                int specCount = checkSpecResult.getInt("COUNT(*)");
                checkSpecStatement.close();
                
                if(specCount == 0){
                    String insertSpecSql = "INSERT INTO especialidades (nome) VALUES"
                            + "(?);";
                    PreparedStatement insertSpecStatement = ConnectionClass.getPreparedStatement(insertSpecSql, Statement.RETURN_GENERATED_KEYS);
                    insertSpecStatement.setString(1, especialidade);
                    insertSpecStatement.execute();
                    
                    ResultSet insertSpecResult = insertSpecStatement.getGeneratedKeys();
                    insertSpecResult.next();
                    
                    int key = insertSpecResult.getInt(1);
                    insertSpecStatement.close();
                    
                    if(key != 0){
                        MedicoModel medico = new MedicoModel(
                                tipo, nome,
                                cpf, dataNasc,
                                telefone, email,
                                cadastro, senha,
                                sexo, key
                        );
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
