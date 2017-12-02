public class ChatClient {
	public static void main(String[] args){
		try {
			System.out.println("Added " + args[0] + " to the group chat.");
			ChatGUI client = new ChatGUI(args[0], args[1]);
			Thread thread = new Thread(client);
			thread.start();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
