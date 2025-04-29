package core.basesyntax;

import java.util.concurrent.RecursiveTask;

public class MyTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;

    private int startPoint;

    private int finishPoint;

    public MyTask(int startPoint, int finishPoint) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected Long compute() {
        if (startPoint >= finishPoint) {
            return 0L;
        }

        if (finishPoint - startPoint <= THRESHOLD) {
            System.out.printf("RecursiveTask:Splitting the Task where start = %s , and end = %s "
                    + "Thread:%s%n", startPoint, finishPoint, Thread.currentThread().getName());
            return consecutiveSum(startPoint, finishPoint);

        }
        int middle = (startPoint + finishPoint) / 2;
        MyTask leftTask = new MyTask(startPoint, middle);
        MyTask rightTask = new MyTask(middle, finishPoint);
        leftTask.fork();
        long rightResult = rightTask.compute();
        long leftResult = leftTask.join();
        return leftResult + rightResult;
    }

    private Long consecutiveSum(int startPoint, int finishPoint) {
        Long sum = 0L;
        for (int i = startPoint; i < finishPoint; i++) {
            sum += i;
        }
        return sum;
    }
}
