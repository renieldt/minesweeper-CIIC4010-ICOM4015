import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 60;
	private static final int INNER_CELL_SIZE = 29;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 10;   //Last row has only one cell
	private int minesWanted = 20;
	private Random random;
	public int x = -1;
	public int y = -1;
	public static int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public int difficult = 0;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	public int[][] minas = new int [TOTAL_COLUMNS][TOTAL_ROWS];
	public int [][] around = new int[TOTAL_COLUMNS][TOTAL_ROWS];

	public MyPanel() {   //This is the constructor... this code runs first to initialize
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}
		
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
			}
		}
	}
		
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		//Paint the background
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x1, y1, width + 1, height+1);

		//Draw the grid minus the bottom row (which has only one cell)
		//By default, the grid will be 10x10 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS - 1; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS - 1)));
		}

		//Draw an additional cell at the bottom left
		//g.drawRect(x1 + GRID_X, y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS - 1)), INNER_CELL_SIZE + 1, INNER_CELL_SIZE + 1);

		//Paint cell colors and look for nearby bombs
				for (int x = 0; x < TOTAL_COLUMNS; x++) {
					for (int y = 0; y < TOTAL_ROWS; y++) {
						if ((x == 0) || (y != TOTAL_ROWS)) {
							Color c = colorArray[x][y];
							g.setColor(c);
							g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
							
							if (colorArray[x][y].equals(Color.GRAY)) {
								int nearby = setNumbers(x, y);
								if((nearby != 0) && (nearby == 1)){
									g.setColor(Color.BLUE);
									g.drawString("" + nearby, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
								}
								if((nearby != 0) && (nearby == 2)){
									g.setColor(Color.RED);
									g.drawString("" + nearby, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
								}
								if((nearby != 0) && (nearby == 3)){
									g.setColor(Color.GREEN);
									g.drawString("" + nearby, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
								}
								if((nearby != 0) && (nearby == 4)){
									g.setColor(Color.YELLOW);
									g.drawString("" + nearby, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
								}
								if((nearby != 0) && (nearby == 5)){
									g.setColor(Color.ORANGE);
									g.drawString("" + nearby, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
								}
								if((nearby != 0) && (nearby == 6)){
									g.setColor(Color.BLACK);
									g.drawString("" + nearby, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
								}
								if((nearby != 0) && (nearby == 7)){
									g.setColor(Color.PINK);
									g.drawString("" + nearby, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
								}
								if((nearby != 0) && (nearby == 8)){
									g.setColor(Color.MAGENTA);
									g.drawString("" + nearby, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
								}
							}

						}
					}
				}
			}
	
	public int getColumns(){
		return TOTAL_COLUMNS;
	}
	public int getROWS(){
		return TOTAL_ROWS;
	}
	
	//Method to determine the adjacent cells with no mines and colors them gray
	public void innocent(int x, int y){
		if(setNumbers(x, y) == 0) {
			for(int i = x - 1; i <= x + 1; i++) {
				for (int j = y - 1; j <= y + 1; j++) {
					if (i < getColumns() && i >= 0 && j < getROWS() && j >= 0) {
						if(colorArray[i][j] == Color.WHITE){
							colorArray[i][j] = Color.LIGHT_GRAY;
							innocent(i, j);
						}
					}
				}
			}
		}
	}
	
	
	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		/*if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return x;
		}*/
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
	public int getGridY(int x, int y) {

		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		/*if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return y;
		}*/
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
	//By Lemanuel Colon
	public void genMines(){
				int minesPlaced = 0;
				while(minesPlaced < minesWanted){
					int x = new Random().nextInt(TOTAL_COLUMNS);
					int y = new Random().nextInt(TOTAL_ROWS-1);
					if(minas[x][y] == -1){
						x = new Random().nextInt(TOTAL_COLUMNS);
						y = new Random().nextInt(TOTAL_ROWS-1); 
					}
					minas[x][y] = -1;
					minesPlaced++;
		}
	}
	
	public void mineGenerator(){//Method generator of mines 
		random = new Random();
		for(int x = 0; x < minesWanted;){
			int X = random.nextInt(TOTAL_COLUMNS);
			int Y = random.nextInt(TOTAL_ROWS);
			if(minas[X][Y] != 1){
				minas[X][Y] = 1;
				x++;
			}
		}
	}
	
	public int setNumbers(int x, int y) {//Method to set the numbers of nearby mines 
		int nearbyMines = 0;
		for(int i = x-1; i <= x+1; i++) {
			for(int j = y-1; j <= y+1; j++) {
				if(i < TOTAL_COLUMNS && i >= 0 && j < TOTAL_ROWS && j >= 0 ) {
					if(minas[i][j] == 1) {
						around[x][y] = 2;
						nearbyMines++;
					}
				}
			}
		}
		return nearbyMines;
	}
	
	//By Lemanuel Colon
	public void readMines(int x, int y){
		if(minas[x][y] == -1){
			Color newColor = Color.BLACK;
			colorArray[x][y] = newColor;
			this.repaint();
		} else {
			Color newColor = Color.LIGHT_GRAY;
			colorArray[x][y] = newColor;
			this.repaint();
			
		}
		
	}
	
	public boolean winGame(){// Method for winning
		int gridCount=0; 
		for (int i=0; i<TOTAL_COLUMNS; i++){
			for(int j=0; j<TOTAL_ROWS; j++){ 
				if(colorArray[i][j]==Color.LIGHT_GRAY){
					gridCount++;
				} 
			}
		}
		return (gridCount==(TOTAL_COLUMNS*TOTAL_ROWS - minesWanted));
	}
}

	

	

