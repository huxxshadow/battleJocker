package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            Scanner consoleReader = new Scanner(System.in);

            String userInput;
            while (true) {
                System.out.print("请输入消息: ");
                userInput = consoleReader.nextLine();  // 读取用户输入
                writer.write(userInput + "\n");  // 发送用户输入的消息
                writer.flush();  // 刷新输出流，确保消息被立即发送
                System.out.println("服务器回应: " + reader.readLine());  // 读取服务器的响应
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
