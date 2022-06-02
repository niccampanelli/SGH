package projetoa3.model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import projetoa3.util.Resultado;
import projetoa3.util.database.ConnectionClass;

/**
 * Classe modelo de pacientes
 * @author Nicholas Campanelli
 */
public class PacienteModel {
    
    private int id;
    private String nome;
    private String sexo;
    private String dataNasc;
    private String cpf;
    private String telefone;
    private String endereco;

    public PacienteModel(){
        
    }
    
    public PacienteModel(
            String nome, String sexo,
            String dataNasc, String cpf,
            String telefone, String endereco
    ){
        this.setNome(nome);
        this.setSexo(sexo);
        this.setDataNasc(dataNasc);
        this.setCpf(cpf);
        this.setTelefone(telefone);
        this.setEndereco(endereco);
    }
    
    public PacienteModel(
            int id,
            String nome, String sexo,
            String dataNasc, String cpf,
            String telefone, String endereco
    ){
        this.setId(id);
        this.setNome(nome);
        this.setSexo(sexo);
        this.setDataNasc(dataNasc);
        this.setCpf(cpf);
        this.setTelefone(telefone);
        this.setEndereco(endereco);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome.length() <= 50)
            this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        if(dataNasc.length() == 10)
            this.dataNasc = dataNasc;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if(cpf.length() == 11)
            this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if(telefone.length() == 11)
            this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        if(endereco.length() <= 100)
            this.endereco = endereco;
    }
    
    public Resultado create(){
        try{
            String insertPacienteSql = "INSERT INTO pacientes (nome, sexo, data_nasc, cpf, telefone, endereco)"
                                        + "VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement insertPacienteStatement = ConnectionClass.getPreparedStatement(insertPacienteSql, Statement.RETURN_GENERATED_KEYS);
            
            String[] dataNascPieces = this.getDataNasc().split("/");
            String newDate = dataNascPieces[2]+"-"+dataNascPieces[1]+"-"+dataNascPieces[0];
            
            insertPacienteStatement.setString(1, this.getNome());
            insertPacienteStatement.setString(2, this.getSexo());
            insertPacienteStatement.setDate(3, Date.valueOf(newDate));
            insertPacienteStatement.setString(4, this.getCpf());
            insertPacienteStatement.setString(5, this.getTelefone());
            insertPacienteStatement.setString(6, this.getEndereco());
            
            insertPacienteStatement.execute();
            
            ResultSet insertPacienteResult = insertPacienteStatement.getGeneratedKeys();
            insertPacienteResult.next();
            
            int key = insertPacienteResult.getInt(1);
            insertPacienteStatement.close();
            return new Resultado(true, "Sucesso", key);
        }
        catch(SQLException e){
            System.err.println("Não foi possível criar um paciente: "+ e.getMessage());
            return new Resultado(false, "Não foi possível criar um paciente. Tente novamente.\n" + e.getMessage());
        }
    }
}
