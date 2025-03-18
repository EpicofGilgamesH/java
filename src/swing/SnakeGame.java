package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class SnakeGame  extends JPanel implements ActionListener, KeyListener {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final int DOT_SIZE = 10;
	private static final int ALL_DOTS = WIDTH * HEIGHT / (DOT_SIZE * DOT_SIZE);
	private static final int RAND_POS = 39;

	private int[] x = new int[ALL_DOTS];
	private int[] y = new int[ALL_DOTS];

	private int dots;
	private int apple_x;
	private int apple_y;

	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	private boolean inGame = true;

	private Timer timer;
	private Random random;

	public SnakeGame() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);

		random = new Random();
		initGame();
	}

	public void initGame() {
		dots = 3;

		for (int i = 0; i < dots; i++) {
			x[i] = 50 - i * DOT_SIZE;
			y[i] = 50;
		}

		createApple();
		timer = new Timer(80, this);
		timer.start();
	}

	public void createApple() {
		int r = (int) (Math.random() * RAND_POS);
		apple_x = r * DOT_SIZE;

		r = (int) (Math.random() * RAND_POS);
		apple_y = r * DOT_SIZE;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	private void doDrawing(Graphics g) {
		if (inGame) {
			g.setColor(Color.GREEN);
			for (int i = 0; i < dots; i++) {
				g.fillRect(x[i], y[i], DOT_SIZE, DOT_SIZE);
			}

			g.setColor(Color.RED);
			g.fillRect(apple_x, apple_y, DOT_SIZE, DOT_SIZE);

			Toolkit.getDefaultToolkit().sync();
		} else {
			gameOver(g);
		}
	}

	private void gameOver(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		FontMetrics fm = getFontMetrics(g.getFont());
		g.drawString("Game Over", (WIDTH - fm.stringWidth("Game Over")) / 2, HEIGHT / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (inGame) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}

	private void move() {
		for (int i = dots; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		if (left) {
			x[0] -= DOT_SIZE;
		}

		if (right) {
			x[0] += DOT_SIZE;
		}

		if (up) {
			y[0] -= DOT_SIZE;
		}

		if (down) {
			y[0] += DOT_SIZE;
		}
	}

	private void checkApple() {
		if (x[0] == apple_x && y[0] == apple_y) {
			dots++;
			createApple();
		}
	}

	private void checkCollisions() {
		for (int i = dots; i > 0; i--) {
			if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
				inGame = false;
			}
		}

		if (x[0] > WIDTH) {
			inGame = false;
		}

		if (x[0] < 0) {
			inGame = false;
		}

		if (y[0] > HEIGHT) {
			inGame = false;
		}

		if (y[0] < 0) {
			inGame = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Not used
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT && !right) {
			left = true;
			up = false;
			down = false;
		}

		if (key == KeyEvent.VK_RIGHT && !left) {
			right = true;
			up = false;
			down = false;
		}

		if (key == KeyEvent.VK_UP && !down) {
			up = true;
			right = false;
			left = false;
		}

		if (key == KeyEvent.VK_DOWN && !up) {
			down = true;
			right = false;
			left = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Not used
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Snake Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new SnakeGame());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
