package Server;

import Server.Controller.RoundController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    private int port;
    private RoundController roundController;

    public MyServer(int port) {
        this.port = port;
        this.roundController = new RoundController();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();  // 等待客户端连接
                System.out.println("新客户端连接: " + clientSocket.getInetAddress());

                // 启动一个新线程处理每个客户端的请求
                new Thread(new ClientHandler(clientSocket)).start();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        MyServer server = new MyServer(8080);
        server.start();
    }
}
