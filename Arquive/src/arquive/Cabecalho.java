/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arquive;

/**
 * 
 * @author kelwin
 */
public class Cabecalho {
    private byte status;
    private int tamanhoNome;
    private String nome;
    private int posicao;
    private int tamanhoArq;

    public Cabecalho(byte status, int tamanhoNome, String nome, int posicao, int tamanhoArq) {
        this.status = status;
        this.tamanhoNome = tamanhoNome;
        this.nome = nome;
        this.posicao = posicao;
        this.tamanhoArq = tamanhoArq;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public int getTamanhoNome() {
        return tamanhoNome;
    }

    public void setTamanhoNome(int tamanhoNome) {
        this.tamanhoNome = tamanhoNome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public int getTamanhoArq() {
        return tamanhoArq;
    }

    public void setTamanhoArq(int tamanhoArq) {
        this.tamanhoArq = tamanhoArq;
    }
    
    
}
