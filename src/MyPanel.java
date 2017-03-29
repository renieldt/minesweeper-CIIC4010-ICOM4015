import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;
import javax.swing.JPanel;

public class MyPanel extends JPanel { 
	private static final long serialVersionUID = 3426940946811133635L;
	static final int GRID_X = 55;
	static final int GRID_Y = 55;
	static final int INNER_CELL_SIZE = 29;
	public static final int TOTAL_COLUMNS = 9;
	public static final int TOTAL_ROWS = 9;   //Last row has only one cell
	private static final int AMOUNT_OF_MINES = 10;
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	
	private Random random;
	
	public Color[][] gridCells = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	public int [][] mines = new int [TOTAL_COLUMNS][TOTAL_ROWS];
	public int [][] nearbyCell = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	
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
				gridCells[x][y] = Color.WHITE;
			}
		}
		
		mineGen();
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
		
		//menuBar.add(m);

		//Paint the background
		g.setColor(Color.BLACK);
		g.fillRect(x1, y1, width + 1, height + 1);

		//Draw the grid minus the bottom row (which has only one cell)
		//By default, the grid will be 10x10 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS ; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS)));
		}
		
		//Paint cell colors and evaluate near mines near the cell
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				if ((x == 0) || (y != TOTAL_ROWS)) {
					Color c = gridCells[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
					
					if (gridCells[x][y].equals(Color.LIGHT_GRAY)) {
						int nearMines = setNumbers(x, y);
						if((nearMines != 0) && (nearMines == 1)){
							g.setColor(Color.BLUE);
							g.drawString("" + nearMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
						}
						if((nearMines != 0) && (nearMines == 2)){
							g.setColor(Color.RED);
							g.drawString("" + nearMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
						}
						if((nearMines != 0) && (nearMines == 3)){
							g.setColor(Color.GREEN);
							g.drawString("" + nearMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
						}
						if((nearMines != 0) && (nearMines == 4)){
							g.setColor(Color.YELLOW);
							g.drawString("" + nearMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
						}
						if((nearMines != 0) && (nearMines == 5)){
							g.setColor(Color.ORANGE);
							g.drawString("" + nearMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
						}
						if((nearMines != 0) && (nearMines == 6)){
							g.setColor(Color.BLACK);
							g.drawString("" + nearMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
						}
						if((nearMines != 0) && (nearMines == 7)){
							g.setColor(Color.PINK);
							g.drawString("" + nearMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
						}
						if((nearMines != 0) && (nearMines == 8)){
							g.setColor(Color.MAGENTA);
							g.drawString("" + nearMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
						}
					}

				}
			}
		}
	}
	
	public void innocent(int x, int y){//Recursive method which determines the adjacent grids that not have mines and colors them gray
		if(setNumbers(x, y) == 0) {
			for(int i = x - 1; i <= x + 1; i++) {
				for (int j = y - 1; j <= y + 1; j++) {
					if (i < getTotalColumns() && i >= 0 && j < getTotalRows() && j >= 0) {
						if(gridCells[i][j] == Color.WHITE){
							gridCells[i][j] = Color.LIGHT_GRAY;
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
		if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return x;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
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
		if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return y;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
	
	public void mineGen(){//Method that serves as the generator for the mines 
		random = new Random();
		for(int x = 0; x < AMOUNT_OF_MINES;){
			int X = random.nextInt(TOTAL_COLUMNS);
			int Y = random.nextInt(TOTAL_ROWS);
			if(mines[X][Y] != 1){
				mines[X][Y] = 1;
				x++;
			}
		}
	}
	
	public int setNumbers(int x, int y) {//Method for establishing the number of mines are near
		int nearbyMines = 0;
		for(int i = x-1; i <= x+1; i++) {
			for(int j = y-1; j <= y+1; j++) {
				if(i < TOTAL_COLUMNS && i >= 0 && j < TOTAL_ROWS && j >= 0 ) {
					if(mines[i][j] == 1) {
						nearbyCell[x][y] = 2;
						nearbyMines++;
					}
				}
			}
		}
		return nearbyMines;
	}
	
	public boolean gameWon(){// Method for the winning condition in the game
		int winningCount=0; 
		for (int i=0; i<TOTAL_COLUMNS; i++){
			for(int j=0; j<TOTAL_ROWS; j++){
				if(gridCells[i][j]==Color.LIGHT_GRAY){
					winningCount++;
				} 
			}
		}
		return (winningCount==(TOTAL_COLUMNS*TOTAL_ROWS - AMOUNT_OF_MINES));

	}
	
	public int getTotalColumns(){// Total columns in the frame of the game
		return TOTAL_COLUMNS;
	}
	
	public int getTotalRows(){// Total rows in the frame of the game
		return TOTAL_ROWS;
	}
}