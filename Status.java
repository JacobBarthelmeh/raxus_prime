package raxus_prime;
import battlecode.common.*;

public class Status {
	
	double health;
	
	int[] enemiesX;
	int[] enemiesY;
	int nearEnemyX;
	int nearEnemyY;
	
	int[] alliesX;
	int[] alliesY;
	int nearAlleyX;
	int nearAlleyY;
	
	
	public int update(RobotController rc)
	{
		setHealth(rc.getHealth());
		setEnemies(rc);
		setAllies(rc);
		return 0;
	}
	
	private void setHealth(double h)
	{
		health = h;
	}
	
	private void setEnemies(RobotController rc)
	{
		//TODO sense nearby enemies (and find closest while sensing)
		//Robot e[] = rc.senseNearbyGameObjects(arg0, arg1, arg2, rc.getRobot().getTeam().opponent())
	}
	
	private void setAllies(RobotController rc)
	{
		//@TODO sense nearby allies
	}
	
	
	/**
	 * Get the location of the nearest enemy
	 * @return array  [x , y] of nearest enemy
	 */
	public int[] getClosestEnemy()
	{
		int[] ret = {nearEnemyX, nearEnemyY};
		return ret;
	}
	
	/**
	 * Get the location of the nearest alley
	 * @return array  [x , y] of nearest alley
	 */
	public int[] getClosestAlley()
	{
		int[] ret = {nearAlleyX, nearAlleyY};
		return ret;
	}
	
	/**
	 * Get the number of enemies seen
	 * @return number enemy
	 */
	public int getNumberEnemies()
	{
		return enemiesX.length;
	}

	/**
	 * Get the number of allies seen
	 * @return number allies
	 */
	public int getNumberAllies()
	{
		return alliesX.length;
	}
}
