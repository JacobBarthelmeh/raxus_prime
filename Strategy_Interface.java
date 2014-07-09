package raxus_prime;

public interface Strategy_Interface {
	
/* this is a rough idea of what may be needed for a strategy */
	public int[] getTargetLocations();
	public Behavior_Interface[] getSuggestedBehaviors();
	public int[] getTurnDelays();
	
	
	
}
