public class RecursoCompartilhado {
    private int leitoresAtivos = 0;
    private boolean escritorAtivo = false;

    public synchronized void ler(int id) {
        try {
            while (escritorAtivo) {
                wait();
            }
            leitoresAtivos++;
            System.out.println("Leitor " + id + " está lendo.");
            Thread.sleep(1000);
            System.out.println("Leitor " + id + " terminou de ler.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            synchronized (this) {
                leitoresAtivos--;
                if (leitoresAtivos == 0) {
                    notifyAll();
                }
            }
        }
    }

    public synchronized void escrever(int id) {
        try {
            while (leitoresAtivos > 0 || escritorAtivo) {
                wait();
            }
            escritorAtivo = true;
            System.out.println("Escritor " + id + " está escrevendo.");
            Thread.sleep(1500);
            System.out.println("Escritor " + id + " terminou de escrever.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            synchronized (this) {
                escritorAtivo = false;
                notifyAll();
            }
        }
    }
}
