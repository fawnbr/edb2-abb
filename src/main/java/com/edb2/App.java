package com.edb2;

public class App {
    public static void main(String[] args) {
        Integer valores[] = {32, 13, 5, 41, 20, 36, 60};

        ArvoreBinariadeBusca abb = new ArvoreBinariadeBusca(valores);
        abb.imprimir(1);
        System.out.println();
        System.out.println(abb.emOrdem());
        System.out.println(abb.ehCompleta());
        System.out.println(abb.ehCheia());
    }
}