package raxus_prime;

/**
 * 4bits mac , 10bits y, 10bits x, 8bits action 
 * info[0] = action
 * info[1] = x cord
 * info[2] = y cord
 * 
 * @author TheLogicalWeapon
 *
 */
abstract class Comm {
	
	private boolean enc = true;
	
	Api api = Object_Pool.getApi();
	
	int[] chanIn;
	int[] chanOut;

	/**
	 * Set the channels to listen to
	 * @param in channel values
	 * @param out channel to send to
	 * @return
	 */
	public int setChannels(int[] in, int[] out)
	{
		chanIn = new int[in.length];
		chanOut = new int[out.length];
		System.arraycopy(in, 0, chanIn, 0, chanIn.length);
		System.arraycopy(out, 0, chanOut, 0, chanOut.length);
		return 0;
	}
	
	
	/**
	 * Set the channels to listen to
	 * @param in channel value
	 * @return
	 */
	public int setChannels(int in, int out)
	{
		chanIn = new int[0];
		chanOut = new int[0];
		chanIn[0] = in;
		chanOut[0] = out;
		return 0;
	}
	
	/**
	 * Send final int info to channels.
	 * @param a action
	 * @param x cord
	 * @param y cord
	 * @param chan specific channels, null for send all stored out channels
	 * @return
	 */
	public int send(int a, boolean optionalFlag, int x, int y,  int[] chan)
	{
		if (a > 128) {
			System.out.printf("Action can not be larger than 7 bits\n");
			return 1;
		}
		
		if (x > (2^10) || y > (2^10)) {
			System.out.printf("Cordinants outside capablility\n");
			return 1;
		}
		
		int out = (a | (x << 8) | (y << 18));
		if (optionalFlag) { out |= 0x80; };
			
		if (enc) {
			out = encrypt(out);
		}
		
		//if chan is null than send to all channels
		if (chan == null) {
			for (int channel : chanOut) {
				api.sendMsg(channel, out);
			}
		}
		else {
			for (int channel : chan) {
				api.sendMsg(channel, out);
			}
		}
		
		return out;
	}
	
	/**
	 * Look for info on channels
	 * First four bits (farthest left) reserved for mac if used
	 * Next two 10 bit chunks for location
	 * Final 8 bits for action
	 * @param channel information on channels if -1 then read all
	 * @return info from channels, if single channel selected then it's in info[0]
	 */
	public int[][] recieve(int channel)
	{
		int[][] info = new int[1][4];//chanIn.length][4];
		
		if (channel == -1) {
			int read = 10; //@TODO read channel channel 
			info[0] = recieve_helper(read);
			return info; /* short circuit for single channel read */
		}		
		
		for (int i = 0; i < chanIn.length; i++) {
			int read = 10; //@TODO read channel chanIn[i]
			info[i] = recieve_helper(read);
		}
		return info;
	}
	
	//process of breaking apart incoming message
	private int[] recieve_helper(int read)
	{
		if (enc) {
		read = decrypt(read);
		/* check if msg was tampered with */
		if (read == Integer.MIN_VALUE)
			return null;
		}
		
		int[] info = new int[5];
		info[0] = read & 0x0000005f;		 /*action*/
		info[1] = read & 0x00000080;		 /*optFlag*/
		info[2] = (read & 0x0003ff00) >>  8; /*x cord*/
		info[3] = (read & 0x0ffc0000) >> 18; /*y cord*/
		
		return info; 
	}
	
	/**
	 * Encrypt information if desired
	 * @param m
	 * @return
	 */
	public int encrypt(int m)
	{
		return mac(process(m));

	}
	
	/**
	 * childs play encryption but for 4 bytes and short time don't need more
	 * @param m
	 * @return
	 */
	private int process(int m)
	{

		int[] x = {
                0x05468654, 0x0c6f6769, 0x063616c5,
                0x07656170, 0x06f6e000
        };
		
        for (int i = 0; i < x.length; i++) {
		    m ^= x[i];
        }

        return m;
	}
	
	
	/**
	 * Hash a mac for the message and append to front
	 * @param m
	 * @return
	 */
	private int mac(int m)
	{
		int mac = 0;
		m &= 0x0fffffff; //clear way for mac
		mac = m * 113;
		mac %= 16; //4 bits to work with
		return (mac << 28) | m;	
	}
	
	/**
	 * Decrypt information if desired
	 * @param c
	 * @return
	 */
	public int decrypt(int c)
	{
		int info = Integer.MIN_VALUE;

		//if tampered with return min int value
		if (mac(c) != c) {
		    System.out.println("Mac did not match!!!");
            return info;

        }

		return process(c&= 0x0fffffff);
	}
}
