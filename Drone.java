package raxus_prime;

/**
 * Drone to handle grunt work does not use higher level strategies
 * 
 * @author TheLogicalWeapon
 * 
 */
public class Drone {

	/* current orders */
	Behavior_Interface b;
	int x, y;

	/* current status */
	Status status;
	int mapChannel;
	CommDrone com;

	/* keep track of leader alive */
	int alive = 0;
	int count = 0;

	public Drone(int c, int m) {
		int channel = c;
		mapChannel = m;

		/* add a comm object to object pool */
		com = Object_Pool.getCommDrone();
		com.setDrone(this);
		com.setChannels(channel, mapChannel);

		/* add a status object to object pool */
		status = Object_Pool.getStatus();

		while (true) {
			try {
				// sets target location and behavior
				if (com.getMsg() != 0) {
					// either no message being looked for or an error when
					// reading

				}

				// sense information around drone
				status.update();

				// if it's been a while since hearing from leader, then form new
				// group
				if (count > 7) {
					//b = Behavior_Enum.ChangeLeader.getType();
				}

				// pass target location and status to behavior type and perform
				// actions
				b.setStatus();
				b.setTarget(x, y);
				b.action();
				b.secondaryAction(this); // secondary action can perform update
											// for a new leader
				b.move();

				// @TODO sense if new map information / waypoint should be sent
				// com.sendMsg(0,x,y);
			} catch (Exception e) {
				System.out.println("Drone Exception");
			}
		}
	}

	/**
	 * Used to change leader channel listened to
	 * 
	 * @param channel
	 *            new channel to take orders from
	 * @return
	 */
	public int setLeader(int channel) {
		com.setChannels(channel, mapChannel);
		return 0;
	}

	/**
	 * Called by CommDrone to set current behavior
	 * 
	 * @param in
	 * @return
	 */
	public int setBehavior(Behavior_Enum in) {
		try {
			b = in.getType();
			return 0;
		} catch (Exception e) {
			System.out.println("setBehavior Exception");
		}
		return 0;
	}

	/**
	 * Keep track if the leader is alive by using the optional flag
	 * 
	 * @param f
	 *            flag value
	 * @return
	 */
	public int setOptionalFlag(int f) {
		if (f == alive) {
			count++;
		} else {
			count = 0;
			alive = f;
		}
		return 0;
	}

	/**
	 * Called by CommDrone to set target location
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public int setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		return 0;
	}
}
