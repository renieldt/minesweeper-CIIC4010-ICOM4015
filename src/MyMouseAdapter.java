import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


 
public class MyMouseAdapter extends MouseAdapter {
	
	public void mousePressed(MouseEvent e) {
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) { 
			c = c.getParent();
			if (c == null) {
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
		switch (e.getButton()) {
		case 1:		//Left mouse button

			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			//Do nothing
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
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
		Color closed = Color.WHITE;
		Color flag = Color.RED;
		Color mine = Color.BLACK;
		Color opened = Color.LIGHT_GRAY;

		int gridX = myPanel.getGridX(x, y);
		int gridY = myPanel.getGridY(x, y);
		
		switch (e.getButton()){
		case 1:		//Left mouse button
			
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell
						//Do nothing
					} else {
						//Released and pressed in same cell
						if (myPanel.gridCells[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == flag) {

						} 
						else {
							myPanel.gridCells[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = closed;
							myPanel.innocent(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
							myPanel.repaint();
							myPanel.gridCells[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.LIGHT_GRAY;
							if(myPanel.gameWon()){
								//Checks if the user have win the came 
								JOptionPane.showMessageDialog(null, "You beated this game!!", "Minesweeper", JOptionPane.INFORMATION_MESSAGE);
								System.exit(0);
							}
						}
						
						
						
						if((myPanel.mines[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == 1) && 
								myPanel.gridCells[myPanel.mouseDownGridX][myPanel.mouseDownGridY] != flag){
							for (int r = 0; r < myPanel.getTotalColumns(); r++){
								for (int t = 0; t < myPanel.getTotalRows(); t++){
									if(myPanel.mines[r][t] == 1){
										myPanel.gridCells[r][t] = mine;
										myPanel.repaint();
									}
								}
							}
							JOptionPane.showMessageDialog(null, "You have stepped on a mine!", "Minesweeper", JOptionPane.INFORMATION_MESSAGE);
							System.exit(0);
						}
					}
				}
			}
			
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					//Click in the grid
					if((myPanel.mouseDownGridX == gridX) && (myPanel.mouseDownGridY == gridY)){
						//White grid with right click
						if(myPanel.gridCells[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == opened){
							//Do nothing
						}
						else {  
							//Grid gray, incorporate the flag
							if(myPanel.gridCells[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == closed){
								myPanel.gridCells[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = flag;
								myPanel.repaint();
								
							}
							else{
								//If grid is a flag, remove it
								if(myPanel.gridCells[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == flag){
									myPanel.gridCells[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = closed;
									myPanel.repaint();
								}
							}
								
						}
					}
				
				}
			}
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
}