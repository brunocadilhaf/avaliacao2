public class Produto {
    private String codigo;
    private String nome;
    private double valor;
    private int quantidade;

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return String.format("Código: %s\nNome: %s\nValor: %f\nQuantidade: %d",
            this.codigo, this.nome, this.valor, this.quantidade);
    }
}
