import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class LeitoresEscritoresBarreiras {

    public static void main(String[] args) {
        RecursoCompartilhado recurso = new RecursoCompartilhado();
        Random random = new Random();

        // Barreiras para sincronizar 5 leitores e 2 escritores
        CyclicBarrier barreiraLeitores = new CyclicBarrier(5, () -> {
            System.out.println("Todos os leitores finalizaram sua leitura. Escritores podem prosseguir.\n");
        });

        CyclicBarrier barreiraEscritores = new CyclicBarrier(2, () -> {
            System.out.println("Todos os escritores finalizaram sua escrita. Leitores podem prosseguir.\n");
        });

        // Threads leitoras
        for (int i = 1; i <= 5; i++) {
            final int id = i;
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    recurso.ler(id);
                    aguardarBarreira(barreiraLeitores);
                }
            }, "Leitor " + id).start();
        }

        // Threads escritoras
        for (int i = 1; i <= 2; i++) {
            final int id = i;
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    recurso.escrever(id);
                    aguardarBarreira(barreiraEscritores);
                }
            }, "Escritor " + id).start();
        }
    }

    private static void aguardarBarreira(CyclicBarrier barreira) {
        try {
            barreira.await(); // Espera até que todas as threads associadas cheguem à barreira
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            System.err.println("Erro ao aguardar barreira: " + e.getMessage());
        }
    }
}
