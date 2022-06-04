package projetoa3.controller;

import java.sql.Statement;
import projetoa3.model.ExameModel;
import projetoa3.util.Resultado;
import projetoa3.util.database.ConnectionClass;

/**
 *
 * @author Nicholas Campanelli
 */
public class ExameController {
    
    public static Resultado create(
            int idConsulta, String titulo,
            String descricao, String resultado
    ){
        
        try{
            if(!"".equals(titulo)){
                
                ExameModel exame = new ExameModel(
                        idConsulta, titulo, descricao, resultado
                );
                return exame.create();
            }
            else{
                System.err.println("Erro ao cadastrar exame: Campos obrigatórios faltando");
                return new Resultado(false, "Erro ao cadastrar exame: Campos obrigatórios faltando");
            }
        }
        catch(Exception e){
            System.err.println("Erro inesperado ao cadastrar exame.");
            e.printStackTrace();
            return new Resultado(false, "Erro inesperado ao cadastrar exame. Tente novamente.");
        }
    }
}
