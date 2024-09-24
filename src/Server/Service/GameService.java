package Server.Service;


import Server.Model.Entity.RoundEntity;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GameService {

    private static volatile GameService instance;
    private final ExecutorService threadPool;
    private final GameLogicOperatorPool operatorPool;
    // 构造器私有化，外部不能 new
    private GameService(){
        // 使用缓存线程池处理并发任务
        threadPool = Executors.newCachedThreadPool();

        // 创建一个 GameLogicOperator 对象池，池大小为 10
        operatorPool = new GameLogicOperatorPool(10);

    }

    // 静态公有方法，加入双重检查代码
    // 解决线程安全问题，同时解决懒加载问题
    public static synchronized GameService getInstance(){
        if (instance == null){
            synchronized (GameService.class){
                if(instance == null){
                    instance = new GameService();
                }
            }
        }
        return instance;
    }

    public Future<?> update(RoundEntity roundEntity, String direction) {
        return threadPool.submit(() -> {
            synchronized (roundEntity) {
                // 从对象池中获取一个 GameLogicOperator 实例
                GameLogicOperator operator = operatorPool.acquire();
                try {
                    // 设置 RoundEntity 的状态到 GameLogicOperator
                    operator.setAll(roundEntity);

                    operator.moveMerge(direction);

                    operator.getAll(roundEntity);


                } finally {
                    // 将 GameLogicOperator 归还到对象池
                    operatorPool.release(operator);
                }
            }
        });
    }

    public Future<?> initialize(RoundEntity roundEntity) {
        return threadPool.submit(() -> {
            synchronized (roundEntity) {
                // 从对象池中获取一个 GameLogicOperator 实例
                GameLogicOperator operator = operatorPool.acquire();
                try {
                    // 设置 RoundEntity 的状态到 GameLogicOperator
                    operator.setAll(roundEntity);

                    operator.nextRound();

                    operator.getAll(roundEntity);

                } finally {
                    // 将 GameLogicOperator 归还到对象池
                    operatorPool.release(operator);
                }
            }
        });
    }

}
