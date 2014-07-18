package raxus_prime;

import battlecode.common.*;

public class Api implements Api_Interface{

	RobotController rc;
	
	@Override
	public int setRc(RobotController rc)
	{
		this.rc = rc;
		return 0;
	}
	
	@Override
	public MapLocation location(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int move(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int attack(int x, int y) {
		try {
		MapLocation loc = location(x, y);
		if (rc.canAttackSquare(loc))
				rc.attackSquare(loc);
		} catch (GameActionException e) {
			System.out.println("Error when attacking.");
		}
		return 0;
	}

	@Override
	public int spawn() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readMsg(int channel) {
		try {
			rc.readBroadcast(channel);
		} catch (GameActionException e) {
			System.out.println("Error while reading msg.");
		}
		return 0;
	}

	@Override
	public int sendMsg(int channel, int out) {
		try {
			rc.broadcast(channel, out);
		} catch (GameActionException e) {
			System.out.println("Error while sending msg.");
		}
		return 0;
	}

}
