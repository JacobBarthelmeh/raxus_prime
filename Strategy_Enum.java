package raxus_prime;

public enum Strategy_Enum {
	Protect(1, Protect_Strategy.class);
	
	//add any more strategies here
	
	private int value; //tag used for across communication channels
	private Strategy_Interface type;
	Strategy_Enum(int in, Object t)
	{
		this.value = in;
		this.type  = (Strategy_Interface)t;
	}
	
	/**
	 * Useful for when sending across channel
	 * @return
	 */
	public int getValue()
	{
		return value;
	}
	
	public Strategy_Interface getType()
	{
		return type;
	}
}
