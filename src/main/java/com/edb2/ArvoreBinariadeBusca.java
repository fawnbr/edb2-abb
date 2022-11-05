package com.edb2;

import java.util.List;
import java.util.Vector;

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
 *      cE   = booleano que indica se o filho da esquerda é uma costura ou não 
 *             (se for de costura, o endereço do filho da esquerda é o endereço do antecessor)
 *      fDir = endereço do filho da direita
 *      cD   = booleano que indica se o filho da direita é uma costura ou não 
 *             (se for de costura, o endereço do filho da direita é o endereço do sucessor)
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
 *             |                  V   |                                                           |     V                |
 *             |  |+++++++++++++++++++++++++++++++++++++++|                   |+++++++++++++++++++++++++++++++++++++++|  |
 *             |  |END:_1x02______|_______|_______________|                   |END:_1x03______|__PAI__|_______________|  |  
 *             |  |           | 1 | VALOR | 1 |   1x01    |                   |    1x01   | 1 | VALOR | 1 |           |  |
 *             |  |+++++++++++++++++++++++++++++++++++++++|                   |+++++++++++++++++++++++++++++++++++++++|  |
 *             |        |                           |                                |                          |        |
 *             +--------|---------------------------+                                +--------------------------|--------+
 *                      V                                                                                       V
 *                     NULO                                                                                    NULO
 *  @author Lucas Nogueira (@fawnbr) e Ianco Oliveira (@ianco-so)
 *  @since 04/11/2022
*/                                                                                                   
public class ArvoreBinariadeBusca {
    private No raiz;
    private Integer tamanho;
    private Integer altura;

