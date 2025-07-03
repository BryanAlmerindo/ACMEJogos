package dados.cliente;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroCliente extends JFrame {
    private JButton botaoSair;
    private JButton botaoClienteEmpresarial;
    private JButton botaoClienteIndividual;

    private JPanel painelPrincipal;

    public TelaCadastroCliente(Clientela clientes) {
        super();
        setTitle("Cadastro de Cliente");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        JPanel painelRotulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel menu = new JLabel("Menu - Cadastro de Cliente");
        painelRotulo.add(menu);
        painelPrincipal.add(painelRotulo);


        JPanel painelBotaoTipo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botaoClienteEmpresarial = new JButton("Empresarial");
        botaoClienteIndividual = new JButton("Individual");

        botaoClienteEmpresarial.addActionListener(e -> {
            new TelaEmpresarial(clientes);
        });

        botaoClienteIndividual.addActionListener(e -> {
            new TelaIndividual(clientes);
        });
        painelBotaoTipo.add(botaoClienteEmpresarial);
        painelBotaoTipo.add(botaoClienteIndividual);

        JPanel painelBotaoSair = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botaoSair = new JButton("Sair");

        botaoSair.addActionListener(e
                -> dispose());

        painelBotaoSair.add(botaoSair);

        painelPrincipal.add(painelBotaoTipo);
        painelPrincipal.add(painelBotaoSair);

        this.add(painelPrincipal);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
