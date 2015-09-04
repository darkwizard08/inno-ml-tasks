package task2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * @author darkwizard
 */
public class PolygonBuilder {
	private PolygonRenderer renderer;

	public PolygonBuilder() {
		JFrame frame = new JFrame("Builder");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(720, 480);
		frame.setVisible(true);
		frame.setLayout(null);

		JPanel controlPanel = this.createControlPanel();
		frame.add(controlPanel);
		controlPanel.setBounds(0, 0, 240, 480);

		renderer = new PolygonRenderer(8);
		frame.add(renderer);
		renderer.setBounds(240, 0, 720 - 240, 480);
	}

	public JPanel createControlPanel() {
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

		// creating scale Slider
		JLabel scaleLabel = new JLabel("Scale");
		JSlider scaleSlider = new JSlider(100, 200, 100);
		scaleSlider.addChangeListener(listener -> renderer.scale(scaleSlider.getValue() / 100.0));
		controlPanel.add(scaleLabel);
		controlPanel.add(Box.createVerticalBox());
		controlPanel.add(scaleSlider);
		controlPanel.add(Box.createVerticalGlue());

		// creating offsets
		JLabel offsetLabel = new JLabel("Offset (x, y)");
		JSlider xOffsetSlider = new JSlider(0, 100, 0);
		JSlider yOffsetSlider = new JSlider(0, 100, 0);
		xOffsetSlider.addChangeListener(l -> renderer.shift(xOffsetSlider.getValue(), yOffsetSlider.getValue()));
		yOffsetSlider.addChangeListener(l -> renderer.shift(xOffsetSlider.getValue(), yOffsetSlider.getValue()));
		controlPanel.add(offsetLabel);
		controlPanel.add(xOffsetSlider);
		controlPanel.add(yOffsetSlider);
		controlPanel.add(Box.createVerticalGlue());

		// creating rotation
		JLabel rotateLabel = new JLabel("Rotation");
		JSlider rotateSlider = new JSlider(0, 180, 0);
		rotateSlider.addChangeListener(l -> renderer.rotate(rotateSlider.getValue()));
		controlPanel.add(rotateLabel);
		controlPanel.add(rotateSlider);
		controlPanel.add(Box.createVerticalGlue());

		controlPanel.setSize(240, 480);

		return controlPanel;
	}
}
