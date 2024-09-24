package Server.Controller;

import Server.Model.Entity.PlayerEntity;
import Server.Model.Entity.RoundEntity;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class RoundRepositoryController {
        // 本类内部声明对象实例
        private static volatile RoundRepositoryController instance;

        // 构造器私有化，外部不能 new
        private RoundRepositoryController(){}

        // 静态公有方法，加入双重检查代码
        // 解决线程安全问题，同时解决懒加载问题
        public static synchronized RoundRepositoryController getInstance(){
            if (instance == null){
                synchronized (RoundRepositoryController.class){
                    if(instance == null){
                        instance = new RoundRepositoryController();
                    }
                }
            }
            return instance;
        }

        private ConcurrentHashMap<String, RoundController> rounds = new ConcurrentHashMap<>();

        public RoundController joinRound(PlayerController playerController, String playerName,String roomName){
            PlayerEntity pe=playerController.playerEntity;
            String address = pe.clientSocket.getInetAddress() + ":" + pe.clientSocket.getPort();
            System.out.println("玩家地址"+address);

            pe.playerName = playerName;
            if (rounds.containsKey(roomName)){
                rounds.get(roomName).join(playerController);
                System.out.println("加入房间"+roomName);
            }
            else {
                RoundController roundController = new RoundController(playerController);
                rounds.put(roomName,roundController);
                System.out.println("创建房间"+roomName);
            }
            return rounds.get(roomName);
        }




}
