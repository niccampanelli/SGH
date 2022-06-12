package sgh.controller;

import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import sgh.util.Resultado;
import sgh.util.database.ConnectionClass;

/**
 * Contoller para funções relacionadas à persistência de sessões
 * @author Nicholas Campanelli
 */
public class SessionController {
    
    /**
     * Método controlador para verificar e iniciar uma nova sessão
     * @param address - Endereço de e-mail da conta a ser persistida
     * @param key - Senha da conta a ser persistida
     * @return Resultado - Objeto de resultado contendo um boolean informando se a criação foi bem-sucedida, uma mensagem e um objeto contendo o id do usuário armazenado caso seja sucesso
     */
    public static Resultado create(String address, String key){
        
        // Inicializa uma String com o modelo do dado a ser armazenado
        String filebody = "null;null";
                
        try{
            // Se os campos foram fornecidos corretamente
            if((!"".equals(address)) && (!"".equals(key))){
                // Verifica se existe um usuátio com o e-mail informado
                String checkEmailSql = "SELECT COUNT(*) FROM usuarios WHERE email = '"+address+"';";
                Statement checkEmailStatement = ConnectionClass.getStatement();
                
                // Executa a query e obtém o resultado
                ResultSet checkEmailResult = checkEmailStatement.executeQuery(checkEmailSql);
                checkEmailResult.next(); // Avança para o primeiro registro
                int emailCount = checkEmailResult.getInt("COUNT(*)"); // Pega a quantidade
                checkEmailStatement.close(); // Fecha a conexão com o banco de dados
                
                // Se existir 1 usuário com o e-mail informado
                if(emailCount == 1){
                    // Pega a senha e o id do usuário com o e-amil informado
                    String validatePasswordSql = "SELECT senha, id FROM usuarios WHERE email = '"+address+"' LIMIT 1;";
                    Statement validatePasswordStatement = ConnectionClass.getStatement();
                    
                    // Executa a query e obtém o resultado
                    ResultSet validatePasswordResult = validatePasswordStatement.executeQuery(validatePasswordSql);
                    validatePasswordResult.next(); // Avança para o primeiro registro
                    String passwordResult = validatePasswordResult.getString("senha"); // Pega a senha
                    int idResult = validatePasswordResult.getInt("id"); // Pega o id
                    validatePasswordStatement.close(); // Fecha a conexão com o banco
                    
                    // Inicia dois BigInteger
                    BigInteger bigint = BigInteger.ONE;
                    BigInteger salted = BigInteger.ONE;
                    
                    try{
                        // Obtém uma instância do algorítimo de conversão MD5
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        bigint = new BigInteger(1, md.digest(key.getBytes())); // Criptografa a senha
                        salted = new BigInteger(1, md.digest((bigint.toString(16) + "segredo").getBytes())); // Salt a senha e criptografa de novo
                    }
                    catch(Exception e){
                        System.out.println("Erro na criptografia:");
                        e.printStackTrace();
                        return new Resultado(false, "Erro interno. Tente novamente.", e);
                    }
                    
                    // Se a senha criptografada bate com a senha do banco
                    if(salted.toString(16).equals(passwordResult)){
                        
                        // Cria diretório .config na raiz
                        File directory = new File(".config/");
                        
                        // Se o diretório for criado
                        if(directory.mkdir())
                            System.out.println("Pasta config criada");
                        
                        // Cria um novo arquivo temp
                        File file = new File(".config\\temp.config");
                        
                        // Se o arquivo for criado
                        if(file.createNewFile())
                            System.out.println("Arquivo config criado");

                        // Instancia um objeto FileWriter para escrever no arquivo
                        FileWriter writer = new FileWriter(file);
                        // Define o texto a ser escrito
                        filebody = address + ";" + bigint.toString(16);
                        
                        // Escreve no arquivo
                        writer.write(filebody);
                        writer.close(); // Fecha o FileWriter
                        
                        System.out.println("Arquivo config salvo");
                        return new Resultado(true, "Sucesso", idResult);
                    }
                    else{
                        System.err.println("Erro ao realizar login: Senha inválida.");
                        return new Resultado(false, "Senha inválida.");
                    }
                }
                else{
                    System.err.println("Erro ao realizar login: Email não cadastrado.");
                    return new Resultado(false, "E-mail não cadastrado.");
                }
            }
            else{
                return new Resultado(false, "E-mail senha devem ser preenchidos.");
            }
        }
        catch(Exception e){
            System.out.println("Erro no arquivo:");
            e.printStackTrace();
            return new Resultado(false, "Erro ao criar arquivo temp. Tente novamente.", e);
        }
    }
    
