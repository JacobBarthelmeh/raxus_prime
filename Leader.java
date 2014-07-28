package raxus_prime;

import java.util.Arrays;

import battlecode.common.*;

public class Leader {

	/* current orders from hq */
	Strategy_Interface s;

	int x, y;

	/* @TODO potential communication with other leaders */

	/* @TODO orders to be given to drones */

	/* current status */
	Status status;
	int mapChannel;
	CommLeader com;

	@SuppressWarnings("unused")
	public Leader(int c, int m) {
		int channel = c;
		int mapChannel = m;

		/* add a comm object to object pool */
		com = Object_Pool.getCommLeader();
		com.setLeader(this);
		com.setChannels(channel, mapChannel);

		/* add a status object to object pool */
		status = Object_Pool.getStatus();

		Movement_Panther pant = Object_Pool.getMovement_Panther();
		Movement_Bugging mb = Object_Pool.getMovement_Bugging();
		Api api = Object_Pool.getApi();

		// make sure map is not null
		Object_Pool.getMap();

		while (true) {
			boolean[][] previous = new boolean[api.getMapWidth()][api
					.getMapHeight()];
			previous = Object_Pool.map.clone();

			// try to path to enemy hg
			int[] xy = api.getCurrentLocation();
			int[] x2y2 = api.getEnemyHQLocation();
			int[][] path = pant.getPathTo(xy[0], xy[1], x2y2[0], x2y2[1]);
			if (path == null)
				System.out.println("Path was null");
			for (int i = 0; i < path.length; i++) {
				int[] to;
				int[] at;
				do {
					to = mb.bugToo(path[0][i], path[1][i]);
					api.move(to[0], to[1]);
					at = api.getCurrentLocation();
				} while (to != null && !Arrays.equals(to, at));

				if (to == null) {
					// hit a wall update and try again
					boolean[][] current = Object_Pool.map;
					for (int j = 0; j < api.getMapWidth(); j++) {
						for (int k = 0; k < api.getMapHeight(); k++) {
							if (previous[j][k] != current[j][k]) {
								pant.addObstacle(j, k);
							}
						}
					}

					break;
				}
			}
		}
	}
}
