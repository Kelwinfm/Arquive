/**
 * Arquive
 *
 * Equipe desenvolvedora do sistema Arquive para Estrutura de Arquivos
 * Universidade Estadual de Campinas - 2017
 */
package archive.controller;

import archive.dao.ArchiveDAO;
import archive.dao.BinarioDAO;
import archive.dao.CabecalhoDAO;
import archive.dao.GenericDAO;
import archive.exceptions.CabecalhoCorrompidoException;
import archive.exceptions.CabecalhoEsgotadoException;
import archive.model.Archive;
import archive.model.Arquivo;
import archive.model.Cabecalho;
import archive.model.ItemCabecalho;
import archive.model.ItemCabecalho.Status;
import archive.view.TelaGerenciamento;
import archive.view.TelasPopup;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsável por interpretar bytes a fim de se obter um Cabecalho
 */
public class ControladorArchive {

    public static final String EXTENSAO = ".dev";

    private static RandomAccessFile acessoArquivoAberto = null;

    private static Archive archiveAberto = null;

    /**
     *
     * @return Archive criado OU null se não for criado
     * @throws IOException
     * @throws CabecalhoEsgotadoException
     */
    public static Archive criarNovoArchive(File arquivo)
            throws IOException, CabecalhoEsgotadoException {
        if (!arquivo.toPath().toString().endsWith(EXTENSAO)) {
            arquivo = new File(arquivo.toPath() + EXTENSAO);
        }

        // Verificar se arquivo existe
        if (arquivo.exists()) {
            if (TelasPopup.verificarConfirmacaoSobrescrita() == false) {
                // O usuário deseja cancelar a operação abrir archive
                return null;
            }
        }

        // Limpar arquivo
        if (arquivo.exists()) {
            arquivo.delete();
        }
        arquivo.createNewFile();

        // Criar cabeçalho vazio e adicionar ao arquivo
        Cabecalho cabecalho = new Cabecalho();
        Archive archive = new Archive(cabecalho, arquivo);

        RandomAccessFile acessoArquivo = new RandomAccessFile(arquivo, "rw");
        CabecalhoDAO.gravarCabecalho(acessoArquivo, cabecalho);
        acessoArquivo.close();

        return archive;
    }

    /**
     * Abre um archive no gerenciador de arquivos. Retorna verdadeiro caso o
     * arquivo seja aberto no gerenciador
     *
     * @param file
     * @return Verdadeiro caso o archive seja aberto no gerenciador
     * @throws FileNotFoundException
     */
    public static boolean abrirArchive(File file)
            throws FileNotFoundException, CabecalhoCorrompidoException, IOException {
        acessoArquivoAberto = new RandomAccessFile(file, "rw");

        // Carregar cabeçalho do archive
        Cabecalho cabecalho = CabecalhoDAO.carregarCabecalho(acessoArquivoAberto);

        archiveAberto = new Archive(cabecalho, file);

        // Exibir tela de gerenciamento
        TelaGerenciamento telaGerenciamento = new TelaGerenciamento(archiveAberto);
        telaGerenciamento.setVisible(true);

        return true;
    }

    public static void fecharSessao() throws IOException {
        archiveAberto = null;
        if (acessoArquivoAberto == null) {
            assert false : "Nenhum arquivo aberto na sessão";
        } else {
            acessoArquivoAberto.close();
            acessoArquivoAberto = null;
        }
    }

    /**
     * Inserir arquivo no Archive atualmente aberto na sessão
     *
     * @param file
     * @throws IOException
     * @throws CabecalhoEsgotadoException
     */
    public static void inserirArquivo(File file) throws IOException, CabecalhoEsgotadoException {
        if (archiveAberto == null || acessoArquivoAberto == null) {
            assert false : "Nenhum arquivo aberto na sessão";
            return;
        }

        byte[] bytesArquivo = Files.readAllBytes(file.toPath());

        Arquivo arquivo = new Arquivo(file.getName(), bytesArquivo);

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

        // Novo item para o cabeçalho
        ItemCabecalho novoItem = new ItemCabecalho(
                Status.Valido,
                arquivo.getNome(), posicao, arquivo.getTamanho()
        );

        // Inserir conteúdo binário no archive
        BinarioDAO.gravarArquivo(archiveAberto, acessoArquivoAberto, arquivo, novoItem);

        // Inserir no cabeçalho
        cabecalho.adicionarItem(novoItem);

        // Gravar cabeçalho no archive
        CabecalhoDAO.gravarCabecalho(acessoArquivoAberto, cabecalho);

    }

