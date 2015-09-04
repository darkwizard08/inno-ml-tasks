package task1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

/**
 * @author darkwizard
 */
public class GaussianFilter {
	public static BufferedImage applyFilter(BufferedImage image, int sigma) {
		return new GaussianFilter().getProcessed(image, sigma);
	}

	public static BufferedImage applyXDerivativeFilter(BufferedImage image, int sigma) {
		return new GaussianFilter().getDerGx(image, sigma);
	}

	public static BufferedImage applyYDerivativeFilter(BufferedImage image, int sigma) {
		return new GaussianFilter().getDerGy(image, sigma);
	}

	private BufferedImage getDerGx(BufferedImage image, int sigma) {
		double[][] kernel = this.derGx(sigma);
		Color[][] data = this.convolve(this.convertImageToArray(image), kernel);
		return this.getImageFromArray(data);
	}

	private BufferedImage getDerGy(BufferedImage image, int sigma) {
		double[][] kernel = this.derGy(sigma);
		Color[][] data = this.convolve(this.convertImageToArray(image), kernel);
		return this.getImageFromArray(data);
	}

	private BufferedImage getProcessed(BufferedImage image, int sigma) {
		List<double[][]> kernels = this.Gxy(sigma);
		Color[][] data = this.convolve(this.convertImageToArray(image), kernels.get(0));
		data = this.convolve(data, kernels.get(1));
		return this.getImageFromArray(data);
	}

	private BufferedImage getImageFromArray(Color[][] data) {
		int width = data.length, height = data[0].length;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < width; ++i)
			for (int j = 0; j < height; ++j) {
				img.setRGB(i, j, data[i][j].getRGB());
			}

		return img;
	}

	private void print2dArray(double[][] array) {
		for (int i = 0; i < array.length; ++i) {
			for (int j = 0; j < array[i].length; ++j)
				System.out.print(String.format("%.3f ", array[i][j]));
			System.out.println();
		}
	}

	private Color[][] convertImageToArray(BufferedImage image) {
		Color[][] result = new Color[image.getWidth()][image.getHeight()];
		for (int i = 0; i < image.getWidth(); ++i)
			for (int j = 0; j < image.getHeight(); ++j) {
				Color c = new Color(image.getRGB(i, j));
				result[i][j] = c;
			}

		return result;
	}

	private Color[][] convolve(Color[][] image, double[][] kernel) {
		int height = image[0].length - kernel[0].length;
		int width = image.length - kernel.length;
		Color[][] result = new Color[width][height];

		for (int i = 0; i < width; ++i)
			for (int j = 0; j < height; ++j) {
				double r = 0, g = 0, b = 0;
				for (int k = 0; k < kernel.length; ++k)
					for (int l = 0; l < kernel[k].length; ++l) {
						Color c = image[i + k][j + l];
						r += c.getRed() * kernel[k][l];
						g += c.getGreen() * kernel[k][l];
						b += c.getBlue() * kernel[k][l];
					}
				result[i][j] = this.verifyColor(r, g, b);
			}

		return result;
	}

	private Color verifyColor(double r, double g, double b) {
		if (r > 255) r = 255;
		if (g > 255) g = 255;
		if (b > 255) b = 255;
		if (r < 0) r = 0;
		if (g < 0) g = 0;
		if (b < 0) b = 0;

		return new Color((int) r, (int) g, (int) b);
	}

	private double[][] G(int sigma) {
		double sqsigma = sigma * sigma;
		int s3 = 3 * sigma;
		double[][] result = new double[2 * s3 + 1][2 * s3 + 1];

		for (int i = -s3; i <= s3; ++i)
			for (int j = -s3; j <= s3; ++j)
				result[i + s3][j + s3] = 1.0 / (2 * sqsigma * Math.PI) * Math.pow(Math.E, -(i * i + j * j) / (2 * sqsigma));

		return result;
	}

	private double[][] derGx(int sigma) {
		double sqsigma = sigma * sigma;
		int s3 = 3 * sigma;
		double[][] result = new double[2 * s3 + 1][2 * s3 + 1];

		for (int i = -s3; i <= s3; ++i)
			for (int j = -s3; j <= s3; ++j)
				result[i + s3][j + s3] = -i / (2 * Math.PI * sqsigma * sqsigma) * Math.exp(-(i * i + j * j) / (2 * sqsigma));

		double[][] fValue = new double[2 * s3 + 1][1];
		for (int i = 0; i < 2 * s3 + 1; ++i) {
			double sum = 0;
			for (int j = 0; j < 2 * s3 + 1; j++)
				sum += result[i][j];
			fValue[i][0] = sum;
		}
		return fValue;
	}

	private double[][] derGy(int sigma) {
		double sqsigma = sigma * sigma;
		int s3 = 3 * sigma;
		double[][] result = new double[2 * s3 + 1][2 * s3 + 1];

		for (int i = -s3; i <= s3; ++i)
			for (int j = -s3; j <= s3; ++j)
				result[i + s3][j + s3] = -j / (2 * Math.PI * sqsigma * sqsigma) * Math.exp(-(i * i + j * j) / (2 * sqsigma));

		return result;
	}

	private List<double[][]> Gxy(int sigma) {
		double[][] gaussian = G(sigma);
		double[][] Gx = new double[1][gaussian.length];
		double[][] Gy = new double[gaussian.length][1];
		for (int i = 0; i < gaussian.length; ++i) {
			double sum = 0d;
			for (int j = 0; j < gaussian.length; ++j)
				sum += gaussian[i][j];
			Gx[0][i] = sum;
			Gy[i][0] = sum;
		}

		return Arrays.asList(Gx, Gy);
	}

}
