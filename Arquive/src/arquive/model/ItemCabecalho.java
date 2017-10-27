/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package arquive.model;

/**
 * Descritor de um item do cabeçalho, o qual se refere à uma arquivo dentro do
 * pacote
 */
public class ItemCabecalho {

    /**
     * Status do arquivo, 1 = válido, 2 = excluído
     */
    private byte status;

    private String nome;

    private int posicao;

    private int tamanhoArquivo;

    public ItemCabecalho(
            byte status,
            String nome,
            int posicao,
            int tamanhoArquivo
    ) {
        this.status = status;
        this.nome = nome;
        this.posicao = posicao;
        this.tamanhoArquivo = tamanhoArquivo;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
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

    public int getTamanhoArquivo() {
        return tamanhoArquivo;
    }

    public void setTamanhoArquivo(int tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

}
