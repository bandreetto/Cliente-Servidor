package clienteServidor;

import java.net.*;
import java.io.*;
import java.util.*;

public class ServidorWeb {

    private int port;
    private ServerSocket sw;
    private String raiz;

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

                ConexaoWeb conexao = new ConexaoWeb(socket, raiz);
                conexao.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Servidor foi abortardo");
        }
    }

    public static void main(String[] args) {
        ServidorWeb servidorWeb = new ServidorWeb(2525, ".");
    }

}