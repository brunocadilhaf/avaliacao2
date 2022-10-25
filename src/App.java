import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        int opcao;
        Scanner in = new Scanner(System.in);
        List<Produto> listaProdutos = new ArrayList<>();
        List<Venda> listaVendas = new ArrayList<>();

        do {
            System.out.println("\n****\nMENU\n****\n");
            System.out.println("1 - Incluir produto");
            System.out.println("2 - Consultar produto");
            System.out.println("3 - Listagem de produtos");
            System.out.println("4 - Vendas por período - detalhado");
            System.out.println("5 - Realizar venda");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            opcao = in.nextInt();
            in.nextLine(); // Tira o ENTER que ficou na entrada na instrução anterior

            if (opcao == 1) {
                /* Pegando dados do usuário */
                System.out.println("Informe o código do produto:");
                String codigo = in.nextLine();

                System.out.println("Informe o nome do produto:");
                String nome = in.nextLine();

                System.out.println("Informe o valor do produto:");
                double valor = in.nextDouble();

                System.out.println("Informe a quantidade do produto:");
                int quantidade = in.nextInt();

                /* Construindo o nosso objeto */
                Produto prod = new Produto();
                prod.setCodigo(codigo);
                prod.setNome(nome);
                prod.setValor(valor);
                prod.setQuantidade(quantidade);

                /* Adicionar objeto a lista */
                listaProdutos.add(prod);

                voltarMenu(in);
            } else if (opcao == 2) {
                /* Verifica se existem produtos já cadastrados */
                if (listaProdutos.isEmpty()) {
                    System.out.println("Não existem produtos cadastrados!");
                } else {
                    /* Pegando dados do usuário */
                    System.out.println("Informe o código do produto a ser consultado:");
                    String codigo = in.nextLine();
                    Boolean achou = false;

                    /* Localiza produto pelo código */
                    for (Produto prod : listaProdutos) {
                        if (prod.getCodigo().equals(codigo)) {
                            System.out.println(prod);
                            achou = true;
                        }
                    }

                    /* Mensagem de falha caso não encontre */
                    if (!achou)
                        System.out.println("Produto não encontrado!");
                }

                voltarMenu(in);
            } else if (opcao == 3) {
                /* Verifica se existem produtos já cadastrados */
                if (listaProdutos.isEmpty()) {
                    System.out.println("Não existem produtos cadastrados!");
                } else {
                    // Cabeçalho
                    System.out.println("*** Listagem de produtos ***");

                    // Detalhe:
                    for (Produto prod : listaProdutos) {
                        System.out.println("---");
                        System.out.println(prod);
                        System.out.println("---");
                    }

                    // Rodapé
                    DoubleSummaryStatistics resumo = listaProdutos.stream()
                        .collect(Collectors.summarizingDouble(Produto::getValor));
                    
                    System.out.printf("Valor médio: %f\nValor máximo: %f\nValor mínimo: %f",
                        resumo.getAverage(), resumo.getMax(), resumo.getMin());
                }

                voltarMenu(in);
            } else if (opcao == 4) {
                if (listaVendas.isEmpty()) {
                    System.out.println("Não existem vendas cadastradas!");
                } else {
                    System.out.println("*** Listagem de vendas ***");

                    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    Map<LocalDate, List<Venda>> novalista = listaVendas.stream()
                        .collect(Collectors.groupingBy(Venda::getData, Collectors.toList()));

                    novalista.entrySet().forEach(l -> {
                        System.out.printf("Período de emissão: %s\n",
                            l.getKey().format(df));

                        /* Detalhe das vendas por período */
                        for (Venda vd : l.getValue()) {
                            System.out.println("---");
                            System.out.println(vd);
                            System.out.println("---");
                        }

                        /* Rodapé com a média */
                        Double media = l.getValue().stream()
                            .collect(Collectors.averagingDouble(Venda::getValorTotal));

                        System.out.printf("Valor da média: %f", media);
                    });
                }

                voltarMenu(in);
            } else if (opcao == 5) {
                /* Verifica se existem produtos já cadastrados */
                if (listaProdutos.isEmpty()) {
                    System.out.println("Não existem produtos cadastrados!");
                } else {
                    try {
                        Venda vd = new Venda();

                        System.out.println("Informe o código do produto:");
                        String codigo = in.nextLine();
    
                        for (Produto prod : listaProdutos) {
                            if (prod.getCodigo().equals(codigo)) {
                                vd.setProduto(prod);
                            }
                        }
    
                        /* Mensagem de falha caso não encontre */
                        if (vd.getProduto() == null)
                            throw new InputMismatchException("Produto não encontrado!");
    
                        System.out.println("Informe a quantidade do produto:");
                        int quantidade = in.nextInt();
    
                        // Se a quantidade for maior do que a quantidade disponivel do produto
                        if (quantidade > vd.getProduto().getQuantidade()) {
                            throw new InputMismatchException("Quantidade inválida!");
                        } else {
                            vd.setQuantidade(quantidade);
                        }

                        listaVendas.add(vd);
                        vd.getProduto().setQuantidade(vd.getProduto().getQuantidade() - quantidade);

                    } catch (InputMismatchException ex) {
                        System.out.println(ex.getMessage());
                        in.nextLine();
                    }
                    
                }

                voltarMenu(in);
            }
            else if (opcao != 0) {
                System.out.println("\nOpção inválida!");
            }
        } while (opcao != 0);

        System.out.println("Fim do programa!");

        in.close();
    }

    private static void voltarMenu(Scanner in) throws InterruptedException, IOException {
        System.out.println("\nPressione ENTER para voltar ao menu.");
        in.nextLine();

        // Limpa toda a tela, deixando novamente apenas o menu
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            System.out.print("\033[H\033[2J");
        
        System.out.flush();
    }
}
