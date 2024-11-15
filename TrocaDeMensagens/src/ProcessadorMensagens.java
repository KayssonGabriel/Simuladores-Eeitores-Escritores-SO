import java.util.concurrent.BlockingQueue;

public class ProcessadorMensagens implements Runnable {
    private final RecursoCompartilhado recurso;
    private final BlockingQueue<Mensagem> filaMensagens;

    public ProcessadorMensagens(RecursoCompartilhado recurso, BlockingQueue<Mensagem> filaMensagens) {
        this.recurso = recurso;
        this.filaMensagens = filaMensagens;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Mensagem mensagem = filaMensagens.take();
                switch (mensagem.getTipo()) {
                    case LEITURA:
                        recurso.ler(mensagem.getId());
                        break;
                    case ESCRITA:
                        recurso.escrever(mensagem.getId());
                        break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Processador de mensagens interrompido.");
        }
    }
}

