package app;

import dados.TipoEletronico;
import dados.TipoMesa;
import dados.aluguel.Aluguel;
import dados.aluguel.CatalogoAluguel;
import dados.cliente.Cliente;
import dados.cliente.Clientela;
import dados.cliente.Empresarial;
import dados.cliente.Individual;
import dados.jogo.CatalogoJogos;
import dados.jogo.Jogo;
import dados.jogo.JogoEletronico;
import dados.jogo.JogoMesa;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GerenciadorArquivos {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void salvarJogos(List<Jogo> listaJogos, String nomeArquivo) throws IOException {
        Path path = Paths.get(nomeArquivo);
        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset())) {
            writer.write("codigo;nome;valorbase;tipoJogo;tipo;plataforma_numeropecas\n");
            for (Jogo jogo : listaJogos) {
                if (jogo instanceof JogoEletronico) {
                    JogoEletronico je = (JogoEletronico) jogo;
                    writer.write(String.format(Locale.US, "%d;%s;%.2f;1;%s;%s\n",
                            je.getCodigo(), je.getNome(), je.getValorBase(), je.getTipo().toString(), je.getPlataforma()));
                } else if (jogo instanceof JogoMesa) {
                    JogoMesa jm = (JogoMesa) jogo;
                    writer.write(String.format(Locale.US, "%d;%s;%.2f;2;%s;%d\n",
                            jm.getCodigo(), jm.getNome(), jm.getValorBase(), jm.getTipo().toString(), jm.getNumeroPecas()));
                }
            }
        }
    }

    public static void salvarClientes(List<Cliente> listaClientes, String nomeArquivo) throws IOException {
        Path path = Paths.get(nomeArquivo);
        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset())) {
            writer.write("numero;nome;endereco;tipoCliente;cpf_nomefantasia;cnpj\n");
            for (Cliente cliente : listaClientes) {
                if (cliente instanceof Individual) {
                    Individual ind = (Individual) cliente;
                    writer.write(String.format("%d;%s;%s;1;%s;\n",
                            ind.getNumero(), ind.getNome(), ind.getEndereco(), ind.getCPF()));
                } else if (cliente instanceof Empresarial) {
                    Empresarial emp = (Empresarial) cliente;
                    writer.write(String.format("%d;%s;%s;2;%s;%s\n",
                            emp.getNumero(), emp.getNome(), emp.getEndereco(), emp.getNomeFantasia(), emp.getCnpj()));
                }
            }
        }
    }

    public static void salvarAlugueis(List<Aluguel> listaAlugueis, String nomeArquivo) throws IOException {
        Path path = Paths.get(nomeArquivo);
        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset())) {
            writer.write("identificador;datainicial;período;numero;codigo\n");
            for (Aluguel aluguel : listaAlugueis) {
                writer.write(String.format("%d;%s;%d;%d;%d\n",
                        aluguel.getIdentificador(),
                        dateFormat.format(aluguel.getDataInicial()),
                        aluguel.getPeriodo(),
                        aluguel.getCliente().getNumero(),
                        aluguel.getJogo().getCodigo()));
            }
        }
    }

    public static void carregarJogos(CatalogoJogos catalogo, String nomeArquivo) throws IOException {
        Path path = Paths.get(nomeArquivo);
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try {
                    String[] dados = line.split(";");
                    int codigo = Integer.parseInt(dados[0]);
                    String nome = dados[1];
                    double valorBase = Double.parseDouble(dados[2].replace(',', '.'));
                    int tipoJogo = Integer.parseInt(dados[3]);
                    String tipoEspecifico = dados[4];

                    Jogo novoJogo = null;
                    if (tipoJogo == 1) {
                        TipoEletronico tipo = TipoEletronico.valueOf(tipoEspecifico);
                        String plataforma = dados[5];
                        novoJogo = new JogoEletronico(codigo, nome, valorBase, tipo, plataforma);
                    } else if (tipoJogo == 2) {
                        TipoMesa tipo = TipoMesa.valueOf(tipoEspecifico);
                        int numPecas = Integer.parseInt(dados[5]);
                        novoJogo = new JogoMesa(codigo, nome, valorBase, tipo, numPecas);
                    }
                    if (novoJogo != null) {
                        catalogo.adicionar(novoJogo);
                    }
                } catch (Exception e) {
                    System.err.println("Aviso: Falha ao ler linha de jogo. Linha ignorada -> " + line);
                }
            }
        }
    }

    public static void carregarClientes(Clientela clientela, String nomeArquivo) throws IOException {
        Path path = Paths.get(nomeArquivo);
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try {
                    String[] dados = line.split(";", -1);
                    int numero = Integer.parseInt(dados[0]);
                    String nome = dados[1];
                    String endereco = dados[2];
                    int tipoCliente = Integer.parseInt(dados[3]);

                    Cliente novoCliente = null;
                    if (tipoCliente == 1) {
                        String cpf = dados[4];
                        novoCliente = new Individual(numero, nome, endereco, cpf);
                    } else if (tipoCliente == 2) {
                        String nomeFantasia = dados[4];
                        String cnpj = dados[5];
                        novoCliente = new Empresarial(numero, nome, endereco, nomeFantasia, cnpj);
                    }
                    if (novoCliente != null) {
                        clientela.adicionaCliente(novoCliente);
                    }
                } catch (Exception e) {
                    System.err.println("Aviso: Falha ao ler linha de cliente. Linha ignorada -> " + line);
                }
            }
        }
    }

    public static void carregarAlugueis(CatalogoAluguel catalogoAluguel, CatalogoJogos catalogoJogos, Clientela clientela, String nomeArquivo) throws IOException, ParseException {
        Path path = Paths.get(nomeArquivo);
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try {
                    String[] dados = line.split(";");
                    int id = Integer.parseInt(dados[0]);
                    Date dataInicial = dateFormat.parse(dados[1]);
                    int periodo = Integer.parseInt(dados[2]);
                    int numeroCliente = Integer.parseInt(dados[3]);
                    int codigoJogo = Integer.parseInt(dados[4]);

                    Cliente cliente = clientela.getCliente(numeroCliente);
                    Jogo jogo = catalogoJogos.getJogo(codigoJogo);

                    if (cliente != null && jogo != null) {
                        Aluguel novoAluguel = new Aluguel(id, dataInicial, periodo, jogo, cliente);
                        catalogoAluguel.adicionar(novoAluguel);
                    } else {
                        System.err.println("Aviso: Não foi possível carregar o aluguel com ID " + id + ". Cliente ou Jogo não encontrado.");
                    }
                } catch (Exception e) {
                    System.err.println("Aviso: Falha ao ler linha de aluguel. Linha ignorada -> " + line);
                }
            }
        }
    }
}
