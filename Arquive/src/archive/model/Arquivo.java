/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.model;

/**
 * Descritor do conteúdo de um arquivo binário
 */
public class Arquivo {

    private final String nome;

    private final byte[] conteudo;

    public Arquivo(String nome, byte[] conteudo) {
        this.nome = nome;
        this.conteudo = conteudo;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public int getTamanho() {
        return conteudo.length;
    }

    public String getNome() {
        return nome;
    }

}
