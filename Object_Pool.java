package raxus_prime;

/**
 * Used so that only creating one object then using it by refrence. Using static
 * in the assumption that will only be called by on rc. Otherwise ..... why have
 * communication and all that jazz.
 * 
 * @author TheLogicalWeapon
 * 
 */
public class Object_Pool {
	static Api api;
	static Comm comm;
	static Status status;

	public static Api getApi() {
		if (api == null)
			api = new Api();
		return api;
	}

	/**
	 * Gets rid of the refference to Api object
	 */
	public static void freeApi() {
		api = null;

	}

	public static CommDrone getCommDrone() {
		try {
			if (comm == null || comm.getClass().equals(CommLeader.class))
				comm = new CommDrone();
			return (CommDrone) comm;
		} catch (Exception e) {
			System.out.println("getCommDrone Exception");
		}
		return null;
	}

	/**
	 * Gets rid of the refference to comm
	 */
	public static void freeCommDrone() {
		comm = null;
	}

	public static CommLeader getCommLeader() {
		try {
			if (comm == null || comm.getClass().equals(CommDrone.class))
				comm = new CommLeader();
			return (CommLeader) comm;
		} catch (Exception e) {
			System.out.println("getCommLeader Exception");
		}
		return null;
	}

	/**
	 * Gets rid of the refference to comm
	 */
	public static void freeCommLeader() {
		comm = null;
	}
	
	public static CommHq getCommHq() {
		try {
			if (comm == null || comm.getClass().equals(CommHq.class))
				comm = new CommHq();
			return (CommHq) comm;
		} catch (Exception e) {
			System.out.println("getCommHq Exception");
		}
		return null;
	}

	/**
	 * Gets rid of the refference to comm
	 */
	public static void freeCommHq() {
		comm = null;
	}

	public static Status getStatus() {
		try {
			if (status == null)
				status = new Status();
			return status;
		} catch (Exception e) {
			System.out.println("getStatus Exception");
		}
		return null;
	}

	/**
	 * Gets rid of the refference to status
	 */
	public static void freeStatus() {
		status = null;
	}
}
