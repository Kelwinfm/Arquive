/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.dao;

import archive.model.Cabecalho;
import java.nio.file.Path;

/**
 * Responsável por carregar e atualizar o cabeçalho de um archive
 */
public class CabecalhoDAO extends AbstractDAO {

    public static Cabecalho carregarCabecalho(Path caminho) {
        
        byte[] bytes = lerBytes(caminho, 0, 00000);
        
        return new Cabecalho();
    }

    public static void gravarCabecalho(Path caminho) {

    }

}
