import javax.swing.JFrame;


public class Main {
		private static void main(String[] args) {
			
			JFrame myFrame = new JFrame("Minesweeper");
			myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			myFrame.setLocation(400, 150);
			myFrame.setSize(500, 400);

			MyPanel myPanel = new MyPanel();
			myPanel.genMines();
			myPanel.setLayout(null);
			myFrame.getContentPane().add(myPanel);
			myFrame.add(myPanel);

			MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
			MyMouseAdapter.getFlags(); 
			myFrame.addMouseListener(myMouseAdapter);
			myFrame.setVisible(true);

			
		}
	}