    /**
     * Obter um arquivo no Archive atualmente aberto na sessão a partir do seu
     * nome
     *
     * @param nome Nome do arquivo a ser localizado no cabeçalho
     * @throws IOException
     * @return Arquivo encontrado OU nulo se não for encontrado com este nome
     */
    public static Arquivo obterArquivo(String nome) throws IOException {
        if (archiveAberto == null) {
            assert false : "Nenhum arquivo aberto na sessão";
            return null;
        }

        Cabecalho cabecalho = archiveAberto.getCabecalho();

        for (ItemCabecalho itemCabecalho : cabecalho.getItens()) {
            if (itemCabecalho.getNome().equals(nome)) {

                Arquivo arquivo = BinarioDAO.lerArquivo(
                        archiveAberto,
                        acessoArquivoAberto,
                        itemCabecalho
                );

                return arquivo;
            }
        }

        return null;
    }

    /**
     * Apagar arquivo do Archive atualmente aberto na sessão a partir do seu
     * nome. Vários serão apagados se tiverem o mesmo nome
     *
     * @param nome Nome do arquivo a ser apagado
     * @throws IOException
     * @throws CabecalhoEsgotadoException
     */
    public static void apagarArquivo(String nome)
            throws IOException, CabecalhoEsgotadoException {
        if (archiveAberto == null) {
            assert false : "Nenhum arquivo aberto na sessão";
            return;
        }

        Cabecalho cabecalho = archiveAberto.getCabecalho();

        for (ItemCabecalho itemCabecalho : cabecalho.getItens()) {
            if (itemCabecalho.getNome().equals(nome)) {
                itemCabecalho.setStatus(Status.Excluido);

                // Verificar posição física no archive
                if (itemCabecalho == cabecalho.getItemUltimoArquivo()) {
                    // O arquivo ocupa última posição
                    ArchiveDAO.removerArquivoDoFinal(acessoArquivoAberto, itemCabecalho);
                }
            }
        }

        // Regravar cabeçalho no arquivo
        CabecalhoDAO.gravarCabecalho(acessoArquivoAberto, cabecalho);
    }

    /**
     * Obter lista de arquivos válidos no archive aberto na sessão
     *
     * @return
     */
    public static List<ItemCabecalho> listarArquivos() {
        List<ItemCabecalho> lista = new ArrayList<>();

        if (archiveAberto == null) {
            assert false : "Nenhum arquivo aberto na sessão";
            return lista;
        }

        for (ItemCabecalho itemCabecalho : archiveAberto.getCabecalho().getItens()) {
            if (itemCabecalho.getStatus() == Status.Valido) {
                lista.add(itemCabecalho);
            }
        }

        return lista;
    }

    /**
     * Extrair arquivos do archive aberto na sessão a partir de seus nomes
     *
     * @param nomesArquivos
     * @param local Local para onde os arquivos serão extraídos
     */
    public static void extrairArquivos(List<String> nomesArquivos, File local)
            throws IOException {
        if (archiveAberto == null) {
            assert false : "Nenhum arquivo aberto na sessão";
            return;
        }

        for (String nome : nomesArquivos) {
            Cabecalho cabecalho = archiveAberto.getCabecalho();

            for (ItemCabecalho item : cabecalho.getItens()) {
                if (item.getNome().equals(nome)) {
                    // Extrair este item

                    Arquivo arquivo = BinarioDAO.lerArquivo(
                            archiveAberto, acessoArquivoAberto, item
                    );

                    File arquivoSalvar = new File(local, arquivo.getNome());

                    if (arquivoSalvar.exists()) {
                        if (!TelasPopup.verificarConfirmacaoSobrescrita()) {
                            continue;
                        }
                    }

                    RandomAccessFile acessoArquivoSalvar = new RandomAccessFile(
                            arquivoSalvar, "rw"
                    );
                    GenericDAO.gravarBytes(acessoArquivoSalvar, 0, arquivo.getConteudo());
                }
            }
        }
    }

}
