package view;

import general.Location;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class GameFrame extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = -6399886456682347905L;
	private GameViewDelegate delegate;
	
	/**
	 * Constructs a new GameFrame object with the specified number of levels, dimensions, and player names.
	 */
	public GameFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(500,500));
        setVisible(true);
	}
	
	public void initialize(int levels, int dimensions, String[] playerNames) {
		JPanel gamePanel = new JPanel(); // {
		this.setContentPane(gamePanel);
		gamePanel.setLayout(new BorderLayout());
		JPanel controlPanel = new JPanel(); // {
			controlPanel.setLayout(new GridLayout(1,3));
			//controlPanel.setSize(gamePanel.getWidth(), 50);
			//controlPanel.setLocation(0, 0);
			JPanel leftPlayerPanel = new JPanel();
			leftPlayerPanel.add(new JLabel(playerNames[0]));
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
			rightPlayerPanel.add(new JLabel(playerNames[1]));
			controlPanel.add(rightPlayerPanel);
	//  }
		gamePanel.add(controlPanel, BorderLayout.NORTH);
		TilePanel gridPanel = new TilePanel(new Location(new int[] {})); // {
			//gridPanel.setSize(gamePanel.getWidth(), gamePanel.getHeight()-50);
			//gridPanel.setLocation(0, 50);
	//  }
		gamePanel.add(gridPanel, BorderLayout.CENTER);
		pack();
		gridPanel.initialize(levels, dimensions, this);
		gamePanel.setMinimumSize(null);
//  }
	//this.setContentPane(gamePanel);
	gamePanel.revalidate();
	}
	
	/**
	 * Changes the view's representation of the specified locations according to the specified values.
	 */
	public void updateLocations(Map<Location, Integer> locs) {
		
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
		e.getComponent().setBackground(Color.RED);
	}
	public void mouseReleased(MouseEvent e) { }
}
