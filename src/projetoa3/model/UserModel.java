package projetoa3.model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
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
            int tipo, String nome,
            String dataNasc, String telefone,
            String email, String senha
    ){
        this.setTipo(tipo);
        this.setNome(nome);
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
    
    public int create(){
        try{
            String insertUserSql = "INSERT INTO usuarios (tipo, nome, cpf, data_nascimento, "
                    + "telefone, email, senha, data_cadastro) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertUserStatement = ConnectionClass.getPreparedStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
            
            String[] dataNascPieces = dataNasc.split("/");
            String newDate = dataNascPieces[2]+"-"+dataNascPieces[1]+"-"+dataNascPieces[0];
            
            insertUserStatement.setInt(1, tipo);
            insertUserStatement.setString(2, nome);
            insertUserStatement.setString(3, cpf);
            insertUserStatement.setDate(4, Date.valueOf(newDate));
            insertUserStatement.setString(5, telefone);
            insertUserStatement.setString(6, email);
            insertUserStatement.setString(7, senha);
            insertUserStatement.setTimestamp(8, dataCad);
            
            insertUserStatement.execute();
            
            ResultSet insertUserResult = insertUserStatement.getGeneratedKeys();
            insertUserResult.next();
            
            int key = insertUserResult.getInt(1);
            insertUserStatement.close();
            return key;
        }
        catch(SQLException e){
            System.err.println("Não foi possível criar um usuário: "+ e.getMessage());
            return 0;
        }
    }
}