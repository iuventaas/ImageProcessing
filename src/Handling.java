import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Handling {


    public static BufferedImage fit2(BufferedImage orig) {
        int[] newSize = new int[2];
        double w = (double) orig.getWidth() / 800;
        double h = (double) orig.getHeight() / 800;
        if (orig.getWidth() <= 800 && orig.getHeight() <= 800) {
            return orig;
        }
        if (w > h) {
            newSize[0] = 800;
            newSize[1] = orig.getHeight() * 800 / orig.getWidth();
        } else {
            newSize[0] = orig.getWidth() * 800 / orig.getHeight();
            newSize[1] = 800;
        }

        BufferedImage resizedImage = new BufferedImage(newSize[0], newSize[1], orig.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(orig, 0, 0, newSize[0], newSize[1], null);
        g.dispose();
        return resizedImage;

    }


    public static BufferedImage fit(BufferedImage orig) {
        int[] newSize = new int[2];
        double w = (double) orig.getWidth() / 800;
        double h = (double) orig.getHeight() / 800;

        if (w > h) {
            newSize[0] = 800;
            newSize[1] = orig.getHeight() * 800 / orig.getWidth();
        } else {
            newSize[0] = orig.getWidth() * 800 / orig.getHeight();
            newSize[1] = 800;
        }

        BufferedImage resizedImage = new BufferedImage(newSize[0], newSize[1], orig.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(orig, 0, 0, newSize[0], newSize[1], null);
        g.dispose();
        return resizedImage;

    }

    public static BufferedImage usred(BufferedImage orig) {

        BufferedImage image = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < orig.getWidth(); i++) {
            for (int j = 0; j < orig.getHeight(); j++) {
                Color c = new Color(orig.getRGB(i, j));
                int grey = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                image.setRGB(i, j, new Color(grey, grey, grey).getRGB());
            }
        }

        return image;
    }

    public static BufferedImage correction(BufferedImage orig) {

        BufferedImage image = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < orig.getWidth(); i++) {
            for (int j = 0; j < orig.getHeight(); j++) {
                Color c = new Color(orig.getRGB(i, j));
                int grey = ((int) (c.getRed() * 0.3) + (int) (c.getGreen() * 0.59)
                        + (int) (c.getBlue() * 0.11)) / 3;
                image.setRGB(i, j, new Color(grey, grey, grey).getRGB());
            }
        }

        return image;
    }

    public static BufferedImage desaturation(BufferedImage orig) {

        BufferedImage image = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < orig.getWidth(); i++) {
            for (int j = 0; j < orig.getHeight(); j++) {
                Color c = new Color(orig.getRGB(i, j));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                int grey = (Math.min(red, Math.min(green, blue)) + Math.max(red, Math.max(green, blue))) / 2;
                image.setRGB(i, j, new Color(grey, grey, grey).getRGB());
            }
        }

        return image;
    }

    public static BufferedImage minGradation(BufferedImage orig) {

        BufferedImage image = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < orig.getWidth(); i++) {
            for (int j = 0; j < orig.getHeight(); j++) {
                Color c = new Color(orig.getRGB(i, j));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                int grey = Math.min(red, Math.min(green, blue));
                image.setRGB(i, j, new Color(grey, grey, grey).getRGB());
            }
        }

        return image;
    }

    public static BufferedImage maxGradation(BufferedImage orig) {

        BufferedImage image = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < orig.getWidth(); i++) {
            for (int j = 0; j < orig.getHeight(); j++) {
                Color c = new Color(orig.getRGB(i, j));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                int grey = Math.max(red, Math.min(green, blue));
                image.setRGB(i, j, new Color(grey, grey, grey).getRGB());
            }
        }

        return image;
    }

    public static BufferedImage greenChannel(BufferedImage orig) {

        BufferedImage image = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < orig.getWidth(); i++) {
            for (int j = 0; j < orig.getHeight(); j++) {
                Color c = new Color(orig.getRGB(i, j));
                int grey = c.getGreen();
                image.setRGB(i, j, new Color(grey, grey, grey).getRGB());
            }
        }

        return image;
    }

    public static BufferedImage redChannel(BufferedImage orig) {

        BufferedImage image = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < orig.getWidth(); i++) {
            for (int j = 0; j < orig.getHeight(); j++) {
                Color c = new Color(orig.getRGB(i, j));
                int grey = c.getRed();
                image.setRGB(i, j, new Color(grey, grey, grey).getRGB());
            }
        }

        return image;
    }

    public static BufferedImage blueChannel(BufferedImage orig) {

        BufferedImage image = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < orig.getWidth(); i++) {
            for (int j = 0; j < orig.getHeight(); j++) {
                Color c = new Color(orig.getRGB(i, j));
                int grey = c.getBlue();
                image.setRGB(i, j, new Color(grey, grey, grey).getRGB());
            }
        }

        return image;
    }

    public static int[] buildHist(BufferedImage orig) {
        int[] hist = new int[256];

        for (int i = 0; i < orig.getWidth(); i++) {
            for (int j = 0; j < orig.getHeight(); j++) {
                Color c = new Color(orig.getRGB(i, j));
                //   int grey = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                int grey = c.getRed();
                hist[grey]++;
            }
        }

        return hist;
    }


    public static BufferedImage equalization(BufferedImage orig) {
        BufferedImage image = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        int[] hist = buildHist(orig);
        int[] histEq = new int[256];
        int pix = orig.getWidth() * orig.getHeight();
        int minHist = pix;
        int max = 0;
        int sum = 0;

        for (int i = 0; i < hist.length; i++) {
            if ((hist[i] < minHist) && (hist[i] != 0))
                minHist = hist[i];
            if (hist[i] > max)
                max = hist[i];
        }


        for (int i = 0; i < hist.length; i++) {
            sum += hist[i];
            double tmp = ((double) (sum - minHist) * 255 / (double) (pix - 1));
            int grey = (int) Math.round(tmp);
            histEq[i] = grey;

        }

        for (int i = 0; i < orig.getWidth(); i++) {
            for (int j = 0; j < orig.getHeight(); j++) {
                Color c = new Color(orig.getRGB(i, j));
                int grey = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                int nGrey = histEq[grey];
                image.setRGB(i, j, new Color(nGrey, nGrey, nGrey).getRGB());

            }
        }

        return image;

    }

    private static int[] linearization(BufferedImage orig) {
        int size = orig.getHeight() * orig.getWidth();
        int[] origMas = new int[size];
        int k = 0;
        for (int i = 0; i < orig.getWidth(); i++) {
            for (int j = 0; j < orig.getHeight(); j++) {
                Color c = new Color(orig.getRGB(i, j));
                int grey = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                origMas[k] = grey;
                k++;
            }
        }
        return origMas;
    }


    public static BufferedImage binarizationOcu(BufferedImage orig) {
        BufferedImage image = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        int[] massive = linearization(orig);
        int size = massive.length;


        int[] histNew = new int[size];

        for (int i = 0; i < size; i++)
            histNew[massive[i]]++;

        int m = 0; // m - сумма высот всех бинов, домноженных на положение их середины
        int n = 0; // n - сумма высот всех бинов
        for (int t = 0; t < size; t++) {
            m += t * histNew[t];
            n += histNew[t];
        }

        float maxSigma = -1; // Максимальное значение межклассовой дисперсии
        int threshold = 0;

        int alpha1 = 0;
        int beta1 = 0;

        for (int t = 0; t < size; t++) {
            alpha1 += t * histNew[t];
            beta1 += histNew[t];
            float w1 = (float) beta1 / n;
            float a = (float) alpha1 / beta1 - (float) (m - alpha1) / (n - beta1);
            float sigma = w1 * (1 - w1) * a * a;
            if (sigma > maxSigma) {
                maxSigma = sigma;
                threshold = t;
            }
        }

        for (int i = 0; i < size; i++) {
            if (massive[i] > threshold)
                massive[i] = 255;
            else massive[i] = 0;
        }

        int k = 0;
        for (int i = 0; i < orig.getWidth(); i++) {
            for (int j = 0; j < orig.getHeight(); j++) {
                int nGrey = massive[k];
                image.setRGB(i, j, new Color(nGrey, nGrey, nGrey).getRGB());
                k++;
            }
        }


        return image;
    }


    public static double[][] toMatrix(BufferedImage orig) {
        double[][] matrix = new double[orig.getWidth()][orig.getHeight()];
        for (int i = 0; i < orig.getWidth(); i++) {
            for (int j = 0; j < orig.getHeight(); j++) {
                Color c = new Color(orig.getRGB(i, j));
                int grey = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                matrix[i][j] = grey;
            }
        }
        return matrix;
    }

    public static BufferedImage binarizationNiblack(BufferedImage orig, int w, double k) {
        BufferedImage image = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        int width = orig.getWidth();
        int height = orig.getHeight();

        double[][] matrix = toMatrix(orig);
        int[][] tmp = new int[width][height];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < w; j++) {
                double mean = 0;
                double dev = 0;
                for (int m = -i; m < w; m++) {
                    for (int n = -j; n < w; n++) {
                        mean += matrix[i + m][j + n];
                    }
                }
                int win = 2 * (i) * (j);
                mean /= win;
                for (int m = -i; m < w; m++) {
                    for (int n = -j; n < w; n++) {
                        dev += Math.pow(matrix[i + m][j + n] - mean, 2);
                    }
                }
                dev = Math.sqrt(dev / win);

                int threshold = (int) Math.round(mean + k * dev);
                tmp[i][j] = threshold;
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double mean = 0;
                double dev = 0;
                int wLowI = Math.max(-i, -w);
                int wHighI = Math.min(width - i, w);
                int wLowJ = Math.max(-j, -w);
                int wHighJ = Math.min(height - j, w);
                for (int m = wLowI; m < wHighI; m++) {
                    for (int n = wLowJ; n < wHighJ; n++) {
                        mean += matrix[i + m][j + n];
                    }
                }
                int win = (wHighI - wLowI) * (wHighJ - wLowJ);
                mean /= win;
                for (int m = wLowI; m < wHighI; m++) {
                    for (int n = wLowJ; n < wHighJ; n++) {
                        dev += Math.pow(matrix[i + m][j + n] - mean, 2);
                    }
                }
                dev = Math.sqrt(dev / (win));

                int threshold = (int) Math.round(mean + k * dev);
                tmp[i][j] = threshold;
            }
        }


        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int nGrey = 0;
                if (matrix[i][j] >= tmp[i][j])
                    nGrey = 255;
                image.setRGB(i, j, new Color(nGrey, nGrey, nGrey).getRGB());
            }
        }


        return image;
    }



    public static void dil(BufferedImage orig, double[][] structureElement) {
        orig = fit(orig);
        BufferedImage image = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        int width = orig.getWidth();
        int height = orig.getHeight();
        int x = structureElement.length;
        int y = structureElement[0].length;
        int nw = x / 2 + 1;
        int nh = y / 2 + 1;
        double[][] obj = toMatrix(orig);
        Color white = new Color(255,255,255);
        Color black = new Color(0,0,0);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rn = 0;
                int koef = 0;
                for (int k = 0; k < x; k++) {
                    for (int p = 0; p < y; p++) {
                        if (((i + k - nw + 1) >= 0) & ((i + k - nw + 1) <= (width - 1)) & ((j + p - nh + 1) >= 0)
                                & ((j + p - nh + 1) <= (height - 1)) & (structureElement[k][p] != 0))
                            rn += obj[i + k - nw + 1][j + p - nh + 1];
                        koef += 1;
                    }
                }
                if (rn == koef * 255)
                    image.setRGB(i, j, white.getRGB());
                else
                    image.setRGB(i, j, black.getRGB());
            }
        }
        Frame nFrame1 = new Frame();
        JLabel label1 = new JLabel(new ImageIcon(image));
        nFrame1.setSize(750, 900);
        nFrame1.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                nFrame1.setVisible(false);
            }
        });
        nFrame1.add(label1);
        nFrame1.setVisible(true);

    }

}
