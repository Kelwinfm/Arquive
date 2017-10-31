/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Descritor do cabeçalho de um arquivo
 */
public class Cabecalho {

    private final List<ItemCabecalho> itens = new ArrayList<>();

    public void adicionarItem(ItemCabecalho item) {
        itens.add(item);
    }

}
