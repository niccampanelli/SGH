package projetoa3.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import projetoa3.util.Resultado;
import projetoa3.util.database.ConnectionClass;

/**
 * Classe modelo de exames
 * @author Nicholas Campanelli
 */
public class ExameModel {
    
    private int id;
    private int idConsulta;
    private String titulo;
    private String descricao;
    private String resultado;

    public ExameModel(){
        
    }
    
    public ExameModel(
            int idConsulta, String titulo,
            String descricao, String resultado
    ){
        this.setIdConsulta(idConsulta);
        this.setTitulo(titulo);
        this.setDescricao(descricao);
        this.setResultado(resultado);
    }
    
    public ExameModel(
            int id,
            int idConsulta, String titulo,
            String descricao, String resultado
    ){
        this.setId(id);
        this.setIdConsulta(idConsulta);
        this.setTitulo(titulo);
        this.setDescricao(descricao);
        this.setResultado(resultado);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if(titulo.length() <= 255)
            this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    public Resultado create(){
        try{
            String insertExameSql = "INSERT INTO exames (id_consulta, titulo, descricao, resultado) VALUES"
                                        + "(?, ?, ?, ?);";
            PreparedStatement insertExameStatement = ConnectionClass.getPreparedStatement(insertExameSql, Statement.RETURN_GENERATED_KEYS);
            
            insertExameStatement.setInt(1, idConsulta);
            insertExameStatement.setString(2, titulo);
            insertExameStatement.setString(3, descricao);
            insertExameStatement.setString(4, resultado);
            
            insertExameStatement.execute();
            
            ResultSet insertExameResult = insertExameStatement.getGeneratedKeys();
            insertExameResult.next();
            
            int key = insertExameResult.getInt(1);
            insertExameStatement.close();
            return new Resultado(true, "Sucesso", key);
        }
        catch(SQLException e){
            return new Resultado(false, "Erro inesperado ao cadastrar exame: "+e.getMessage());
        }
    }
    
    public Resultado update(){
        try{
            String updateExameSql = "UPDATE exames SET descricao = ?, resultado = ? WHERE id = '"+this.getId()+"';";
            PreparedStatement updateExameStatement = ConnectionClass.getPreparedStatement(updateExameSql);
            
            updateExameStatement.setString(1, this.getDescricao());
            updateExameStatement.setString(2, this.getResultado());
            
            if(updateExameStatement.executeUpdate() == 0){
                return new Resultado(false, "Não foi possivel atualizar o exame. Tente novamente.");
            }
            
            updateExameStatement.close();
            return new Resultado(true, "Sucesso");
        }
        catch(SQLException e){
            System.err.println("Não foi possível atualizar o exame: "+ e.getMessage());
            return new Resultado(false, "Não foi possivel atualizar o exame. Tente novamente. \n"+ e.getMessage());
        }
    }
}
