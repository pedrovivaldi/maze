package projetomaze;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Maze {

    /**
     * Numero de quadrados do labirinto
     */
    private final int x;
    /**
     * Numero de quadrados do labirinto
     */
    private final int y;
    /**
     * Largura em pixels
     */
    private int ncols;
    /**
     * Altura em pixels
     */
    private int nrows;
    /**
     * Mapa de quadrados do labirinto
     */
    private int[][] map;
    /**
     * Representacao em ASCII do labirinto
     */
    private ArrayList<String> rows;
    /**
     * Pontos adicionados pelo usuário representados por *
     */
    private ArrayList<Point> points;
    /**
     * Pontos adicionados pelo usuário representados por ?
     */
    private ArrayList<Point> options;

    /**
     * Destino
     */
    private int destinationX;
    /**
     * Destino
     */
    private int destinationY;
    /**
     * Origem
     */
    private int originX;
    /**
     * Origem
     */
    private int originY;

    public Maze(int x, int y, int originX, int originY, int destinationX, int destinationY) {
        this.x = x;
        this.y = y;
        points = new ArrayList<>();
        options = new ArrayList<>();
        map = new int[this.x][this.y];
        generateMaze(0, 0);
        populate();
        this.originX = originX;
        this.originY = originY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    public Maze(Maze m) {
        this.x = m.x;
        this.y = m.y;
        this.ncols = m.ncols;
        this.nrows = m.nrows;
        this.map = m.map;
        this.rows = (ArrayList<String>) m.rows.clone();
        this.points = new ArrayList<>();
        this.options = new ArrayList<>();
        this.destinationX = m.destinationX;
        this.destinationY = m.destinationY;
        this.originX = m.originX;
        this.originY = m.originY;
    }

    public int getNRows() {
        return nrows;
    }

    public int getNCols() {
        return ncols;
    }

    // Checando se espaço está livre para colocar pontos
    public boolean isEmpty(int x, int y) {
        if (x > getNCols()) {
            return false;
        }
        if (y > getNRows()) {
            return false;
        }
        if (x < 0) {
            return false;
        }
        if (y < 0) {
            return false;
        }
        char c = rows.get(y).charAt(x);
        return c == ' ';
    }

    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
        String newRow = rows.get(y).substring(0, x) + "*" + rows.get(y).substring(x + 1, getNCols());
        rows.add(y, newRow);
        rows.remove(y + 1);
    }

    public void addOption(int x, int y) {
        options.add(new Point(x, y));
        String newRow = rows.get(y).substring(0, x) + "?" + rows.get(y).substring(x + 1, getNCols());
        rows.add(y, newRow);
        rows.remove(y + 1);
    }

    public void display() {
        for (String s : rows) {
            System.out.println(s);
        }
    }

    private void populate() {
        rows = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < y; i++) {
            // draw the north edge
            for (int j = 0; j < x; j++) {
                sb.append((map[j][i] & 1) == 0 ? "+---" : "+   ");
            }
            sb.append("+");
            rows.add(sb.toString());
            sb = new StringBuilder();
            // draw the west edge
            for (int j = 0; j < x; j++) {
                sb.append((map[j][i] & 8) == 0 ? "|   " : "    ");
            }
            sb.append("|");
            rows.add(sb.toString());
            sb = new StringBuilder();
        }
        // draw the bottom line
        for (int j = 0; j < x; j++) {
            sb.append("+---");
        }
        sb.append("+");
        rows.add(sb.toString());

        nrows = rows.size();
        ncols = rows.get(0).length();
    }

    int r = 0;

    private void generateMaze(int cx, int cy) {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        r = r + 1;
        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, x) && between(ny, y)
                    && (map[nx][ny] == 0)) {
                map[cx][cy] |= dir.bit;
                map[nx][ny] |= dir.opposite.bit;
                generateMaze(nx, ny);
            }
        }
    }

    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    private enum DIR {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
        private final int bit;
        private final int dx;
        private final int dy;
        private DIR opposite;

        // use the static initializer to resolve forward references
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        private DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    };

    public int getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(int destinationX) {
        this.destinationX = destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(int destinationY) {
        this.destinationY = destinationY;
    }

    public int getOriginX() {
        return originX;
    }

    public void setOriginX(int originX) {
        this.originX = originX;
    }

    public int getOriginY() {
        return originY;
    }

    public void setOriginY(int originY) {
        this.originY = originY;
    }

    public static void main(String[] args) {
        int x = args.length >= 1 ? Integer.parseInt(args[0]) : 8;
        int y = args.length == 2 ? Integer.parseInt(args[1]) : 8;
        Maze maze = new Maze(x, y, 1, 1, 31, 15);
        maze.display();

        maze.addPoint(31, 15);
        maze.addPoint(1, 1);
        maze.display();
    }
}
