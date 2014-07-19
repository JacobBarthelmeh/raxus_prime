package raxus_prime;

public class Behavior_List {
	
	//find what behavior the message has ordered
	public static Behavior_Enum getBehavior(int in)
	{
		switch(in)
		{
		case 1 :
			return Behavior_Enum.Attack;
		case 2 :
			return Behavior_Enum.Run;
		case 3 :
			return Behavior_Enum.Evade;
		case 4 :
			return Behavior_Enum.Seppuku;
		case 5 :
			return Behavior_Enum.ChangeLeader;
		
			//add any more behaviors here
			
			default :
				System.out.printf("Behavior %s not yet implemented\n", in);
		}
		return null;
	}
}
