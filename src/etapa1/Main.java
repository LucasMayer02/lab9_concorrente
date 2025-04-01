import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {
        int producerDelay = 500;
        int consumerDelay = 300;
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

        Thread producer = new Thread(new Producer(queue, producerDelay));
        Thread consumer = new Thread(new Consumer(queue, consumerDelay));

        producer.start();
        consumer.start(); 
    }

}

class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final int delay;
    private final Random random = new Random();

    public Producer(BlockingQueue<Integer> queue, int delay) {
        this.queue = queue;
        this.delay = delay;
    }

    public void run() {
        while (true) {
            try {
                int number = random.nextInt(10) + 1;
                queue.put(number);
                System.out.println("[Produtor] Produziu: " + number);
                Thread.sleep(delay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final int delay;

    public Consumer(BlockingQueue<Integer> queue, int delay) {
        this.queue = queue;
        this.delay = delay;
    }

    public void run() {
        while (true) {
            try {
                int number = queue.take();
                System.out.println("[Consumidor] Consumiu " + number);
                Thread.sleep(delay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

