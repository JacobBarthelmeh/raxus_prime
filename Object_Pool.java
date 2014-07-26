package raxus_prime;

import java.util.Arrays;

/**
 * Used so that only creating one object then using it by refrence. Using static
 * in the assumption that will only be called by one rc. Otherwise ..... why have
 * communication and all that jazz.
 * 
 * @author TheLogicalWeapon
 * 
 */
public class Object_Pool {
	static Api api;
	static Comm comm;
	static Status status;
	static Movement_Bugging mooBug;
	static boolean map[][];

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
	
	public static boolean[][] getMap() {
		if (map == null) {
			map = new boolean[api.getMapWidth()][api.getMapHeight()];
			for (boolean[] i: map)
				Arrays.fill(i, true);
		}
		return map;
	}

	/**
	 * Gets rid of the refference to map object
	 */
	public static void freeMap() {
		map = null;
	}
	
	public static Movement_Bugging getMovement_Bugging() {
		if (mooBug == null)
			mooBug = new Movement_Bugging();
		return mooBug;
	}

	/**
	 * Gets rid of the refference to Movement_Bugging
	 */
	public static void freeMovement_Bugging() {
		mooBug = null;
	}
}
