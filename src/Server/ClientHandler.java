package Server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader reader;

    private BufferedWriter writer;
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            // 创建输入输出流，用于与客户端进行通信
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String command;
            // 不断读取客户端发送的消息
            while ((command = reader.readLine()) != null) {
                System.out.println("收到客户端指令: " + command+ " from: " + clientSocket.getInetAddress());
                writer.write("已收到指令: " + command + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭客户端连接
                System.out.println("客户端断开连接: " + clientSocket.getInetAddress());
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
