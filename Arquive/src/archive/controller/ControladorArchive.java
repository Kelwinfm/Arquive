/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.controller;

import archive.dao.BinarioDAO;
import archive.dao.CabecalhoDAO;
import archive.exceptions.CabecalhoEsgotadoException;
import archive.model.Archive;
import archive.model.Arquivo;
import archive.model.Cabecalho;
import archive.model.ItemCabecalho;
import archive.model.ItemCabecalho.Status;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JFileChooser;

/**
 * Responsável por interpretar bytes a fim de se obter um Cabecalho
 */
public class ControladorArchive {

    public static final String EXTENSAO = ".dev";

    private static RandomAccessFile acessoArquivoAberto = null;

    private static Archive archiveAberto = null;

    public static Archive criarNovoArchive() throws IOException, CabecalhoEsgotadoException {
        JFileChooser selecionador = new JFileChooser();
        selecionador.setFileSelectionMode(JFileChooser.FILES_ONLY);
        selecionador.showSaveDialog(null);

        File arquivo = selecionador.getSelectedFile();
        if (!arquivo.toPath().toString().endsWith(EXTENSAO)) {
            arquivo = new File(arquivo.toPath() + EXTENSAO);
        }

        Cabecalho cabecalho = new Cabecalho();
        Archive archive = new Archive(cabecalho, arquivo);

        RandomAccessFile acessoArquivo = new RandomAccessFile(arquivo, "rw");
        CabecalhoDAO.gravarCabecalho(cabecalho, acessoArquivo);
        acessoArquivo.close();

        return archive;
    }

    public static void abrirArchive(Archive archive) throws FileNotFoundException {
        archiveAberto = archive;
        acessoArquivoAberto = new RandomAccessFile(archive.getArquivo(), "rw");
    }

    public static void fecharSessao() throws IOException {
        archiveAberto = null;
        acessoArquivoAberto.close();
        acessoArquivoAberto = null;
    }

    /**
     * Inserir arquivo no Archive atualmente aberto na sessão
     *
     * @param arquivo
     * @throws IOException
     * @throws CabecalhoEsgotadoException
     */
    public static void inserirArquivo(Arquivo arquivo) throws IOException, CabecalhoEsgotadoException {
        if (archiveAberto == null || acessoArquivoAberto == null) {
            return;
        }

        Cabecalho cabecalho = archiveAberto.getCabecalho();

        // Posição onde o conteúdo do arquivo será gravado
        int posicao;

        if (cabecalho.getItens().isEmpty()) {
            posicao = Cabecalho.TAMANHO_CABECALHO;
        } else {
            // Localizar lacuna disponível
            // First fit (primeiro encaixe)
            ItemCabecalho lacuna = null;
            for (ItemCabecalho i : cabecalho.getItens()) {
                if (i.getStatus() == Status.Excluido && i.getTamanho() >= arquivo.getTamanho()) {
                    lacuna = i;
                    break;
                }
            }

            // Se não houver lacuna disponível
            ItemCabecalho ultimoArquivo = cabecalho.getItemUltimoArquivo();
            if (lacuna == null) {
                // Obter posição do final do archive
                posicao = ultimoArquivo.getPosicao() + ultimoArquivo.getTamanho();
            } else {
                // Obter posição da lacuna
                posicao = lacuna.getPosicao();

                // Atualizar cabecalho
                if (lacuna.getTamanho() == arquivo.getTamanho() || lacuna == ultimoArquivo) {
                    // Remover lacuna (arquivo apagado) do cabeçalho
                    cabecalho.removerItem(lacuna);
                } else {
                    // Redimensionar e reposicionar lacuna
                    lacuna.setTamanho(lacuna.getTamanho() - arquivo.getTamanho());
                    lacuna.setPosicao(lacuna.getPosicao() + arquivo.getTamanho());
                }
            }
        }

        // Inserir conteúdo binário no archive
        BinarioDAO.gravarArquivo(archiveAberto, acessoArquivoAberto, arquivo, posicao);

        // Inserir no cabeçalho
        cabecalho.adicionarItem(new ItemCabecalho(
                Status.Valido,
                arquivo.getNome(), posicao, arquivo.getTamanho()
        ));

        // Gravar cabeçalho no archive
        CabecalhoDAO.gravarCabecalho(cabecalho, acessoArquivoAberto);

    }

}
