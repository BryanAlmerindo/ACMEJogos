package app;
import dados.aluguel.CatalogoAluguel;
import dados.cliente.Clientela;
import dados.jogo.CatalogoJogos;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TelaSalvarDados extends JFrame {
    private JButton botaoConfirmar, botaoSair;
    private JTextField campoNomeArquivo;

    public TelaSalvarDados(CatalogoJogos catalogoJogos, Clientela catalogoClientes, CatalogoAluguel catalogoAluguel) {
        super();
        setTitle("Salvar Dados em Arquivo");
        setSize(650, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel mensagem = new JLabel("Insira abaixo um nome base para os arquivos de salvamento");
        mensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        mensagem.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel painelCampos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel nomeArquivoLabel = new JLabel("Nome base:");
        campoNomeArquivo = new JTextField(15);
        botaoConfirmar = new JButton("Salvar");
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
                JOptionPane.showMessageDialog(this, "ERRO: Informe um nome base para os arquivos.");
                return;
            }

            try {
                GerenciadorArquivos.salvarJogos(catalogoJogos.getListaJogos(), nomeBase + "_jogos.txt");
                GerenciadorArquivos.salvarClientes(catalogoClientes.getCloneLista(), nomeBase + "_clientes.txt");
                GerenciadorArquivos.salvarAlugueis(catalogoAluguel.getListaAluguel(), nomeBase + "_alugueis.txt");

                JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!");
                campoNomeArquivo.setText("");
                dispose();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "ERRO ao salvar os arquivos: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}