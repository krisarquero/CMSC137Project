import java.util.ArrayList;

public class Board{
	private String[][] board;
	ArrayList<Tile> tiles = new ArrayList<Tile>();

	public Board(int height, int width){
		this.board = new String[height][width];
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				this.board[i][j] = "0";
			}
		}
	}

	public String toString(){
		String retval = "PLAYER ";
		for(int i=0; i<30; i++){
			for(int j=0; j<30; j++){
				retval+= this.board[i][j];
				if(i!=30) retval+=" ";
			}
		}
		retval+=":";
		return retval;
	}

	public void updateBoard(String name, int x, int y){
		this.board[x][y] = name;
	}

	public String[][] getBoard(){
		return this.board;
	}
}