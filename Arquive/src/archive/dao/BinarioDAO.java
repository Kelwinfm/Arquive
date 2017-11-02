/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.dao;

import archive.model.Archive;
import archive.model.Arquivo;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Responsável por carregar e atualizar o conteúdo binário de um archive
 */
public class BinarioDAO extends AbstractDAO {

    public static void gravarArquivo(
            Archive archive, RandomAccessFile acessoArchive,
            Arquivo arquivo,
            int posicao
    ) throws IOException {
        acessoArchive.seek(posicao);
        acessoArchive.write(arquivo.getConteudo());
    }

    public static Arquivo lerArquivo(
            Archive archive, RandomAccessFile acessoArchive,
            String nome, int posicao, int tamanho
    ) throws IOException {
        acessoArchive.seek(posicao);
        byte[] bytes = lerBytes(acessoArchive, posicao, tamanho);

        return new Arquivo(nome, bytes);
    }

}
