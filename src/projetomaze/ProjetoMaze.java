/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetomaze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author PedroHenrique
 */
public class ProjetoMaze {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Insira o tamanho do labirinto: ");
        int tamanho = sc.nextInt();
        Maze maze = new Maze(tamanho, tamanho, 1, 1, tamanho * 4 - 1, tamanho * 2 - 1);
        maze.display();
        Ponto point = new Ponto(1, 1);
        Ponto.setMaze(maze);

        Busca busca = new Busca(point, maze);

        Node resultado = busca.search();

        List<Node> nodesParents = new ArrayList<>();
        while (resultado != null) {
            nodesParents.add(resultado);
            resultado = resultado.getParent();
        }

        Collections.reverse(nodesParents);

        for (Node n : nodesParents) {
            maze.addPoint(n.getState().x, n.getState().y);
        }

        maze.display();
    }

}
