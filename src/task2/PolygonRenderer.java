package task2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

/**
 * @author darkwizard
 */
public class PolygonRenderer extends JPanel {

	private List<Point> points = new ArrayList<>();
	private List<Point> modifiedPoints = new ArrayList<>();
	private Point center;

	private double turnAngle = 0d;
	private double xOffset = 0, yOffset = 0;
	private double scaleSize = 0d;

	public PolygonRenderer(int points) {
		this.generateRandomPoints(points);
	}

	private int getValue(int min, int max) {
		int result, diff = 0;
		if (min < 0) {
			diff = -min;
			min = 0;
			max += diff;
		}
		Random r = new Random();
		while ((result = r.nextInt(max)) < min);
		return result - diff;
	}

	private void generateRandomPoints(int points) {
		int cx = 240;
		int cy = 240;
		int radius = 150;
		center = new Point(cx, cy);
		System.out.println("O: " + center);

		double angle = 2 * Math.PI / points;
		int offset = 2 * radius / points;

		Point keyPoint = new Point(center.getX(), center.getY());
		for (int i = 0; i < points; ++i) {
			int newX = keyPoint.getX() + (int)(Math.cos(i * angle) * radius) + this.getValue(-offset, offset);
			int newY = keyPoint.getY() + (int)(Math.sin(i * angle) * radius) + this.getValue(-offset, offset);
			this.points.add(new Point(newX, newY));
			this.modifiedPoints.add(new Point(newX, newY));
		}
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Graphics2D g2d = (Graphics2D)graphics;

		int N = points.size();
		int[] x = new int[N], y = new int[N];
		int[] mx = new int[N], my = new int[N];

		for (int i = 0; i < points.size(); ++i) {
			x[i] = points.get(i).getX();
			y[i] = points.get(i).getY();
			mx[i] = modifiedPoints.get(i).getX();
			my[i] = modifiedPoints.get(i).getY();
		}
		g2d.setStroke(new BasicStroke(5));
		g2d.setColor(Color.RED);
		g2d.drawLine(center.getX(), center.getY(), center.getX(), center.getY());
		g2d.setColor(Color.BLACK);
		g2d.drawPolygon(x, y, N);
		g2d.setColor(Color.MAGENTA);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawPolygon(mx, my, N);
		g2d.dispose();
	}

	public void scale(double scaleSize) {
		// calculate distance to each point and multiply it by scale
		this.scaleSize = scaleSize - 1;
		this.transform();
	}

	private void transform() {
		for (int i = 0; i < this.points.size(); ++i) {
			Point p = this.modifiedPoints.get(i);
			Point src = this.points.get(i);

			// scaling
			p.setX(src.getX() + (int)(this.scaleSize * (src.getX() - center.getX())));
			p.setY(src.getY() + (int)(this.scaleSize * (src.getY() - center.getY())));

			// rotating
			double px = p.getX() - center.getX();
			double py = p.getY() - center.getY();

			double xnew = px * Math.cos(this.turnAngle) - py * Math.sin(this.turnAngle);
			double ynew = px * Math.sin(this.turnAngle) + py * Math.cos(this.turnAngle);

			p.setX((int) xnew + center.getX());
			p.setY((int) ynew + center.getY());

			// finally, shifting
			p.setX(p.getX() + (int)xOffset);
			p.setY(p.getY() + (int)yOffset);
		}

		this.repaint();
	}

	public void shift(int Xoffset, int Yoffset) {
		this.xOffset = Xoffset;
		this.yOffset = Yoffset;
		this.transform();
	}

	public void rotate(int degrees) {
		this.turnAngle = Math.PI * degrees / 180;
		this.transform();
	}
}
