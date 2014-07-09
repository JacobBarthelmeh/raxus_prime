package raxus_prime;

public enum Behavior_Enum {
	
	Attack(1, Attack_Behavior.class),
	Run(2, Run_Behavior.class),
	Evade(3, Evade_Behavior.class),
	Seppuku(4, Seppuku_Behavior.class);
	//add any more behaviors here
	
	private int value; //tag used for across communication channels
	private Behavior_Interface type;
	Behavior_Enum(int in, Object t)
	{
		this.value = in;
		this.type  = (Behavior_Interface)t;
	}
	
	/**
	 * Useful for when sending across channel
	 * @return
	 */
	public int getValue()
	{
		return value;
	}
	
	public Behavior_Interface getType()
	{
		return type;
	}
}
