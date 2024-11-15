import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RecursoCompartilhado {
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();  // Novo lock de leitura e escrita
    private final ReentrantReadWriteLock.ReadLock lockLeitura = rwLock.readLock();  // Lock de leitura
    private final ReentrantReadWriteLock.WriteLock lockEscrita = rwLock.writeLock();  // Lock de escrita

    // Método para leitura
    public void leitor(int id) {
        lockLeitura.lock(); // Adquire o lock de leitura
        try {
            System.out.println(Thread.currentThread().getName() + " está lendo.");
            Thread.sleep(1000); // Simula tempo de leitura
            System.out.println(Thread.currentThread().getName() + " terminou de ler.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lockLeitura.unlock(); // Libera o lock de leitura
        }
    }

    // Método para escrita
    public void escritor(int id) {
        lockEscrita.lock(); // Adquire o lock de escrita
        try {
            System.out.println(Thread.currentThread().getName() + " está escrevendo.");
            Thread.sleep(1500); // Simula tempo de escrita
            System.out.println(Thread.currentThread().getName() + " terminou de escrever.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lockEscrita.unlock(); // Libera o lock de escrita
        }
    }
}
