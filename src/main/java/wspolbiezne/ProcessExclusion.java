package wspolbiezne;

import java.util.concurrent.Semaphore;

public class ProcessExclusion extends Thread {

    private int threadId;
    private Semaphore semaphore;

    public ProcessExclusion(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public static void main(String[] args) {
        final int numberOfProcesses = 2;
        final int numberOfPermits = 2;

        Semaphore semaphore = new Semaphore(numberOfPermits, true);
        ProcessExclusion[] processExclusions = new ProcessExclusion[numberOfProcesses];

        for (int i = 0; i < numberOfProcesses; i++) {
            processExclusions[i] = new ProcessExclusion(semaphore);
            processExclusions[i].setThreadId(processExclusions[i].hashCode());
            processExclusions[i].start();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            try {
                System.out.println(hashCode() + ": pobiera semafor");
                semaphore.acquire();
                System.out.println(hashCode() + ": pobrał semafor");
                criticalCode();
                semaphore.release();
                System.out.println(hashCode() + ": zwolnił semafor");
            } catch (InterruptedException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
    }

    private int random(int n) {
        return (int) Math.round(Math.random() * n);
    }

    private void busyCode() {
        int sleepPeriod = random(500);
        try {
            sleep(sleepPeriod);
        } catch (InterruptedException e) {

        }
    }

    private void criticalCode() {
        busyCode();
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

}
