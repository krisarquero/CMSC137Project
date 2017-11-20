import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//Important: height and width of each player is 20

public class BattleSplixState{
	private Map players=new HashMap();

	public BattleSplixState(){}

	public void update(String name, TankPlayer player){
		players.put(name,player);
	}
	
	public String toString(){
		String retval="";
		//retval+=board.toString();
		for(Iterator ite=players.keySet().iterator();ite.hasNext();){
			String name=(String)ite.next();
			TankPlayer player=(TankPlayer)players.get(name);
			retval+=player.toString()+":";
		}
		return retval;
	}
	
	public Map getPlayers(){
		return players;
	}
}
