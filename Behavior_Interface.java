package raxus_prime;
import battlecode.common.RobotController;

public interface Behavior_Interface {
	int setStatus(Status in);
	int setTarget(int x, int y);
	int action(RobotController rc);
	int secondaryAction(Drone in);
	int move(RobotController rc);
	
}
