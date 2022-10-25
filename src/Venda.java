import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Venda {
    private LocalDate data;
    private Produto produto;
    private int quantidade;

    public Venda() {
        this.data = LocalDate.now();
    }

    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public Double getValorTotal() {
        return this.produto.getValor() * this.quantidade;
    }

    @Override
    public String toString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("Data da venda: %s\nNome do produto: %s\nQuantidade: %d\nValor unitário: %f\nValor total: %f",
            this.data.format(df), this.produto.getNome(), this.quantidade, this.produto.getValor(), getValorTotal());
    }
}
