/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raxus_prime;

/**
 * Integer based coordinate.
 * @author alexhuleatt
 */
public class Point {

    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Point && ((Point) o).x == x && ((Point) o).y == y);
    }

    @Override
    public int hashCode() {
        /* fails for negative numbers and numbers larger than 2^15 */
        return (this.x << 16) & this.y;
    }
    
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
