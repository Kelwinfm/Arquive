/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * DAO Abstrato contendo métodos comuns aos demais DAOs
 */
public abstract class AbstractDAO {

    protected static byte[] lerBytes(RandomAccessFile arquivo, int posicao, int numeroBytes)
            throws FileNotFoundException, IOException {
        arquivo.seek(posicao);

        byte[] bytes = new byte[numeroBytes];
        arquivo.read(bytes, 0, numeroBytes);

        return bytes;
    }

    protected static void gravarBytes(RandomAccessFile arquivo, int posicao, byte[] bytes)
            throws FileNotFoundException, IOException {
        assert bytes != null;

        arquivo.seek(posicao);

        arquivo.write(bytes);
    }

}
