/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package arquive.controller;

import arquive.view.TelaInicial;

/**
 * Classe controladora do fluxo principal do Arquive
 */
public class Arquive {

    public static void main(String[] args) {
        TelaInicial addMenu = new TelaInicial();
        addMenu.setVisible(true);
    }

}
