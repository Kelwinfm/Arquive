/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package arquive.controller;

import arquive.model.Cabecalho;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.ParseException;

/**
 * Responsável por interpretar bytes a fim de se obter um Cabecalho
 */
public class InterpretadorCabecalho {

    /**
     * Classe utilizada para geração de exceções na interpretação do cabeçalho
     */
    private static class CorrompidoException extends ParseException {

        public CorrompidoException(String message, int offset) {
            super("Arquivo corrompido: " + message, offset);
        }

    }

    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    private static String decodificarBytesUTF8(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }

    private static int converterBytesParaInteiro(byte[] bytes) {
        assert bytes.length == 4;

        return ByteBuffer.wrap(bytes).getInt();
    }

    /**
     * Interpreta uma lista
     *
     * @param bytes
     * @return
     * @throws ParseException
     */
    public static Cabecalho interpretarBytesCabecalho(byte[] bytes) throws ParseException {
        Cabecalho cabecalho = new Cabecalho();

        // Interpretar itens do cabeçalho
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == 0) {
                // Fim do cabeçalho
                break;
            }

            // Interpretar tamanho do nome do item
            if (bytes.length < i + 4) {
                throw new CorrompidoException("Fim de arquivo inesperado", i);
            }
            byte[] tamanhoNomeBytes = {bytes[i + 0], bytes[i + 1], bytes[i + 2], bytes[i + 3]};
            int tamanhoNome = converterBytesParaInteiro(tamanhoNomeBytes);

            // Mover quatro bytes à frente
            i += 4;

            // Interpretar nome do item
            if (bytes.length < i + tamanhoNome) {
                throw new CorrompidoException("Fim de arquivo inesperado", i);
            }
            byte[] nomeBytes = new byte[tamanhoNome];
            for (int j = 0; j < tamanhoNome; j++) {
                nomeBytes[j] = bytes[i + j];
            }
            String nome = decodificarBytesUTF8(nomeBytes);

            // Mover n bytes à frente
            i += tamanhoNome;

        }

        return cabecalho;
    }

}
