/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.controller;

import archive.dao.CabecalhoDAO;
import archive.exceptions.CabecalhoEsgotadoException;
import archive.model.Archive;
import archive.model.Cabecalho;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import javax.swing.JFileChooser;

/**
 * Responsável por interpretar bytes a fim de se obter um Cabecalho
 */
public class ControladorArchive {

    public static final String EXTENSAO = ".dev";

    private static RandomAccessFile arquivoAberto;

    public Archive archiveAberto;

    public static Archive criarNovoArchive() throws IOException, CabecalhoEsgotadoException {
        JFileChooser selecionador = new JFileChooser();
        selecionador.setFileSelectionMode(JFileChooser.FILES_ONLY);
        selecionador.showSaveDialog(null);

        File arquivo = selecionador.getSelectedFile();
        if (!arquivo.toPath().endsWith(EXTENSAO)) {
            arquivo = new File(arquivo.toPath() + EXTENSAO);
        }

        Cabecalho cabecalho = new Cabecalho();
        Archive archive = new Archive(cabecalho, arquivo);
        
        RandomAccessFile acessoArquivo = new RandomAccessFile(arquivo, "rw");
        CabecalhoDAO.gravarCabecalho(cabecalho, acessoArquivo);
        acessoArquivo.close();
        
        return archive;
    }
    
    public static void abrirArchive(Archive archive){
        RandomAccessFile acessoArquivo = new RandomAccessFile(arquivo, "rw");
        
        archive.getArquivo()
    }

    public static void salvarArchive(Archive archive) {
        
    }

}
