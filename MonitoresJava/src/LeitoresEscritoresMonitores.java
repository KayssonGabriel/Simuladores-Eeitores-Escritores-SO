public class LeitoresEscritoresMonitores {
    public static void main(String[] args) {
        RecursoCompartilhado recurso = new RecursoCompartilhado();

        // Threads leitoras
        for (int i = 1; i <= 5; i++) {
            final int id = i;
            new Thread(() -> recurso.leitor(id), "Leitor " + id).start();
        }

        // Threads escritoras
        for (int i = 1; i <= 2; i++) {
            final int id = i;
            new Thread(() -> recurso.escritor(id), "Escritor " + id).start();
        }
    }
}
