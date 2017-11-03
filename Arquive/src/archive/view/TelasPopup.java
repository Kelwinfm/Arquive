/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.view;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Conjunto de métodos para obtenção de informações do usuário via prompt
 */
public class TelasPopup {

    public static File obterArquivoParaSalvar() {
        JFileChooser selecionador = new JFileChooser();
        selecionador.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (selecionador.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            return selecionador.getSelectedFile();
        }
        return null;
    }

    public static File obterArquivoParaAbrir() {
        JFileChooser selecionador = new JFileChooser();
        selecionador.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (selecionador.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return selecionador.getSelectedFile();
        }
        return null;
    }

    public static File obterDiretorioParaSalvar() {
        JFileChooser selecionador = new JFileChooser();
        selecionador.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selecionador.showSaveDialog(null);

        return selecionador.getSelectedFile();
    }

    private static boolean verificarConfirmacao(String mensagem, String titulo) {
        int dialogResult = JOptionPane.showConfirmDialog(
                null,
                mensagem,
                titulo,
                JOptionPane.YES_NO_OPTION
        );

        return dialogResult == JOptionPane.YES_OPTION;
    }

    public static boolean verificarConfirmacaoSobrescrita() {
        return verificarConfirmacao(
                "Você deseja realmente sobrescrever o arquivo existente?",
                "Atenção"
        );
    }

    public static void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

}
