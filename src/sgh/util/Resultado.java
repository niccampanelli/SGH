package sgh.util;

/**
 * Classe de resultado para ser utilizada como valor de retorno dos controllers
 * @author Nicholas Campanelli
 */
public class Resultado {
    
    private boolean sucesso;
    private String mensagem;
    private Object corpo;
    
    public Resultado(boolean sucesso){
        this.setSucesso(sucesso);
    }
    
    public Resultado(boolean sucesso, String mensagem){
        this.setSucesso(sucesso);
        this.setMensagem(mensagem);
    }
    
    public Resultado(boolean sucesso, String mensagem, Object corpo){
        this.setSucesso(sucesso);
        this.setMensagem(mensagem);
        this.setCorpo(corpo);
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Object getCorpo() {
        return corpo;
    }

    public void setCorpo(Object corpo) {
        this.corpo = corpo;
    }
}
