/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.dao;

import archive.controller.CompiladorCabecalho;
import archive.controller.InterpretadorCabecalho;
import archive.exceptions.CabecalhoCorrompidoException;
import archive.exceptions.CabecalhoEsgotadoException;
import archive.model.Cabecalho;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Responsável por carregar e atualizar o cabeçalho de um archive
 */
public class CabecalhoDAO extends AbstractDAO {

    public static Cabecalho carregarCabecalho(RandomAccessFile arquivo)
            throws CabecalhoCorrompidoException, IOException {
        byte[] bytes = lerBytes(arquivo, 0, Cabecalho.TAMANHO_CABECALHO);

        InterpretadorCabecalho interpretador = new InterpretadorCabecalho(bytes);

        return interpretador.interpretar();
    }

    public static void gravarCabecalho(RandomAccessFile arquivo, Cabecalho cabecalho)
            throws CabecalhoEsgotadoException, IOException {
        CompiladorCabecalho compilador = new CompiladorCabecalho(cabecalho);

        byte[] bytes = compilador.compilar();

        gravarBytes(arquivo, 0, bytes);
    }

}
