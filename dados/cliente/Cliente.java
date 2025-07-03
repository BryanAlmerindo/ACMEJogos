package dados.cliente;

public class Cliente {
    private int numero;
    private String nome;
    private String endereco;

    public Cliente(int numero, String nome, String endereco) {
        this.numero = numero;
        this.nome = nome;
        this.endereco = endereco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String toString() {
        return "Número: " + numero +
                " | Nome: " + nome +
                " | Endereço: " + endereco;
    }
}