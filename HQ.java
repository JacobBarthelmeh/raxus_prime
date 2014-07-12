package raxus_prime;

import battlecode.common.*;

/**
 * @Author TheLogicalWeapon
 */
public class HQ {

	Status status = new Status();
	Api api = Object_Pool.getApi();

	public HQ(RobotController rc) {
		while (true) {
			try {
				// get status of HQ
				status.update(rc);
				while (rc.getActionDelay() > 0) {
					rc.yield();
				}

				// attack enemies if they are in range
				if (status.getNumberEnemies() > 0) {
					int[] nearest = status.getClosestEnemy();
					api.attack(rc, nearest[0], nearest[1]);
				}

				// spawn new robots
				api.spawn(rc);
				rc.yield();

			} catch (Exception e) {
				System.out
						.println("Exception in " + this.getClass() + "\t" + e);
			}
		}
	}
}
