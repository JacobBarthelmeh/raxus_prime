package raxus_prime;

public class Strategy_List {
	//find what behavior the message has ordered
	private Strategy_Enum getStrategy(int in)
	{
		switch(in)
		{
		case 1 :
			return Strategy_Enum.Protect;
		
			//add any more strategys here
			
			default :
				System.out.printf("Strategy %s not yet implemented\n", in);
		}
		return null;
	}
}
