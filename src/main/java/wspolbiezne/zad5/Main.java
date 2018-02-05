package wspolbiezne.zad5;

public class Main {
    public static void main(String[] args) {

        final int READERS_COUNT  = 10;
        final int WRITERS_COUNT = 5;

        Reader[] readers = new Reader[READERS_COUNT ];
        Writer[] writers = new Writer[WRITERS_COUNT];

        Book book = new Book();
        for (int i = 0; i < readers.length; i++) {
            readers[i] = new Reader((long) i, book);
            readers[i].start();
            if (i < writers.length) {
                writers[i] = new Writer((long) i, book);
                writers[i].start();
            }
        }
    }
}
