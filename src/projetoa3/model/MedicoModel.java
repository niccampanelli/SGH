package projetoa3.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import projetoa3.util.Resultado;
import projetoa3.util.database.ConnectionClass;

/**
 * Classe modelo de medico
 * @author Nicholas Campanelli
 */
public class MedicoModel extends UserModel {
    
    private String cadastro;
    private String sexo;
    private int especialidade;

    public MedicoModel(){
        
    }
    
    public MedicoModel(
            int tipo, String nome,
            String cpf, String dataNasc,
            String telefone, String email,
            String cadastro, String senha,
            String sexo, int especialidade
    ){
        this.setTipo(tipo);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setDataNasc(dataNasc);
        this.setTelefone(telefone);
        this.setEmail(email);
        this.setCadastro(cadastro);
        this.setSenha(senha);
        this.setSexo(sexo);
        this.setEspecialidade(especialidade);
        this.setDataCad(Timestamp.from(Instant.now()));
    }
    
    public MedicoModel(
            int id,
            int tipo, String nome,
            String cpf, String dataNasc,
            String telefone, String email,
            String cadastro, String senha,
            String sexo, int especialidade
    ){
        this.setId(id);
        this.setTipo(tipo);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setDataNasc(dataNasc);
        this.setTelefone(telefone);
        this.setEmail(email);
        this.setCadastro(cadastro);
        this.setSenha(senha);
        this.setSexo(sexo);
        this.setEspecialidade(especialidade);
        this.setDataCad(Timestamp.from(Instant.now()));
    }
    
    public String getCadastro() {
        return cadastro;
    }

    public void setCadastro(String cadastro) {
        if(cadastro.length() == 8)
            this.cadastro = cadastro;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(int especialidade) {
        this.especialidade = especialidade;
    }
    
    @Override
    public Resultado create(){
        try{
            String insertMedicSql = "INSERT INTO usuarios (tipo, nome, cpf, data_nascimento, "
                    + "telefone, email, cadastro, senha, data_cadastro, sexo, especialidade) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertMedicStatement = ConnectionClass.getPreparedStatement(insertMedicSql, Statement.RETURN_GENERATED_KEYS);
            
            String[] dataNascPieces = dataNasc.split("/");
            String newDate = dataNascPieces[2]+"-"+dataNascPieces[1]+"-"+dataNascPieces[0];
            
            insertMedicStatement.setInt(1, tipo);
            insertMedicStatement.setString(2, nome);
            insertMedicStatement.setString(3, cpf);
            insertMedicStatement.setDate(4, Date.valueOf(newDate));
            insertMedicStatement.setString(5, telefone);
            insertMedicStatement.setString(6, email);
            insertMedicStatement.setString(7, cadastro);
            insertMedicStatement.setString(8, senha);
            insertMedicStatement.setTimestamp(9, dataCad);
            insertMedicStatement.setString(10, sexo);
            insertMedicStatement.setInt(11, especialidade);
            
            insertMedicStatement.execute();
            
            ResultSet insertMedicResult = insertMedicStatement.getGeneratedKeys();
            insertMedicResult.next();
            
            int key = insertMedicResult.getInt(1);
            insertMedicStatement.close();
            return new Resultado(true, "Sucesso", key);
        }
        catch(SQLException e){
            System.err.println("Não foi possível criar um médico: "+ e.getMessage());
            return new Resultado(false, "Não foi possível criar um médico. Tente novamente.\n "+ e.getMessage());
        }
    }
    
    @Override
    public Resultado update(){
        try{
            String updateUserSql = "UPDATE usuarios SET nome = ?, data_nascimento = ?, telefone = ?, sexo = ?, especialidade = ? WHERE id = '"+this.getId()+"'";
            PreparedStatement updateUserStatement = ConnectionClass.getPreparedStatement(updateUserSql);
            
            String[] dataNascPieces = this.getDataNasc().split("/");
            String newDate = dataNascPieces[2]+"-"+dataNascPieces[1]+"-"+dataNascPieces[0];
            
            updateUserStatement.setString(1, this.getNome());
            updateUserStatement.setDate(2, Date.valueOf(newDate));
            updateUserStatement.setString(3, this.getTelefone());
            updateUserStatement.setString(4, this.getSexo());
            updateUserStatement.setInt(5, this.getEspecialidade());
            
            if(updateUserStatement.executeUpdate() == 0){
                return new Resultado(false, "Não foi possivel atualizar o médico. Tente novamente.");
            }
            
            updateUserStatement.close();
            
            return new Resultado(true, "Sucesso");
        }
        catch(SQLException e){
            System.err.println("Não foi possível atualizar o médico: "+ e.getMessage());
            return new Resultado(false, "Não foi possivel atualizar o médico. Tente novamente. \n"+ e.getMessage());
        }
    }
}