package clienteServidor;

import java.net.*;
import java.io.*;
import java.util.*;

public class ConexaoWeb {

    DataOutputStream saida;
    DataInputStream input;
    static Socket socket;                    //socket que vai tratar com o cliente.
    static String arqi;    //se nao for passado um arquivo, o servidor fornecera a pagina index.html
    private String metodo;
    private String versao;
    private String raiz;
    private File arquivo;
    private byte[] saidaByte;

    //coloque aqui o construtor
    public ConexaoWeb(Socket s, String raiz) {
        socket = s;
        this.raiz = raiz;
    }

    //metodo TrataConexao, aqui serao trocadas informacoes com o Browser...

    public void TrataConexao() {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Conexao estabelecida com: " + socket.getInetAddress().getHostAddress());

            saida = new DataOutputStream(socket.getOutputStream());

            String clientSentence = inFromClient.readLine();
            System.out.println("Recebido: " + clientSentence);

            StringTokenizer st = new StringTokenizer(clientSentence);

            metodo = st.nextToken();
            arqi = st.nextToken();
            versao = st.nextToken();

            switch (metodo) {
                case "GET":
                    GET();
                    break;
                default:
                    arquivo = new File(raiz, "badRequest.html");
                    break;
            }

            saida.write(saidaByte);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Servidor foi abortardo");
        }
    }


    private void GET() throws IOException {
        if (arqi.equals("/"))
            arqi = "/" + "index.html";
        try {
            arquivo = new File(raiz, arqi.substring(1, arqi.length()));
            FileInputStream fis = new FileInputStream(arquivo);
            saidaByte = new byte[(int) arquivo.length()];
            fis.read(saidaByte);
        } catch (IOException e) {
            arquivo = new File(raiz, "notFound.html");
            FileInputStream fis = new FileInputStream(arquivo);
            saidaByte = new byte[(int) arquivo.length()];
            fis.read(saidaByte);
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
		

