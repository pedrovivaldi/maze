/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetomaze;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 *
 * @author PedroHenrique
 */
public class Ponto extends Point {

    private static Maze maze;

    public Ponto(int x, int y) {
        super(x, y);
    }

    public Ponto(Ponto point) {
        super(point.x, point.y);
    }

    public static void setMaze(Maze maze) {
        Ponto.maze = maze;
    }

    public Ponto move(Direction d) {
        Ponto point = new Ponto(this.x, this.y);
        switch (d) {
            case LEFT:
                point.x = point.x - 1;
                break;
            case UPLEFT:
                point.x = point.x - 1;
                point.y = point.y - 1;
                break;
            case DOWNLEFT:
                point.x = point.x - 1;
                point.y = point.y + 1;
                break;
            case RIGHT:
                point.x = point.x + 1;
                break;
            case DOWNRIGHT:
                point.x = point.x + 1;
                point.y = point.y + 1;
                break;
            case UPRIGHT:
                point.x = point.x + 1;
                point.y = point.y - 1;
                break;
            case UP:
                point.y = point.y - 1;
                break;
            case DOWN:
                point.y = point.y + 1;
                break;
        }

        if (maze.isEmpty(point.x, point.y)) {
            return point;
        }
        return null;
    }

    public int h() {
        return (int) Point2D.distance(this.x, this.y, maze.getDestinationX(), maze.getDestinationY());
    }

    public boolean isGoal() {
        return this.h() == 0;
    }
}
