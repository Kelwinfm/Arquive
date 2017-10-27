/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package arquive.controller;

import arquive.model.Cabecalho;
import arquive.model.ItemCabecalho;
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

    /**
     * Conjunto de bytes a ser interpretado
     */
    private final byte[] bytes;

    /**
     * Indicador da posição sendo analisada no memento pelo interpretador
     */
    private int posicaoAtual;

    public InterpretadorCabecalho(byte[] bytes) {
        assert bytes != null;

        this.bytes = bytes;
    }

    /**
     * Interpreta o próximo inteiro (quatro bytes) a partir da posição atual e
     * avança o ponteiro
     *
     * @return
     * @throws ParseException
     */
    private int interpretarInteiro() throws ParseException {
        if (bytes.length < posicaoAtual + 4) {
            throw new CorrompidoException("Fim de arquivo inesperado", posicaoAtual);
        }
        // Converter quatro bytes para inteiro
        byte[] inteiroEmBytes = {
            bytes[posicaoAtual + 0],
            bytes[posicaoAtual + 1],
            bytes[posicaoAtual + 2],
            bytes[posicaoAtual + 3]
        };

        // Mover quatro bytes à frente
        posicaoAtual += 4;

        return converterBytesParaInteiro(inteiroEmBytes);
    }

    /**
     * Interpreta uma string UTF8 (n bytes) a partir da posição atual e avança o
     * ponteiro
     *
     * @return
     * @throws ParseException
     */
    private String interpretarStringUTF8(int tamanho) throws ParseException {
        if (bytes.length < posicaoAtual + tamanho) {
            throw new CorrompidoException("Fim de arquivo inesperado", posicaoAtual);
        }

        byte[] stringEmBytes = new byte[tamanho];
        for (int j = 0; j < tamanho; j++) {
            stringEmBytes[j] = bytes[posicaoAtual + j];
        }
        String string = decodificarBytesUTF8(stringEmBytes);

        // Mover n bytes à frente
        posicaoAtual += tamanho;

        return string;
    }

    /**
     * Interpreta um conjunto de bytes e transformá-lo em um Cabecalho
     *
     * @return
     * @throws ParseException
     */
    public Cabecalho interpretar() throws ParseException {
        Cabecalho cabecalho = new Cabecalho();

        // Interpretar itens do cabeçalho
        for (posicaoAtual = 0; posicaoAtual < bytes.length; posicaoAtual++) {
            if (bytes[posicaoAtual] == 0) {
                // Fim do cabeçalho
                break;
            }

            // Status do item
            byte status = bytes[posicaoAtual];

            // Avançar um byte
            posicaoAtual++;

            // Interpretar tamanho do nome do item
            int tamanhoNome = interpretarInteiro();

            // Interpretar nome do item
            String nome = interpretarStringUTF8(tamanhoNome);

            // Interpretar número que indica posição do início do arquivo
            int inicioArquivo = interpretarInteiro();

            // Interpretar tamanho do arquivo
            int tamanhoArquivo = interpretarInteiro();

            // Adicionar este novo item ao cabeçalho
            ItemCabecalho item = new ItemCabecalho(
                    status,
                    nome,
                    inicioArquivo,
                    tamanhoArquivo
            );
            cabecalho.adicionarItem(item);
        }

        return cabecalho;
    }

    private static String decodificarBytesUTF8(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }

    private static int converterBytesParaInteiro(byte[] bytes) {
        assert bytes.length == 4;

        return ByteBuffer.wrap(bytes).getInt();
    }
}
