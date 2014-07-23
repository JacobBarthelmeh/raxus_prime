package raxus_prime;

/**
 * @Author TheLogicalWeapon
 */
public class HQ {

	Status status = new Status();
	Comm com = Object_Pool.getCommHq();
	Api api = Object_Pool.getApi();

	public HQ() {
		while (true) {
			try {
				// get status of HQ
				status.update();
				
				com.setChannels(0, 66);
				com.send(1, true, 0, 0, null);

				// attack enemies if they are in range
				if (status.getNumberEnemies() > 0) {
					int[] nearest = status.getClosestEnemy();
					api.attack(nearest[0], nearest[1]);
				}

				// spawn new robots
				api.spawn();

			} catch (Exception e) {
				System.out
						.println("Exception in " + this.getClass() + "\t" + e);
			}
		}
	}
}
