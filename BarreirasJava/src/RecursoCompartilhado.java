public class RecursoCompartilhado {
    private int leitoresAtivos = 0;
    private boolean escritorAtivo = false;

    public synchronized void ler(int id) {
        try {
            while (escritorAtivo) {
                wait(); // Espera enquanto um escritor estiver ativo
            }
            leitoresAtivos++;
            System.out.println("Leitor " + id + " está lendo.");
            Thread.sleep(500); // Simula o tempo de leitura
            System.out.println("Leitor " + id + " terminou de ler.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            synchronized (this) {
                leitoresAtivos--;
                if (leitoresAtivos == 0) {
                    notifyAll(); // Libera escritores se não houver mais leitores
                }
            }
        }
    }

    public synchronized void escrever(int id) {
        try {
            while (leitoresAtivos > 0 || escritorAtivo) {
                wait(); // Espera enquanto leitores ou outro escritor estiver ativo
            }
            escritorAtivo = true;
            System.out.println("Escritor " + id + " está escrevendo.");
            Thread.sleep(1000); // Simula o tempo de escrita
            System.out.println("Escritor " + id + " terminou de escrever.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            synchronized (this) {
                escritorAtivo = false;
                notifyAll(); // Libera leitores e escritores
            }
        }
    }
}
