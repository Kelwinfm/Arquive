/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.model;

/**
 * Descritor de um archive contendo cabeçalho
 */
public class Archive {

    private Cabecalho cabecalho;

    public Archive(Cabecalho cabecalho) {
        this.cabecalho = cabecalho;
    }

    public Cabecalho getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(Cabecalho cabecalho) {
        this.cabecalho = cabecalho;
    }

}
