package projetomaze;

/**
 *
 * @author PedroHenrique
 */
public class Node implements Comparable<Node> {

    private Ponto state;
    private Node parent;
    private int depth;

    public Node(Node parent, Ponto state) {
        this.state = state;
        this.parent = parent;
        if (parent != null) {
            this.depth = parent.depth + 1;
        } else {
            this.depth = 1;
        }
    }

    public int f() {
        return this.getState().h() + depth;
    }

    public Ponto getState() {
        return state;
    }

    public void setState(Ponto state) {
        this.state = state;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public int compareTo(Node t) {
        if (this.f() < t.f()) {
            return -1;
        } else if (this.f() == t.f()) {
            return 0;
        }
        return 1;
    }

}
