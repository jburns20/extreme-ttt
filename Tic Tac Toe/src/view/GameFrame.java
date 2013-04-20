package view;

import general.Location;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class GameFrame extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = -6399886456682347905L;
	private GameViewDelegate delegate;
	private EntireGridPanel gridPanel;
	static int levels;
	static int dimensions;
	private JLabel scoreLabel1;
	private JLabel scoreLabel2;
	
	/**
	 * Constructs a new GameFrame object with the specified number of levels, dimensions, and player names.
	 */
	public GameFrame(int levels, int dimensions, String[] playerNames) {
		GameFrame.levels = levels;
        GameFrame.dimensions = dimensions;
        Container gamePanel = getContentPane(); // {
			gamePanel.setLayout(new BorderLayout());
			JPanel controlPanel = new JPanel(); // {
				controlPanel.setLayout(new GridLayout(1,3));
				//controlPanel.setSize(gamePanel.getWidth(), 50);
				//controlPanel.setLocation(0, 0);
				JPanel leftPlayerPanel = new JPanel();
				leftPlayerPanel.setLayout(new GridLayout(2,1));
				JLabel playerLabel1 = new JLabel(playerNames[0]);
				playerLabel1.setHorizontalAlignment(SwingConstants.CENTER);
				leftPlayerPanel.add(playerLabel1);
				scoreLabel1 = new JLabel("0");
				scoreLabel1.setHorizontalAlignment(SwingConstants.CENTER);
				leftPlayerPanel.add(scoreLabel1);
				controlPanel.add(leftPlayerPanel);
				JPanel controlButtonsPanel = new JPanel(); // {
					controlButtonsPanel.setLayout(new GridLayout(2,1));
					JButton resetButton = new JButton("Reset Scores");
					JButton restartButton = new JButton("Restart Game");
					resetButton.addActionListener(this);
					resetButton.setActionCommand("reset");
					restartButton.addActionListener(this);
					restartButton.setActionCommand("restart");
					controlButtonsPanel.add(resetButton);
					controlButtonsPanel.add(restartButton);
			//  }
				controlPanel.add(controlButtonsPanel);
				JPanel rightPlayerPanel = new JPanel();
				rightPlayerPanel.setLayout(new GridLayout(2,1));
				JLabel playerLabel2 = new JLabel(playerNames[1]);
				playerLabel2.setHorizontalAlignment(SwingConstants.CENTER);
				rightPlayerPanel.add(playerLabel2);
				scoreLabel2 = new JLabel("0");
				scoreLabel2.setHorizontalAlignment(SwingConstants.CENTER);
				rightPlayerPanel.add(scoreLabel2);
				controlPanel.add(rightPlayerPanel);
			//  }
				gamePanel.add(controlPanel, BorderLayout.NORTH);
				//TilePanel gridPanel = new TilePanel(new Location(new int[] {}), levels, dimensions, this);
				gridPanel = new EntireGridPanel(levels, dimensions);
				gridPanel.addMouseListener(this);
				gamePanel.add(gridPanel, BorderLayout.CENTER);
		this.setSize(500,500);
		this.setMinimumSize(new Dimension(250,300));
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.validate();
	}
		
	/**
	 * Changes the view's representation of the specified locations according to the specified values.
	 */
	public void updateLocations(Map<Location, Integer> locs) {
		for (Location loc : locs.keySet()) {
			gridPanel.setValue(loc, locs.get(loc));
		}
	}
	
	public void updateScores(int score1, int score2) {
		scoreLabel1.setText(""+score1);
		scoreLabel2.setText(""+score2);
		scoreLabel1.repaint();
		scoreLabel2.repaint();
	}
	
	public void clearBoard() {
		getContentPane().remove(gridPanel);
		gridPanel = new EntireGridPanel(levels, dimensions);
		gridPanel.addMouseListener(this);
		getContentPane().add(gridPanel, BorderLayout.CENTER);
		getContentPane().invalidate();
		getContentPane().validate();
	}
	
	public void gameIsOver(int winner) {
		gridPanel.removeMouseListener(this);
	}
	
	public void setValidLocation(Location loc) {
		gridPanel.setValidLocation(loc);
	}

	/**
	 * Setter and getter for Delegate
	 */
	public GameViewDelegate getDelegate() { return delegate; }
	public void setDelegate(GameViewDelegate delegate) { this.delegate = delegate; }

	/**
	 * Responds to the reset and restart buttons.
	 */
	public void actionPerformed(ActionEvent e) {
		if (!(e.getSource() instanceof JButton)) return;
		JButton source = (JButton)e.getSource();
		if (source.getActionCommand() == "reset") {
			if (delegate != null) delegate.resetClicked();
		} else if (source.getActionCommand() == "restart") {
			if (delegate != null) delegate.restartClicked();
		}
	}

	/**
	 * Responds to mouse events on the game panel.
	 */
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		int x = p.x;
		int y = p.y;
		int[] locArray = new int[levels];
		double tileWidth = gridPanel.getWidth();
		double tileHeight = gridPanel.getHeight();
		for (int i=0; i<levels; i++) {
			tileWidth /= dimensions;
			tileHeight /= dimensions;
			int col = (int)(x/tileWidth);
			int row = (int)(y/tileHeight);
			x -= tileWidth*col;
			y -= tileHeight*row;
			locArray[i] = dimensions*row + col;
		}
		if (delegate != null) delegate.locationClicked(new Location(locArray));
	}
	public void mouseReleased(MouseEvent e) { }
}
