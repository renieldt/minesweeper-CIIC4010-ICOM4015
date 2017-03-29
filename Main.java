import javax.swing.JFrame;
 
public class Main { 
	public static void main(String[] args) {
		JFrame myFrame = new JFrame("Minesweeper");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(340, 400);
		
		MyPanel myPanel = new MyPanel();
		myPanel.genMines();
		myFrame.add(myPanel);
		
		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		myFrame.addMouseListener(myMouseAdapter);

		myFrame.setVisible(true); 
	}
}
