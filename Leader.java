package raxus_prime;
import battlecode.common.*;

public class Leader {
	
	/* current orders from hq */
	Behavior_Interface b;
	int x,y;
	
	/* communication with other leaders */
	
	/* orders to be given to drones */
	
	
	/* current status */
	Status status;
	
	
	public Leader(RobotController rc, int c, int m)//@TODO add rc to parameter
	{
		int channel = c;
		int mapChannel = m;
		CommLeader com = new CommLeader(this);
		status = new Status();
		
		while(true) {
			com.getMsg(); //sets target location and behavior
			
			//sense information around drone
			status.update(rc);
			
			
			//@TODO sense if new map information / waypoint should be sent
			//com.sendMsg(0,x,y);
			
		}
	}
	
	
	/**
	 * Called by CommDrone to set current behavior
	 * @param in
	 * @return
	 */
	public int setBehavior(Behavior_Enum in)
	{
		b = in.getType();
		return 0;
	}
	
	
	/**
	 * Called by CommDrone to set target location
	 * @param x
	 * @param y
	 * @return
	 */
	public int setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
		return 0;
	}
}
