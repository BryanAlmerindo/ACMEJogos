package dados.cliente;

public class Individual extends Cliente {
    private String cpf;

    public Individual(int numero, String nome, String endereco, String cpf) {
        super(numero, nome, endereco);
        this.cpf = cpf;
    }


    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public int getNumero() {
        return super.getNumero();
    }

    @Override
    public String getNome() {
        return super.getNome();
    }

    @Override
    public String getEndereco() {
        return super.getEndereco();
    }

    public String getCPF() {
        return cpf;
    }

    @Override
    public String toString() {
        return super.toString() +
                " | CPF: " + cpf;
    }
}

