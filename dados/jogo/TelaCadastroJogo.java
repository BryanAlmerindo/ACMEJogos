package dados.jogo;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroJogo extends JFrame {
    private CatalogoJogos catalogo;
    private JButton botaoMesa, botaoEletronico, botaoSair;

    public TelaCadastroJogo(CatalogoJogos catalogo) {
        this.catalogo = catalogo;
        setTitle("Cadastro de Jogos");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("Escolha o tipo de jogo que deseja cadastrar:");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        botaoMesa = new JButton("Jogo de Mesa");
        botaoEletronico = new JButton("Jogo Eletrônico");
        botaoSair = new JButton("Sair");

        botaoMesa.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoEletronico.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoSair.setAlignmentX(Component.CENTER_ALIGNMENT);

        botaoMesa.addActionListener(e -> new TelaJogoMesa(catalogo));
        botaoEletronico.addActionListener(e -> new TelaJogoEletronico(catalogo));
        botaoSair.addActionListener(e -> dispose());

        add(Box.createVerticalGlue());
        add(titulo);
        add(Box.createVerticalStrut(20));
        add(botaoMesa);
        add(Box.createVerticalStrut(10));
        add(botaoEletronico);
        add(Box.createVerticalGlue());
        add(botaoSair);
        add(Box.createVerticalStrut(20));

        setVisible(true);
    }
}
