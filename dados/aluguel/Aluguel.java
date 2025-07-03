package dados.aluguel;

import dados.cliente.Cliente;
import dados.cliente.Empresarial;
import dados.cliente.Individual;
import dados.jogo.Jogo;
import dados.jogo.JogoEletronico;
import dados.jogo.JogoMesa;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Aluguel {
    private int identificador;
    private Date dataInicial;
    private int periodo;
    private Jogo jogo;
    private Cliente cliente;

    public Aluguel(int identificador, Date data, int periodo, Jogo jogo, Cliente cliente) {
        this.identificador = identificador;
        this.dataInicial = data;
        this.periodo = periodo;
        this.jogo = jogo;
        this.cliente = cliente;
    }

    public double calculaValorFinal() {
        double valorAluguelPeriodo = jogo.calculaAluguel() * periodo;
        double valorFinal = 0.0;

        if (cliente instanceof Individual) {
            if (periodo < 7) {
                valorFinal = 0.9 * valorAluguelPeriodo;
            } else if (periodo <= 14) {
                valorFinal = 0.8 * valorAluguelPeriodo;
            } else {
                valorFinal = 0.75 * valorAluguelPeriodo;
            }
        } else if (cliente instanceof Empresarial) {
            if (jogo instanceof JogoEletronico) {
                valorFinal = valorAluguelPeriodo;
            } else if (jogo instanceof JogoMesa) {
                valorFinal = 0.85 * valorAluguelPeriodo;
            }
        }
        return valorFinal;
    }

    public int getIdentificador() {
        return identificador;
    }

    public String formatarData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dataInicial);
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public int getPeriodo() {
        return periodo;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        return "ID: " + identificador +
                " | Data: " + formatarData() +
                " | Período: " + periodo + " dias" +
                " | Jogo: " + jogo.getNome() +
                " | Cliente: " + cliente.getNome() +
                " | Valor final: R$" + String.format("%.2f", calculaValorFinal());
    }
}
