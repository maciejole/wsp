package wspolbiezne;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

class ProcessExclusionTest {

    @Test
    void processExclusionTest() {
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

}