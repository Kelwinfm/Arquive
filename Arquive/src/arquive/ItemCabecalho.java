/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package arquive;

/**
 * Descritor de um item do cabeçalho, o qual se refere à uma arquivo dentro do
 * pacote
 */
public class ItemCabecalho {

    private byte status;

    private int tamanhoNome;

    private String nome;

    private int posicao;

    private int tamanhoArq;

    public ItemCabecalho(
            byte status,
            int tamanhoNome,
            String nome,
            int posicao,
            int tamanhoArquivo
    ) {
        this.status = status;
        this.tamanhoNome = tamanhoNome;
        this.nome = nome;
        this.posicao = posicao;
        this.tamanhoArq = tamanhoArquivo;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public int getTamanhoNome() {
        return tamanhoNome;
    }

    public void setTamanhoNome(int tamanhoNome) {
        this.tamanhoNome = tamanhoNome;
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

    public int getTamanhoArq() {
        return tamanhoArq;
    }

    public void setTamanhoArq(int tamanhoArq) {
        this.tamanhoArq = tamanhoArq;
    }

}