    /**
     * Método controlador para validar uma sessão
     * @return Resultado - Objeto de resultado contendo um boolean informando se obteve sucesso ou não, uma mensagem e o corpo com o id do usuário logado
     */
    public static Resultado validateTemp(){
        
        // Inicia uma array de string com duas posições
        String[] data = new String[2];
        
        try{
            // Chama o método para ler o arquivo temp e salva o resultado
            data = SessionController.read();
            
            // Se a array com os dados estiver válida
            if(data.length == 2 && (data[0] != null && !"".equals(data[0])) && (data[1] != null && !"".equals(data[1]))){
                
                // Atribui os dados da array
                String address = data[0];
                String key = data[1];
                
                // Verifica se existe um usuário com o e-mail informado
                String checkEmailSql = "SELECT COUNT(*) FROM usuarios WHERE email = '"+address+"';";
                Statement checkEmailStatement = ConnectionClass.getStatement();
                
                // Executa a query e salva o resultados
                ResultSet checkEmailResult = checkEmailStatement.executeQuery(checkEmailSql);
                checkEmailResult.next(); // Avança para o primeiro registro
                int emailCount = checkEmailResult.getInt("COUNT(*)"); // Obtém a quantidade
                checkEmailStatement.close(); // Fecha a conexão com o banco
                
                // Se a quantidade de usuários for 1
                if(emailCount == 1){
                    // Obtém a senha e o id do usuário com o e-mail informado
                    String validatePasswordSql = "SELECT senha, id FROM usuarios WHERE email = '"+address+"' LIMIT 1;";
                    Statement validatePasswordStatement = ConnectionClass.getStatement();
                    
                    // Executa a query e obtém o resultado
                    ResultSet validatePasswordResult = validatePasswordStatement.executeQuery(validatePasswordSql);
                    validatePasswordResult.next(); // Avança para o primeiro registro
                    String passwordResult = validatePasswordResult.getString("senha"); // Pega a senha
                    int idResult = validatePasswordResult.getInt("id"); // Pega o id
                    validatePasswordStatement.close(); // Fecha a conexão com o banco
                    
                    // Inicia um BigInt
                    BigInteger salted = BigInteger.ONE;
                    
                    try{
                        // Obtém uma instancia do algoritimo de criptografia MD5
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        // Criptografa a senha do arquivo armazenado
                        salted = new BigInteger(1, md.digest((key + "segredo").getBytes()));
                    }
                    catch(Exception e){
                        System.out.println("Erro na criptografia:");
                        e.printStackTrace();
                        return new Resultado(false, "Erro interno. Tente novamente.");
                    }
                    
                    // Se a senha criptografada bate com a senha do banco
                    if(salted.toString(16).equals(passwordResult)){
                        // Retorna o resultado com o id do usuário
                        return new Resultado(true, "Sucesso", idResult);
                    }
                    else{
                        System.err.println("Erro ao realizar login: Senha inválida.");
                        return new Resultado(false, "Erro interno. Tente novamente.");
                    }
                }
                else{
                    System.err.println("Erro ao realizar login: Email não cadastrado.");
                    return new Resultado(false, "Erro interno. Tente novamente.");
                }
            }
            else{
                return new Resultado(false, "Erro interno. Tente novamente.");
            }
        }
        catch(Exception e){
            System.err.println("Erro no login:");
            e.printStackTrace();
            return new Resultado(false, "Erro interno. Tente novamente. "+e.getMessage());
        }
    }
    
    /**
     * Método controlador para ler o arquivo temp
     * @return String[] - Array de strings contendo o e-mail na primeira posição e a senha criptografada na segunda
     */
    public static String[] read(){
        
        // Inicia uma array de strings vazia
        String[] data = new String[2];
        
        try{
            // Especifica o caminho do arquivo temp
            File file = new File(".config\\temp.config");
            Scanner scn = new Scanner(file); // Instancia um leitor
            
            // Lê o arquivo, separa os dados e armazena na array
            String[] body = scn.nextLine().split(";");
            
            // Armazena os dados em suas posições
            data[0] = body[0];
            data[1] = body[1];
            scn.close(); // Fecha o leitor
        }
        catch(Exception e){
            System.out.println("Não foi possível obter o arquivo de config:");
            e.printStackTrace();
        }
        
        // Retorna os dados
        return data;
    }
    
    /**
     * Método controlador para excluir uma sessão
     * @return Boolean - Boleano se verdadeiro significa que a sessão foi removida, se falso significa que algo errado aconteceu
     */
    public static boolean delete(){
        
        try{
            // Especifica o caminho do arquivo temp
            File file = new File(".config\\temp.config");
            
            // Se for possível excluir o arquivo
            if(file.delete()){
                System.out.println("Sessão encerrada com sucesso.");
                return true;
            }
            else{
                System.err.println("Falha ao encerrar sessão.");
                return false;
            }
        }
        catch(Exception e){
            System.out.println("Não foi possível excluir o arquivo config:");
            e.printStackTrace();
            return false;
        }
    }
}