package raxus_prime;

import battlecode.common.*;

/**
 * Abstract the api for movement and location
 * 
 * @author TheLogicalWeapon
 * 
 */
public interface Api_Interface {

	int setRc(RobotController rc);

	/**
	 * Used to get the current versions representation of map locations
	 * 
	 * @param rc
	 * @param x
	 * @param y
	 * @return
	 */
	MapLocation location(int x, int y);

	/**
	 * Used to get the current versions function to move
	 * 
	 * @param x
	 *            cord
	 * @param y
	 *            cord
	 * @return
	 */
	int move(int x, int y);

	/**
	 * Current versions way of attacking a location
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	int attack(int x, int y, boolean catious);

	int spawn();

	int readMsg(int channel);

	int sendMsg(int channel, int out);

	double getHealth();

	/**
	 * 
	 * @return an array with x in int[0][i] and y in int[1][i]
	 */
	int[][] getEnemieLocations();

	/**
	 * 
	 * @return an array with x in int[0][i] and y in int[1][i]
	 */
	int[][] getAllieLocations();

	int[] getEnemyHQLocation();

	int[] getHQLocation();

	int[] getCurrentLocation();

	/**
	 * Can return null if the location is outside range of sensor.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	Boolean getIsObstacle(int x, int y);

	int getMapWidth();

	int getMapHeight();

}
