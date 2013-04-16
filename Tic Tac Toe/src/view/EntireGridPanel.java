package view;

import general.Location;

import java.awt.*;
import javax.swing.*;

public class EntireGridPanel extends JPanel {
	private static final long serialVersionUID = 1789400913514003324L;
	public static final int X = 1;
	public static final int O = 2;
	public static final int EMPTY = 0;
	public static final int CAT = -1;
	
	private int levels;
	private int dimensions;
	private int[][][] values; //level, row, column
	
	public EntireGridPanel(int levels, int dimensions) { //level 0 is smallest
		this.levels=levels;
		this.dimensions=dimensions;
		values = new int[levels+1][][];
		for (int lvl=0; lvl<=levels; lvl++) {
			values[lvl] = new int[(int)Math.pow(dimensions, levels-lvl)][(int)Math.pow(dimensions, levels-lvl)];
		}
		//this.setValue(new Location(new int[] {4,3}), TilePanel.X);
	}
	
	public void setValue(Location loc, int value) {
		int level = levels-loc.numValues();
		int r=0, c=0;
		for (int lvl = level; lvl < levels; lvl++) {
			System.out.println(lvl);
			c += Math.pow(dimensions, lvl)*(loc.get(levels-lvl-1)%dimensions);
			r += Math.pow(dimensions, lvl)*(loc.get(levels-lvl-1)/dimensions);
		}
		values[level][r][c] = value;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int lvl = 0; lvl <= levels; lvl++) {
			int thisLevelDimensions = (int)Math.pow(dimensions, levels-lvl);
			double realWidth = this.getWidth()/(double)thisLevelDimensions;
			double realHeight = this.getHeight()/(double)thisLevelDimensions;
			double leftoverWidth = 0, leftoverHeight=0;
			for (int r=0; r<thisLevelDimensions; r++) for (int c=0; c<thisLevelDimensions; c++) {
				int pixelWidth = (int)realWidth;
				leftoverWidth += realWidth-pixelWidth;
				if (leftoverWidth > 1) {
					pixelWidth++;
					leftoverWidth--;
				}
				int pixelHeight = (int)realHeight;
				leftoverHeight += realHeight-pixelHeight;
				if (leftoverHeight > 1) {
					pixelHeight++;
					leftoverHeight--;
				}
				drawValue(g, (int)(realWidth*c), (int)(realHeight*r), pixelWidth, pixelHeight, values[lvl][r][c]);
			}
		}
		
	}
	
	private void drawValue(Graphics g, int x, int y, int width, int height, int value) {
		if (value == X) {
			g.setColor(new Color(200,0,0,64));
		} else {
			g.setColor(new Color(0, 200, 0, 64));
		}
		g.fillOval(x, y, width, height);
	}
}
