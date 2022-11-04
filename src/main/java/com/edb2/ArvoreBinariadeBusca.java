package com.edb2;

/**
 *  Assim como uma arvore generaliza uma lista encadeada, uma arvore binaria de busca generaliza uma
 * lista ordenada. Uma arvore binaria de busca permite inserção de elementos não previamente inseridos,
 * a remoção e a busca (tudo em tempo logaritmico).
 *  Essa implementação usa nós com costura, ou seja, não há necessidade de ponteiros nulos para os campos de
 * filho esquerdo e direito. A costura é um ponteiro para o sucessor ou antecessor do nó no percurso em ordem simétrica.
 * Caso o nó não tenha sucessor ou antecessor, o ponteiro para o sucessor ou antecessor será nulo mas o nó será de costura.
 *  Note que apenas o ponteiro de pai do nó raiz é nulo.
 * 
 *  Estrutura de um Nó:
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
 *
 *  Estrutura da Arvore de Busca com Costura:
 *                                                                  NULO
 *                                                                    ^
 *                                                                    |
 *                                                                    |
 *                                                |+++++++++++++++++++++++++++++++++++++++|
 *             +--------------------------------->|END:_1x01______|_______|_______________|<-----------------------------+
 *             |                                  |    1x02   | 0 | VALOR | 0 |   1x03    |                              |
 *             |                                  |+++++++++++++++++++++++++++++++++++++++|                              |
 *             |                                        |            ^   ^          |                                    |
 *             |                  +---------------------|            |   |          +-------------------+                |
 *             |                  |   +------------------------------+   +------------------------+     |                |
 *             |                  v   |                                                           |     V                |
 *             |  |+++++++++++++++++++++++++++++++++++++++|                   |+++++++++++++++++++++++++++++++++++++++|  |
 *             |  |END:_1x02______|_______|_______________|                   |END:_1x03______|__PAI__|_______________|  |  
 *             |  |           | 1 | VALOR | 1 |   1x01    |                   |    1x01   | 1 | VALOR | 1 |           |  |
 *             |  |+++++++++++++++++++++++++++++++++++++++|                   |+++++++++++++++++++++++++++++++++++++++|  |
 *             |        |                           |                                |                          |        |
 *             +--------|---------------------------+                                +--------------------------|--------+
 *                      v                                                                                       v
 *                     NULO                                                                                    NULO
 *  @author Lucas Nogueira (@fawnbr) e Ianco Oliveira (@ianco-so)
 *  @since 04/11/2022
*/                                                                                                   
public class ArvoreBinariadeBusca {
    private No raiz;
    private Integer tamanho;
    private Integer altura;

    public ArvoreBinariadeBusca() {
        this.raiz = null;
        this.tamanho = 0;
        this.altura = 0;
    }
    // public ArvoreBinariadeBusca() {
    // }
    /**
     * Retorna o tamanho da arvore, ou seja, a quantidade de nós.
     * @return tamanho da arvore
     */
    public Integer getTamanho() {
        return this.tamanho;
    }
    /**
     * Retorna a altura da arvore
     * @return altura da arvor
     */
    public Integer getAltura() { //TODO: implementar
        return this.altura;
    }
    /**
     * Verifica se a arvore está vazia
     * @return true se a arvore estiver vazia, false caso contrário
     */
    public Boolean vazia() {
        return this.raiz == null;
    }

    /**
     * Bunca um nó na arvore binaria de busca que contem o valor passado como parametro,
     * caso o valor não esteja na arvore, retorna null.
     * @param valor
     * @return No
     */
    public No buscar (Integer valor) {
        return buscar(this.raiz, valor);
    }
    private No buscar(No no, Integer valor) {
        if (no == null) {
            return null;
        }
        if (no.getValor() == valor) {
            return no;
        }
        if (valor < no.getValor()) {
            return no.isCosturaEsquerda() ? null : buscar(no.getFilhoEsquerda(), valor); //Lembre-se que se o filho da esquerda for uma costura, ele não tem filho da esquerda
        }
        return no.isCosturaDireita() ? null : buscar(no.getFilhoDireita(), valor);
    }

