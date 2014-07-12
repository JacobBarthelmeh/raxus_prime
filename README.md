raxus_prime
===========

Framework for competition code

### Adding a Behavior

1. Add new class with name XXX_Behavior
2. Add class name to Behavior_List and Behavior_Enum
3. Implement Behavior_Interface in new XXX_Behavior class
4. Code stuff in new class


### Adding a Strategy

1. Add new class with name XXX_Strategy
2. Add class name to Strategy_List and Strategy_Enum
3. Implement Strategy_Interface in new XXX_Strategy class
4. Code stuff in new class

### API Calls 

To account for possible changes in basic api calls such as rc.move() they can all be found in Api.java and updated as needed.

## Misc

Status.java is to contain information about the robots surroundings ie near enemies and allies.

Comm.java is used for general communications and then extended for specialized use.

# @TODO
* Abstract interaction with momvement
* Tons with Leader and leader choices
* HQ communications
* Fleshing out drone class
