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

    public void removerItem(ItemCabecalho item) {
        itens.remove(item);
    }

    public List<ItemCabecalho> getItens() {
        return itens;
    }

    /**
     * Encontrar último arquivo do cabeçalho (com maior valor posicao)
     *
     * @return Item se referindo ao último arquivo dentro do archive OU null se
     * não houver arquivo
     */
    public ItemCabecalho getItemUltimoArquivo() {
        if (itens.isEmpty()) {
            return null;
        }

        ItemCabecalho selecionado = itens.get(0);
        int maiorPosicao = selecionado.getPosicao();

        for (ItemCabecalho item : itens) {
            if (item.getPosicao() > maiorPosicao) {
                selecionado = item;
                maiorPosicao = selecionado.getPosicao();
            }
        }

        return selecionado;
    }

}
