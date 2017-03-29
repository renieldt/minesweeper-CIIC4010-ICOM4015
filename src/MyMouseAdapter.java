import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyMouseAdapter extends MouseAdapter {
	//private Random generator = new Random();
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
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
			MyPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			//Do nothing
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find myPanel
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
			
			
			if ((MyPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((MyPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed
						if (myPanel.colorArray[MyPanel.mouseDownGridX][myPanel.mouseDownGridY] == flag) {
									//do nothing
						}
						else {
							myPanel.colorArray[MyPanel.mouseDownGridX][myPanel.mouseDownGridY] = closed;
							myPanel.innocent(MyPanel.mouseDownGridX, myPanel.mouseDownGridY);
							myPanel.repaint();
							myPanel.colorArray[MyPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.LIGHT_GRAY;
							if(myPanel.winGame()){
								//Verifies in myPanel if won 
								JOptionPane.showMessageDialog(null, "You beated the game!", "Minesweeper", JOptionPane.INFORMATION_MESSAGE);
								System.exit(0);
							}
						}
						if((myPanel.minas[MyPanel.mouseDownGridX][myPanel.mouseDownGridY] == 1) && 
								myPanel.colorArray[MyPanel.mouseDownGridX][myPanel.mouseDownGridY] != flag){
							for (int r = 0; r < myPanel.getColumns(); r++){
								for (int t = 0; t < myPanel.getROWS(); t++){
									if(myPanel.minas[r][t] == 1){
										myPanel.colorArray[r][t] = mine;
										myPanel.repaint();
									}
								}
							}
							JOptionPane.showMessageDialog(null, "You found the bomb!", "Minesweeper", JOptionPane.INFORMATION_MESSAGE);
							System.exit(0);
						}
					}
				}
			}
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			
			if ((MyPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} 
			else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} 
				else {
					if((myPanel.mouseDownGridX == gridX) && (myPanel.mouseDownGridY == gridY)){
						if(myPanel.colorArray[MyPanel.mouseDownGridX][myPanel.mouseDownGridY] == opened){
							//Do nothing
						}
						else {  
							//pressed and released in cell
							if (myPanel.colorArray[MyPanel.mouseDownGridX][myPanel.mouseDownGridY] == closed){
								myPanel.colorArray[MyPanel.mouseDownGridX][myPanel.mouseDownGridY] = flag;
								myPanel.repaint();
								
							}
							else{
								if(myPanel.colorArray[MyPanel.mouseDownGridX][myPanel.mouseDownGridY] == flag){
									myPanel.colorArray[MyPanel.mouseDownGridX][myPanel.mouseDownGridY] = closed;
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