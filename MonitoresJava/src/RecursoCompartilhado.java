public class RecursoCompartilhado {
    private int leitoresAtivos = 0; // Número de leitores ativos
    private boolean escritorAtivo = false; // Indica se há um escritor ativo

    // Método para leitura
    public synchronized void leitor(int id) {
        try {
            // Espera enquanto há um escritor ativo
            while (escritorAtivo) {
                wait();
            }
            leitoresAtivos++; // Incrementa o número de leitores

            // Região crítica para leitura
            System.out.println(Thread.currentThread().getName() + " está lendo.");
            Thread.sleep(1000); // Simula tempo de leitura
            System.out.println(Thread.currentThread().getName() + " terminou de ler.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            synchronized (this) {
                leitoresAtivos--;
                if (leitoresAtivos == 0) {
                    notifyAll(); // Notifica os escritores, se necessário
                }
            }
        }
    }

    // Método para escrita
    public synchronized void escritor(int id) {
        try {
            // Espera enquanto há leitores ou um escritor ativo
            while (leitoresAtivos > 0 || escritorAtivo) {
                wait();
            }
            escritorAtivo = true;

            // Região crítica para escrita
            System.out.println(Thread.currentThread().getName() + " está escrevendo.");
            Thread.sleep(1500); // Simula tempo de escrita
            System.out.println(Thread.currentThread().getName() + " terminou de escrever.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            synchronized (this) {
                escritorAtivo = false;
                notifyAll(); // Notifica leitores e escritores
            }
        }
    }
}
