package raxus_prime;

public class Attack_Behavior implements Behavior_Interface{

	Api api       = Object_Pool.getApi();
	Status status = Object_Pool.getStatus();
	
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
		
		//or perform some logic dependent on current status and previouslly set flags
		return 0;
	}

	@Override
	public int action() {
		api.attack(x, y);
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
		api.move(x, y);
		return 0;
	}

}
