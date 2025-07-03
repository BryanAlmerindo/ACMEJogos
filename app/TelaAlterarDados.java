package app;

import dados.cliente.Cliente;
import dados.cliente.Clientela;
import dados.cliente.Empresarial;
import dados.cliente.Individual;
import javax.swing.*;
import java.awt.*;

public class TelaAlterarDados extends JFrame {
    private Clientela clientes;
    private Cliente clienteAtual;
    private JTextField campoNumero, campoNome, campoEndereco, campoCPF, campoNomeFantasia, campoCNPJ;
    private JTextArea areaInformacoes;
    private JButton botaoVisualizar, botaoSalvar, botaoEncerrar, botaoLimpar;

    public TelaAlterarDados(Clientela clientes) {
        super();
        this.clientes = clientes;
        setTitle("Atualizar Cliente");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel painelNumero = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel labelNumero = new JLabel("Número do cliente:");
        campoNumero = new JTextField(10);
        botaoVisualizar = new JButton("Visualizar dados");
        painelNumero.add(labelNumero);
        painelNumero.add(campoNumero);
        painelNumero.add(botaoVisualizar);

        areaInformacoes = new JTextArea(5, 50);
        areaInformacoes.setEditable(false);
        JScrollPane scrollArea = new JScrollPane(areaInformacoes);

        JPanel painelAtualizacao = new JPanel();
        painelAtualizacao.setLayout(new GridLayout(5, 2, 10, 10));

        campoNome = new JTextField(20);
        campoEndereco = new JTextField(20);
        campoCPF = new JTextField(20);
        campoNomeFantasia = new JTextField(20);
        campoCNPJ = new JTextField(20);

        limpaCampos();

        painelAtualizacao.add(new JLabel("Novo nome:"));
        painelAtualizacao.add(campoNome);
        painelAtualizacao.add(new JLabel("Novo endereço:"));
        painelAtualizacao.add(campoEndereco);
        painelAtualizacao.add(new JLabel("Novo CPF:"));
        painelAtualizacao.add(campoCPF);
        painelAtualizacao.add(new JLabel("Novo Nome Fantasia:"));
        painelAtualizacao.add(campoNomeFantasia);
        painelAtualizacao.add(new JLabel("Novo CNPJ:"));
        painelAtualizacao.add(campoCNPJ);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        botaoSalvar = new JButton("Salvar");
        botaoEncerrar = new JButton("Encerrar");
        botaoLimpar = new JButton("Limpar");
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoEncerrar);
        painelBotoes.add(botaoLimpar);

        painelPrincipal.add(painelNumero);
        painelPrincipal.add(scrollArea);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(painelAtualizacao);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(painelBotoes);

        add(painelPrincipal);
        setVisible(true);

        botaoEncerrar.addActionListener(e -> {
            dispose();
        });

        botaoLimpar.addActionListener(e -> {
            limpaCampos();
        });

        botaoVisualizar.addActionListener(e -> {
            String numeroStr = campoNumero.getText().trim();

            if (numeroStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERRO: Informe o número do cliente.");
                return;
            }
            try {
                int numero = Integer.parseInt(numeroStr);

                clienteAtual = clientes.getCliente(numero);
                if (clienteAtual != null) {
                    campoNumero.setEditable(false);
                    areaInformacoes.setText(clienteAtual.toString());
                    campoNome.setEditable(true);
                    campoEndereco.setEditable(true);
                    if (clienteAtual instanceof Individual) {
                        campoCPF.setEditable(true);
                        campoCNPJ.setEditable(false);
                        campoNomeFantasia.setEditable(false);

                    } else if (clienteAtual instanceof Empresarial) {
                        campoCNPJ.setEditable(true);
                        campoNomeFantasia.setEditable(true);
                        campoCPF.setEditable(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "ERRO: Número não existente.");
                    limpaCampos();
                }

            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(this, "ERRO: Campo número possui entrada em formato inválido.");
                limpaCampos();
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(this, "ERRO: " + e2.getMessage());
                limpaCampos();
            }

        });

        botaoSalvar.addActionListener(e -> {
            boolean atualizou = false;
            if (clienteAtual == null) {
                JOptionPane.showMessageDialog(this, "ERRO: Nenhum cliente carregado.");
                return;
            }

            String nomeStr = campoNome.getText().trim();
            String enderecoStr = campoEndereco.getText().trim();
            String cpfStr = campoCPF.getText().trim();
            String nomeFantasiaStr = campoNomeFantasia.getText().trim();
            String cnpjStr = campoCNPJ.getText().trim();

            if (nomeStr.isEmpty() && enderecoStr.isEmpty() && cpfStr.isEmpty() &&
                    cnpjStr.isEmpty() && nomeFantasiaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERRO: Nenhum campo foi preenchido.");
            }

            if (!nomeStr.isEmpty()) {
                clienteAtual.setNome(nomeStr);
                atualizou = true;
            }
            if (!enderecoStr.isEmpty()) {
                clienteAtual.setEndereco(enderecoStr);
                atualizou = true;
            }
            if (clienteAtual instanceof Individual) {
                if (!cpfStr.isEmpty()) {
                    ((Individual) clienteAtual).setCpf(cpfStr);
                    atualizou = true;
                }
            } else if (clienteAtual instanceof Empresarial) {
                if (!cnpjStr.isEmpty()) {
                    ((Empresarial) clienteAtual).setCnpj(cnpjStr);
                    atualizou = true;
                }
                if (!nomeFantasiaStr.isEmpty()) {
                    ((Empresarial) clienteAtual).setNomeFantasia(nomeFantasiaStr);
                    atualizou = true;
                }
            }

            if (atualizou) {
                JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
                limpaCampos();
            }
        });
    }

    public void limpaCampos() {
        campoNumero.setText("");
        campoNumero.setEditable(true);
        areaInformacoes.setText("");
        campoNome.setEditable(false);
        campoNome.setText("");
        campoEndereco.setEditable(false);
        campoEndereco.setText("");
        campoCNPJ.setEditable(false);
        campoCNPJ.setText("");
        campoNomeFantasia.setEditable(false);
        campoNomeFantasia.setText("");
        campoCPF.setEditable(false);
        campoCPF.setText("");
    }
}
