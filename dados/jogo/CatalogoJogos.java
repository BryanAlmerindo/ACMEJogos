package dados.jogo;


import java.util.*;

public class CatalogoJogos {
    private ArrayList<Jogo> listaJogos;

    public CatalogoJogos() {
        listaJogos = new ArrayList<>();
    }

    public boolean adicionar(Jogo jogo) {
        if (existeCodigo(jogo.getCodigo())) {
            return false;
        } else {
            listaJogos.add(jogo);
            Collections.sort(listaJogos, Comparator.comparingInt(Jogo::getCodigo));
            return true;
        }
    }

    public boolean existeCodigo(int codigo) {
        for (Jogo jogo : listaJogos) {
            if (jogo.getCodigo() == codigo) {
                return true;
            }
        }
        return false;
    }

    public Jogo getJogo(int codigo) {
        for (Jogo jogo : listaJogos) {
            if (jogo.getCodigo() == codigo) {
                return jogo;
            }
        }
        return null;
    }

    public Jogo buscarJogo(String nome) {
        for (Jogo jogo : listaJogos) {
            if (jogo.getNome().equals(nome)) {
                return jogo;
            }
        }
        return null;
    }

    public String relatorioJogos() {
        if (listaJogos.isEmpty()) {
            return "Erro: sem jogos cadastrados.";
        }
        StringBuilder relatorio = new StringBuilder();
        relatorio.append(" @ RELATÓRIO DE JOGOS @ \n");

        for (Jogo aux : listaJogos) {
            relatorio.append(aux.toString()).append("\n");
        }

        return relatorio.toString();
    }


    public ArrayList<Jogo> getListaJogos() {
        ArrayList<Jogo> listaJogosCopia = (ArrayList<Jogo>) listaJogos.clone();
        return listaJogosCopia;
    }
}
