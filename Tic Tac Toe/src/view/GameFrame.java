package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GameFrame extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = -6399886456682347905L;
	private GridPanel gridPanel;
	private GameViewDelegate delegate;
	
	public GameFrame(int levels, int dimensions, String[] playerNames) {
		JPanel gamePanel = new JPanel(); // {
			gamePanel.setLayout(new GridLayout(2,1));
			JPanel controlPanel = new JPanel(); // {
				controlPanel.setLayout(new GridLayout(1,3));
				JPanel leftPlayerPanel = new JPanel();
				leftPlayerPanel.add(new JLabel(playerNames[0]));
				controlPanel.add(leftPlayerPanel);
				JPanel controlButtonsPanel = new JPanel(); // {
					controlButtonsPanel.setLayout(new GridLayout(2,1));
					JButton resetButton = new JButton("Reset Scores");
					JButton restartButton = new JButton("Restart Game");
					resetButton.addActionListener(this);
					restartButton.addActionListener(this);
					controlButtonsPanel.add(resetButton);
					controlButtonsPanel.add(restartButton);
			//  }
				controlPanel.add(controlButtonsPanel);
		//  }
			gamePanel.add(controlPanel);
			gridPanel = new GridPanel(); // {
				
		//  }
			gamePanel.add(gridPanel);
	//  }
		this.setContentPane(gamePanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(500,500);
	}

	public GameViewDelegate getDelegate() { return delegate; }
	public void setDelegate(GameViewDelegate delegate) { this.delegate = delegate; }

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton && ((JButton)(e.getSource())).getText() == "Reset Scores") {
			if (delegate != null) delegate.resetClicked();
		} else if (e.getSource() instanceof JButton && ((JButton)(e.getSource())).getText() == "Restart Game") {
			if (delegate != null) delegate.restartClicked();
		}
	}

	public void mouseClicked(MouseEvent e) { }

	public void mouseEntered(MouseEvent e) { }

	public void mouseExited(MouseEvent e) { }

	public void mousePressed(MouseEvent e) { }

	public void mouseReleased(MouseEvent e) { }
}
