package alexandru.stefanica;

import java.util.Random;

import vacworld.GoForward;
import vacworld.ShutOff;
import vacworld.SuckDirt;
import vacworld.TurnLeft;
import vacworld.TurnRight;
import vacworld.VacPercept;
import agent.Action;
import agent.Agent;
import agent.Percept;

public class VacAgent extends Agent {
	private VacPercept vp;
	private final Action[] actions = new Action[] {new TurnLeft(), 
											 	   new TurnRight(), 
											       new GoForward(),
											       new SuckDirt(),
											       new ShutOff()
											      };
	private final Random r = new Random();
	private int count = 0;

	@Override
	public void see(Percept p) {
		vp = (VacPercept)p;
	}

	@Override
	public Action selectAction() {
		if (100 == count) {
			return actions[4];
		}
		else if (vp.seeDirt()) {
			++count;
			return actions[3];
		}
		else if (vp.seeObstacle()) {
			return actions[r.nextInt(2)];	
		}
		++count;
		return actions[2];
	}

	@Override
	public String getId() {
		return "VacAgent";
	}

}
