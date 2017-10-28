/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package arquive.exceptions;

import javax.naming.LimitExceededException;

/**
 * Exceção a ser lançada na geração de cabeçalho quando o cabeçalho exceder o
 * tamanho limite
 */
public class CabecalhoEsgotadoException extends LimitExceededException {

    public CabecalhoEsgotadoException() {
        super("O cabeçalho excede o tamanho limite de 4KB");
    }

    public CabecalhoEsgotadoException(String mensagem) {
        super(mensagem);
    }

}
