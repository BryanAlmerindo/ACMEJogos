package app;

import dados.aluguel.CatalogoAluguel;
import dados.cliente.Clientela;
import dados.jogo.CatalogoJogos;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TelaCarregarDados extends JFrame {
    private JButton botaoConfirmar, botaoSair;
    private JTextField campoNomeArquivo;

    public TelaCarregarDados(CatalogoJogos catalogoJogos, Clientela catalogoClientes, CatalogoAluguel catalogoAluguel) {
        super();
        setTitle("Carregar Dados de Arquivo");
        setSize(650, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JLabel mensagem = new JLabel("Insira abaixo o nome base dos arquivos a serem carregados");
        mensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        mensagem.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel painelCampos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel nomeArquivoLabel = new JLabel("Nome base:");
        campoNomeArquivo = new JTextField(15);
        botaoConfirmar = new JButton("Carregar");
        botaoSair = new JButton("Sair");

        painelCampos.add(nomeArquivoLabel);
        painelCampos.add(campoNomeArquivo);
        painelCampos.add(botaoConfirmar);
        painelCampos.add(botaoSair);

        painelPrincipal.add(mensagem);
        painelPrincipal.add(painelCampos);

        this.add(painelPrincipal);


        botaoSair.addActionListener(e -> {
            dispose();
        });

        botaoConfirmar.addActionListener(e -> {
            String nomeBase = campoNomeArquivo.getText().trim();

            if (nomeBase.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERRO: Informe um nome base para os arquivos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int resposta = JOptionPane.showConfirmDialog(this,
                        "Isso limpará os dados atuais em memória. Deseja continuar?",
                        "Confirmar Carregamento",
                        JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.NO_OPTION) {
                    return;
                }

                catalogoJogos.getListaJogos().clear();
                catalogoClientes.getCloneLista().clear();
                catalogoAluguel.getListaAluguel().clear();

                GerenciadorArquivos.carregarJogos(catalogoJogos, nomeBase + "_jogos.txt");
                GerenciadorArquivos.carregarClientes(catalogoClientes, nomeBase + "_clientes.txt");
                GerenciadorArquivos.carregarAlugueis(catalogoAluguel, catalogoJogos, catalogoClientes, nomeBase + "_alugueis.txt");

                JOptionPane.showMessageDialog(this, "Dados carregados com sucesso!");
                campoNomeArquivo.setText("");
                dispose();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "ERRO ao carregar os arquivos: " + ex.getMessage(), "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace(); // Ajuda na depuração
                JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro Inesperado", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
