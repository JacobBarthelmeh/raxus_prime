/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package raxus_prime;

import java.util.Arrays;

/**
 *
 * @author alexhuleatt
 */
public class Pathfinding2 {

    public static void main(String[] args) {
        GraphBuilder g = new GraphBuilder(20,20);
        
        g.addObstacle(new Point(2,2));
        g.addObstacle(new Point(2,3));
        g.addObstacle(new Point(1,3));
        g.addObstacle(new Point(3,1));
        g.addObstacle(new Point(3,2));
        g.print();
        System.out.println(Arrays.toString(g.getPath(new Point(0,0), new Point(19,19))));
        
    }
    
}
