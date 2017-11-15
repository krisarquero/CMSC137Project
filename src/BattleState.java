/*
	Code referenced from sir Joseph Anthony Hermocilla
*/

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("unchecked")

public class BattleState{
	private Map players=new HashMap();

	public BattleState(){}

	public void update(String name, Tank player){
		players.put(name,player);
	}

	public String toString(){
		String val="";
		for(Iterator i=players.keySet().iterator();i.hasNext();){
			String name=(String)i.next();
			Tank player=(Tank)players.get(name);
			val+=player.toString()+":";
		}
		return val;
	}
	
	public Map getPlayers(){
		return players;
	}
}
