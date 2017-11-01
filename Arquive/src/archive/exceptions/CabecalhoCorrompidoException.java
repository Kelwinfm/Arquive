/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.exceptions;

import java.text.ParseException;

/**
 * Classe utilizada para geração de exceções na interpretação do cabeçalho
 */
public class CabecalhoCorrompidoException extends ParseException {

    public CabecalhoCorrompidoException(String message, int offset) {
        super("Arquivo corrompido: " + message, offset);
    }

}
