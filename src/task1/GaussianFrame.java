package task1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * @author darkwizard
 */
public class GaussianFrame {
	private final int WIDTH = 960;
	private final int HEIGHT = 640;

	private GaussDisplayPanel gdp;

	public GaussianFrame() {
		JFrame frame = new JFrame("Gaussian filter");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(null);

		frame.add(this.createControlPanel());

		try {
			BufferedImage img = ImageIO.read(new File("contrast.jpg"));
			gdp = new GaussDisplayPanel(img, WIDTH / 2, (HEIGHT - 40) / 2);
			frame.add(gdp);
			gdp.setBounds(0, 40, WIDTH, HEIGHT);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public JPanel createControlPanel() {
		JPanel control = new JPanel();
		control.setLayout(new BoxLayout(control, BoxLayout.Y_AXIS));
		control.setSize(WIDTH, 40);

		// adding sigma controller
		JLabel sigmaLabel = new JLabel("Sigma");
		JSlider sigmaSlider = new JSlider(1, 10, 1);
		sigmaSlider.addChangeListener(l -> gdp.setSigma(sigmaSlider.getValue()));
		control.add(sigmaLabel);
		control.add(sigmaSlider);

		return control;
	}
}
