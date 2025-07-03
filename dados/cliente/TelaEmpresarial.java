package dados.cliente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaEmpresarial extends JFrame {
    private JButton botaoSalvar, botaoLimpar, botaoConsultar, botaoEncerrar;
    private JTextArea campoConsulta;
    private JTextField campoNumero, campoNome, campoEndereco, campoNomeFantasia, campoCNPJ;
    private Clientela clientes;

    public TelaEmpresarial(Clientela clientes) {
        super();
        this.clientes = clientes;
        setTitle("Cadastro Cliente Empresarial");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        JPanel painelRotulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titulo = new JLabel("Cadastro de Cliente Empresarial");
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

        JLabel labelNomeFantasia = new JLabel("Nome Fantasia:");
        campoNomeFantasia = new JTextField(20);
        JPanel painelNomeFantasia = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelNomeFantasia.add(labelNomeFantasia);
        painelNomeFantasia.add(campoNomeFantasia);

        JLabel labelCNPJ = new JLabel("CNPJ:");
        campoCNPJ = new JTextField(20);
        JPanel painelCNPJ = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelCNPJ.add(labelCNPJ);
        painelCNPJ.add(campoCNPJ);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botaoSalvar = new JButton("Salvar");
        botaoLimpar = new JButton("Limpar");
        botaoConsultar = new JButton("Consultar");
        botaoEncerrar = new JButton("Encerrar");
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoLimpar);

        botaoSalvar.addActionListener(e -> {
            try {
                int numeroCliente = Integer.parseInt(campoNumero.getText());
                String nomeCliente = campoNome.getText();
                String enderecoCliente = campoEndereco.getText();
                String nomeFantasiaCliente = campoNomeFantasia.getText();
                String CNPJ = campoCNPJ.getText();

                Cliente clienteAux = new Empresarial(numeroCliente, nomeCliente, enderecoCliente, nomeFantasiaCliente, CNPJ);
                if (clientes.adicionaCliente(clienteAux) == false) {
                    campoConsulta.setText("ERRO: Código já existente.");
                } else {
                    campoConsulta.setText("Cadastro realizado com sucesso.");
                }
            } catch (NumberFormatException e1) {
                campoConsulta.setText("ERRO: Um ou mais campos possui entrada em formato inválido.");
            }
        });

        botaoLimpar.addActionListener(e -> {
            campoNumero.setText(null);
            campoNome.setText(null);
            campoEndereco.setText(null);
            campoCNPJ.setText(null);
            campoNomeFantasia.setText(null);
            campoConsulta.setText(null);
        });

        botaoConsultar.addActionListener(e -> {
            ArrayList<Cliente> clientesTotais = clientes.getCloneLista();
            for (Cliente aux : clientesTotais) {
                if (aux instanceof Empresarial) {
                    campoConsulta.append(aux.getNumero() + ": " + aux.getNome() + ", " + aux.getEndereco() + ", " + ((Empresarial) aux).getNomeFantasia() + ", " + ((Empresarial) aux).getCnpj() + "\n");
                }
            }
        });

        botaoEncerrar.addActionListener(e -> {
            dispose();
        });

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
        painelPrincipal.add(painelNomeFantasia);
        painelPrincipal.add(painelCNPJ);
        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(painelConsulta);

        add(painelPrincipal);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
