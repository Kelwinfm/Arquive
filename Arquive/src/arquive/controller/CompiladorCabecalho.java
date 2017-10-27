/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package arquive.controller;

import arquive.exceptions.CabecalhoEsgotadoException;
import arquive.model.Cabecalho;
import arquive.model.ItemCabecalho;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Responsável por transformar um Cabecalho em um conjunto de bytes
 */
public class CompiladorCabecalho {

    /**
     * Tamanho do cabeçalho em bytes. 8192 Equivale a dois clusters de 4096
     * bytes
     */
    public static final int TAMANHO_CABECALHO = 8192;

    /**
     * Cabeçalho a ser compilado
     */
    private final Cabecalho cabecalho;

    /**
     * Array de bytes inicialmente preenchido com zeros (automaticamente). Irá
     * compor o cabeçalho compilado
     */
    private final byte[] bytes = new byte[TAMANHO_CABECALHO];

    /**
     * Flag para indicar se foi já compilado
     */
    private boolean compilado = false;

    /**
     * Indicador da posição atual dentro do array de bytes
     */
    private int posicaoAtual = 0;

    public CompiladorCabecalho(Cabecalho cabecalho) {
        assert cabecalho != null;

        this.cabecalho = cabecalho;
    }

    /**
     * Concatenar uma lista de bytes ao array de bytes
     */
    private void concatenarArrayDeBytes(byte[] array) throws CabecalhoEsgotadoException {
        assert array != null;

        if (bytes.length < posicaoAtual + array.length) {
            throw new CabecalhoEsgotadoException();
        }

        for (int i = 0; i < array.length; i++) {
            bytes[posicaoAtual + i] = array[i];
        }

        posicaoAtual += array.length;
    }

    /**
     * Concatenar um inteiro ao array de bytes
     */
    private void concatenarInteiro(int inteiro) throws CabecalhoEsgotadoException {
        byte[] inteiroEmBytes = converterInteiroParaBytes(inteiro);
        concatenarArrayDeBytes(inteiroEmBytes);
    }

    /**
     * Concatenar uma string ao array de bytes
     */
    private void concatenarString(String string) throws CabecalhoEsgotadoException {
        byte[] stringEmBytes = converterStringUTF8EmBytes(string);
        concatenarArrayDeBytes(stringEmBytes);
    }

    /**
     * Converter um cabeçalho para um array de bytes
     *
     * @return Lista de bytes representando a forma compilada do cabeçalho
     * @throws CabecalhoEsgotadoException
     */
    public byte[] compilar() throws CabecalhoEsgotadoException {
        if (compilado) {
            return bytes;
        }
        compilado = true;

        for (ItemCabecalho item : cabecalho.getItens()) {
            // Concatenar status ao vetor de bytes
            byte status = item.getStatus();
            bytes[posicaoAtual] = status;

            // Avançar um byte
            posicaoAtual++;

            // Concatenar tamanho do nome ao vetor
            int tamanhoNome = item.getNome().length();
            concatenarInteiro(tamanhoNome);

            // Concatenar nome ao vetor
            String nome = item.getNome();
            concatenarString(nome);

            // Concatenar posição de início do arquivo
            int posicaoInicio = item.getPosicao();
            concatenarInteiro(posicaoInicio);

            // Concatenar tamanho do arquivo
            int tamanho = item.getTamanhoArquivo();
            concatenarInteiro(tamanho);
        }

        return bytes;
    }

    public static byte[] converterStringUTF8EmBytes(String string) {
        return string.getBytes(Charset.forName("UTF-8"));
    }

    public static byte[] converterInteiroParaBytes(int inteiro) {
        return ByteBuffer.allocate(4).putInt(inteiro).array();
    }

}
