package Server;

import Server.Controller.PlayerController;
import Server.Controller.RoundController;
import Server.Controller.RoundRepositoryController;
import Server.Model.Entity.PlayerEntity;
import Server.Model.Entity.RoundEntity;
import Server.Service.GameService;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ClientHandler implements Runnable {
//    private Socket clientSocket;
//    private BufferedReader reader;
//
//    private BufferedWriter writer;

    private PlayerController playerController;

    private RoundController roundController;


    public ClientHandler(Socket clientSocket) {
        try {
            // 创建输入输出流，用于与客户端进行通信
            this.playerController = new PlayerController(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {


            String command;
            while ((command = playerController.playerEntity.reader.readLine()) != null){
                String[] content=command.split(",");
                if (content[0].equals("start")){
                    roundController= RoundRepositoryController.getInstance().joinRound(playerController,content[1],content[2]);
                    if (!roundController.roundEntity.isStarted){
                        Future<?> future = GameService.getInstance().initialize(roundController.roundEntity);
                        future.get();
                    }
                    roundController.updateAllPlayers();
//                    String out = Serializer.getInstance().serializeRoundEntity(roundController.roundEntity);
//                    System.out.println("发送给客户端: " + out);
//                    playerController.send(out);
                    break;
                }
            }

            // 不断读取客户端发送的消息
            while ((command = playerController.playerEntity.reader.readLine()) != null) {
                System.out.println("收到客户端指令: " + command+ " from: " + playerController.playerEntity.clientSocket.getInetAddress());
                Future<?> future=GameService.getInstance().update(roundController.roundEntity, command);
                future.get();
                String out = Serializer.getInstance().serializeRoundEntity(roundController.roundEntity);
                System.out.println("发送给客户端: " + out);
                roundController.updateAllPlayers();
//                roundController.updateAllPlayers();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                // 关闭客户端连接
                System.out.println("客户端断开连接: " + playerController.playerEntity.clientSocket.getInetAddress());
                playerController.playerEntity.clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





}
