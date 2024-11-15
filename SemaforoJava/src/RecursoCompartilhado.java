import java.util.concurrent.Semaphore;

public class RecursoCompartilhado {
    private int leitoresAtivos = 0; // Contador de leitores ativos
    private final Semaphore semaforoLeitores = new Semaphore(1); // Controle do contador de leitores
    private final Semaphore semaforoEscrita = new Semaphore(1); // Controle da escrita

    // Método para leitores
    public void leitor(int id) {
        try {
            semaforoLeitores.acquire(); // Equivalente ao "down" no semáforo
            leitoresAtivos++;
            if (leitoresAtivos == 1) {
                semaforoEscrita.acquire(); // Primeiro leitor bloqueia escritores
            }
            semaforoLeitores.release(); // Equivalente ao "up" no semáforo

            // Região crítica para leitura
            System.out.println("Leitor " + id + " está lendo.");
            Thread.sleep(1000); // Simula tempo de leitura
            System.out.println("Leitor " + id + " terminou de ler.");

            // Finaliza leitura
            semaforoLeitores.acquire();
            leitoresAtivos--;
            if (leitoresAtivos == 0) {
                semaforoEscrita.release(); // Último leitor libera escritores
            }
            semaforoLeitores.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método para escritores
    public void escritor(int id) {
        try {
            semaforoEscrita.acquire(); // Equivalente ao "down" no semáforo

            // Região crítica para escrita
            System.out.println("Escritor " + id + " está escrevendo.");
            Thread.sleep(1500); // Simula tempo de escrita
            System.out.println("Escritor " + id + " terminou de escrever.");

            semaforoEscrita.release(); // Equivalente ao "up" no semáforo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}