package raxus_prime;

public class Movement_Panther {

	Panther panther;
	Api api = Object_Pool.getApi();
	Comm com = Object_Pool.getComm();

	int chan; // channel to look for new obstacles
	boolean duplicate;
	int x, y;

	public Movement_Panther() {
		panther = new Panther(api.getMapWidth(), api.getMapHeight());
		chan = Object_Pool.obstacleChan; // starting channel
		clean();
	}

	/**
	 * Finds an array of x,y cord. to a location on the map x and y are starting
	 * point and x2, y2 are the destination
	 * 
	 * @return [x][x][x][x] [y][y][y][y]
	 */
	public int[][] getPathTo(int x, int y, int x2, int y2) {

		int[][] xy = null;
		try {
			Point[] points = panther.path(new Point(x, y), new Point(x2, y2));

			if (points == null) {
				System.out.println("points was null");
				xy = new int[2][1];
				xy[0][0] = x2;
				xy[1][0] = y2;
			} else {
				for (Point p : points)
					System.out.println(p);
				xy = new int[2][points.length];
				for (int i = 0; i < points.length; i++) {
					xy[0][i] = points[i].x;
					xy[1][i] = points[i].y;
				}
			}
		} catch (Exception e) {
			System.out.println("Error in getPathTo " + e);
		}
		return xy;
	}

	/**
	 * Adds in an obstacle to the graph then checks for any updates
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public int addObstacle(int x, int y) {

		this.x = x;
		this.y = y;
		duplicate = false;
		// read in obstacles so chan is correct then broadcast
		readObstacle();
		if (!duplicate) {
			int increment = 1;
			add(x, y);
			int[] temp = { chan };
			com.send(increment, false, x, y, temp);
			chan += increment;
			clean();
		}

		return 0;
	}

	private int add(int x, int y) {
		panther.addObstacle(new Point(x, y));
		return 0;
	}

	private int clean() {
		panther.flush();
		if (panther.readyToAddEdges()) {
			panther.addEdges();
			panther.removeEdges();
		}
		return 0;
	}

	public int readObstacle() {
		int[][] info = com.recieve(chan);

		while (info != null && info[0][0] != 0) {
			if (info[0][2] == x && info[0][3] == y)
				duplicate = true;
			add(info[0][2], info[0][3]);
			chan += info[0][0]; // action section of msg is used as increament
			info = com.recieve(chan);
			if (chan % 5 == 0)
				clean();
		}

		if (info == null) {
			// @TODO logic to handle tampered with data
			System.out.println("Ohhh snap the channel was tampered with");
		}

		return 0;
	}
}
