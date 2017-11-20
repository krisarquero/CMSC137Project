import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import java.io.FileNotFoundException;

public class Board implements BattleSplixConstants{
	private String[][] board;
	private ArrayList<Tile> tiles = new ArrayList<Tile>();

	public Board(int height, int width){
		this.board = new String[height][width];
		this.getFromFile("Map.txt");
		this.setLayer();
	}

	public String[][] getBoard(){
		return this.board;
	}

	public void updateBoard(String data, int x, int y){
		this.board[x][y] = data;
	}

	public void removeTile(int x, int y){
		board[x][y] = "0";
	}

	public void replaceTile(int x, int y, String data){
		board[x][y] = data;
	}

	public void getFromFile(String file){
		ArrayList<ArrayList<String>> temp = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String currentLine;

			while((currentLine = br.readLine()) != null){
				if(currentLine.isEmpty()){
					continue;
				}

				ArrayList<String> row = new ArrayList<>();
				String[] data = currentLine.split(" ");

				for(String str:data){
					if(!(str.isEmpty())){
						String block = str;
						row.add(block);
					}
				}
				temp.add(row);
			}
		}catch(Exception e){ }
		int width = temp.get(0).size();
		int height = temp.size();

		for(int x=0;x<height;x++){
			for(int y=0;y<width;y++){
				this.board[x][y] = temp.get(x).get(y);
			}
		}
	}

	public void setLayer(){
		ArrayList<Tile> temp = new ArrayList<Tile>();
		int x =0 ,y=0, h = BOARD_HEIGHT/BLOCK_HEIGHT, w = BOARD_WIDTH/BLOCK_WIDTH;
		Tile s;

		for(int j=0;j<this.board.length;j++){
			y = j % h;
			for(int i=0;i<this.board[0].length;i++){
				x = i%w;
				switch(Integer.parseInt(board[j][i])){
					case BRICK:
							s = new Tile(x*BLOCK_HEIGHT,y*BLOCK_WIDTH,BLOCK_HEIGHT,BLOCK_WIDTH,BRICK);
							temp.add(s);
							//s.sprite.start();
							break;
					case VINE:
							s = new Tile(x*BLOCK_HEIGHT,y*BLOCK_WIDTH,BLOCK_HEIGHT,BLOCK_WIDTH,VINE);
							temp.add(s);
							//s.sprite.start();
							break;
					case METAL:
							s = new Tile(x*BLOCK_HEIGHT,y*BLOCK_WIDTH,BLOCK_HEIGHT,BLOCK_WIDTH,METAL);
							temp.add(s);
							break;
				}
			}
		}
	}

	// public void removeBlock(Block block){
	// 	if (block.getType() == Sprite.BRICK || block.getType()==PowerUp.AMMO_UPGRADE
	// 		|| block.getType()==PowerUp.GRENADE || block.getType()==PowerUp.NEW_LIFE || block.getType()==PowerUp.SPEED_BOOSTER){
	// 		this.tiles.remove(block);
	// 		layer.removeBlock(block.getYPos()/Map.BLOCK_WIDTH,block.getXPos()/Map.BLOCK_HEIGHT);
	// 		try{
	// 			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("Audio/brick.wav"));
	// 			Clip clip = AudioSystem.getClip();
	// 			clip.open(audioInputStream);
	// 			clip.start();
	// 		}catch(Exception e){}

	// 	}
	// }

	// public void replaceBlock(Block block){
	// 	if (block.getType() == Sprite.BRICK){
	// 		this.tiles.remove(block);
	// 		Random rand = new Random();
	// 		int type = rand.nextInt(4)+100;

	// 		this.tiles.add(new PowerUp(block.getXPos(),block.getYPos(),Map.BLOCK_WIDTH,Map.BLOCK_HEIGHT,type));
	// 		layer.replaceBlock(block.getYPos()/Map.BLOCK_WIDTH,block.getXPos()/Map.BLOCK_HEIGHT,type);
	// 	}
	// }
}