package dados.aluguel;

import dados.jogo.Jogo;

import java.util.*;

public class CatalogoAluguel {
    private ArrayList<Aluguel> listaAluguel;

    public CatalogoAluguel() {
        listaAluguel = new ArrayList<>();
    }

    public boolean adicionar(Aluguel aluguel) {
        if (existeIdentificador(aluguel.getIdentificador())) {
            return false;
        } else {
            listaAluguel.add(aluguel);
            Collections.sort(listaAluguel,
                    Comparator.comparingInt(Aluguel::getIdentificador).reversed());
            return true;
        }
    }


    public boolean existeIdentificador(int identificador) {
        for (Aluguel aluguel : listaAluguel) {
            if (aluguel.getIdentificador() == identificador) {
                return true;
            }
        }
        return false;
    }

    public boolean removeAluguel(int id) {
        for (Aluguel aluguel : listaAluguel) {
            if (aluguel.getIdentificador() == id) {
                listaAluguel.remove(aluguel);
                return true;
            }
        }
        return false;
    }

    public boolean jogoDisponivel(Jogo verificarJogo, Date novaDataInicio, int novoPeriodo) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(novaDataInicio);
        cal.add(Calendar.DAY_OF_MONTH, novoPeriodo);
        Date novaDataFim = cal.getTime();

        for (Aluguel aluguel : listaAluguel) {
            if (aluguel.getJogo().equals(verificarJogo)) {
                Date existenteDataInicio = aluguel.getDataInicial();
                cal.setTime(existenteDataInicio);
                cal.add(Calendar.DAY_OF_MONTH, aluguel.getPeriodo());
                Date existenteDataFim = cal.getTime();

                if (novaDataInicio.before(existenteDataFim) && existenteDataInicio.before(novaDataFim)) {
                    return false;
                }
            }
        }
        return true;
    }

    public String relatorioAlugueis() {
        if (listaAluguel.isEmpty()) {
            return "Erro: sem aluguéis cadastrados.";
        }

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("@ RELATÓRIO DE ALUGUÉIS @\n");

        for (Aluguel aluguel : listaAluguel) {
            relatorio.append(aluguel.toString()).append("\n");
        }

        return relatorio.toString();
    }


    public ArrayList<Aluguel> getListaAluguel() {
        ArrayList<Aluguel> copia = (ArrayList<Aluguel>) listaAluguel.clone();
        return copia;
    }
}
