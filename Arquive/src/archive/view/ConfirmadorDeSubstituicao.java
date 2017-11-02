/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.view;

import javax.swing.JOptionPane;

/**
 * Utilizado ao confirmar se um arquivo existente deve ser sobrescrito na hora
 * da criação de um novo Archive
 */
public class ConfirmadorDeSubstituicao {

    public ConfirmadorDeSubstituicao() {

    }

    public boolean verificarConfirmacao() {
        int dialogResult = JOptionPane.showConfirmDialog(
                null,
                "Você tem certeza que deseja sobrescrever o arquivo existente?",
                "Atenção",
                JOptionPane.YES_NO_OPTION
        );

        return dialogResult == JOptionPane.YES_OPTION;
    }

}
