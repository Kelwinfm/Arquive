/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.dao;

import java.nio.file.Path;

/**
 * DAO Abstrato contendo métodos comuns aos demais DAOs
 */
public abstract class AbstractDAO {

    public static byte[] lerBytes(Path arquivo, int posicao, int numeroBytes) {
        // TODO
        return null;
    }

    public static void gravarBytes(Path arquivo, int posicao, byte[] bytes) {
        // TODO
    }

}
