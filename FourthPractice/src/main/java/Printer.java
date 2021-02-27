public class Printer {

    private final Object monitor = new Object();
    private volatile char letter = 'A';

    public static void main(String[] args) {

        Printer printer = new Printer();
        Thread print1 = new Thread(printer::printA);
        Thread print2 = new Thread(printer::printB);
        Thread print3 = new Thread(printer::printC);

        print1.start();
        print2.start();
        print3.start();
    }

    private void printA() {
        synchronized (monitor){
            try{
                for (int i = 0; i < 5; i++){
                    while (letter != 'A'){
                        monitor.wait();
                    }
                    System.out.print("A");
                    letter = 'B';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printB() {
        synchronized (monitor){
            try{
                for (int i = 0; i < 5; i++){
                    while (letter != 'B'){
                        monitor.wait();
                    }
                    System.out.print("B");
                    letter = 'C';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printC() {
        synchronized (monitor){
            try{
                for (int i = 0; i < 5; i++){
                    while (letter != 'C'){
                        monitor.wait();
                    }
                    System.out.print("C");
                    letter = 'A';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
