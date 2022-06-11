package sgh.model;

/**
 * Classe modelo dos tipos de usuários
 * @author Nicholas Campanelli
 */
public class TipoModel {
    
    private int id;
    private String nome;
    
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
}
