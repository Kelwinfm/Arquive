/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.model;

/**
 * Descritor de um item do cabeçalho, o qual se refere à uma arquivo dentro do
 * pacote
 */
public class ItemCabecalho {

    public static enum Status {
        Valido,
        Excluido
    }

    /**
     * Status do arquivo
     */
    private Status status;

    private String nome;

    private int posicao;

    private int tamanho;

    public ItemCabecalho(
            Status status,
            String nome,
            int posicao,
            int tamanho
    ) {
        this.status = status;
        this.nome = nome;
        this.posicao = posicao;
        this.tamanho = tamanho;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

}
