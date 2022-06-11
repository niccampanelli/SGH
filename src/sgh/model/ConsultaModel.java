package sgh.model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sgh.util.Resultado;
import sgh.util.database.ConnectionClass;

/**
 * Classe modelo de consultas
 * @author Nicholas Campanelli
 */
public class ConsultaModel {
    
    private int id;
    private int idMedico;
    private int idPaciente;
    private String dataCad;
    private String data;
    private String hora;
    private String dataAtu;

    public ConsultaModel(){
        
    }
    
    public ConsultaModel(
            int idMedico, int idPaciente,
            String data, String hora
    ){
        this.setIdMedico(idMedico);
        this.setIdPaciente(idPaciente);
        this.setData(data);
        this.setHora(hora);
    }
    
    public ConsultaModel(
            int id,
            int idMedico, int idPaciente,
            String data, String hora
    ){
        this.setId(id);
        this.setIdMedico(idMedico);
        this.setIdPaciente(idPaciente);
        this.setData(data);
        this.setHora(hora);
    }
    
    public ConsultaModel(
            int idMedico, int idPaciente,
            String dataCad, String data,
            String hora, String dataAtu
    ){
        this.setIdMedico(idMedico);
        this.setIdPaciente(idPaciente);
        this.setDataCad(dataCad);
        this.setData(data);
        this.setHora(hora);
        this.setDataAtu(dataAtu);
    }
    
    public ConsultaModel(
            int id,
            int idMedico, int idPaciente,
            String dataCad, String data,
            String hora, String dataAtu
    ){
        this.setId(id);
        this.setIdMedico(idMedico);
        this.setIdPaciente(idPaciente);
        this.setDataCad(dataCad);
        this.setData(data);
        this.setHora(hora);
        this.setDataAtu(dataAtu);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getDataCad() {
        return dataCad;
    }

    public void setDataCad(String dataCad) {
        if(dataCad.length() == 10)
            this.dataCad = dataCad;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        if(data.length() == 10)
            this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        if(hora.length() == 5)
            this.hora = hora;
    }

    public String getDataAtu() {
        return dataAtu;
    }

    public void setDataAtu(String dataAtu) {
        this.dataAtu = dataAtu;
    }
    
    public Resultado create(){
        
        try{
            String insertConsultaSql = "INSERT INTO consultas (id_medico, id_paciente, data, hora) VALUES"
                                            + "(?, ?, ?, ?)";
            PreparedStatement insertConsultaStatement = ConnectionClass.getPreparedStatement(insertConsultaSql, Statement.RETURN_GENERATED_KEYS);
            
            String[] dataPieces = this.getData().split("/");
            String newDate = dataPieces[2] + "-" + dataPieces[1] + "-" + dataPieces[0];
            
            insertConsultaStatement.setInt(1, idMedico);
            insertConsultaStatement.setInt(2, idPaciente);
            insertConsultaStatement.setDate(3, Date.valueOf(newDate));
            insertConsultaStatement.setString(4, hora);
            
            insertConsultaStatement.execute();
            
            ResultSet insertConsultaResult = insertConsultaStatement.getGeneratedKeys();
            insertConsultaResult.next();
            
            int key = insertConsultaResult.getInt(1);
            insertConsultaStatement.close();
            return new Resultado(true, "Sucesso", key);
        }
        catch(SQLException e){
            System.err.println("Erro ao inserir consulta: "+e.getMessage());
            return new Resultado(false, "Erro ao inserir consulta: "+e.getMessage());
        }
    }
    
    public Resultado update(){
        
        try{
            String updateConsultaSql = "UPDATE consultas SET id_medico = ?, data = ?, hora = ? WHERE id = "+this.getId()+"";
            PreparedStatement updateConsultaStatement = ConnectionClass.getPreparedStatement(updateConsultaSql);
            
            String[] dataPieces = this.getData().split("/");
            String newDate = dataPieces[2] + "-" + dataPieces[1] + "-" + dataPieces[0];
            
            updateConsultaStatement.setInt(1, this.getIdMedico());
            updateConsultaStatement.setDate(2, Date.valueOf(newDate));
            updateConsultaStatement.setString(3, this.getHora());
            
            if(updateConsultaStatement.executeUpdate() == 0){
                System.err.println("Erro inesperado ao atualizar consulta");
                return new Resultado(false, "Erro inesperado ao atualizar consulta. Tente novamente.");
            }
            
            updateConsultaStatement.close();
            return new Resultado(true, "Sucesso");
        }
        catch(SQLException e){
            System.err.println("Erro ao atualizar consulta: "+e.getMessage());
            return new Resultado(false, "Erro ao atualizar consulta: "+e.getMessage());
        }
    }
}
