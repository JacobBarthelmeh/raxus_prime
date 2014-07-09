package raxus_prime;

public class CommLeader extends Comm{
private Leader leader;
	
	public CommLeader(Leader in)
	{
		this.leader = in;
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
	

	public int sendMsg(int a, int x, int y)
	{
		send(a, x, y, chanOut);
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
