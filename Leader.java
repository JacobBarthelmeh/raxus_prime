package raxus_prime;
import battlecode.common.*;

public class Leader {
	
	/* current orders from hq */
	Strategy_Interface s;
	
	int x,y;
	
	/* @TODO potential communication with other leaders */
	
	
	/* @TODO orders to be given to drones */
	
	
	/* current status */
	Status status;
	int mapChannel;
	CommLeader com;
	
	
	public Leader(RobotController rc, int c, int m)//@TODO add rc to parameter
	{
		int channel = c;
		int mapChannel = m;
		
		/* add a comm object to object pool */
		com = Object_Pool.getCommLeader();
		com.setLeader(this);
		com.setChannels(channel, mapChannel);
		
		/* add a status object to object pool */
		status = Object_Pool.getStatus();
		
		
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
	public int setBehavior(Strategy_Enum in)
	{
		s = in.getType();
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
