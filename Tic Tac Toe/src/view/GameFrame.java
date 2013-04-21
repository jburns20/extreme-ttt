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
        //Initialize the Game Panel (content pane)
        Container gamePanel = getContentPane();
		gamePanel.setLayout(new BorderLayout());
		//Initialize the Control Panel {
			JPanel controlPanel = new JPanel();
			controlPanel.setLayout(new GridLayout(1,3));
			//Initialize the Left Player Panel {
				JPanel leftPlayerPanel = new JPanel();
				leftPlayerPanel.setLayout(new GridLayout(2,1));
				JLabel playerLabel1 = new JLabel(playerNames[0]);
				playerLabel1.setHorizontalAlignment(SwingConstants.CENTER);
				leftPlayerPanel.add(playerLabel1);
				scoreLabel1 = new JLabel("0");
				scoreLabel1.setHorizontalAlignment(SwingConstants.CENTER);
				leftPlayerPanel.add(scoreLabel1);
		//  }
			controlPanel.add(leftPlayerPanel);
			//Initialize the Control Buttons Panel {
				JPanel controlButtonsPanel = new JPanel();
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
			//Initialize the Right Player Panel {
				JPanel rightPlayerPanel = new JPanel();
				rightPlayerPanel.setLayout(new GridLayout(2,1));
				JLabel playerLabel2 = new JLabel(playerNames[1]);
				playerLabel2.setHorizontalAlignment(SwingConstants.CENTER);
				rightPlayerPanel.add(playerLabel2);
				scoreLabel2 = new JLabel("0");
				scoreLabel2.setHorizontalAlignment(SwingConstants.CENTER);
				rightPlayerPanel.add(scoreLabel2);
		//  }
			controlPanel.add(rightPlayerPanel);
	//  }
		gamePanel.add(controlPanel, BorderLayout.NORTH);
		//Initialize the Grid Panel {
			gridPanel = new EntireGridPanel(levels, dimensions);
			gridPanel.addMouseListener(this);
	//  }
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
	
	/**
	 * Updates the score text for both players to the specified values.
	 */
	public void updateScores(int score1, int score2) {
		scoreLabel1.setText(""+score1);
		scoreLabel2.setText(""+score2);
		scoreLabel1.repaint();
		scoreLabel2.repaint();
	}
	
	/**
	 * Deletes the current grid and replaces it with a new one.
	 */
	public void clearBoard() {
		getContentPane().remove(gridPanel);
		gridPanel = new EntireGridPanel(levels, dimensions);
		gridPanel.addMouseListener(this);
		getContentPane().add(gridPanel, BorderLayout.CENTER);
		getContentPane().invalidate();
		getContentPane().validate();
	}
	
	/**
	 * Sets the view's representation of the valid move location to the specified location.
	 */
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
	 * Methods implementing the MouseListener interface
	 */
	
	/**
	 * Responds to clicks by determining the location of the click and informing the controller that that location was clicked.
	 */
	public void mousePressed(MouseEvent e) {
		//Fancy math to determine which square the user clicked on
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
		//Inform the delegate that the user clicked on the calculated location
		if (delegate != null) delegate.locationClicked(new Location(locArray));
	}
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
}
