import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {
	private Random generator = new Random();
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
		myPanel.mouseDownGridX = myPanel.getGridX(x, y);
		myPanel.mouseDownGridY = myPanel.getGridY(x, y);
		int gridX = myPanel.getGridX(x, y);
		int gridY = myPanel.getGridY(x, y);
		myPanel.repaint();

		switch (e.getButton()) {
		case 1:		//Left mouse button

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
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed
						if ((gridX == myPanel.mouseDownGridX) || (gridY == myPanel.mouseDownGridY)) {
							myPanel.placeFlag(gridX, gridY);

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
		case 1:		//Left mouse button
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
						if ((gridX == myPanel.mouseDownGridX) || (gridY == myPanel.mouseDownGridY)) {

							//Check Bombs 
							if (myPanel.minesOnField[myPanel.mouseDownGridX][myPanel.mouseDownGridY]) {
								for(int X = 0; X <= MyPanel.TOTAL_COLUMNS; X++){

									for(int Y = 0; Y < MyPanel.TOTAL_ROWS; Y++) {

										if (myPanel.minesOnField[X][Y]) {
											myPanel.mineField[X][Y]=Color.BLUE;
										}
									}

								}
							}else{
								check(myPanel.mouseDownGridX, myPanel.mouseDownGridY, myPanel);
								
							}
						}


					}
				}
			}
			myPanel.repaint();

			break;
		case 3:		//Right mouse button
			//Do Nothing
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	private void check(int n, int m, MyPanel myPanel) {
		// TODO Auto-generated method stub
		if(myPanel.minas[n][m] == -1) {
			myPanel.minas[n][m] = 0;
			for(int X = 0; X <= MyPanel.TOTAL_COLUMNS; X++){
				if (X < 0 || X > MyPanel.TOTAL_COLUMNS) {
					continue;
				}

			for(int Y = 0; Y < MyPanel.TOTAL_ROWS; Y++) {
				if (Y < 0 || Y > MyPanel.TOTAL_ROWS){
					continue;
				}
				if (X == n && Y == m){
					continue;
				}
				if (myPanel.minesOnField[X][Y]){
					myPanel.minas[n][m]++;
				}
			}
		}
			if(myPanel.minas[n][m] == 0){
				for(int X = 0; X <= MyPanel.TOTAL_COLUMNS; X++){
					if (X < 0 || X > MyPanel.TOTAL_COLUMNS) {
						continue;
					}

				for(int Y = 0; Y < MyPanel.TOTAL_ROWS; Y++) {
					if (Y < 0 || Y > MyPanel.TOTAL_ROWS){
						continue;
					}
					if (X == n && Y == m){
						continue;
					}
						check(X, Y, myPanel); 
					
				}
			}
			}
		}
		
		
	}


}
