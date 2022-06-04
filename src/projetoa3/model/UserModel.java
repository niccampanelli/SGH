package projetoa3.model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import projetoa3.util.Resultado;
import projetoa3.util.database.ConnectionClass;

/**
 * Classe modelo de usuário
 * @author Nicholas Campanelli
 */
public class UserModel {
    
    protected int id;
    protected int tipo;
    protected String nome;
    protected String cpf;
    protected String dataNasc;
    protected String telefone;
    protected String email;
    protected String senha;
    protected Timestamp dataCad;

    public UserModel(){
        
    }
    
    public UserModel(
            int tipo, String nome, String cpf,
            String dataNasc, String telefone,
            String email, String senha
    ){
        this.setTipo(tipo);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setDataNasc(dataNasc);
        this.setTelefone(telefone);
        this.setEmail(email);
        this.setSenha(senha);
        this.setDataCad(Timestamp.from(Instant.now()));
    }
    
    public UserModel(
            int id, int tipo, String nome, String cpf,
            String dataNasc, String telefone,
            String email, String senha
    ){
        this.setId(id);
        this.setTipo(tipo);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setDataNasc(dataNasc);
        this.setTelefone(telefone);
        this.setEmail(email);
        this.setSenha(senha);
        this.setDataCad(Timestamp.from(Instant.now()));
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome.length() <= 50)
            this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if(cpf.length() == 11)
            this.cpf = cpf;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        if(dataNasc.length() == 10)
            this.dataNasc = dataNasc;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if(telefone.length() == 11)
            this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email.length() <= 50)
            this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if(senha.length() <= 255)
            this.senha = senha;
    }

    public Timestamp getDataCad() {
        return dataCad;
    }

    public void setDataCad(Timestamp dataCad) {
        this.dataCad = dataCad;
    }
    
    public Resultado create(){
        try{
            String insertUserSql = "INSERT INTO usuarios (tipo, nome, cpf, data_nascimento, "
                    + "telefone, email, senha, data_cadastro) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertUserStatement = ConnectionClass.getPreparedStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
            
            String[] dataNascPieces = this.getDataNasc().split("/");
            String newDate = dataNascPieces[2]+"-"+dataNascPieces[1]+"-"+dataNascPieces[0];
            
            insertUserStatement.setInt(1, this.getTipo());
            insertUserStatement.setString(2, this.getNome());
            insertUserStatement.setString(3, this.getCpf());
            insertUserStatement.setDate(4, Date.valueOf(newDate));
            insertUserStatement.setString(5, this.getTelefone());
            insertUserStatement.setString(6, this.getEmail());
            insertUserStatement.setString(7, this.getSenha());
            insertUserStatement.setTimestamp(8, this.getDataCad());
            
            insertUserStatement.execute();
            
            ResultSet insertUserResult = insertUserStatement.getGeneratedKeys();
            insertUserResult.next();
            
            int key = insertUserResult.getInt(1);
            insertUserStatement.close();
            return new Resultado(true, "Sucesso", key);
        }
        catch(SQLException e){
            System.err.println("Não foi possível criar um usuário: "+ e.getMessage());
            return new Resultado(false, "Não foi possível criar um usuário. Tente novamente.\n" + e.getMessage());
        }
    }
    
    public Resultado update(){
        try{
            String updateUserSql = "UPDATE usuarios SET nome = ?, data_nascimento = ?, telefone = ? WHERE id = '"+this.getId()+"'";
            PreparedStatement updateUserStatement = ConnectionClass.getPreparedStatement(updateUserSql);
            
            String[] dataNascPieces = this.getDataNasc().split("/");
            String newDate = dataNascPieces[2]+"-"+dataNascPieces[1]+"-"+dataNascPieces[0];
            
            updateUserStatement.setString(1, this.getNome());
            updateUserStatement.setDate(2, Date.valueOf(newDate));
            updateUserStatement.setString(3, this.getTelefone());
            
            if(updateUserStatement.executeUpdate() == 0){
                System.err.println("Não foi possível atualizar o usuário.");
                return new Resultado(false, "Não foi possivel atualizar o usuário. Tente novamente.");
            }
            
            updateUserStatement.close();
            return new Resultado(true, "Sucesso");
        }
        catch(SQLException e){
            System.err.println("Não foi possível atualizar o usuário: "+ e.getMessage());
            return new Resultado(false, "Não foi possivel atualizar o usuário. Tente novamente. \n"+ e.getMessage());
        }
    }
}