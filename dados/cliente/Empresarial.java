package dados.cliente;

public class Empresarial extends Cliente {
    private String nomeFantasia;
    private String cnpj;

    public Empresarial(int numero, String nome, String endereco, String nomeFantasia, String cnpj) {
        super(numero, nome, endereco);
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Nome Fantasia: " + nomeFantasia +
                " | CNPJ: " + cnpj;
    }
}

