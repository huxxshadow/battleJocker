package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private GameEngine gameEngine;


    public MyClient(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }
    public void connect(){
        try {
            socket = new Socket("localhost", 8080);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            writer.write("start,player1,room2\n");
            writer.flush();
//            String response = reader.readLine();
            new Thread(this::listenToServer).start();
//            System.out.println("Response from server: " + response);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMove(String dir){

        try {
            writer.write(dir + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 持续监听服务器的消息
    private void listenToServer() {
        try {
            String response;
            while ((response = reader.readLine()) != null) {
                System.out.println("更新 " + response);
                handleServerUpdate(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 处理从服务器接收到的数据
    private void handleServerUpdate(String response) {
        gameEngine.setAll(response.split(","));
    }
}
