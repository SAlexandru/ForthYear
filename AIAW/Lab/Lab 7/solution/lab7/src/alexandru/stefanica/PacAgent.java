package alexandru.stefanica;

import java.util.Random;

import pacworld.Dropoff;
import pacworld.Move;
import pacworld.PacPercept;
import pacworld.Pickup;
import pacworld.Say;
import pacworld.VisibleAgent;
import pacworld.VisiblePackage;
import agent.Action;
import agent.Agent;
import agent.Percept;

public class PacAgent extends Agent {

	private String id;
	private int moveDirection = 5;
	private int pickupDirection = 5;
	private int dropOffDirection = 5;

	
	public PacAgent(int id) {
		this.id = "Id:" + id;
	}
	
	@Override
	public void see(Percept p) {
		moveDirection = 5;
		pickupDirection = 5;
		dropOffDirection = 5;
		boolean madeDecision = false;
		//here he can observer different things
		PacPercept p1 = (PacPercept)p;
		//if he has a package, check if he can deliver it this turn
		if(p1.getHeldPackage() != null) {
		madeDecision = verifyIfShouldDropOffPackage(p1);
		}
		//if he can't deliver it this turn check if there are any 
		//agent around that can provide some info
		if(!madeDecision) {
			madeDecision = verifyAgentsAround(p1);
		}
		
		if(!madeDecision && p1.getHeldPackage() == null) {
			verifyPackagesAround(p1);
		}
		if(!madeDecision && p1.getHeldPackage() != null) {
			moveToTarget(p1);
		}
			
		
	}

	@Override
	public Action selectAction() {
		if(moveDirection >=0 && moveDirection <=3) {
			return new Move(moveDirection);
		}
		else if(pickupDirection >= 0 && pickupDirection <=3) {
			return new Pickup(pickupDirection);
		}
		else if(dropOffDirection >= 0  && dropOffDirection <=3) {
			return new Dropoff(dropOffDirection);
		}
		else {
			return new Move((int) (Math.random()*3));
		}
	}
	
	public String getId() {
		return id;
	}
	

	private boolean verifyIfShouldDropOffPackage(PacPercept p1) {
		//check if dropoff is one step from you
		int currentX = p1.getHeldPackage().getX();
		int currentY = p1.getHeldPackage().getY();
		int destX = p1.getHeldPackage().getDestX();
		int destY = p1.getHeldPackage().getDestY();
		
		if(((currentX-1) == destX) && (currentY == destY)) {
			//move left
			dropOffDirection = 3;
			return true;
		}
		else if(((currentX) == destX) && ((currentY+1) == destY)) {
			//move up
			dropOffDirection = 2;
			return true;
		}
		else if(((currentX+1) == destX) && (currentY == destY)) {
			//move right
			dropOffDirection = 1;
			return true;
		}
		else if((((currentX) == destX) && ((currentY-1) == destY))) {
			//move down
			dropOffDirection = 0;
			return true;
		}
		else
			return false;
	}
	
	private boolean verifyAgentsAround(PacPercept p) {
		VisibleAgent[] visAgents = p.getVisAgents();
		//check if any agent is around
		if(visAgents.length > 1) {
			String[] messages = p.getMessages();
			
			//check if any messages
			if(messages.length != 0) {
				moveDirection = processMessages(messages);
				return true;
			}
		}
		return false;
	}
	
	private boolean verifyPackagesAround(PacPercept p) {
			VisiblePackage[] packages = p.getVisPackages();
			//check if there are any visible packages
			if(packages.length != 0) {
				//check if not having a package
				if(p.getHeldPackage() == null) {
					pickupDirection = processCurrentInfo(p);
					return true;
				}
			}
			return false;
	}
	
		private int processMessages(String[] message) {
			return 5;
		}
		
		private int processCurrentInfo(PacPercept p) {
			VisiblePackage[] visiblePackages = p.getVisPackages();
			VisibleAgent[] currentAgents = p.getVisAgents();
			VisibleAgent currentAgent = null;
			for(int i=0;i<currentAgents.length;i++) {
				if(getId() == currentAgents[0].getId())
				{
					currentAgent = currentAgents[i];
				}
			}
			if(currentAgent != null)
			{
			int currentX = currentAgent.getX();
			int currentY = currentAgent.getY();
			
			if(visiblePackages.length >0) {
				for(int i=0;i<visiblePackages.length;i++) {
					int packageX = visiblePackages[i].getX();
					int packageY = visiblePackages[i].getY();
					if((packageX != visiblePackages[i].getDestX()) &&(packageY != visiblePackages[i].getDestY()))
					{
					if(((currentX-1) == packageX) && (currentY == packageY)) {
						//move left
						return 3;
					}
					else if(((currentX) == packageX) && ((currentY+1) == packageY)) {
						//move up
						return 2;
					}
					else if(((currentX+1) == packageX) && (currentY == packageY)) {
						//move right
						return 1;
					}
					else if((((currentX) == packageX) && ((currentY-1) == packageY))) {
						//move down
						return 0;
					}
					}
				}
			}
			}
			return 5;
			
		}
		
		private String createMessage(VisiblePackage[] packages) {
			return "";
		}
		

		private void moveToTarget(PacPercept p1) {
			VisiblePackage currentPackage = p1.getHeldPackage();
			int currentX = p1.getHeldPackage().getX();
			int currentY = p1.getHeldPackage().getY();
			int destX = p1.getHeldPackage().getDestX();
			int destY = p1.getHeldPackage().getDestY();
			
			if(currentX > destX) {
				//move left
				moveDirection = 3;
			}
			else if(currentY > destY) {
				//move up
				moveDirection = 0;
			}
			else if(currentX < destX) {
				//move right
				moveDirection = 1;
			}
			else if(currentY < destY) {
				//move down
				moveDirection = 2;
			}
		}
}