    public ArvoreBinariadeBusca(Integer valores[]) {
        this.raiz = null;
        this.tamanho = 0;
        this.altura = 0;
        valores = mergeSort(valores);
        System.out.println(valores);
        criarArvore(valores, 0, valores.length-1);
    }
    /**
     * Cria uma arvore binaria de busca com os valores da lista já ordenados
     * Insere os valores da lista de forma recursiva
     * @param valores
     * @param i
     * @param j
     */
    private void criarArvore(Integer valores[], int i, int j) {
        if (i <= j) {
            int meio = (i + j) / 2;
            inserir(valores[meio]);
            criarArvore(valores, i, meio - 1);
            criarArvore(valores, meio + 1, j);
        }
    }
    /**
     * Ordena um array de Inteiros usando o algoritmo de ordenação MergeSort
     * @param um array de inteiros
     * @return um array de inteiros ordenado
     */
    private static Integer[] mergeSort(Integer[] valores) {
        if (valores.length <= 1) {
            return valores;
        }
        int meio = valores.length / 2;
        Integer[] esquerda = new Integer[meio];
        Integer[] direita = new Integer[valores.length - meio];
        for (int i = 0; i < meio; i++) {
            esquerda[i] = valores[i];
        }
        for (int i = meio; i < valores.length; i++) {
            direita[i - meio] = valores[i];
        }
        esquerda = mergeSort(esquerda);
        direita = mergeSort(direita);
        Integer[] resultado = merge(esquerda, direita);
        return resultado;
    }
    /**
     * Mescla dois arrays de inteiros em um só
     * @param esquerda
     * @param direita
     * @return um array de inteiros ordenado
     */
    private static Integer[] merge(Integer[] esquerda, Integer[] direita) {
        Integer[] resultado = new Integer[esquerda.length + direita.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < esquerda.length && j < direita.length) {
            if (esquerda[i] <= direita[j]) {
                resultado[k] = esquerda[i];
                i++;
            } else {
                resultado[k] = direita[j];
                j++;
            }
            k++;
        }
        while (i < esquerda.length) {
            resultado[k] = esquerda[i];
            i++;
            k++;
        }
        while (j < direita.length) {
            resultado[k] = direita[j];
            j++;
            k++;
        }
        return resultado;
    }
    /**
     * Retorna o tamanho da arvore, ou seja, a quantidade de nós.
     * @return tamanho da arvore
     */
    public Integer getTamanho() {
        return this.tamanho;
    }
    /**
     * Calcula recursivamente a altura da arvore a partir da raiz
     * @return altura da arvore
     */
    public Integer getAltura() {
        this.altura = getAltura(this.raiz);
        return this.altura;
    }
    private static Integer getAltura(No no) {
        if (no == null) {
            return -1;
        }
        return 1 + Math.max(getAltura(no.isCosturaEsquerda()? null : no.getFilhoEsquerda()), getAltura(no.isCosturaDireita()? null : no.getFilhoDireita()));
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
            //O nó a ser removido é substituido pelo seu sucessor.
            //Como, ou o sucessor é folha ou tem apenas um filho, o processo de remoção é o mesmo.
            No sucessor = sucessor(no);
            no.setValor(sucessor.getValor());
            if (sucessor.isFolha()) {
                if (sucessor.getPai() == null) { //Apenas o nó raiz tem pai nulo
                    this.raiz = null;
                } else if (sucessor.getPai().getFilhoEsquerda() == sucessor) { //É filho da esquerda do pai
                    sucessor.getPai().setCosturaEsquerda(true);
                    //O antecessor do pai passa a ser o antecessor do nó a ser removido
                    sucessor.getPai().setFilhoEsquerda(sucessor.getFilhoEsquerda());
                    
                } else {
                    sucessor.getPai().setCosturaDireita(true);
                    //O sucessor do pai passa a ser o sucessor do nó a ser removido
                    sucessor.getPai().setFilhoDireita(sucessor.getFilhoDireita());
                }
                this.tamanho--;
                return true;
            } else {
                if (sucessor.getFilhoDireita().isCosturaEsquerda()) { 
                    //Se o filho tem nó de costura na esquerda este aponta para o antecessor, nesse caso o pai.
                    //Como o pai será removido, então o antecessor do filho passa a ser o antecessor do pai.
                    //E ele tem nó de costura na esquerda.
                    sucessor.getFilhoDireita().setFilhoEsquerda(antecessor(sucessor));
                }
                if (sucessor.getPai() == null) {
                    this.raiz = sucessor.getFilhoDireita();
                    this.raiz.setPai(null);
                } else if (sucessor.getPai().getFilhoEsquerda() == sucessor) { //Se o nó é  filho da esquerda do pai
                    sucessor.getPai().setFilhoEsquerda(sucessor.getFilhoDireita());
                    sucessor.getFilhoDireita().setPai(sucessor.getPai());
                } else {
                    sucessor.getPai().setFilhoDireita(sucessor.getFilhoDireita());
                    sucessor.getFilhoDireita().setPai(sucessor.getPai());
                }
                this.tamanho--;
                return true;
            }
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
    /**
     * Imprime a arovre inteira em pré-ordem no formato desejado. Exemplo:
     *  para a lista a = 32, 13, 5, 41, 20, 36, 60.
     * A árvore terá a seguinte forma hierárquica:
     *              	+--------32--------+
     *               	|                  |
	 *                  V                  V
     *             +----13----+       +----41----+
     *             |          |       |          |
     *             V  	      V       V	         V
     *             5          20      36         60     
     * A impressão 1 será da seguinte forma: (Diagrama de Barras)
     *  32------------------
     *      13-------------- 
     *          5-----------
     *          20----------
     *      41--------------
     *          36----------
     *          60----------
     * A impressão 2 será da seguinte forma: (Aninhamento)
     * (32 (13 (5) (20)) (41 (60)))
     * @param s Formato de impressão da árvore. 1 para diagrama de barras e 2 para aninhamento.
     */
    public void imprimir(int s) {
        if (s == 1) {
            imprimirDiagramaBarras(this.raiz, 0);
        } else if (s == 2) {
            imprimirAninhamento(this.raiz);
        }
    }
    private void imprimirDiagramaBarras(No no, int n) {
        //Percorrer a árvore em pré-ordem e imprimir os nós.
        if (no != null) { 
            for (int i = 0; i < n; i++) {
                System.out.print("    ");
            }
            System.out.println(no.getValor() + "------------------");
            imprimirDiagramaBarras(no.isCosturaEsquerda()? null : no.getFilhoEsquerda(), n + 1);
            imprimirDiagramaBarras(no.isCosturaDireita()? null : no.getFilhoDireita(), n + 1);
        }
    }
    private void imprimirAninhamento(No no) {
        if (no != null) {
            System.out.print("(" + no.getValor());
            if (!no.isFolha()) System.out.print(" ");
            imprimirAninhamento(no.isCosturaEsquerda()? null : no.getFilhoEsquerda());
            imprimirAninhamento(no.isCosturaDireita()? null : no.getFilhoDireita());
            System.out.print(")");
        }
    }
    /**
     * Retorna uma string contendo os valores da árvore em pré-ordem.
     * @return uma string contendo os valores da árvore em pré-ordem.
     */
    String preOrdem() {
        return preOrdem(this.raiz);
    }
    private String preOrdem(No no) {
        if (no == null) {
            return "";
        }
        return  no.getValor() +
                " " + preOrdem(no.isCosturaEsquerda()? null : no.getFilhoEsquerda()) +
                preOrdem(no.isCosturaDireita()? null : no.getFilhoDireita());
    }
    /**
     * Retorna uma string contendo os valores da árvore em ordem simétrica.
     * @return uma string contendo os valores da árvore em ordem simétrica.
     */
    String emOrdem() {
        return emOrdem(this.raiz);
    }
    
    private String emOrdem(No no) {
        if (no == null) {
            return "";
        }
        return  emOrdem(no.isCosturaEsquerda()? null : no.getFilhoEsquerda()) +
                no.getValor() +
                " " + emOrdem(no.isCosturaDireita()? null : no.getFilhoDireita());
    }
    /**
     * Dado um valor x, retorna a posição em que ele se encontra na árvore.
     * A posição refere-se a ordem simétrica da árvore.
     * Se o valor não estiver na árvore, retorna -1.
     * @param x o valor a ser pesquisado.
     * @return a posição em que o valor x se encontra na árvore.
     */
    // public Integer posicao(Integer x) {
    //     return posicao(this.raiz, x, 0);
    // }
    // private Integer posicao(No no, Integer x, int i) {
    //     if (no == null) {
    //         return 0;
    //     }
    //     Integer posicao = posicao(no.isCosturaEsquerda()? null : no.getFilhoEsquerda(), x, i);


    // }
    /**
     * Dado o nó da árvore, percorre a sub-árvore da qual esse nó é raiz e retorna a média dos valores dos nós.
     * @return a média dos valores dos nós de uma sub-árvore.
     */
    // Double media(Integer valor) {
    //     No no = this.buscar(valor);
    //     if (no == null) {
    //         return 0.0;
    //     } else {
    //         return media(no, 0, 0);
    //     }
    // }
    // private Double media(No no, int sum, int qNos) {
    //     if (no == null) {
    //         return 0.0;
    //     } else {
    //         sum += no.getValor();
    //         qNos++;
    //         return  (sum / qNos) +
    //                 media(no.isCosturaEsquerda()? null : no.getFilhoEsquerda(), sum, qNos) +
    //                 media(no.isCosturaDireita()? null : no.getFilhoDireita(), sum, qNos);
    //     }
    // }

    // public Boolean ehCompleta () {
    //     return ehCompleta(this.raiz);
    // }
    // private Boolean ehCompleta(No no) {
    //     if (no == null) {
    //         return true;
    //     } else {
    //         if (no.isFolha()) {
    //             return true;
    //         } else if (no.isCosturaEsquerda() && no.isCosturaDireita()) {
    //             return true;
    //         } else if (no.isCosturaEsquerda() && !no.isCosturaDireita()) {
    //             return false;
    //         } else if (!no.isCosturaEsquerda() && no.isCosturaDireita()) {
    //             return false;
    //         } else {
    //             return ehCompleta(no.getFilhoEsquerda()) && ehCompleta(no.getFilhoDireita());
    //         }
    //     }
    // }
    // public Boolean ehCheia () {
    //     return ehCheia(this.raiz);
    // }
    // private Boolean ehCheia(No no) {
    //     if (no == null) {
    //         return true;
    //     } else {
    //         if (no.isFolha()) {
    //             return true;
    //         } else if (no.isCosturaEsquerda() && no.isCosturaDireita()) {
    //             return true;
    //         } else if (no.isCosturaEsquerda() && !no.isCosturaDireita()) {
    //             return false;
    //         } else if (!no.isCosturaEsquerda() && no.isCosturaDireita()) {
    //             return false;
    //         } else {
    //             return ehCheia(no.getFilhoEsquerda()) && ehCheia(no.getFilhoDireita());
    //         }
    //     }
    // }
}