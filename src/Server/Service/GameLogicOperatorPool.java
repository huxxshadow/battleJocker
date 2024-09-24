package Server.Service;

import java.util.Stack;

public class GameLogicOperatorPool {

    private final Stack<GameLogicOperator> pool;
    private final int maxPoolSize;

    // 构造方法，初始化池的大小
    public GameLogicOperatorPool(int poolSize) {
        this.maxPoolSize = poolSize;
        this.pool = new Stack<>();

        // 预先创建 poolSize 个 GameLogicOperator 实例
        for (int i = 0; i < poolSize; i++) {
            pool.push(new GameLogicOperator());
        }
    }

    /**
     * 从对象池中获取一个 GameLogicOperator 实例
     * @return GameLogicOperator 实例
     */
    public synchronized GameLogicOperator acquire() {
        // 如果池中有对象可用，弹出一个对象
        if (!pool.isEmpty()) {
            return pool.pop();
        }
        // 如果池中没有可用对象，创建一个新的 GameLogicOperator
        return new GameLogicOperator();
    }

    /**
     * 将 GameLogicOperator 实例归还到对象池
     * @param operator GameLogicOperator 实例
     */
    public synchronized void release(GameLogicOperator operator) {
        // 如果池中对象数量未达到最大值，将对象放回池中
        if (pool.size() < maxPoolSize) {
            operator.resetAll();
            pool.push(operator);

        }
    }
}