package util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class ToolbarImages {

  public static final ImageView NEGATIVE = getImage("negative.png");
  public static final ImageView THRESHOLD = getImage("threshold.png");
  public static final ImageView THRESHOLD_GLOBAL = getImage("threshold_global.png");
  public static final ImageView THRESHOLD_OTSU = getImage("threshold_otsu.png");
  public static final ImageView CONTRAST = getImage("contrast.png");
  public static final ImageView DYNAMIC_RANGE = getImage("dynamic_range.png");
  public static final ImageView EQUALIZE = getImage("equalize.png");
  public static final ImageView ADD = getImage("add.png");
  public static final ImageView SUBSTRACT = getImage("substract.png");
  public static final ImageView MULTIPLY = getImage("multiply.png");
  public static final ImageView SCALAR = getImage("scalar.png");
  public static final ImageView POWER = getImage("pow.png");
  public static final ImageView NOISE_GAUSS = getImage("noise_gauss.png");
  public static final ImageView NOISE_EXP = getImage("noise_exp.png");
  public static final ImageView NOISE_RAYLEIGH = getImage("noise_rayleigh.png");
  public static final ImageView NOISE_SALT_AND_PEPPER = getImage("noise_salt_and_pepper.png");
  public static final ImageView FILTER_AVERAGE = getImage("filter_average.png");
  public static final ImageView FILTER_MEDIAN = getImage("filter_median.png");
  public static final ImageView FILTER_GAUSS = getImage("filter_gauss.png");
  public static final ImageView FILTER_HIPASS = getImage("filter_hipass.png");
  public static final ImageView FILTER_SOBEL = getImage("filter_sobel.png");
  public static final ImageView FILTER_PREWITT = getImage("filter_prewitt.png");
  public static final ImageView FILTER_KIRSH = getImage("filter_kirsh.png");
  public static final ImageView FILTER_A = getImage("filter_a.png");
  public static final ImageView FILTER_LAPLACE = getImage("filter_laplace.png");
  public static final ImageView FILTER_LOG = getImage("filter_log.png");
  public static final ImageView FILTER_ISOTROPIC = getImage("filter_isotropic.png");
  public static final ImageView FILTER_ANISOTROPIC = getImage("filter_anisotropic.png");

  private static ImageView getImage(String name) {
    try {
      ImageView imageView = new ImageView(new Image(new FileInputStream("resources/" + name)));
      imageView.setFitWidth(30);
      imageView.setFitHeight(30);
      return imageView;
    } catch (FileNotFoundException e) {
      // TODO: Auto-generated code.
      e.printStackTrace();
      return null;
    }
  }
}
