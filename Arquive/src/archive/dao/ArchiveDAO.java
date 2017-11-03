/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.dao;

import archive.model.ItemCabecalho;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Responsável por gerenciar um archive físico
 */
public class ArchiveDAO extends GenericDAO {

    /**
     * Remover arquivo fisicamente do archive
     *
     * @param acessoArchive
     * @param descricaoArquivo
     * @throws IOException
     */
    public static void removerArquivoDoFinal(
            RandomAccessFile acessoArchive, ItemCabecalho descricaoArquivo
    ) throws IOException {
        acessoArchive.setLength(
                acessoArchive.length() - descricaoArquivo.getTamanho()
        );
    }

}
