package raxus_prime;

public enum Behavior_Enum {

	Attack(1, new Attack_Behavior()), Run(2, new Run_Behavior()), Evade(3,
			new Evade_Behavior()), Seppuku(4, new Seppuku_Behavior()), ChangeLeader(
			5, new ChangeLeader_Behavior());
	// add any more behaviors here

	private int value; // tag used for across communication channels
	private Behavior_Interface type;

	Behavior_Enum(int in, Object t) {
		this.value = in;
		this.type = (Behavior_Interface)t;
	}

	/**
	 * Useful for when sending across channel
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}

	public Behavior_Interface getType() {
		return type;
	}
}
