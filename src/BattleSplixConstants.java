public interface BattleSplixConstants {
	public static final String APP_NAME="Battle Splix 0.01";
	
	/**
	 * Game states.
	 */
	public static final int GAME_START=0;
	public static final int IN_PROGRESS=1;
	public final int GAME_END=2;
	public final int WAITING_FOR_PLAYERS=3;

	//Board Specifications
	public final static int BOARD_WIDTH = 640;
	public final static int BOARD_HEIGHT = 600;
	public final static int BLOCK_WIDTH = 20;
	public final static int BLOCK_HEIGHT = 20;

	//Types of Tiles
	public static final int BRICKLESS = 0;
	public static final int BRICK = 1;
	public static final int VINE = 2;
	public static final int METAL = 3;

	/**
	 * Game port
	 */
	public static final int PORT=4444;
}