package dados.aluguel;

import dados.cliente.Cliente;
import dados.cliente.Clientela;
import dados.jogo.CatalogoJogos;
import dados.jogo.Jogo;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TelaCadastroAluguel extends JFrame {
    private CatalogoAluguel catalogo;
    private Clientela clientes;
    private CatalogoJogos jogos;
    private JButton botaoEncerrar;
    private JButton botaoConfirmar;
    private JButton botaoLimpar;
    private JButton botaoVisualizar;
    private JTextField campoIdentificador;
    private JTextField campoDataInicial;
    private JTextField campoPeriodo;
    private JTextArea campoMensagem;
    private JComboBox<String> campoClientes;
    private JComboBox<String> campoJogos;

    public TelaCadastroAluguel(CatalogoAluguel catalogo, CatalogoJogos jogos, Clientela clientes) {
        this.catalogo = catalogo;
        this.clientes = clientes;
        this.jogos = jogos;
        setTitle("Cadastro de aluguel");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        Dimension tamanhoLabel = new Dimension(100, 24);
        Dimension tamanhoCampo = new Dimension(300, 24);

        JPanel painelClientes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelClientes = new JLabel("Clientes:");
        labelClientes.setPreferredSize(tamanhoLabel);
        campoClientes = new JComboBox<>();
        carregarComboBoxCliente(clientes.getCloneLista());
        campoClientes.setBounds(50, 50, 200, 30);
        campoClientes.setPreferredSize(tamanhoCampo);
        painelClientes.add(labelClientes);
        painelClientes.add(campoClientes);

        JPanel painelJogos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelJogos = new JLabel("Jogos:");
        labelJogos.setPreferredSize(tamanhoLabel);
        campoJogos = new JComboBox<>();
        carregarComboBoxJogo(jogos.getListaJogos());
        campoJogos.setBounds(50, 50, 200, 30);
        campoJogos.setPreferredSize(tamanhoCampo);
        painelJogos.add(labelJogos);
        painelJogos.add(campoJogos);

        JPanel painelIdentificador = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelIdentificador = new JLabel("Identificador:");
        labelIdentificador.setPreferredSize(tamanhoLabel);
        campoIdentificador = new JTextField();
        campoIdentificador.setPreferredSize(tamanhoCampo);
        painelIdentificador.add(labelIdentificador);
        painelIdentificador.add(campoIdentificador);

        JPanel painelDataInicial = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelDataInicial = new JLabel("Data de Inicial:");
        labelDataInicial.setPreferredSize(tamanhoLabel);
        campoDataInicial = new JTextField();
        campoDataInicial.setPreferredSize(tamanhoCampo);
        painelDataInicial.add(labelDataInicial);
        painelDataInicial.add(campoDataInicial);

        JPanel painelPeriodo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelPeriodo = new JLabel("Periodo:");
        labelPeriodo.setPreferredSize(tamanhoLabel);
        campoPeriodo = new JTextField();
        campoPeriodo.setPreferredSize(tamanhoCampo);
        painelPeriodo.add(labelPeriodo);
        painelPeriodo.add(campoPeriodo);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botaoConfirmar = new JButton("Confirmar");
        botaoLimpar = new JButton("Limpar");
        botaoVisualizar = new JButton("Visualizar dados");
        botaoEncerrar = new JButton("Encerrar");
        painelBotoes.add(botaoConfirmar);
        painelBotoes.add(botaoLimpar);
        painelBotoes.add(botaoVisualizar);
        painelBotoes.add(botaoEncerrar);


        JPanel painelMensagem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelMensagem = new JLabel("Mensagem:");
        labelMensagem.setPreferredSize(tamanhoLabel);
        campoMensagem = new JTextArea(6, 30);
        campoMensagem.setEditable(Boolean.FALSE);
        campoMensagem.setLineWrap(true);
        campoMensagem.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(campoMensagem);
        painelMensagem.add(labelMensagem);
        painelMensagem.add(scroll);

        painelPrincipal.add(painelClientes);
        painelPrincipal.add(painelJogos);
        painelPrincipal.add(painelIdentificador);
        painelPrincipal.add(painelDataInicial);
        painelPrincipal.add(painelPeriodo);
        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(painelMensagem);

        add(painelPrincipal);
        setVisible(true);

        botaoEncerrar.addActionListener(e -> {
            dispose();
        });

        botaoLimpar.addActionListener(e -> {
            campoIdentificador.setText("");
            campoDataInicial.setText("");
            campoPeriodo.setText("");
            campoMensagem.setText("");
        });

        botaoVisualizar.addActionListener(e -> {
            campoMensagem.setText("");
            ArrayList<Aluguel> lista = catalogo.getListaAluguel();
            for (Aluguel a : lista) {
                campoMensagem.append(a.getIdentificador() + ": " + a.formatarData() + " | " + a.getPeriodo() + " dias | " + a.getCliente().getNome() + " | " + a.getJogo().getNome() + "\n");
            }
        });

        botaoConfirmar.addActionListener(e -> {
            String identificadorStr = campoIdentificador.getText().trim();
            String dataInicialStr = campoDataInicial.getText().trim();
            String periodoStr = campoPeriodo.getText().trim();

            if (identificadorStr.isEmpty() || dataInicialStr.isEmpty() || periodoStr.isEmpty()) {
                campoMensagem.setText("ERRO: Por favor, preencha todos os campos.");
                return;
            }


            try {
                String cliente = campoClientes.getSelectedItem().toString();
                Cliente clienteEncontrado = clientes.buscarCliente(cliente);
                String jogo = campoJogos.getSelectedItem().toString();
                Jogo jogoEncontrado = jogos.buscarJogo(jogo);
                int identificador = Integer.parseInt(identificadorStr);
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                Date dataInicial = formato.parse(dataInicialStr);
                int periodo = Integer.parseInt(periodoStr);

                if (!catalogo.jogoDisponivel(jogoEncontrado, dataInicial, periodo)) {
                    campoMensagem.setText("ERRO: O jogo '" + jogoEncontrado.getNome() + "' já está alugado neste período.");
                    return;
                }

                Aluguel novoAluguel = new Aluguel(identificador, dataInicial, periodo, jogoEncontrado, clienteEncontrado);
                if (!catalogo.adicionar(novoAluguel)) {
                    campoMensagem.setText("ERRO: código já existente.");
                } else {
                    campoMensagem.setText("Cadastro realizado com sucesso.");
                }

            } catch (ParseException pe) {
                campoMensagem.setText("ERRO: Formato de data inválido. Use dd/mm/aaaa.");
            } catch (NumberFormatException nfe) {
                campoMensagem.setText("ERRO: identificador e período devem ser números.");
            } catch (Exception ex) {
                campoMensagem.setText("Ocorreu um erro inesperado: " + ex.getMessage());
            }

        });
    }

    public void carregarComboBoxCliente(ArrayList<Cliente> clientes) {
        campoClientes.removeAllItems();

        for (Cliente c : clientes) {
            campoClientes.addItem(c.getNome());
        }
    }

    public void carregarComboBoxJogo(ArrayList<Jogo> jogos) {
        campoJogos.removeAllItems();

        for (Jogo j : jogos) {
            campoJogos.addItem(j.getNome());
        }
    }

}
