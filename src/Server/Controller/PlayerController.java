package Server.Controller;

import Server.Model.Entity.PlayerEntity;
import Server.Model.Entity.RoundEntity;
import Server.Serializer;

import java.io.*;
import java.net.Socket;

public class PlayerController {

    public PlayerEntity playerEntity;


    public PlayerController(Socket clientSocket) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        playerEntity = new PlayerEntity(clientSocket, reader, writer);
    }

    public void send(String message) {
        try {
            playerEntity.writer.write(message);
            playerEntity.writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void update(RoundEntity roundEntity) {
            String out = Serializer.getInstance().serializeRoundEntity(roundEntity);
            send(out);
    }
}
