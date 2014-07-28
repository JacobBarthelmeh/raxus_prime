package raxus_prime;

import battlecode.common.*;

public class RobotPlayer {

	public static void run(RobotController rc) {

		/* set up api */
		Api api = Object_Pool.getApi();
		api.setRc(rc);

		int firstChannel = 66;
		int mapchannel = 299;

		while (true) {
			if (rc.getType() == RobotType.HQ) {
				try {
					new HQ();
				} catch (Exception e) {
					System.out.println("HQ Exception");
				}
			}
			if (rc.getType() == RobotType.SOLDIER) {
				try {
					// new Drone(firstChannel, mapchannel);
					new Leader(firstChannel, mapchannel);
				} catch (Exception e) {
					System.out.println("Soldier Exception \t" + e);
				}
			}

			rc.yield();
		}
	}
}
