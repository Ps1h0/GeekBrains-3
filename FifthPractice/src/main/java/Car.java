import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Car implements Runnable {

    private static int CARS_COUNT;
    private static final AtomicBoolean isWinner = new AtomicBoolean(false);
    private final CyclicBarrier cyclicBarrier;

    static {
        CARS_COUNT = 0;
    }

    private final Race race;
    private final int speed;
    private final String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier cyclicBarrier) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");

            cyclicBarrier.await();
            cyclicBarrier.await();

            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }

            if (isWinner.compareAndSet(false, true)){
                System.out.println("Победитель - " + name);
            }

            cyclicBarrier.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}