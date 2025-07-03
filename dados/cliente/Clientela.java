package dados.cliente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Clientela {
    private ArrayList<Cliente> clientes;

    public Clientela() {
        clientes = new ArrayList<>();
    }

    public boolean adicionaCliente(Cliente cliente) {
        if (verificaNumero(cliente.getNumero()) == true) {
            return false;
        } else {
            clientes.add(cliente);
            Collections.sort(clientes, Comparator.comparingInt(Cliente::getNumero));
            return true;
        }
    }

    public boolean verificaNumero(int numero) {
        for (Cliente cliente : clientes) {
            if (cliente.getNumero() == numero) {
                return true;
            }
        }
        return false;
    }

    public Cliente getCliente(int numero) {
        for (Cliente cliente : clientes) {
            if (cliente.getNumero() == numero) {
                return cliente;
            }
        }
        return null;
    }

    public Cliente buscarCliente(String nome) {
        for (Cliente cliente : this.getCloneLista()) {
            if (cliente.getNome().equals(nome)) {
                return cliente;
            }
        }
        return null;
    }

    public String relatorioClientes() {
        if (clientes.isEmpty()) {
            return "Erro: sem clientes cadastrados.";
        }

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("@ RELATÓRIO DE CLIENTES @\n");

        for (Cliente aux : clientes) {
            relatorio.append(aux.toString()).append("\n");
        }

        return relatorio.toString();
    }


    public ArrayList<Cliente> getCloneLista() {
        ArrayList<Cliente> copia = (ArrayList<Cliente>) clientes.clone();
        return copia;
    }
}
