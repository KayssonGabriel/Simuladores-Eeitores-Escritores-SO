public class Mensagem {
    public enum Tipo {LEITURA, ESCRITA}

    private final Tipo tipo;
    private final int id;

    public Mensagem(Tipo tipo, int id) {
        this.tipo = tipo;
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public int getId() {
        return id;
    }

}
