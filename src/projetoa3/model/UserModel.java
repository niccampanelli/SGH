package projetoa3.model;

/**
 * Classe modelo de usuário
 * @author Nicholas Campanelli
 */
public class UserModel {
    
    private int id;
    private int tipo;
    private String nome;
    private String cpf;
    private String dataNasc;
    private String telefone;
    private String email;
    private String senha;
    private String dataCad;

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

    public String getDataCad() {
        return dataCad;
    }

    public void setDataCad(String dataCad) {
        if(dataCad.length() == 10)
            this.dataCad = dataCad;
    }
}