package raxus_prime;

public interface Behavior_Interface {
	int setStatus();
	int setTarget(int x, int y);
	int action();
	int secondaryAction(Drone in);
	int move();
	
}
