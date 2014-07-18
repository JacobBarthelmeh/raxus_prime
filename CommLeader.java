package raxus_prime;

public class CommLeader extends Comm{
private Leader leader;

	boolean alive = false;

	public int setLeader(Leader in)
	{
		this.leader = in;
		return 0;
	}
	
	/**
	 * Reads in a message and sets drone member behavior
	 * Assumes drone is only getting behavior and location
	 * @param channel
	 * @return
	 */
	public int getMsg()
	{	
		
		return 0;
	}
	
	
	/**
	 * Easy sendMsg that constantly flips optional flag so drone know leader is alive
	 * @param a
	 * @param x
	 * @param y
	 * @return
	 */
	public int sendMsg(int a, int x, int y)
	{
		send(a, alive, x, y, chanOut);
		alive = !alive;
		return 0;
	}
	
	/**
	 * Choose what optional flag to send
	 * @param a
	 * @param optionalFlag
	 * @param x
	 * @param y
	 * @return
	 */
	public int sendMsg(int a, boolean optionalFlag, int x, int y)
	{
		send(a, optionalFlag, x, y, chanOut);
		return 0;
	}
	
	/**
	 * Send panic message
	 * @return
	 */
	public int sendPanic()
	{
		
		return 0;
	}
	
}
