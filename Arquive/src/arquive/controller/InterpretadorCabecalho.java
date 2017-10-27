/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package arquive.controller;

import arquive.model.Cabecalho;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.ParseException;

/**
 * Responsável por interpretar bytes a fim de se obter um Cabecalho e vice versa
 */
public class InterpretadorCabecalho {

    private static int converterBytesParaInteiro(byte[] bytes) {
        assert bytes.length == 4;

        return ByteBuffer.wrap(bytes).getInt();
    }

    private static byte[] converterInteiroParaBytes(int inteiro) {
        return ByteBuffer.allocate(4).putInt(inteiro).array();
    }

    public static Cabecalho interpretarBytes(byte[] bytes) throws ParseException {
        Cabecalho cabecalho = new Cabecalho();

        // Interpretar itens do cabeçalho
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == 0) {
                // Fim do cabeçalho
                break;
            }

            // Interpretar tamanho do nome do item
            if (bytes.length < i + 4) {
                throw new ParseException(
                        "Arquivo corrompido; fim de arquivo inesperado",
                        i
                );
            }
            byte[] tamanhoNomeBytes = {bytes[0], bytes[1], bytes[2], bytes[3]};
            int tamanhoNome = converterBytesParaInteiro(tamanhoNomeBytes);
        }

        return cabecalho;
    }

}
