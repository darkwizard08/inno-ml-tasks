package task1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * @author darkwizard
 */
public class GaussDisplayPanel extends JPanel {
	private BufferedImage imageSrc = null;
	private int sigma = 1;

	public GaussDisplayPanel(BufferedImage image, int quaterWidth, int quaterHeight) {
		imageSrc = new BufferedImage(quaterWidth, quaterHeight, BufferedImage.TYPE_INT_RGB);
		Graphics g = imageSrc.createGraphics();
		g.drawImage(image, 0, 0, quaterWidth, quaterHeight, null);
		g.dispose();
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		int width = this.getWidth(), height = this.getHeight();
		if (imageSrc != null) {
			graphics.drawImage(this.imageSrc, 0, 0, width / 2, height / 2, Color.BLACK, null);
			BufferedImage firstGauss = GaussianFilter.applyFilter(this.imageSrc, sigma);
			graphics.drawImage(firstGauss, width / 2 + 1, 0, width / 2, height / 2, null);

			BufferedImage gaussianDerX = GaussianFilter.applyXDerivativeFilter(this.imageSrc, sigma);
			graphics.drawImage(gaussianDerX, 0, height / 2, width / 2, height / 2, null);

			BufferedImage gaussianDerY = GaussianFilter.applyYDerivativeFilter(this.imageSrc, sigma);
			graphics.drawImage(gaussianDerY, width / 2 + 1, height / 2, width / 2, height / 2, null);
		}
	}

	public void setSigma(int sigma) {
		this.sigma = sigma;
		this.repaint();
	}
}
