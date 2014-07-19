package raxus_prime;

public class Status {
	
	Api api = Object_Pool.getApi();
	
	double health;
	
	int[] enemiesX;
	int[] enemiesY;
	int nearEnemyX;
	int nearEnemyY;
	Integer EnemyHQX;
	Integer EnemyHQY;
	
	int[] alliesX;
	int[] alliesY;
	int nearAlleyX;
	int nearAlleyY;
	Integer AllieHQX;
	Integer AllieHQY;
	
	
	public int update()
	{
		//get the hq locations for first time then not update it
		if(EnemyHQX == null) {
			int[] xy = api.getEnemyHQLocation();
			EnemyHQX = xy[0];
			EnemyHQY = xy[1];
			xy = api.getHQLocation();
			AllieHQX = xy[0];
			AllieHQY = xy[1];
		}
		
		setHealth(api.getHealth());
		setEnemies();
		setAllies();
		return 0;
	}
	
	private void setHealth(double h)
	{
		health = h;
	}
	
	private void setEnemies()
	{
		int[][] xy = api.getEnemieLocations();
		
		//if none sensed nearby then nearest might be enemy hq
		if (xy == null) {
			enemiesX = null;
			enemiesY = null;
			nearEnemyX = EnemyHQX;
			nearEnemyY = EnemyHQY;
			return;
		}
		
		//@TODO potential scope issue ....
		enemiesX = xy[0];
		enemiesY = xy[1];
	}
	
	private void setAllies()
	{
		int[][] xy = api.getAllieLocations();
		
		//if none sensed nearby then nearest might be hq
		if (xy == null) {
			alliesX = null;
			alliesY = null;	
			nearAlleyX = AllieHQX;
			nearAlleyY = AllieHQY;
			return;
		}
		
		//@TODO potential scope issue ....
		alliesX = xy[0];
		alliesY = xy[1];
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
		if (enemiesX == null) {
			return 0;
		}
		else {
			return enemiesX.length;
		}
	}

	/**
	 * Get the number of allies seen
	 * @return number allies
	 */
	public int getNumberAllies()
	{
		if (alliesX == null) {
			return 0;
		}
		else {
			return alliesX.length;
		}
	}
}
