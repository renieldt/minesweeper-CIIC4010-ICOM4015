import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyMouseAdapter extends MouseAdapter {
	public static int flags = 14;
	public static int grayTiles = 0;
	public static int redTiles = 0;
	
	public void mousePress(MouseEvent e) {
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null){
				return;
			}
		}
	
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			
			switch (e.getButton()) {
				case 1: //Left button
					
				case 3: //Right button
					
					break;
				default: //other buttons
					//Do nothing
					break;
			}
			
		}

	
	public void mouseReleased(MouseEvent e) {
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
			
			switch (e.getButton()) {
			case 1: //Left button
				if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed
						if ((gridX > 8) || (gridY > 8)) {
							
						} else {
							//checks if the tile is mine and paints it black if true
							if(myPanel.isMine(myPanel.mouseDownGridX, myPanel.mouseDownGridY)) {
								Color newColor = Color.BLACK;
								for(int i = 0; i < 9; i++){
									for(int j = 0; j < 9; j++){
										if(myPanel.minesOnField[i][j] == true){
											myPanel.mineField[i][j] = newColor;
											myPanel.repaint();
										}
									}
								}
								//Pane with losing message
								final JOptionPane pane = new JOptionPane("Game Over");
								final JDialog d = pane.createDialog("KABOOM");
								d.setVisible(true);
								
							}							
						}
						//Paints tiles gray
						Color newColor = Color.GRAY;
						myPanel.mineField[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
						checkTiles(myPanel, myPanel.mouseDownGridX, myPanel.mouseDownGridY);
						myPanel.repaint();
						
						grayTiles++;
						
						//if the gamer won
						boolean hasWon = true;
						for(int i = 0; i < 8; i++){
							for(int j = 0; j < 8; j++){
								if(myPanel.mineField[i][j].equals(Color.WHITE) && !myPanel.isMine(i, j)) {
									hasWon = false;
									break;
								}
								if(!hasWon){
									break;
								}
										
							}
						}
						
						//Winning Message Pane
						if(hasWon){
							final JOptionPane pane = new JOptionPane("YOU WON");
							final JDialog d = pane.createDialog("Congratulations!");
							d.setVisible(true);
						}	
					}
				}
			}
			myPanel.repaint();
			break;
		case 3:		
			
			//Right mouse button
			
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1) || (myPanel.mouseDownGridX > 8)
					|| (myPanel.mouseDownGridY > 8)) {
				// Pressed outside
				// Do nothing
			} 
			
			else {
				
				if ((gridX == -1) || (gridY == -1)) {
					// Releasing outside
					// Do nothing
				} 
				else {
					
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						// Released on a different cell where it was pressed
						// Do nothing
					} 
					else {
						
						// Released the button on the same cell where it was pressed
						if ((gridX > 8) && (gridY > 8)) {
						} 
						else {
							
							//Paints tile red (Flags) when it was originally white
							
							if (myPanel.mineField[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.WHITE)){
								if (flags > 0){
									Color newColor = Color.RED;
									flags--;
									redTiles ++;
									myPanel.mineField[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
									myPanel.repaint();
								}
							}
							
							
							//If tile = gray
							//Do nothing
							
							else if (myPanel.mineField[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.GRAY)){
								break;
							}
							
							
							//If tile = red, paints it white (removal of flags)
							
							else if(myPanel.mineField[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.RED)){
								flags ++;
								redTiles--;
								Color newColor = Color.WHITE;
								myPanel.mineField[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
								myPanel.repaint();
							}
						}
					}
				}
			}
			myPanel.repaint();
			break;
			
		default: // Some other button (2 = Middle mouse button, etc.)
			
			// Do nothing
			
			break;
		}
	}	
	
	
	/*private void ActionListener() throws IOException {
		//System.exit(0);
		Main.masterFrame =  Main.reinitialize();
		Main.masterFrame.setVisible(true);
	} */
 
	
	//Get number of flags
	public static String getFlags(){
		if(flags <= 0){
			return "0";
		}
		return "" + flags;
	}
	

	//Check tiles for adjacent mines
	public void checkTiles (MyPanel panel, int x, int y){

		int counter = 0;

		if((x -1 >= 0 && x -1 < 9) &&  (y >= 0 && y < 9) && panel.isMine(x -1, y)){
			counter ++;
		} 
		
		if((x -1 >= 0 && x -1 < 9) &&  (y-1 >= 0 && y -1 < 9) && panel.isMine(x -1, y -1 )){
			counter ++;
		} 

		if((x  >= 0 && x < 9) &&  (y-1 >= 0 && y-1 < 9) && panel.isMine(x, y-1)){
			counter ++;
		} 
		
		if((x +1 >= 0 && x + 1< 9) &&  (y >= 0 && y < 9) && panel.isMine(x + 1, y)){
			counter ++;
		} 
		
		if((x + 1 >= 0 && x + 1 < 9) &&  (y + 1>= 0 && y + 1 < 9) && panel.isMine(x + 1, y + 1)){
			counter ++;
		} 
		
		if((x >= 0 && x < 9) &&  (y + 1>= 0 && y + 1< 9) && panel.isMine(x, y + 1)){
			counter ++;
		} 
		
		if((x -1 >= 0 && x -1 < 9) &&  (y + 1 >= 0 && y + 1 < 9) && panel.isMine(x -1, y + 1)){
			counter ++;
		} 
		
		if((x + 1 >= 0 && x + 1 < 9) &&  (y - 1 >= 0 && y - 1 < 9) && panel.isMine(x + 1, y - 1)){
			counter ++;
		} 
		
		//Set numbers on tiles
		if (counter > 0) {

			Color newColor = Color.LIGHT_GRAY;
			panel.mineField[x][y] = newColor;	
			panel.gridAmount[x][y] =  counter + "";

		} 
		else {
			//If mine not found
			if((x - 1 >= 0 && x - 1 < 9) &&  (y >= 0 && y < 9) 
					&& !panel.mineField[x - 1][y].equals(Color.GRAY) 
					&& !panel.mineField[x - 1][y].equals(Color.RED) 
					&& !panel.isMine(x - 1, y)){
				Color newColor =  Color.GRAY;
				panel.mineField[x - 1][y] = newColor;
				checkTiles(panel, x - 1, y);	
			} 
			if((x - 1 >= 0 && x - 1 < 9)
					&&  (y -1 >= 0 && y -1 < 9) 
					&& !panel.mineField[x - 1][y -1].equals(Color.GRAY) 
					&& !panel.mineField[x - 1][y -1].equals(Color.RED) 
					&& !panel.isMine(x - 1, y -1)){
				Color newColor =  Color.GRAY;
				panel.mineField[x - 1][y -1] = newColor;
				checkTiles(panel, x - 1, y -1);
			} 
			if((x - 1 >= 0 && x - 1 < 9)
					&&  (y + 1 >= 0 && y + 1 < 9) 
					&& !panel.mineField[x - 1][y + 1].equals(Color.GRAY) 
					&& !panel.mineField[x - 1][y + 1].equals(Color.RED) 
					&& !panel.isMine(x - 1, y + 1)){
				Color newColor =  Color.GRAY;
				panel.mineField[x - 1][y + 1] = newColor;
				checkTiles(panel, x - 1, y + 1);
			} 
			if((x >= 0 && x < 9)
					&&  (y -1 >= 0 && y -1 < 9) 
					&& !panel.mineField[x][y -1].equals(Color.GRAY) 
					&& !panel.mineField[x][y -1].equals(Color.RED) 
					&& !panel.isMine(x, y -1)){
				Color newColor =  Color.GRAY;
				panel.mineField[x][y -1] = newColor;
				checkTiles(panel, x, y -1);
			} 
			if((x >= 0 && x < 9)
					&&  (y + 1 >= 0 && y + 1 < 9) 
					&& !panel.mineField[x][y + 1].equals(Color.GRAY) 
					&& !panel.mineField[x][y + 1].equals(Color.RED) 
					&& !panel.isMine(x, y + 1)){
				Color newColor =  Color.GRAY;
				panel.mineField[x][y + 1] = newColor;
				checkTiles(panel, x, y + 1);
			} 
			if((x + 1 >= 0 && x + 1 < 9)
					&&  (y >= 0 && y < 9) 
					&& !panel.mineField[x + 1][y].equals(Color.GRAY) 
					&& !panel.mineField[x + 1][y].equals(Color.RED) 
					&& !panel.isMine(x + 1, y)){
				Color newColor =  Color.GRAY;
				panel.mineField[x + 1][y] = newColor;
				checkTiles(panel, x + 1, y);
			} 
			if((x + 1 >= 0 && x + 1 < 9)
					&&  (y - 1>= 0 && y -1 < 9) 
					&& !panel.mineField[x + 1][y -1].equals(Color.GRAY) 
					&& !panel.mineField[x + 1][y - 1].equals(Color.RED) 
					&& !panel.isMine(x + 1, y - 1)){
				Color newColor =  Color.GRAY;
				panel.mineField[x + 1][y - 1] = newColor;
				checkTiles(panel, x + 1, y - 1);
			} 
			if((x + 1 >= 0 && x + 1 < 9)
					&&  (y + 1 >= 0 && y + 1 < 9) 
					&& !panel.mineField[x + 1][y + 1].equals(Color.GRAY) 
					&& !panel.mineField[x + 1][y + 1].equals(Color.RED) 
					&& !panel.isMine(x + 1, y + 1)){
				Color newColor =  Color.GRAY;
				panel.mineField[x + 1][y + 1] = newColor;
				checkTiles(panel, x + 1, y + 1);
			} 
		}
	}
}

