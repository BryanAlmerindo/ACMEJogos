package dados.jogo;

import dados.TipoEletronico;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaJogoEletronico extends JFrame {
    private JTextField campoCodigo, campoNome, campoValorBase, campoPlataforma;
    private JTextArea campoMensagem;
    private JComboBox<TipoEletronico> campoTipo;
    private JButton botaoConfirmar, botaoLimpar, botaoVisualizar, botaoEncerrar;
    private CatalogoJogos catalogo;

    public TelaJogoEletronico(CatalogoJogos catalogo) {
        super("Jogo Eletrônico");
        this.catalogo = catalogo;
        setSize(580, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        Dimension tamanhoLabel = new Dimension(100, 24);
        Dimension tamanhoCampo = new Dimension(300, 24);

        JPanel painelCodigo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelCodigo = new JLabel("Código:");
        labelCodigo.setPreferredSize(tamanhoLabel);
        campoCodigo = new JTextField();
        campoCodigo.setPreferredSize(tamanhoCampo);
        painelCodigo.add(labelCodigo);
        painelCodigo.add(campoCodigo);

        JPanel painelNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setPreferredSize(tamanhoLabel);
        campoNome = new JTextField();
        campoNome.setPreferredSize(tamanhoCampo);
        painelNome.add(labelNome);
        painelNome.add(campoNome);


        JPanel painelValor = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelValor = new JLabel("Valor Base:");
        labelValor.setPreferredSize(tamanhoLabel);
        campoValorBase = new JTextField();
        campoValorBase.setPreferredSize(tamanhoCampo);
        painelValor.add(labelValor);
        painelValor.add(campoValorBase);


        JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelTipo = new JLabel("Tipo:");
        labelTipo.setPreferredSize(tamanhoLabel);
        campoTipo = new JComboBox<>(TipoEletronico.values());
        campoTipo.setPreferredSize(tamanhoCampo);
        painelTipo.add(labelTipo);
        painelTipo.add(campoTipo);


        JPanel painelPlataforma = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelPlataforma = new JLabel("Plataforma:");
        labelPlataforma.setPreferredSize(tamanhoLabel);
        campoPlataforma = new JTextField();
        campoPlataforma.setPreferredSize(tamanhoCampo);
        painelPlataforma.add(labelPlataforma);
        painelPlataforma.add(campoPlataforma);


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

        painelPrincipal.add(painelCodigo);
        painelPrincipal.add(painelNome);
        painelPrincipal.add(painelValor);
        painelPrincipal.add(painelTipo);
        painelPrincipal.add(painelPlataforma);
        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(painelMensagem);

        add(painelPrincipal);
        setVisible(true);

        botaoEncerrar.addActionListener(e -> {
            dispose();
        });

        botaoLimpar.addActionListener(e -> {
            campoCodigo.setText("");
            campoNome.setText("");
            campoValorBase.setText("");
            campoTipo.setSelectedIndex(0);
            campoPlataforma.setText("");
            campoMensagem.setText("");
        });

        botaoVisualizar.addActionListener(e -> {
            campoMensagem.setText("");
            ArrayList<Jogo> jogosEletronicos = catalogo.getListaJogos();
            for (Jogo jogo : jogosEletronicos) {
                if (jogo instanceof JogoEletronico) {
                    campoMensagem.append(jogo.getCodigo() + ": " + jogo.getNome() + " | R$" + jogo.getValorBase() + " | " + ((JogoEletronico) jogo).getTipo().toString() + " | " + ((JogoEletronico) jogo).getPlataforma() + "\n");
                } else if (jogo instanceof JogoMesa) {
                    campoMensagem.append(jogo.getCodigo() + ": " + jogo.getNome() + " | R$" + jogo.getValorBase() + " | " + ((JogoMesa) jogo).getTipo().toString() + " | " + ((JogoMesa) jogo).getNumeroPecas() + "\n");
                }
            }

        });

        botaoConfirmar.addActionListener(e -> {
            String codigoStr = campoCodigo.getText().trim();
            String nome = campoNome.getText().trim();
            String valorBaseStr = campoValorBase.getText().trim();
            String plataforma = campoPlataforma.getText().trim();

            if (codigoStr.isEmpty() || nome.isEmpty() || valorBaseStr.isEmpty() || plataforma.isEmpty()) {
                campoMensagem.setText("ERRO: Por favor, preencha todos os campos.");
                return;
            }

            try {
                int codigo = Integer.parseInt(codigoStr);
                double valorBase = Double.parseDouble(valorBaseStr);
                String tipo = campoTipo.getSelectedItem().toString();
                TipoEletronico tipoEletronico = TipoEletronico.valueOf(tipo.toUpperCase());


                Jogo novoJogo = new JogoEletronico(codigo, nome, valorBase, tipoEletronico, plataforma);
                if (!catalogo.adicionar(novoJogo)) {
                    campoMensagem.setText("ERRO: Código já existente.");
                } else {
                    campoMensagem.setText("Cadastro realizado com sucesso.");
                }


            } catch (NumberFormatException e1) {
                campoMensagem.setText("ERRO: Um ou mais campos possui entrada em formato inválido.");
            } catch (Exception e2) {
                campoMensagem.setText(e2.toString());
            }
        });
    }

}
