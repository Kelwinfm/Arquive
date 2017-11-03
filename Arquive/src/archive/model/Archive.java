/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.model;

import java.io.File;

/**
 * Descritor de um archive contendo cabeçalho
 */
public class Archive {

    private Cabecalho cabecalho;

    private final File local;

    public Archive(Cabecalho cabecalho, File local) {
        this.cabecalho = cabecalho;
        this.local = local;
    }

    public Cabecalho getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(Cabecalho cabecalho) {
        this.cabecalho = cabecalho;
    }

    public File getLocal() {
        return local;
    }

}
