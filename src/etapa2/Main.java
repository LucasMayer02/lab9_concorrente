import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        int producerDelay = 1;
        int consumerDelay = 1;
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
                for (int i = 0; i < 10000; i++) {
                    int number = random.nextInt(10) + 1;
                    queue.put(number);
                    System.out.println("[Produtor] Produziu: " + number);
                    Thread.sleep(delay);
                }
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
        try {
            while (true) {
                Integer number = queue.poll(600, TimeUnit.MILLISECONDS);
                if (number == null) {
                    System.out.println("[Consumidor] Timeout de 600ms. Fim do consumo. Fila Vazia. Encerrando");
                    break;
                }
                System.out.println("[Consumidor] Consumiu :" + number);
                Thread.sleep(delay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

