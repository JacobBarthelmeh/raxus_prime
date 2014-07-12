package raxus_prime;

import battlecode.common.*;

/**
 * Abstract the api for movement and location 
 * @author TheLogicalWeapon
 *
 */
public interface Api_Interface {
	
	
	/**
	 * Used to get the current versions representation of map locations
	 * @param rc
	 * @param x
	 * @param y
	 * @return
	 */
	MapLocation location(RobotController rc, int x, int y);
	
	/**
	 * Used to get the current versions function to move
	 * @param x cord
	 * @param y cord
	 * @return
	 */
	 int move(RobotController rc, int x, int y);
	
	/**
	 * Current versions way of attacking a location
	 * @param x
	 * @param y
	 * @return
	 */
	 int attack(RobotController rc, int x, int y);
	 
	 int spawn(RobotController rc);
	
}
