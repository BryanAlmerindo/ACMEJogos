package dados.cliente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaIndividual extends JFrame {
    private JButton botaoSalvar, botaoLimpar, botaoConsultar, botaoEncerrar;
    private JTextArea campoConsulta;
    private JTextField campoNumero, campoNome, campoEndereco, campoCPF;
    private Clientela clientes;

    public TelaIndividual(Clientela clientes) {
        super();
        this.clientes = clientes;
        setTitle("Cadastro Cliente Individual");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        JPanel painelRotulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titulo = new JLabel("Cadastro de Cliente Individual");
        painelRotulo.add(titulo);
        painelPrincipal.add(painelRotulo);

        JLabel labelNumero = new JLabel("Número:");
        campoNumero = new JTextField(20);
        JPanel painelNumero = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelNumero.add(labelNumero);
        painelNumero.add(campoNumero);

        JLabel labelNome = new JLabel("Nome:");
        campoNome = new JTextField(20);
        JPanel painelNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelNome.add(labelNome);
        painelNome.add(campoNome);

        JLabel labelEndereco = new JLabel("Endereço:");
        campoEndereco = new JTextField(20);
        JPanel painelEndereco = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelEndereco.add(labelEndereco);
        painelEndereco.add(campoEndereco);

        JLabel labelCPF = new JLabel("CPF:");
        campoCPF = new JTextField(20);
        JPanel painelCPF = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelCPF.add(labelCPF);
        painelCPF.add(campoCPF);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botaoSalvar = new JButton("Salvar");
        botaoLimpar = new JButton("Limpar");
        botaoConsultar = new JButton("Consultar");
        botaoEncerrar = new JButton("Encerrar");
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoLimpar);

        JPanel botoesLaterais = new JPanel();
        botoesLaterais.setLayout(new BoxLayout(botoesLaterais, BoxLayout.Y_AXIS));
        botoesLaterais.add(botaoConsultar);
        botoesLaterais.add(Box.createVerticalStrut(10));
        botoesLaterais.add(botaoEncerrar);

        JPanel painelConsulta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelMensagem = new JLabel("Consulta");
        campoConsulta = new JTextArea(5, 35);
        JScrollPane scroll = new JScrollPane(campoConsulta);
        painelConsulta.add(labelMensagem);
        painelConsulta.add(scroll);
        painelConsulta.add(botoesLaterais);


        painelPrincipal.add(painelNumero);
        painelPrincipal.add(painelNome);
        painelPrincipal.add(painelEndereco);
        painelPrincipal.add(painelCPF);
        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(painelConsulta);

        add(painelPrincipal);
        setLocationRelativeTo(null);
        setVisible(true);

        botaoEncerrar.addActionListener(e -> {
            dispose();
        });

        botaoLimpar.addActionListener(e -> {
            campoNumero.setText("");
            campoNome.setText("");
            campoEndereco.setText("");
            campoCPF.setText("");
            campoConsulta.setText("");
        });

        botaoConsultar.addActionListener(e -> {
            campoConsulta.setText("");
            ArrayList<Cliente> clientesTotais = clientes.getCloneLista();
            for (Cliente aux : clientesTotais) {
                if (aux instanceof Individual) {
                    campoConsulta.append(aux.getNumero() + ": " + aux.getNome() + ", " + aux.getEndereco() + ", " + ((Individual) aux).getCPF() + "\n");
                }
            }
        });

        botaoSalvar.addActionListener(e -> {
            try {
                int numeroCliente = Integer.parseInt(campoNumero.getText());
                String nomeCliente = campoNome.getText();
                String enderecoCliente = campoEndereco.getText();
                String CPF = campoCPF.getText();

                Cliente clienteAux = new Individual(numeroCliente, nomeCliente, enderecoCliente, CPF);
                if (!clientes.adicionaCliente(clienteAux)) {
                    campoConsulta.setText("ERRO: Código já existente.");
                } else {
                    campoConsulta.setText("Cadastro realizado com sucesso.");
                }
            } catch (NumberFormatException e1) {
                campoConsulta.setText("ERRO: Um ou mais campos possui entrada em formato inválido.");
            }
        });
    }
}
