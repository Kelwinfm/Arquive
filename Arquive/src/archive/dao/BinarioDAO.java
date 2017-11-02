/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.dao;

import archive.model.Archive;
import archive.model.Arquivo;
import archive.model.ItemCabecalho;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Responsável por carregar e atualizar o conteúdo binário de um archive
 */
public class BinarioDAO extends GenericDAO {

    public static void gravarArquivo(
            Archive archive, RandomAccessFile acessoArchive,
            Arquivo arquivo,
            ItemCabecalho descricao
    ) throws IOException {
        acessoArchive.seek(descricao.getPosicao());
        acessoArchive.write(arquivo.getConteudo());
    }

    public static Arquivo lerArquivo(
            Archive archive, RandomAccessFile acessoArchive, ItemCabecalho descricao
    ) throws IOException {
        acessoArchive.seek(descricao.getPosicao());
        byte[] bytes = lerBytes(acessoArchive, descricao.getPosicao(), descricao.getTamanho());

        return new Arquivo(descricao.getNome(), bytes);
    }

}
