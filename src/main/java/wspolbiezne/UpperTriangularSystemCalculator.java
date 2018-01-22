package wspolbiezne;

import java.util.concurrent.Semaphore;

class UpperTriangularSystemCalculator extends Thread {

    private Semaphore semaphore;
    private int processRow;
    private SetOfEquations setOfEquations;

    @Override
    public void run() {
        try {
            semaphore.acquire();
            setOfEquations.x[processRow] = setOfEquations.b[processRow] * setOfEquations.coefficientMatrix[processRow][processRow];
            semaphore.release();
            for (int i = processRow + 1; i < 3; i++) {

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public UpperTriangularSystemCalculator(Semaphore semaphore, SetOfEquations setOfEquations, int processRow) {
        this.semaphore = semaphore;
        this.setOfEquations = setOfEquations;
        this.processRow = processRow;
    }
}