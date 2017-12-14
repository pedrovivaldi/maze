package projetomaze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 *
 * @author PedroHenrique
 */
public class Busca {

    private List<Node> fringe;
    private Set<Node> closed;
    private int[][] closedMap;
    private final Node inicio;
    private Maze maze;

    public Busca(Node inicio, Maze maze) {
        this.inicio = inicio;
        this.maze = maze;
    }

    public Busca(Ponto inicio, Maze maze) {
        this.inicio = new Node(null, inicio);
        this.maze = maze;
    }

    public Node search() {
        fringe = new ArrayList<>();
        //closed = new HashSet<>();
        closedMap = new int[maze.getNRows() * 4][maze.getNCols() * 2];

        fringe.add(inicio);

        while (!fringe.isEmpty()) {
            Node auxNode = fringe.remove(0);
            //closed.add(auxNode);
            closedMap[auxNode.getState().x][auxNode.getState().y] = auxNode.f();

            if (auxNode.getState().isGoal()) {
                return auxNode;
            }

            for (Direction d : Direction.values()) {
                Ponto pontoMove = auxNode.getState().move(d);

                if (pontoMove != null) {
                    Ponto newPonto = new Ponto(pontoMove);
                    Node node = new Node(auxNode, newPonto);

                    //if (!isInClosedList(node)) {
                    if (closedMap[node.getState().x][node.getState().y] == 0 || closedMap[node.getState().x][node.getState().y] > node.f()) {
                        //closed.add(node);
                        closedMap[node.getState().x][node.getState().y] = node.f();
                        int index = Collections.binarySearch(fringe, node);
                        if (index < 0) {
                            fringe.add((-index - 1), node);
                        } else {
                            fringe.add(index, node);
                        }
                    }
                }
            }
        }

        return null;
    }

    /*private boolean isInClosedList(Node node2) {
        for (Node node : closed) {
            if (node.getState().equals(node2.getState())) {
                return true;
            }
        }
        return false;
    }*/
}
