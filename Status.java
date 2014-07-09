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
	

}
