/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.model;

import java.io.File;

/**
 * Descritor de um archive contendo cabeçalho e gerenciador da parte binária
 */
public class Archive {

    private final Cabecalho cabecalho;

    private final File arquivo;

    public Archive(Cabecalho cabecalho, File arquivo) {
        this.cabecalho = cabecalho;
        this.arquivo = arquivo;
    }

    public File getArquivo() {
        return arquivo;
    }

    public Cabecalho getCabecalho() {
        return cabecalho;
    }

}
