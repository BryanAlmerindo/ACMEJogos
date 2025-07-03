package app;

import dados.aluguel.CatalogoAluguel;
import dados.aluguel.TelaCadastroAluguel;
import dados.cliente.Clientela;
import dados.cliente.TelaCadastroCliente;
import dados.jogo.CatalogoJogos;
import dados.jogo.TelaCadastroJogo;
import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JFrame {
    private JButton botaoCadastrarJogo, botaoCadastrarCliente, botaoCadastrarAluguel, botaoRelatorioJogos, botaoRelatorioCliente,
            botaoRelatorioAluguel, botaoRemoverDadosAluguel, botaoAlterarDadosCliente, botaoSalvarArquivo, botaoCarregarArquivo, botaoSair;

    public TelaInicial(CatalogoJogos listaJogos, Clientela clientes, CatalogoAluguel catalogoAluguel) {
        super();
        setTitle("Sistema ACMEJogos");
        setSize(650, 300);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("Bem-vindo ao Sistema da ACMEJogos!");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel painelBotoesCadastro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel painelBotoesRelatorios = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel painelSeparacaoBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel painelDados = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel painelArquivo = new JPanel(new FlowLayout(FlowLayout.CENTER));

        botaoCadastrarJogo = new JButton("Cadastrar Jogo");
        botaoCadastrarCliente = new JButton("Cadastrar Cliente");
        botaoCadastrarAluguel = new JButton("Cadastrar Aluguel");
        botaoRelatorioCliente = new JButton("Relatório Clientes");
        botaoRelatorioJogos = new JButton("Relatório Jogos");
        botaoRelatorioAluguel = new JButton("Relatório Aluguel");
        botaoRemoverDadosAluguel = new JButton("Remover Aluguel");
        botaoAlterarDadosCliente = new JButton("Alterar Dados Cliente");
        botaoSalvarArquivo = new JButton("Salvar Dados");
        botaoCarregarArquivo = new JButton("Carregar Dados");


        botaoSair = new JButton("Sair");

        botaoCadastrarJogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoCadastrarCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoCadastrarAluguel.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoSair.setAlignmentX(Component.CENTER_ALIGNMENT);

        botaoCadastrarJogo.addActionListener(e -> {
            new TelaCadastroJogo(listaJogos);
        });

        botaoCadastrarCliente.addActionListener(e -> {
            new TelaCadastroCliente(clientes);
        });

        botaoCadastrarAluguel.addActionListener(e -> {
            new TelaCadastroAluguel(catalogoAluguel, listaJogos, clientes);
        });

        botaoRelatorioJogos.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, listaJogos.relatorioJogos());
        });

        botaoRelatorioCliente.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, clientes.relatorioClientes());
        });

        botaoRelatorioAluguel.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, catalogoAluguel.relatorioAlugueis());
        });

        botaoRemoverDadosAluguel.addActionListener(e -> {
            new TelaRemoverDados(catalogoAluguel);
        });

        botaoAlterarDadosCliente.addActionListener(e -> {
            new TelaAlterarDados(clientes);
        });

        botaoSalvarArquivo.addActionListener(e -> {
            new TelaSalvarDados(listaJogos, clientes, catalogoAluguel);
        });

        botaoCarregarArquivo.addActionListener(e -> {
            new TelaCarregarDados(listaJogos, clientes, catalogoAluguel);
        });

        botaoSair.addActionListener(e -> {
            System.exit(0);
        });

        add(Box.createVerticalGlue());
        add(titulo);
        add(Box.createVerticalStrut(20));

        painelBotoesCadastro.add(botaoCadastrarJogo);
        painelBotoesCadastro.add(botaoCadastrarCliente);
        painelBotoesCadastro.add(botaoCadastrarAluguel);

        painelBotoesRelatorios.add(botaoRelatorioJogos);
        painelBotoesRelatorios.add(botaoRelatorioCliente);
        painelBotoesRelatorios.add(botaoRelatorioAluguel);

        painelDados.add(botaoAlterarDadosCliente);
        painelDados.add(botaoRemoverDadosAluguel);

        painelArquivo.add(botaoCarregarArquivo);
        painelArquivo.add(botaoSalvarArquivo);

        add(painelBotoesCadastro);
        add(painelBotoesRelatorios);
        add(painelDados);
        add(painelArquivo);
        add(painelSeparacaoBotoes);
        add(botaoSair);
        add(Box.createVerticalGlue());

        setVisible(true);
    }
}
