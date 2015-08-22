package org.jointheleague.stephen.bejeweled;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class JewelPanel extends JPanel implements Runnable, ActionListener {

	private JPanel infoPanel = new JPanel();
	private JLabel scoreLabel = new JLabel("Score: " + 0);
	private JLabel timeLabel = new JLabel("Time: " + TIME_START);

	private static final int PANEL_WIDTH = 900;
	private static final int JEWLPANEL_HEIGHT = 800;
	private static final int INFO_HEIGHT = 100;
	private static final int TIME_START = 60;

	private int score = 0;
	private int time = TIME_START;
	private boolean gameOver = false;

	private Timer ticker = new Timer(1000, this);
	
	private enum Gem {
		RED_GEM(1),
		ORANGE_GEM(2),
		YELLOW_GEM(3),
		GREEN_GEM(4),
		BLUE_GEM(5),
		PURPLE_GEM(6),
		WHITE_GEM(7);
		private int value;
		private Gem(int num) {
			value = num;
		}
	}
	private Gem[][] grid = new Gem[8][8];

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new JewelPanel());
	}

	@Override
	public void run() {
		JFrame frame = new JFrame("Bejeweled S");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(PANEL_WIDTH, JEWLPANEL_HEIGHT));
		this.setBackground(Color.CYAN);
		frame.add(this, BorderLayout.CENTER);
		infoPanel.setPreferredSize(new Dimension(PANEL_WIDTH, INFO_HEIGHT));
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		infoPanel.setBackground(Color.YELLOW);
		Font labelFont = new Font("Helvetica", Font.PLAIN, 100);
		scoreLabel.setFont(labelFont);
		timeLabel.setFont(labelFont);
		infoPanel.add(scoreLabel);
		infoPanel.add(timeLabel);
		frame.add(infoPanel, BorderLayout.NORTH);
		frame.pack();
		initializeGrid();
		repaint();
		frame.setVisible(true);
		ticker.start();
	}

	private void initializeGrid() {
		Random r = new Random();
		for (int row = 0; row < grid.length; row++) {
			for (int column = 0; column < grid[row].length; column++) {
				int gemNum = r.nextInt(7);
				grid[row][column] = Gem.values()[gemNum];
			}
		}
	}

	private void updateInfo(int score, int time) {
		scoreLabel.setText("Score: " + score);
		timeLabel.setText("Time: " + time);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		time--;
		updateInfo(score, time);
		if (time == 0) {
			gameOver();
		}
		repaint();
	}

	private void gameOver() {
		ticker.stop();
		gameOver = true;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.CYAN);
		g2.fillRect(0, 0, PANEL_WIDTH, JEWLPANEL_HEIGHT);
		g2.setColor(Color.BLACK);
		Font graphicsFont = new Font("Helvetica", Font.PLAIN, 100);
		g2.setFont(graphicsFont);
		for (int row = 0; row < grid.length; row++) {
			for (int column = 0; column < grid[row].length; column++) {
				g2.drawString(grid[row][column].value + "", 150 + column * 80, 100 + row * 80);
			}
		}
		
		if (gameOver) {
			g2.setColor(Color.RED);
			g2.translate(100, JEWLPANEL_HEIGHT - 100);
			g2.rotate(Math.toRadians(-30));
			g2.scale(3, 3);
			g2.drawString("Game Over", 0, 0);
		}
	}
}
