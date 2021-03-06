/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raxus_prime;

import java.util.Arrays;

/**
 * Basic bugging algorithm for cheap pathfinding.
 * 
 * @author Alex
 */
public class MBugger {

	private Point start;
	private Point finish;
	private int moveCount;
	private int last_move;
	private Point closest;
	private boolean reverse;

	// TheLogicalWeapon
	// get Api and status from Object_Pool
	private Api api = Object_Pool.getApi();
	private Comm com = Object_Pool.getComm();

	/**
	 * Code in the following range needs to be modified for specific purposes
	 * 
	 */
	// /////////////////////////////////////////////////////////////////////////
	private Movement_Bugging tb;

	public MBugger(Movement_Bugging tb) {
		this.tb = tb;
		this.closest = null;
		reverse = true;
		last_move = -1;
		moveCount = 0;
	}

	/**
	 * 
	 * @return the position that this agent is at
	 */
	private Point getCurrentPosn() {
		return tb.me;
	}

	/**
	 * 
	 * @param p
	 * @return true if the inputted point is traversable, false otherwise.
	 */
	private boolean isTraversable(int x, int y) {
		try {
			// TheLogicalWeapon
			// use api to check if other robots in the way or map location is
			// traversable
			// sure wish I had direct access to pointers here
			boolean traversable = true;
			Boolean temp = null;
			int[][] xy = api.getAllieLocations();

			// check if off the map
			if (x >= tb.map.length || y >= tb.map[0].length || x < 0 || y < 0) {
				return false;
			}

			if (xy != null) {
				for (int i = 0; i < xy[0].length; i++) {
					if (xy[0][i] == x) {
						if (xy[1][i] == y) {
							traversable = false;
							break;
						}
					}
				}
			}

			if (traversable) {
				xy = api.getEnemieLocations();
				if (xy != null) {
					for (int i = 0; i < xy[0].length; i++) {
						if (xy[0][i] == x) {
							if (xy[1][i] == y) {
								traversable = false;
								break;
							}
						}
					}
				}
			}

			// if it is an obstacle add obstacle and update map
			if (traversable && !isOOB(x, y)) {
				temp = api.getIsObstacle(x, y);
				if (temp != null && !temp) {
					traversable = false;
					// check if already been discovered and just not added
					if (tb.map[x][y]) {
						int[] arr = { Object_Pool.obstacleReport };
						com.send(0, false, x, y, arr);
						tb.map[x][y] = false;
					}
				}
			}

			return traversable && tb.map[x][y];
		} catch (Exception e) {
			System.out.println("Error in isTraversable()" + e);
		}
		return false;
	}

	private boolean isOOB(int x, int y) {
		try {
			return (x >= tb.map.length || x < 0 || y >= tb.map[x].length || y < 0);
		} catch (Exception e) {
			System.out.println("Error in isOOB()" + e);
		}
		return true;
	}

	// /////////////////////////////////////////////////////////////////////////////
	public void setStartAndFinish(Point start, Point finish) {
		this.start = start;
		this.finish = finish;
		closest = start;
	}

	public void reset() {
		last_move = -1;
		closest = null;
		start = null;
		finish = null;
		moveCount = 0;
	}

	private double isOnLine(Point p) {
		double m = ((double) (finish.y - start.y)) / (finish.x - start.x);
		double b = -m * start.x + start.y;
		double ty = m * p.x + b;
		return Math.abs(ty - p.y);
	}

	/**
	 * As long as this thing is called, and the map does not change, you are
	 * either on the line, or hugging a wall.
	 * 
	 * @return the next position to move to.
	 */
	public Point nextMove() {
		try {
			moveCount++;
			Point me = getCurrentPosn();
			Point potential;
			if (isOnLine(me) < 2
					&& (closest == null || Point.manhattan(me, finish) <= Point
							.manhattan(finish, closest))) {
				// find the next spot that is on the line, return it.
				if ((potential = followLine(me)) != null) {
					return potential;
				}
			}
			// wall hug.
			if ((potential = bug(me)) != null) {
				return potential;
			}
		} catch (Exception e) {
			System.out.println("Error in nextMove()" + e);
		}
		// if it gets here, there are major problems.
		// just in case, we'll modify the move count.
		moveCount--;
		return null;
	}

	private boolean recursed;

	public Point bug(Point me) {
		Point temp;
		Point obs1;
		Point obs2;
		for (int i = 0; i != 8; i++) {
			int d = (reverse) ? (i + 4) % 8 : i;
			int obs_d = (d + (((reverse) ? -1 : 1) * (2 - d % 2)) + 8) % 8;
			int obs_d2 = (obs_d + ((reverse) ? 1 : -1) + 8) % 8;
			temp = moveTo(me, d);
			obs1 = moveTo(me, obs_d);
			obs2 = moveTo(me, obs_d2);
			if (isTraversable(temp.x, temp.y)
					&& (isObstacle(obs1) || (d % 2 == 0 && isObstacle(obs2)))) {
				last_move = d;
				recursed = false;
				return temp;
			}

		}
		if (recursed) {
			return null;
		}
		recursed = true;
		System.out.println("REVERSE DIRECTION!");
		reverse = !reverse;
		return bug(me);
	}

	public boolean isObstacle(Point p) {
		return !isTraversable(p.x, p.y) && !isOOB(p.x, p.y);
	}

	public Point followLine(Point me) {
		Point potential;
		Point backup = null;
		double dis;
		for (int i = 0; i < 8; i++) {
			potential = moveTo(me, i);
			dis = isOnLine(potential);
			if (dis < 2
					&& Point.manhattan(finish, potential) < Point.manhattan(me,
							finish)) {
				if (isTraversable(potential.x, potential.y)) {
					if ((int) dis != 0) {
						backup = potential;
					} else {
						last_move = i;
						closest = potential;
						return potential;
					}
				}
			}
		}
		return backup;
	}

	private boolean isNextToObstacle(Point p) {
		for (int i = 0; i < 8; i++) {
			Point p2 = moveTo(p, i);
			if (!isTraversable(p2.x, p2.y) && !isOOB(p2.x, p2.y)) {
				return true;
			}
		}
		return false;
	}

	private Point moveTo(Point p, int d) {
		switch (d) {
		case 0:
			return new Point(p.x, p.y - 1);
		case 1:
			return new Point(p.x + 1, p.y - 1);
		case 2:
			return new Point(p.x + 1, p.y);
		case 3:
			return new Point(p.x + 1, p.y + 1);
		case 4:
			return new Point(p.x, p.y + 1);
		case 5:
			return new Point(p.x - 1, p.y + 1);
		case 6:
			return new Point(p.x - 1, p.y);
		case 7:
			return new Point(p.x - 1, p.y - 1);
		default:
			return null;
		}
	}

	public int getMoveCount() {
		return moveCount;
	}

	public double pathRatio() {
		if (moveCount == 0) {
			return 0;
		} else {
			return ((double) moveCount) / Point.manhattan(start, finish);
		}
	}

}
