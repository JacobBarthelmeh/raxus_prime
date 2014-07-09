package raxus_prime;

public class CommDrone extends Comm{
	
	private Drone drone;
	
	public CommDrone(Drone in)
	{
		this.drone = in;
	}
	
	/**
	 * Reads in a message and sets drone member behavior
	 * Assumes drone is only getting behavior and location
	 * @param channel
	 * @return
	 */
	public int getMsg()
	{	
		int[][] orders = recieve(-1);
		drone.setLocation(orders[0][1], orders[0][2]);
		drone.setBehavior(Behavior_List.getBehavior(orders[0][0]));
		return 0;
	}
	
	/**
	 * Drone send a message to all recipeants in chanOut
	 * @return
	 */
	public int sendMsg(int a, int x, int y)
	{
		send(a, x, y, chanOut);
		return 0;
	}
	
	/**
	 * Send panic message
	 * @return
	 */
	public int sendPanic()
	{
		
		return 0;
	}
}
