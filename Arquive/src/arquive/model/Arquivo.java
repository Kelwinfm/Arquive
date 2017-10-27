/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package arquive.model;

/**
 * Descritor do conteúdo de um arquivo binário
 */
public class Arquivo {

    private final byte[] conteudo;

    public Arquivo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

}
