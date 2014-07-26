package raxus_prime;

/**
 * Should be all that drones need for movements
 * 
 * @author Alex and TheLogicalWeapon
 */
public class Movement_Bugging {
	Point me;
	boolean[][] map;
	int[] target = new int[2];
	MBugger mb;
	Api api = Object_Pool.getApi();

	public Movement_Bugging() {
		/*
		 * should be refrence to map and therfor be updated in Object_pool also
		 * when updated in Movement
		 */
		this.map = Object_Pool.getMap();
		mb = new MBugger(this);
		target[0] = -1;
		target[1] = -1;
	}

	/**
	 * returns next location for bugging to target
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public int[] bugToo(int x, int y) {
		try {
			// set new location if it is different than current target
			//System.out.printf("Bugging to (%d,%d)", x, y);
			//System.out.printf("Demensions of map [%d][%d]\n", map.length,
			//		map[0].length);
			if (target[0] != x && target[1] != y || me == null) {
				int[] xy = api.getCurrentLocation();
				this.me = new Point(xy[0], xy[1]);
				//System.out.println("Setting me" + this.me);

				setGoal(new Point(x, y));
				target[0] = x;
				target[1] = y;
			}
			return getNextStep();
		} catch (Exception e) {
			System.out.println("Error in bugToo()");
		}
		return null;
	}

	/**
	 * Gets the {x,y} location for where the next step should be
	 * 
	 * @return
	 */
	private int[] getNextStep() {
		try {
			int[] xy = new int[2];

			// sanity check
			if (this.me == null) {
				System.out.println("Error me is null");
				return null;
			}

			this.me = mb.nextMove();
			if (me == null) {
				xy = null;
			} else {
				xy[0] = me.x;
				xy[1] = me.y;
			}

			return xy;
		} catch (Exception e) {
			System.out.println("Error in getNextStep()");
		}
		return null;
	}

	public void setGoal(Point p) {
		mb.setStartAndFinish(me, p);
	}

	/**
	 * Used in case of custom behaviors for movement patterns Such as setting
	 * routes on the map as false not being traversable Users responsibility to
	 * make sure to keep a copy of the old map if they wish to revert back
	 * though since by refrence if Object_Pool is unaltered then Object_Pool
	 * should still contain the original map.
	 * 
	 * @param map
	 * @return
	 */
	public int setMap(boolean map[][]) {
		this.map = map;
		return 0;
	}
}
