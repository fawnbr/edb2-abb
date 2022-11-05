package com.edb2;

import java.util.List;
import java.util.Vector;

public class App {
    public static void main(String[] args) {
        Integer valores[] = {32, 13, 5, 41, 20, 36, 60};

        ArvoreBinariadeBusca abb = new ArvoreBinariadeBusca(valores);
        abb.imprimir(1);
        System.out.println();
        System.out.println(abb.media(41));
    }
}
