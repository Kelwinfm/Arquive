/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package arquive.controller;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Responsável por transformar um Cabecalho em um conjunto de bytes
 */
public class CompiladorCabecalho {

    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    private static byte[] codificarBytesUTF8(String string) {
        return string.getBytes(UTF8_CHARSET);
    }

    private static byte[] converterInteiroParaBytes(int inteiro) {
        return ByteBuffer.allocate(4).putInt(inteiro).array();
    }

    public static byte[] compilarCabecalho(byte[] bytes) {
        // TODO
        throw new UnsupportedOperationException("Ainda não suportado.");
    }

}
