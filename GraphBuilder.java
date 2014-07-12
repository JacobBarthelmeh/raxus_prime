/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raxus_prime;

import java.util.ArrayList;
/**
 * A class to handle pathfinding in a grid based world with stationary obstacles.
 * @author alexhuleatt
 */
public class GraphBuilder {

    private final int length;
    private final int height;

    private int[][] adj_matrix;
    private Point[] ways;
    private int start_index;
    private int finish_index;
    private boolean REUSE;

    private static final double octile_constant = .41421356237;
    private double octile_multiplier = 1.2;

    private int manhat(Point p1, Point p2) {
        return Math.abs(p2.x - p1.x) + Math.abs(p2.y - p1.y);
    }

    private boolean bresenham(Point p1, Point p2) {
        int x1 = p1.x;
        int y1 = p1.y;
        int x2 = p2.x;
        int y2 = p2.y;
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;
        while (true) {
            if (map[x1][y1]) {
                return false;
            }
            if (x1 == x2 && y1 == y2) {
                break;
            }
            int e2 = err << 1;
            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }
            if (map[x1][y1]) {
                return false;
            }
            if (x1 == x2 && y1 == y2) {
                break;
            }
            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
        }
        return true;
    }

    BossList<Point> waypoints;
    Point[] obstacles;
    int obstacle_index;
    boolean[][] map;
    
    /**
     * 
     * @param length Length of the grid.
     * @param height Height of the grid.
     */
    public GraphBuilder(int length, int height) {
        this.length = length;
        this.height = height;
        map = new boolean[length][height];
        obstacles = new Point[10000];
        waypoints = new BossList<Point>(10000);
        obstacle_index = 0;
        adj_matrix = new int[2][2];
    }
    
    public double getPathConstant() {return octile_multiplier;}
    
    /**
     * 
     * @param length Length of the grid.
     * @param height Height of the grid.
     * @param path_constant Alternative value to determine the speed-to-find/optimality of the path.
     */
    public GraphBuilder(int length, int height, double path_constant) {
        this(length,height);
        octile_multiplier = path_constant;
    }

    /**
     * @return List of all obstacles in this graph.
     */
    public Point[] getObstacles() {
        return obstacles;
    }
    
    /**
     * Add a single obstacle to the graph.
     * @param p The obstacle's position.
     */
    public void addObstacle(Point p) {
        if (!isValid(p.x, p.y)) {
            return;
        }
        obstacles[obstacle_index++] = p; //add the obstacle to the array
        map[p.x][p.y] = true;
        checkAround(p);
        adj_matrix = new int[waypoints.size() + 2][waypoints.size() + 2];
        REUSE = false;
    }

    private void checkAround(Point p) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isValid(p.x + i, p.y + j)) {
                    Point newP = new Point(p.x + i, p.y + j);
                    if (isWaypoint(p.x + i, p.y + j)) {
                        if (!waypoints.contains(newP)) {
                            waypoints.add(newP);
                        }
                    } else {
                        if (waypoints.contains(newP)) {
                            waypoints.remove(newP);
                        }
                    }
                }
            }
        }
    }
    /**
     * Optimized method of adding obstacles if multiple obstacles are being added.
     * @param ps List of obstacle positions to add.
     */
    public void addObstacles(Point[] ps) {
        ArrayList<Point> toCheck = new ArrayList<Point>(ps.length);
        for (Point p : ps) {
            if (!map[p.x][p.y]) {
                map[p.x][p.y] = true;
                toCheck.add(p);
                obstacles[obstacle_index++] = p;
            }
        }
        for (Point p : toCheck) {
            checkAround(p);
        }

    }

    private boolean isWaypoint(int x, int y) {
        return (!map[x][y] && (isOutsideCorner(x, y) || isInsideCorner(x, y)));
    }

    private boolean isOutsideCorner(int x, int y) {
        if (x > 0 && y > 0 && map[x - 1][y - 1]) {
            if (!map[x - 1][y] && !map[x][y - 1]) {
                return true;
            }
        }
        if (x + 1 < length && y > 0 && map[x + 1][y - 1]) {
            if (!map[x + 1][y] && !map[x][y - 1]) {
                return true;
            }
        }
        if (x + 1 < length && y + 1 < height && map[x + 1][y + 1]) {
            if (!map[x + 1][y] && !map[x][y + 1]) {
                return true;
            }
        }
        if (x > 0 && y + 1 < height && map[x - 1][y + 1]) {
            if (!map[x - 1][y] && !map[x][y + 1]) {
                return true;
            }
        }
        return false;
    }

    private boolean isValid(int x, int y) {
        return (x < length && x >= 0 && y < height && y >= 0);
    }

    private boolean isInsideCorner(int x, int y) {
        if (isValid(x, y - 1) && map[x][y - 1]) {
            if (isValid(x + 1, y) && map[x + 1][y] && !map[x + 1][y - 1]) {
                return true;
            }
            if (isValid(x - 1, y) && map[x - 1][y] && !map[x - 1][y - 1]) {
                return true;
            }
        }
        if (isValid(x, y + 1) && map[x][y + 1]) {
            if (isValid(x + 1, y) && map[x + 1][y] && !map[x + 1][y + 1]) {
                return true;
            }
            if (isValid(x - 1, y) && map[x - 1][y] && !map[x - 1][y + 1]) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isVisible(Point p1, Point p2) {
        return (bresenham(p1, p2) || Math.abs(p1.x - p2.x) == 1 && Math.abs(p1.y - p2.y) == 1);
    }

    private boolean isVisible(int a, int b) {
        if (REUSE && a != start_index && a != finish_index && b != start_index && b != finish_index) {
            return adj_matrix[a][b] != -1;
        }
        if (isVisible(ways[a], ways[b]) && isVisible(ways[b], ways[a])) {
            adj_matrix[a][b] = manhat(ways[a], ways[b]);
            adj_matrix[b][a] = adj_matrix[a][b];
            return true;
        } else {
            adj_matrix[a][b] = -1;
            adj_matrix[b][a] = adj_matrix[a][b];
            return false;
        }
    }
    
    /**
     * 
     * @return List of positions that are waypoints used in pathfinding
     */
    public Point[] getWaypoints() {
        Object[] way = waypoints.condensed();
        ways = new Point[way.length];
        System.arraycopy(way, 0, ways, 0, way.length);
        return ways;
    }

    /**
     * Given two points, finds a near-optimal path between them using the
     * already-made adjacency matrix.
     *
     * @param start the initial position
     * @param finish the desired ending location
     * @return a list of waypoints describing where to go. From each waypoint,
     * it is guaranteed that the next is visible.
     */
    public Point[] getPath(Point start, Point finish) {
        Point[] temp = getWaypoints();
        ways = new Point[temp.length + 2];
        System.arraycopy(temp, 0, ways, 0, temp.length);
        start_index = ways.length - 2;
        finish_index = ways.length - 1;
        ways[start_index] = start;
        ways[finish_index] = finish;
        int[] path = findPath(ways); //find the path
        REUSE = true;
        if (path == null) { //if null, return null
            return null;
        }

        Point[] final_path = new Point[path.length]; //convert the path from indices to positions
        for (int i = 0; i < path.length; i++) {
            final_path[i] = ways[path[i]];
        }
        final_path[path.length - 1] = finish;
        return final_path;
    }

    private int[] findPath(Point[] ways) {
        IntDoubleHeap to_evaluate = new IntDoubleHeap(ways.length); //Uses a simple heap as a priority queue
        double[] f_costs = new double[adj_matrix.length]; //the minimum cost to get to a vertex
        double[] g_costs = new double[adj_matrix.length];
        int[] min_index = new int[adj_matrix.length]; //the vertex's lowest cost neighbor
        boolean[] closed_set = new boolean[adj_matrix.length];
        boolean[] isInOpenSet = new boolean[adj_matrix.length];

        int count = 0;
        int current;
        to_evaluate.add(start_index, 0);

        while (!to_evaluate.isEmpty()) { //evaluate until there are no more vertices to evaluate
            count++;
            current = to_evaluate.pop(); //pop the lowest f-cost
            if (current == finish_index) { //if at the end, finish right now
                System.out.println(count);
                return cleanup(min_index, adj_matrix);
            }
            closed_set[current] = true;
            for (int i = 0; i < adj_matrix[current].length; i++) { //visit all neighbors
                if (!closed_set[i] && isVisible(current, i)) {
                    double cost = adj_matrix[i][current] + g_costs[current]; //total cost to visit node i from current
                    if (!isInOpenSet[i] || cost < g_costs[i]) {
                        g_costs[i] = cost;
                        f_costs[i] = cost + octile(ways[i], ways[finish_index]) * octile_multiplier; //uses octile search heuristic times two.
                        min_index[i] = current;
                        if (!isInOpenSet[i]) {
                            to_evaluate.add(i, f_costs[i]);
                            isInOpenSet[i] = true;
                        }
                    }
                }
            }
        }
        return null;
    }

    private double octile(Point p, Point p2) {
        int x = Math.abs(p2.x - p.x);
        int y = Math.abs(p2.y - p.y);
        return Math.max(x, y) + octile_constant * Math.min(x, y);
    }

    private int[] cleanup(int[] indices, int[][] adj_matrix) {
        int current = finish_index;
        int[] path = new int[adj_matrix.length];
        int index = 0;
        while (current != start_index) {
            index++;
            path[adj_matrix.length - index] = current;
            current = indices[current];
        }
        int[] final_path = new int[index];
        System.arraycopy(path, path.length - index, final_path, 0, index);
        return final_path;
    }
    
    /**
     * Displays a simple representation of the graph to the terminal.
     */
    public void print() {
        System.out.print(" ");
        for (int i = 0; i < length * 2; i++) {
            System.out.print("_");
        }
        System.out.println();
        for (int i = 0; i < height; i++) {
            System.out.print("|");
            for (int j = 0; j < length; j++) {
                if (waypoints.contains(new Point(j, i))) {
                    System.out.print("* ");
                } else {
                    System.out.print((map[j][i]) ? "+ " : "  ");
                }
            }
            System.out.println("|");

        }
        System.out.print(" ");
        for (int i = 0; i < length * 2; i++) {
            System.out.print("_");
        }
        System.out.println();
    }

}
