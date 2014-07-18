package raxus_prime;

import battlecode.common.*;

/**
 * Abstract the api for movement and location 
 * @author TheLogicalWeapon
 *
 */
public interface Api_Interface {
	

	
	int setRc(RobotController rc);
	
	
	/**
	 * Used to get the current versions representation of map locations
	 * @param rc
	 * @param x
	 * @param y
	 * @return
	 */
	MapLocation location(int x, int y);
	
	/**
	 * Used to get the current versions function to move
	 * @param x cord
	 * @param y cord
	 * @return
	 */
	 int move(int x, int y);
	
	/**
	 * Current versions way of attacking a location
	 * @param x
	 * @param y
	 * @return
	 */
	 int attack(int x, int y);
	 
	 int spawn();
	 
	 int readMsg(int channel);
	 
	 int sendMsg(int channel, int out);
	 
	 
	
}
