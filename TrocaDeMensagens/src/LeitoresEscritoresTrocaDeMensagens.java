import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LeitoresEscritoresTrocaDeMensagens {

    public static void main(String[] args) {
        BlockingQueue<Mensagem> filaMensagens = new ArrayBlockingQueue<>(50);
        RecursoCompartilhado recurso = new RecursoCompartilhado();

        // Processador de mensagens
        Thread processador = new Thread(new ProcessadorMensagens(recurso, filaMensagens));
        processador.start();

        // Threads leitoras
        for (int i = 1; i <= 5; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    filaMensagens.put(new Mensagem(Mensagem.Tipo.LEITURA, id));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Leitor " + id).start();
        }

        // Threads escritoras
        for (int i = 1; i <= 2; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    filaMensagens.put(new Mensagem(Mensagem.Tipo.ESCRITA, id));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Escritor " + id).start();
        }
    }
}

