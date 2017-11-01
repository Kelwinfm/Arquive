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

    /**
     * Tamanho do cabeçalho em bytes. 8192 Equivale a dois clusters de 4096
     * bytes
     */
    public static final int TAMANHO_CABECALHO = 8192;

    private final List<ItemCabecalho> itens = new ArrayList<>();

    public void adicionarItem(ItemCabecalho item) {
        itens.add(item);
    }

    public List<ItemCabecalho> getItens() {
        return itens;
    }

}
