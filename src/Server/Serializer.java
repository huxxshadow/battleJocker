package Server;

import Server.Model.Entity.RoundEntity;

public class Serializer {

    private static volatile Serializer instance;

    // 构造器私有化，外部不能 new
    private Serializer(){}

    // 静态公有方法，加入双重检查代码
    // 解决线程安全问题，同时解决懒加载问题
    public static synchronized Serializer getInstance(){
        if (instance == null){
            synchronized (Serializer.class){
                if(instance == null){
                    instance = new Serializer();
                }
            }
        }
        return instance;
    }









    public String serializeRoundEntity(RoundEntity roundEntity) {
        StringBuilder sb = new StringBuilder();

        // 序列化 board 数组 (固定大小16)
        for (int i = 0; i < 16; i++) {
            sb.append(roundEntity.getBoard()[i]);
            if (i < 15) {  // 在最后一个元素后不加逗号
                sb.append(",");
            }
        }

        // 添加逗号分隔符，继续序列化其他变量
        sb.append(",");

        // 序列化其他变量
        sb.append(roundEntity.getLevel()).append(",");
        sb.append(roundEntity.getScore()).append(",");
        sb.append(roundEntity.getCombo()).append(",");
        sb.append(roundEntity.getTotalMoveCount()).append(",");
        sb.append(roundEntity.getNumOfTilesMoved()).append(",");
        sb.append(roundEntity.getGameOver()).append("\n");

        return sb.toString();
    }


}

