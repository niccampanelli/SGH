package projetoa3.model;

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
}
