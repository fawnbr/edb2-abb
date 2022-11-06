package com.edb2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // = {32, 13, 5, 41, 20, 36, 60};

        // === LENDO VALORES
        List<Integer> lista = lerValores("src/main/java/com/edb2/arquivo1.txt");
        Integer valores[] = new Integer[lista.size()];
        for (int i = 0; i < valores.length; i++) {
            valores[i] = lista.get(i);
        }

        ArvoreBinariadeBusca abb = new ArvoreBinariadeBusca(valores);

        // === LENDO OPERAÇÕES
        try (Scanner scanner = new Scanner(new File("src/main/java/com/edb2/arquivo2.txt"))) {
            while(scanner.hasNext()) {
                String linha = scanner.nextLine();
                String[] operacoes = linha.split(" ");

                // Cheia, Completa, Mediana, PreOrdem
                if(operacoes.length == 1) {
                    if(operacoes[0].equalsIgnoreCase("CHEIA")) {
                        // TODO
                    }
                    if(operacoes[0].equalsIgnoreCase("COMPLETA")) {
                        // TODO
                    }
                    if(operacoes[0].equalsIgnoreCase("PREORDEM")) {
                        System.out.println(abb.preOrdem());
                    }
                    if(operacoes[0].equalsIgnoreCase("MEDIANA")) {
                        // TODO
                    }
                }
                else if(operacoes.length == 2){
                    if(operacoes[0].equalsIgnoreCase("CHEIA")) {
                        // TODO
                    }
                }
                else {
                    throw new IllegalArgumentException("Número de parâmetros excedido!");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        abb.imprimir(1);
        System.out.println();
        //System.out.println(abb.media(41));
    }

    private static List<Integer> lerValores(String arquivo) {
        List<Integer> valores = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(arquivo))) {
            scanner.useDelimiter(" ");
            while(scanner.hasNext()) {
                valores.add(Integer.parseInt(scanner.next()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return valores;
    }
}
