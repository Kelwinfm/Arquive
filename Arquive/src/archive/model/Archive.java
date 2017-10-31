/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.model;

import java.nio.file.Path;

/**
 * Descritor de um archive contendo cabeçalho e gerenciador da parte binária
 */
public class Archive {

    private final Cabecalho cabecalho;

    private final Path caminho;

    public Archive(Cabecalho cabecalho, Path caminho) {
        this.cabecalho = cabecalho;
        this.caminho = caminho;
    }

    public Path obterCaminho() {
        return caminho;
    }

}
