package raxus_prime;

public class Attack_Behavior implements Behavior_Interface {

	Api api = Object_Pool.getApi();
	Status status = Object_Pool.getStatus();
	Movement_Bugging mooBug = Object_Pool.getMovement_Bugging();

	int x, y;

	@Override
	public int setStatus() {
		// TODO Auto-generated method stub
		// setup flags from current status
		return 0;
	}

	@Override
	public int setTarget(int x, int y) {
		this.x = x;
		this.y = y;

		// or perform some logic dependent on current status and previouslly set
		// flags
		return 0;
	}

	@Override
	public int action() {
		try {
			api.attack(x, y);
		} catch (Exception e) {
			System.out
					.println("Error while trying to attack in attack behavior");
		}
		return 0;
	}

	@Override
	public int secondaryAction(Drone in) {
		// TODO Auto-generated method stub
		// attack most likely will leave this as a stub
		return 0;
	}

	@Override
	public int move() {
		try {
			int[] xy = mooBug.bugToo(x, y);
			if (xy == null) {
				//no where found to move
				return 0;
			}
			//System.out.printf("Moving to (%d,%d)\n", xy[0], xy[1]);
			api.move(xy[0], xy[1]);
		} catch (Exception e) {
			System.out.println("Error while attack behavior move");
		}
		return 0;
	}

}
