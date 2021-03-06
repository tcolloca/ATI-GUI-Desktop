package util.pf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import model.ImageManager;
import util.FileHelper;
import util.SiftMatcher;
import view.ImagePanel;

import com.goodengineer.atibackend.transformation.DecreaseResolutionTransformation;

public class SarExperiments {

	private static final int EXPERIMENTS = 400;
	private final ImageManager imageManager;

	public SarExperiments(ImageManager imageManager) {
		super();
		this.imageManager = imageManager;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		new SarExperiments(null).runAlphaAndGamma();
	}

	private static class ImagePanelMock extends ImagePanel {
		ImagePanelMock(ImageManager imageManager) {
			super(imageManager);
		}

		public void showOriginal() {
		}
	}
	
	public void runAlphaAndGamma() throws IOException, InterruptedException, ExecutionException {
		double alpha1 = -1.5;
		double gamma1 = 1;

		double gamma2 = 1;
		double alpha2 = -3;

		double[] alphas = new double[] { -2, -5, -8, -11, -14, -17, -20 };
		double[] gammas = new double[] { 0.1, 1, 10, 100, 1000 };
		int[] Ls = new int[] { 1, 3, 5, 8 };

		String shapesPath = "E:\\BACKUPData\\Tom\\projects\\ATI-GUI-Desktop\\shapes_difficult.png";
		File shapesFile = new File(shapesPath);

		ExecutorService executor = Executors.newFixedThreadPool(5);
		CompletionService<String> completionService = new ExecutorCompletionService<>(executor);
		System.out.println("exec created");
		
		for (double alpha : alphas) {
			for (int L : Ls) {
				for (int i = 0; i < EXPERIMENTS; i++) {
					completionService.submit(new MyRunnable(shapesFile, alpha1, gamma1, alpha, gamma2, L, i));
				}
			}
		}

		for (double gamma : gammas) {
			for (int L : Ls) {
				for (int i = 0; i < EXPERIMENTS; i++) {
					completionService.submit(new MyRunnable(shapesFile, alpha1, gamma1, alpha2, gamma, L, i));
				}
			}
		}
		System.out.println("taking!");
		int taken = 0;
		int size = (alphas.length + gammas.length) * Ls.length * EXPERIMENTS;
		while (taken++ < size) {
			System.out.println("Finished: " + completionService.take().get());
		}
		
		executor.shutdown();
	}

	public void runResolution() throws IOException {

		String munichPath = "E:\\BACKUP Data\\Tom\\projects\\ATI-GUI-Desktop\\munich.png";
		File munichFile = new File(munichPath);
		String munichSlicePath = "E:\\BACKUP Data\\Tom\\projects\\ATI-GUI-Desktop\\munich_slice.png";
		File munichSliceFile = new File(munichSlicePath);
		File csvFile = new File("experiments_res.csv");
		csvFile.createNewFile();
		PrintWriter writer = new PrintWriter(csvFile);

		for (int resolution = 2; resolution <= 16;  resolution++) {
			writer.write(String.format("res=%d;\n", resolution));
			imageManager.setImageFile(munichSliceFile);
			imageManager.applyTransformation(new DecreaseResolutionTransformation(resolution));
			BufferedImage newImage = imageManager.getModifiableBufferedImage();
			String newImagePath = String.format("resolutions/res_%d.png", resolution);
			FileHelper.saveImage(newImage, newImagePath);
			int[] matches = SiftMatcher.match(munichFile, new File(newImagePath));

			writer.write(String.format("%d;%d;\n", matches[0], matches[1]));
		}
		writer.close();
	}
	
	public class MyRunnable implements Callable<String> {
		private final File shapesFile;
		private final double a1;
		private final double g1;
		private final double a2;
		private final double g2;
		private final int L;
		private final int i;

		public MyRunnable(File shapesFile, double a1, double g1, double a2, double g2, int l, int i) {
			super();
			this.shapesFile = shapesFile;
			this.a1 = a1;
			this.g1 = g1;
			this.a2 = a2;
			this.g2 = g2;
			this.L = l;
			this.i = i;
		}

		@Override
		public String call() {
			ImageManager imageManager = new ImageManager();
			new ImagePanelMock(imageManager);
			imageManager.setImageFile(shapesFile);
			imageManager.createSyntheticImageFromOriginal(L, a1, g1, a2, g2);
			System.out.println("created");
			BufferedImage noiseImage = imageManager.getModifiableBufferedImage();
			String noiseImagePath = String.format("shapes/shapes_a1=%.1f_g1=%.1f_a2=%.1f_g2=%.1f_L=%d_i=%d.png", a1, g1, a2, g2, L, i);
			System.out.println("saving " + noiseImagePath);
			FileHelper.saveImage(noiseImage, noiseImagePath);
			return noiseImagePath;
		}
		
		
	}
}
