package clienteServidor;

import java.net.*;
import java.io.*;
import java.util.*;

public class ServidorWeb {

    private int port;
    private ServerSocket sw;
    private DataOutputStream saida;
    private DataInputStream entrada;
    private byte[] saidaByte;
    private String metodo;
    private String nomeArquivo;
    private String versao;
    private String raiz;
    private File arquivo;

    public ServidorWeb(int port, String raiz) {
        this.port = port;
        this.raiz = raiz;

        try {

            //crie aqui o socket Servidor!!!!!!!!(objeto = new ServerSocket(parametros))
            System.out.println("Iniciando Servidor. ");
            sw = new ServerSocket(this.port);
            System.out.println("Aguardando conexao");
            while (true) {
                Socket socket = sw.accept();
                BufferedReader inFromClient =
                        new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Conexao estabelecida com: " + socket.getInetAddress().getHostAddress());
                saida =  new DataOutputStream(socket.getOutputStream());
                String clientSentence = inFromClient.readLine();
                System.out.println("Recebido: " + clientSentence);

                StringTokenizer st = new StringTokenizer(clientSentence);

                metodo = st.nextToken();
                nomeArquivo = st.nextToken();
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
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Servidor foi abortardo");
        }
    }

    public static void main(String[] args) {
        ServidorWeb servidorWeb = new ServidorWeb(2525, ".");
    }

    private void GET() throws IOException {
        if (nomeArquivo.equals("/"))
            nomeArquivo = "/" + "index.html";
        try {
            arquivo = new File(raiz, nomeArquivo.substring(1, nomeArquivo.length()));
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
}