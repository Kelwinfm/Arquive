/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.controller;

import archive.exceptions.CabecalhoEsgotadoException;
import archive.model.Cabecalho;
import archive.model.ItemCabecalho;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Responsável por transformar um Cabecalho em um conjunto de bytes
 */
public class CompiladorCabecalho {

    /**
     * Cabeçalho a ser compilado
     */
    private final Cabecalho cabecalho;

    /**
     * Array de bytes inicialmente preenchido com zeros (automaticamente). Irá
     * compor o cabeçalho compilado
     */
    private final byte[] bytes = new byte[Cabecalho.TAMANHO_CABECALHO];

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
        Arrays.fill(bytes, (byte) 0);
        posicaoAtual = 0;

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
