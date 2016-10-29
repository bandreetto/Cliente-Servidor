package clienteServidor;

import java.net.*;
import java.io.*;
import java.util.*;

public class ConexaoWeb {

    DataOutputStream saida;
    DataInputStream input;
    static Socket socket;                    //socket que vai tratar com o cliente.
    static String arqi = "index.html";    //se nao for passado um arquivo, o servidor fornecera a pagina index.html

    //coloque aqui o construtor
    public ConexaoWeb(Socket s) {
        socket = s;
    }

    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 2525);
        ConexaoWeb conexao = new ConexaoWeb(s);
        conexao.TrataConexao();

    }
    //metodo TrataConexao, aqui serao trocadas informacoes com o Browser...

    public static void TrataConexao() {
        String metodo = "";                //String que vai guardar o metodo HTTP requerido
        String ct;                    //String que guarda o tipo de arquivo: text/html;image/gif....
        String versao = "";            //String que guarda a versao do Protocolo.
        File arquivo;                //Objeto para os arquivos que vao ser enviados.
        String NomeArq;                //String para o nome do arquivo.
        String raiz = ".";                //String para o diretorio raiz.
        String inicio;                //String para guardar o inicio da linha
        String senha_user = "";        //String para armazenar o nome e a senha do usuario
        Date now = new Date();
        try {

            //Coloque aqui o acesso aos streams do socket!

            String answer;
            String sentence;
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Iniciando cliente. ");
            System.out.println("Iniciando conexao com o servidor. ");
            DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
            System.out.println("Conexao estabelecida ");

            System.out.println("Digite sua Requisicao: ");
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');
            answer = inFromServer.readLine();
            System.out.println("Mensagem do servidor: " + answer);

            //Coloque aqui o procedimento para ler toda a mensagem do cliente. Imprima na tela com System.out.println()!

			/* Para a segunda parte, ignore para a primeira!
  			// Enviar o arquivo
			try {
				
			
			
			//Crie aqui o objeto do tipo File
			
			//agora faca a leitura do arquivo.

			//Mande aqui a mensagem para o cliente.
			
			}
			//este catch e para o caso do arquivo nao existir. Mande para o browser uma mensagem de not found, e um texto html!
			catch(IOException e)
			{
				
			}   
			*/

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Fecha o socket.
        try {
            socket.close();
        } catch (IOException e) {
        }
    }

//Funcao que retorna o tipo do arquivo.

    public String TipoArquivo(String nome) {
        if (nome.endsWith(".html") || nome.endsWith(".htm")) return "text/html";
        else if (nome.endsWith(".txt") || nome.endsWith(".java")) return "text/plain";
        else if (nome.endsWith(".gif")) return "image/gif";
        else if (nome.endsWith(".class")) return "application/octed-stream";
        else if (nome.endsWith(".jpg") || nome.endsWith(".jpeg")) return "image/jpeg";
        else return "text/plain";
    }
}
		

