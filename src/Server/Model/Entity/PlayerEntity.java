package Server.Model.Entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public class PlayerEntity {

    public String playerName;
    public Socket clientSocket;

    public BufferedReader reader;
    public BufferedWriter writer;
    public PlayerEntity(Socket clientSocket, BufferedReader reader, BufferedWriter writer) {
        this.clientSocket = clientSocket;
        this.reader = reader;
        this.writer = writer;
    }

}
