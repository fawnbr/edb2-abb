package com.edb2;

/**
 * Um Nó para uma avore binária de busca de costura. Os atributos desse Nó são: valor, filhoEsquerda, filhoDireita, pai,
 * costuraEsquerda e costuraDireita. O valor é o valor do Nó (aqui a chave a informação serão representados por valor),
 * filhoEsquerda é o filho da esquerda, filhoDireita é o filho da direita, pai é o pai do Nó, costuraEsquerda é um booleano que
 * indica se o filho da esquerda é uma costura ou não e costuraDireita é um booleano que indica se o filho da direita é
 * uma costura ou não.
 *
 * Estrutura de um Nó:
 *
 *  |+++++++++++++++++++++++++++++++++++++++|
 *  |_______________|__PAI__|_______________|
 *  |    fEsq   |cE | VALOR | cD|    fDir   |
 *  |+++++++++++++++++++++++++++++++++++++++|
 *  Onde:
 *      fEsq = enderreço do filho da esquerda
 *      cE   = booleano que indica se o filho da esquerda é uma costura ou não (se for de costura, o endereço do filho da esquerda é o endereço do antecessor)
 *      fDir = endereço do filho da direita
 *      cD   = booleano que indica se o filho da direita é uma costura ou não (se for de costura, o endereço do filho da direita é o endereço do sucessor)
 *      PAI  = endereço do pai do nó
 *      VALOR= valor do nó (inteiro)
 * @author Lucas Nogueira (@fawnbr) e Ianco Oliveira (@ianco-so)
 * @since 04/11/2022
 */

public class No implements Comparable<No> {
    private Integer valor;

    private Boolean costuraEsquerda;
    private No filhoEsquerda;

    private Boolean costuraDireita;
    private No filhoDireita;

    private No pai;

    // CONSTRUTOR
    public No(Integer valor) {
        this.valor = valor;

        this.costuraEsquerda = false;
        this.filhoEsquerda = null;

        this.costuraDireita = false;
        this.filhoDireita = null;

        this.pai = null;
    }

    /**
     * Retorna se o nó é uma folha ou não. Um nó é uma folha se ele não tem filhos
     * então, se o filhoEsquerda e filhoDireita forem de costura, então o nó é uma folha.
     *
     * @return true se o nó é uma folha, false caso contrário.
     */
    public Boolean isFolha() {
        return this.isCosturaEsquerda() && this.isCosturaDireita();
    }

    // GETTERS E SETTERS
    public Integer getValor() {
        return this.valor;
    }
    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Boolean isCosturaEsquerda() {
        return this.costuraEsquerda;
    }
    public void setCosturaEsquerda(Boolean costuraEsquerda) {
        this.costuraEsquerda = costuraEsquerda;
    }

    public No getFilhoEsquerda() {
        return this.filhoEsquerda;
    }
    public void setFilhoEsquerda(No filhoEsquerda) {
        this.filhoEsquerda = filhoEsquerda;
    }

    public Boolean isCosturaDireita() {
        return this.costuraDireita;
    }
    public void setCosturaDireita(Boolean costuraDireita) {
        this.costuraDireita = costuraDireita;
    }

    public No getFilhoDireita() {
        return this.filhoDireita;
    }
    public void setFilhoDireita(No filhoDireita) {
        this.filhoDireita = filhoDireita;
    }

    public No getPai() {
        return this.pai;
    }
    public void setPai(No pai) {
        this.pai = pai;
    }

    @Override
    public String toString() {
        return  "{" +
                " valor='" + getValor() + "'" +
                ", costuraEsquerda='" + isCosturaEsquerda() + "'" +
                ", filhoEsquerda='" + getFilhoEsquerda() + "'" +
                ", costuraDireita='" + isCosturaDireita() + "'" +
                ", filhoDireita='" + getFilhoDireita() + "'" +
                ", pai='" + getPai() + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof No)) {
            return false;
        }
        No no = (No) o;
        return this.getValor().equals(no.getValor());
    }

    @Override
    public int compareTo(No o) {
        return this.getValor().compareTo(o.getValor());
    }
}
