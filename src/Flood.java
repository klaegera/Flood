import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Flood extends JPanel {

	int border = 20;
	int clicks = 35;
	int[][] field = new int[20][20];
	int size = 30;

	Flood() {
		setPreferredSize(new Dimension(border + field.length * size + border, border + field.length * size + border));
		addMouseListener(new Mouse());
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Flood panel = new Flood();
		frame.setResizable(false);
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(3);
		panel.run();
		frame.setVisible(true);
	}

	void run() {
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[0].length; y++) {
				Random rand = new Random();
				field[x][y] = rand.nextInt(6) + 1;
			}
		}
	}

	void click(int cx, int cy) {
		int color = field[cx][cy];
		mark(0, 0, field[0][0]);
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[0].length; y++) {
				if (field[x][y] == 0) {
					field[x][y] = color;
				}
			}
		}
		clicks--;
		repaint();
	}

	void mark(int x, int y, int color) {
		// check bounds here. ::inBounds method?
		if (field[x][y] == color) {
			field[x][y] = 0;
			if (x > 0) {
				mark(x - 1, y, color);
			}
			if (x < field.length - 1) {
				mark(x + 1, y, color);
			}
			if (y > 0) {
				mark(x, y - 1, color);
			}
			if (y < field[0].length - 1) {
				mark(x, y + 1, color);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[0].length; y++) {
				Color color = Color.BLACK;
				switch (field[x][y]) {
					case 1:
						color = Color.RED;
						break;
					case 2:
						color = Color.GREEN;
						break;
					case 3:
						color = Color.BLUE;
						break;
					case 4:
						color = Color.YELLOW;
						break;
					case 5:
						color = Color.MAGENTA;
						break;
					case 6:
						color = Color.CYAN;
						break;
				}
				g.setColor(color);
				g.fillRect(border + (x * size), border + (y * size), size, size);
			}
		}
		g.setColor(Color.BLACK);
		g.drawString("" + clicks, 3, 15);
	}

	class Mouse implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent me) {
		}

		@Override
		public void mousePressed(MouseEvent me) {
		}

		@Override
		public void mouseReleased(MouseEvent me) {
			if (me.getX() >= border && me.getX() <= border + (field.length * size) && me.getY() >= border && me.getY() <= border + (field[0].length * size)) {
				click((me.getX() - border) / size, (me.getY() - border) / size);
			}
		}

		@Override
		public void mouseEntered(MouseEvent me) {
		}

		@Override
		public void mouseExited(MouseEvent me) {
		}

	}

}
