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
		return new MapLocation(x, y);
	}

	@Override
	public int move(int x, int y) {
		try {
			MapLocation loc = new MapLocation(x, y);
			Direction dir = rc.getLocation().directionTo(loc);
			if (rc.canMove(dir)) {
				rc.move(dir);

			}
		} catch (GameActionException e) {
			System.out.println("Error in api move()");
		}
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
		try {
			int maxRobots = 1000;
			if (rc.isActive() && rc.senseRobotCount() < maxRobots) {
				Direction toEnemy = rc.getLocation().directionTo(
						rc.senseEnemyHQLocation());
				int i = 0;
				while (i < 8
						&& !(rc.senseTerrainTile(rc.getLocation().add(toEnemy))
								.isTraversableAtHeight(RobotLevel.ON_GROUND))) {
					toEnemy = toEnemy.rotateRight();
				}
				if (rc.senseObjectAtLocation(rc.getLocation().add(toEnemy)) == null) {
					rc.spawn(toEnemy);
				}
			}
			return 0;
		} catch (GameActionException e) {
			System.out.println("Error while spawning");
		}

		return 0;
	}

	@Override
	public int readMsg(int channel) {
		try {
			return rc.readBroadcast(channel);
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
		try {
			int[][] xy;
			Robot[] all = rc.senseNearbyGameObjects(Robot.class,
					rc.getLocation(), rc.getType().sensorRadiusSquared,
					rc.getTeam());
			xy = new int[2][all.length];
			for (int i = 0; i < all.length; i++) {
				MapLocation loc = rc.senseLocationOf(all[i]);
				xy[0][i] = loc.x;
				xy[1][i] = loc.y;

			}
			return xy;
		} catch (GameActionException e) {
			System.out.println("Error in getAllieLocations");
		}
		return null;
	}

	@Override
	public int[] getEnemyHQLocation() {
		MapLocation loc = rc.senseEnemyHQLocation();
		int[] ret = { loc.x, loc.y };
		return ret;
	}

	@Override
	public int[] getHQLocation() {
		MapLocation loc = rc.senseHQLocation();
		int[] ret = { loc.x, loc.y };
		return ret;
	}

	@Override
	public int[] getCurrentLocation() {
		int[] xy = { rc.getLocation().x, rc.getLocation().y };
		return xy;
	}

	@Override
	public Boolean getIsObstacle(int x, int y) {
		MapLocation loc = location(x, y);
		if (rc.canSenseSquare(loc)) {
			return rc.senseTerrainTile(loc).isTraversableAtHeight(
					RobotLevel.ON_GROUND);
		} else {
			// cannot sense square therfor unknown if traversable
			return null;
		}
	}

	@Override
	public int getMapWidth() {
		return rc.getMapWidth();
	}

	@Override
	public int getMapHeight() {
		return rc.getMapHeight();
	}

}
