package projetoa3.model;

/**
 * Classe modelo de medico
 * @author Nicholas Campanelli
 */
public class MedicoModel extends UserModel {
    
    private String cadastro;
    private char sexo;
    private int especialidade;

    public String getCadastro() {
        return cadastro;
    }

    public void setCadastro(String cadastro) {
        if(cadastro.length() == 8)
            this.cadastro = cadastro;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public int getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(int especialidade) {
        this.especialidade = especialidade;
    }
}