package app;

import dados.TipoEletronico;
import dados.TipoMesa;
import dados.aluguel.Aluguel;
import dados.aluguel.CatalogoAluguel;
import dados.cliente.*;
import dados.jogo.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ACMEJogos {
    private CatalogoJogos listaDeJogos = new CatalogoJogos();
    private Clientela clientes = new Clientela();
    private CatalogoAluguel listaAluguel = new CatalogoAluguel();


    public void inicializar() {
        cadastrarJogosArquivo();
        cadastrarClientesArquivo();
        cadastrarAlugueisArquivo();
    }

    public void executar() {
        new TelaInicial(listaDeJogos, clientes, listaAluguel);

    }

    private void cadastrarClientesArquivo() {
        Path clientesEntradas = Paths.get("CLIENTESENTRADA.csv");

        try (BufferedReader leitorArquivo = Files.newBufferedReader(clientesEntradas, Charset.defaultCharset())) {
            leitorArquivo.readLine();
            String linha;

            while ((linha = leitorArquivo.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] divisaoDosDados = linha.split(";");

                int numeroCliente = Integer.parseInt(divisaoDosDados[0].trim());
                String nomeCliente = divisaoDosDados[1].trim();
                String endereco = divisaoDosDados[2].trim();
                int tipoCliente = Integer.parseInt(divisaoDosDados[3].trim());

                if (tipoCliente == 1) {
                    String cpf = divisaoDosDados[4].trim();
                    Cliente cliente = new Individual(numeroCliente, nomeCliente, endereco, cpf);
                    clientes.adicionaCliente(cliente);

                } else if (tipoCliente == 2) {
                    String nomeFantasia = divisaoDosDados[4].trim();
                    String cnpj = divisaoDosDados[5].trim();
                    Cliente cliente = new Empresarial(numeroCliente, nomeCliente, endereco, nomeFantasia, cnpj);
                    clientes.adicionaCliente(cliente);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cadastrarJogosArquivo() {
        Path path1 = Paths.get("JOGOSENTRADA.csv");
        try (BufferedReader reader = Files.newBufferedReader(path1,
                Charset.defaultCharset())) {
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] dadosJogo = line.split(";");

                int codigo = Integer.parseInt(dadosJogo[0]);
                String nome = dadosJogo[1];
                double valorBase = Double.parseDouble(dadosJogo[2]);
                int tipoJogo = Integer.parseInt(dadosJogo[3]);
                String tipoStr = dadosJogo[4].trim();

                Jogo novoJogo = null;
                if (tipoJogo == 1) {
                    TipoEletronico tipo = TipoEletronico.valueOf(tipoStr);
                    String plataforma = dadosJogo[5];

                    novoJogo = new JogoEletronico(codigo, nome, valorBase, tipo, plataforma);

                } else if (tipoJogo == 2) {
                    TipoMesa tipo = TipoMesa.valueOf(tipoStr);
                    int numPecas = Integer.parseInt(dadosJogo[5]);

                    novoJogo = new JogoMesa(codigo, nome, valorBase, tipo, numPecas);
                }

                if (novoJogo != null) {
                    if (!listaDeJogos.adicionar(novoJogo)) {
                    } else {
                        listaDeJogos.adicionar(novoJogo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void cadastrarAlugueisArquivo() {
        Path entradaAluguel = Paths.get("ALUGUEISENTRADA.csv");

        try (BufferedReader leitorArquivo = Files.newBufferedReader(entradaAluguel, Charset.defaultCharset())) {
            leitorArquivo.readLine();
            String linha;

            while ((linha = leitorArquivo.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] divisaoDosDados = linha.split(";");

                int identificador = Integer.parseInt(divisaoDosDados[0].trim());
                String dataInicialStr = divisaoDosDados[1].trim();
                int periodo = Integer.parseInt(divisaoDosDados[2].trim());
                int numeroCliente = Integer.parseInt(divisaoDosDados[3].trim());
                int codigoJogo = Integer.parseInt(divisaoDosDados[4].trim());


                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dataInicial = null;
                try {
                    dataInicial = dateFormat.parse(dataInicialStr);
                } catch (ParseException e) {
                    System.out.println("Data inválida");
                }

                Cliente cliente = clientes.getCliente(numeroCliente);
                Jogo jogo = listaDeJogos.getJogo(codigoJogo);
                Aluguel aluguel = new Aluguel(identificador, dataInicial, periodo, jogo, cliente);
                listaAluguel.adicionar(aluguel);
            }
        } catch (IOException e) {
            System.out.println("Erro de leitura de arquivo: " + e.getMessage());
        }
    }


}