    /**
     * Insere um novo elemento na arvore binaria de busca.
     * Um elemento não pode ser inserido se já existir na arvore.
     * Esse é um método recursivo.
     * @param valor O valor a ser inserido.
     * @return true se o elemento foi inserido com sucesso, false caso contrário.
     */
    public Boolean inserir(Integer valor) {
        if (this.raiz == null) {
            this.raiz = new No(valor);
            this.raiz.setPai(null);

            this.raiz.setCosturaDireita(true);
            this.raiz.setFilhoDireita(raiz);                    // O sucessor do nó raiz é ele mesmo caso ele não tenha filho da direita
            this.raiz.setCosturaEsquerda(true);
            this.raiz.setFilhoEsquerda(raiz);                   // O antecessor do nó raiz é ele mesmo caso ele não tenha filho da esquerda

            this.tamanho++;
            return true;
        }
        return inserir(this.raiz, valor);
    }
    private Boolean inserir(No no, Integer valor) {
        if (no.getValor() == valor) {
            return false;
        }
        if (valor < no.getValor()) {
            if (no.isCosturaEsquerda()) {
                No novoNo = new No(valor);
                novoNo.setPai(no);
                //Atualiza o sucessor e antecessor do novo nó. Um nó recém inserido sempre será um nó folha.
                //Como aqui ele é filho da esquerda, seu sucessor será o seu pai.
                novoNo.setCosturaDireita(true);
                novoNo.setFilhoDireita(novoNo.getPai());
                //O seu antecessor será o antecessor do seu pai.
                novoNo.setCosturaEsquerda(true);
                novoNo.setFilhoEsquerda(novoNo.getPai().getFilhoEsquerda());
                
                //Agora nó da esquerda do pai não é mais uma costura, ele aponta para o novo nó.
                no.setFilhoEsquerda(novoNo);
                no.setCosturaEsquerda(false);
            
                this.tamanho++;
                return true;
            }
            return inserir(no.getFilhoEsquerda(), valor);
        }
        if (no.isCosturaDireita()) {
            No novoNo = new No(valor);
            novoNo.setPai(no);
            //Atualiza o sucessor e antecessor do novo nó. Um nó recém inserido sempre será um nó folha.
            //Como aqui ele é filho da direita, seu antecessor será o seu pai.
            novoNo.setCosturaEsquerda(true);
            novoNo.setFilhoEsquerda(novoNo.getPai());
            //O seu sucessor será o sucessor do seu pai.
            novoNo.setCosturaDireita(true);
            novoNo.setFilhoDireita(novoNo.getPai().getFilhoDireita());

            //Agora nó da direita do pai não é mais uma costura, ele aponta para o novo nó.
            no.setFilhoDireita(novoNo);
            no.setCosturaDireita(false);

            this.tamanho++;
            return true;
        }
        return inserir(no.getFilhoDireita(), valor);
    }
    /**
     * Dado um valor, remove o nó que contem esse valor da arvore binaria de busca.
     * Caso o valor não esteja na arvore, não faz nada (não gera erro).
     * @param valor O valor a ser removido.
     * @return true se o elemento foi removido com sucesso, false caso contrário.
     */
    public Boolean remover(Integer valor) {
        No no = buscar(valor);
        if (no == null) {
            return false;
        }
        if (no.isFolha()) {
            if (no.getPai() == null) { //Apenas o nó raiz tem pai nulo
                this.raiz = null;
            } else if (no.getPai().getFilhoEsquerda() == no) { //É filho da esquerda do pai
                no.getPai().setCosturaEsquerda(true);
                //O antecessor do pai passa a ser o antecessor do nó a ser removido
                no.getPai().setFilhoEsquerda(no.getFilhoEsquerda());
                
            } else {
                no.getPai().setCosturaDireita(true);
                //O sucessor do pai passa a ser o sucessor do nó a ser removido
                no.getPai().setFilhoDireita(no.getFilhoDireita());
            }
            this.tamanho--;
            return true;
        } else if(no.isCosturaEsquerda()) { //Tem apenas o filho da direita
            if (no.getFilhoDireita().isCosturaEsquerda()) { 
                //Se o filho tem nó de costura na esquerda este aponta para o antecessor, nesse caso o pai.
                //Como o pai será removido, então o antecessor do filho passa a ser o antecessor do pai.
                //E ele tem nó de costura na esquerda.
                no.getFilhoDireita().setFilhoEsquerda(antecessor(no));
            }
            if (no.getPai() == null) {
                this.raiz = no.getFilhoDireita();
                this.raiz.setPai(null);
            } else if (no.getPai().getFilhoEsquerda() == no) { //Se o nó é  filho da esquerda do pai
                no.getPai().setFilhoEsquerda(no.getFilhoDireita());
                no.getFilhoDireita().setPai(no.getPai());
            } else {
                no.getPai().setFilhoDireita(no.getFilhoDireita());
                no.getFilhoDireita().setPai(no.getPai());
            }
            this.tamanho--;
            return true;
        } else if(no.isCosturaDireita()) { //Tem apenas o filho da esquerda
            if (no.getFilhoEsquerda().isCosturaDireita()) {
                //No caso do filho do no a ser removido ser na esquerda, o sucessor do filho era o pai.
                //Como o pai será removido, então o sucessor do filho passa a ser o sucessor do pai.
                //E ele tem nó de costura na direita.
                no.getFilhoDireita().setFilhoEsquerda(antecessor(no));
            }
            if (no.getPai() == null) {
                this.raiz = no.getFilhoEsquerda();
                this.raiz.setPai(null);
            } else if (no.getPai().getFilhoEsquerda() == no) {
                no.getPai().setFilhoEsquerda(no.getFilhoEsquerda());
                no.getFilhoEsquerda().setPai(no.getPai());
            } else {
                no.getPai().setFilhoDireita(no.getFilhoEsquerda());
                no.getFilhoEsquerda().setPai(no.getPai());
            }
            this.tamanho--;
            return true;
        } else { //Tem filhos a esquerda e a direita
            //O nó a ser removido é substituido pelo seu sucessor, que será necessariamente uma folha.
            //Então, após a substituição, o nó na posição do sucessor pode ser removido.
            No sucessor = sucessor(no);
            no.setValor(sucessor.getValor());
            if (sucessor.getPai().getFilhoEsquerda() == sucessor) {
                sucessor.getPai().setCosturaEsquerda(true);
                sucessor.getPai().setFilhoEsquerda(sucessor.getFilhoEsquerda());
            } else {
                sucessor.getPai().setCosturaDireita(true);
                sucessor.getPai().setFilhoDireita(sucessor.getFilhoDireita());
            }
            this.tamanho--;
            return true;
        }
    }
    /**
     * Pesquisa o sucessor do nó atual, em ordem simétrica da árvore binária de busca com costura.
     * Se o nó atual não tiver sucessor, retorna null.
     * @return o sucessor do nó atual ou null se o nó atual não tiver sucessor.
     */
    public static No sucessor(No atual) {
        if (atual.isCosturaDireita()) {
            return atual.getFilhoDireita();
        } else {
            No sucessor = atual.getFilhoDireita();
            while (!sucessor.isCosturaEsquerda()) {
                sucessor = sucessor.getFilhoEsquerda();
            }
            return sucessor;
        }
    }
    /**
     * Pesquisa o antecessor do nó atual, em ordem simétrica da árvore binária de busca com costura.
     * Se o nó atual não tiver antecessor, retorna null.
     * @return o antecessor do nó atual ou null se o nó atual não tiver antecessor.
     */
    public static No antecessor(No atual) {
        if (atual.isCosturaEsquerda()) {
            return atual.getFilhoEsquerda();
        } else {
            No antecessor = atual.getFilhoEsquerda();
            while (!antecessor.isCosturaDireita()) {
                antecessor = antecessor.getFilhoDireita();
            }
            return antecessor;
        }
    }
}