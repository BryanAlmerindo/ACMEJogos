package app;

import dados.aluguel.CatalogoAluguel;
import javax.swing.*;
import java.awt.*;

public class TelaRemoverDados extends JFrame {
    JButton botaoConfirmar, botaoSair;
    JTextField campoCodigo;
    CatalogoAluguel catalogoAluguel;

    public TelaRemoverDados(CatalogoAluguel catalogo) {
        super();
        this.catalogoAluguel = catalogo;
        setTitle("Remover aluguel");
        setSize(650, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel mensagem = new JLabel("Insira abaixo o código do aluguel que deseja remover");
        mensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        mensagem.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // espaço em volta

        JPanel painelCampos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel codigoAluguel = new JLabel("Código: ");
        campoCodigo = new JTextField(10);
        botaoConfirmar = new JButton("Remover");
        botaoSair = new JButton("Sair");

        painelCampos.add(codigoAluguel);
        painelCampos.add(campoCodigo);
        painelCampos.add(botaoConfirmar);
        painelCampos.add(botaoSair);

        painelPrincipal.add(mensagem);
        painelPrincipal.add(painelCampos);

        this.add(painelPrincipal);

        botaoSair.addActionListener(e -> {
            dispose();
        });

        botaoConfirmar.addActionListener(e -> {
            String codigoStr = campoCodigo.getText().trim();

            if (codigoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERRO: Informe o código do aluguel");
                return;
            }
            try {
                int codigo = Integer.parseInt(codigoStr);
                if (catalogoAluguel.existeIdentificador(codigo)) {
                    if (catalogoAluguel.removeAluguel(codigo)) {
                        JOptionPane.showMessageDialog(this, "Aluguel removido com sucesso");
                        campoCodigo.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "ERRO: Código não existente.");
                    campoCodigo.setText("");
                }

            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(this, "ERRO: Campo código possui entrada em formato inválido.");
                campoCodigo.setText("");
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(this, "ERRO: " + e2.getMessage());
                campoCodigo.setText("");
            }
        });

        setVisible(true);


    }
}
