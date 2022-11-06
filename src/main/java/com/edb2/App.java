package com.edb2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

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
                        if(abb.ehCheia()) {
                            System.out.println("A árvore é cheia");
                        }
                        else {
                            System.out.println("A árvore não é cheia");
                        }
                    }
                    else if(operacoes[0].equalsIgnoreCase("COMPLETA")) {
                        if(abb.ehCompleta()) {
                            System.out.println("A árvore é completa");
                        }
                        else {
                            System.out.println("A árvore não é completa");
                        }
                    }
                    else if(operacoes[0].equalsIgnoreCase("PREORDEM")) {
                        System.out.println(abb.preOrdem());
                    }
                    else if(operacoes[0].equalsIgnoreCase("MEDIANA")) {
                        System.out.println(abb.mediana());
                    }
                }
                // Enesimo, Insira, Imprima, Remova, Posicao, Media, Buscar
                else if(operacoes.length == 2){
                    Integer valor = Integer.parseInt(operacoes[1]);
                    if(operacoes[0].equalsIgnoreCase("ENESIMO")) {
                        System.out.println(abb.enesimoElemento(valor));
                    }
                    else if(operacoes[0].equalsIgnoreCase("INSIRA")) {
                        if(abb.inserir(valor)) {
                            System.out.println(valor + " adicionado");
                        }
                        else {
                            System.out.println(valor + " já está na árvore, não pode ser inserido");
                        }
                    }
                    else if(operacoes[0].equalsIgnoreCase("IMPRIMA")) {
                        abb.imprimir(valor);
                        System.out.println();
                    }
                    else if(operacoes[0].equalsIgnoreCase("REMOVA")) {
                        if(abb.remover(valor)) {
                            System.out.println(valor + " removido");
                        }
                        else {
                            System.out.println(valor + " não está na árvore, não pode ser removido");
                        }
                    }
                    else if(operacoes[0].equalsIgnoreCase("POSICAO")) {
                        System.out.println(abb.posicao(valor));
                    }
                    else if(operacoes[0].equalsIgnoreCase("MEDIA")) {
                       System.out.println(abb.media(valor));
                    }
                    else if(operacoes[0].equalsIgnoreCase("BUSCAR")) {
                        if(abb.buscar(valor) != null) {
                            System.out.println("Chave encontrada");
                        }
                        else {
                            System.out.println("Chave não encontrada");
                        }
                    }
                }
                else {
                    throw new IllegalArgumentException("Número de parâmetros excedido!");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
