package raxus_prime;

import battlecode.common.*;

public class Api implements Api_Interface {

	RobotController rc;

	@Override
	public int setRc(RobotController rc) {
		this.rc = rc;
		return 0;
	}

	@Override
	public MapLocation location(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int move(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int attack(int x, int y) {
		try {
			MapLocation loc = location(x, y);
			if (rc.canAttackSquare(loc))
				rc.attackSquare(loc);
		} catch (GameActionException e) {
			System.out.println("Error when attacking.");
		}
		return 0;
	}

	@Override
	public int spawn() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readMsg(int channel) {
		try {
			rc.readBroadcast(channel);
		} catch (GameActionException e) {
			System.out.println("Error while reading msg.");
		}
		return 0;
	}

	@Override
	public int sendMsg(int channel, int out) {
		try {
			rc.broadcast(channel, out);
		} catch (GameActionException e) {
			System.out.println("Error while sending msg.");
		}
		return 0;
	}

	@Override
	public double getHealth() {
		return rc.getHealth();
	}

	@Override
	public int[][] getEnemieLocations() {
		try {
			int[][] xy;
			Robot[] enm = rc.senseNearbyGameObjects(Robot.class, rc
					.getLocation(), rc.getType().sensorRadiusSquared, rc
					.getTeam().opponent());
			xy = new int[2][enm.length];
			for (int i = 0; i < enm.length; i++) {
				MapLocation loc = rc.senseLocationOf(enm[i]);
				xy[0][i] = loc.x;
				xy[1][i] = loc.y;

			}
			return xy;
		} catch (GameActionException e) {
			System.out.println("Error in getEnemieLocations");
		}
		return null;
	}

	@Override
	public int[][] getAllieLocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getEnemyHQLocation() {
		MapLocation loc = rc.senseEnemyHQLocation();
		int[] ret = {loc.x, loc.y};
		return ret;
	}

	@Override
	public int[] getHQLocation() {
		MapLocation loc = rc.senseHQLocation();
		int[] ret = {loc.x, loc.y};
		return ret;
	}

}
