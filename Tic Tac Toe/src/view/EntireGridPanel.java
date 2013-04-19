package view;

import general.Location;

import java.awt.*;
import javax.swing.*;

public class EntireGridPanel extends JPanel {
	private static final long serialVersionUID = 1789400913514003324L;
	public static final int X = 1;
	public static final int O = 2;
	public static final int EMPTY = 0;
	public static final int CAT = 3;
	
	private int[][][] values; //level, row, column
	private int validLocationRow;
	private int validLocationCol;
	
	public EntireGridPanel(int levels, int dimensions) { //level 0 is smallest
		values = new int[levels+1][][];
		for (int lvl=0; lvl<=levels; lvl++) {
			values[lvl] = new int[(int)Math.pow(dimensions, levels-lvl)][(int)Math.pow(dimensions, levels-lvl)];
		}
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
	}
	
	public void setValue(Location loc, int value) {
		int[] LRC = loc.toLRC(GameFrame.levels, GameFrame.dimensions);
		values[LRC[0]][LRC[1]][LRC[2]] = value;
		System.out.println("valid location: " + LRC[0] + ", " + LRC[1] + ", " + LRC[2]);
		System.out.println(value);
		repaint();
	}
	
	public void setValidLocation(Location loc) {
		int[] LRC = loc.toLRC(GameFrame.levels, GameFrame.dimensions);
		validLocationRow = LRC[1];
		validLocationCol = LRC[2];
	}
	
	public void paintComponent(Graphics gg) {
		super.paintComponent(gg);
		Graphics2D g = (Graphics2D)gg;
		
		//draw the values in the grid
		for (int lvl = 0; lvl <= GameFrame.levels; lvl++) {
			int thisLevelDimensions = (int)Math.pow(GameFrame.dimensions, GameFrame.levels-lvl);
			double realWidth = this.getWidth()/(double)thisLevelDimensions;
			double realHeight = this.getHeight()/(double)thisLevelDimensions;
			double leftoverWidth = 0, leftoverHeight=0;
			for (int r=0; r<thisLevelDimensions; r++) for (int c=0; c<thisLevelDimensions; c++) {
				int pixelWidth = (int)realWidth;
				leftoverWidth += realWidth-pixelWidth;
				if (leftoverWidth > 1) {
					//pixelWidth++;
					//leftoverWidth--;
				}
				int pixelHeight = (int)realHeight;
				leftoverHeight += realHeight-pixelHeight;
				if (leftoverHeight > 1) {
					//pixelHeight++;
					//leftoverHeight--;
				}
				drawValue(g, (int)(realWidth*c), (int)(realHeight*r), pixelWidth, pixelHeight, values[lvl][r][c]);
				if (lvl == 1) {
					if (validLocationCol != c || validLocationRow != r) {
						g.setColor(new Color(0,0,0,30));
						g.fillRect((int)(realWidth*c), (int)(realHeight*r), pixelWidth, pixelHeight);
					}
				}
				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(3*lvl+2));
				g.drawRect((int)(realWidth*c)+1, (int)(realHeight*r)+1, pixelWidth-1, pixelHeight-1);
			}
		}
		
	}
	
	private void drawValue(Graphics g, int x, int y, int width, int height, int value) {
		if (value == X) {
			g.setColor(new Color(200,0,0,64));
		} else if (value == O) {
			g.setColor(new Color(0, 200, 0, 64));
		} else if (value == CAT) {
			g.setColor(new Color(0, 0, 200, 64));
		} else {
			return;
		}
		g.fillOval(x, y, width, height);
	}
}
