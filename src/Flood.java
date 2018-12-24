import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Flood extends JPanel {

	private final int[][] cells;
	private final Color[] palette;
	private final int rows, cols, cellSize;
	private int clicks = 0;

	public Flood(int rows, int cols, int cellSize, Color[] palette) {
		this.rows = rows;
		this.cols = cols;
		this.cellSize = cellSize;
		this.palette = palette.clone();

		cells = new int[rows][cols];
		Random random = new Random();
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				cells[r][c] = random.nextInt(palette.length);

		setPreferredSize(new Dimension(
				(cols + 2) * cellSize,
				(rows + 2) * cellSize
		));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int row = e.getY() / cellSize - 1;
				int col = e.getX() / cellSize - 1;
				if (isWithinBounds(row, col)) {
					int colorBefore = cells[0][0];
					int colorAfter = cells[row][col];
					if (colorBefore != colorAfter) {
						flood(0, 0, colorBefore, colorAfter);
						clicks++;
						repaint();
					}
				}
			}
		});
	}

	public static void main(String[] args) {
		Flood panel = new Flood(20, 30, 30, new Color[]{
				Color.RED, new Color(0x00E709), Color.BLUE,
				new Color(0x88bbff), Color.ORANGE, Color.YELLOW
		});

		panel.setBackground(new Color(0x333333));

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private boolean isWithinBounds(int row, int col) {
		return (row >= 0) && (col >= 0) && (row < rows) && (col < cols);
	}

	private void flood(int row, int col, int colorBefore, int colorAfter) {
		if (isWithinBounds(row, col) && cells[row][col] == colorBefore) {
			cells[row][col] = colorAfter;
			flood(row + 1, col, colorBefore, colorAfter);
			flood(row - 1, col, colorBefore, colorAfter);
			flood(row, col + 1, colorBefore, colorAfter);
			flood(row, col - 1, colorBefore, colorAfter);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				g.setColor(palette[cells[row][col]]);
				g.fillRect((col + 1) * cellSize, (row + 1) * cellSize, cellSize, cellSize);
			}
		}

		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, cellSize / 2));
		g.drawString("Clicks: " + clicks, 6, getHeight() - 8);
	}

}
