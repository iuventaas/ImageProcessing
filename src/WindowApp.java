import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class WindowApp extends JPanel {

    JFrame frame = new JFrame("TestUpdate");
    BufferedImage img;
    private JButton uploadImage;
    Font font = new Font("Verdana", Font.PLAIN, 11);


    private WindowApp() {

        uploadImage = new JButton("Upload image");
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(uploadImage);

        frame.add(buttonsPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 900));
        frame.getContentPane().add(leftPanelGen());
        frame.setLocationRelativeTo(null);
        JMenuBar menuBar = createBar(img);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    private JMenuBar createBar(BufferedImage image) {
        JMenuBar menuBar = new JMenuBar();


        JMenu fileMenuBW = new JMenu("B&W");
        fileMenuBW.setFont(font);

        JMenuItem usredItem = createJMenuItem("Усреднение", image);
        fileMenuBW.add(usredItem);


        JMenuItem correctionItem = createJMenuItem("Коррекция", image);
        fileMenuBW.add(correctionItem);


        JMenuItem desaturationItem = createJMenuItem("Десатурация", image);
        fileMenuBW.add(desaturationItem);


        JMenu gradationMenu = new JMenu("Градация");
        gradationMenu.setFont(font);
        fileMenuBW.add(gradationMenu);

        JMenuItem minItem = createJMenuItem("Градация по минимуму", image);
        gradationMenu.add(minItem);


        JMenuItem maxItem = createJMenuItem("Градация по максимуму", image);
        gradationMenu.add(maxItem);


        JMenu channelMenu = new JMenu("Поканально");
        channelMenu.setFont(font);

        JMenuItem blueItem = createJMenuItem("Синий канал", image);
        channelMenu.add(blueItem);

        JMenuItem greenItem = createJMenuItem("Зеленый канал", image);
        channelMenu.add(greenItem);

        JMenuItem redItem = createJMenuItem("Красный канал", image);
        channelMenu.add(redItem);

        fileMenuBW.add(channelMenu);

        JMenuItem equalizationItem = createJMenuItem("Эквализация", image);
        fileMenuBW.add(equalizationItem);
        menuBar.add(fileMenuBW);

        JMenu fileMenuFilter = new JMenu("Filter");
        fileMenuFilter.setFont(font);

        JMenuItem filter = new JMenuItem("Фильтр");
        filter.setFont(font);
        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (image == null) {
                    JOptionPane.showMessageDialog(frame, "no image");
                    return;
                }
                filtration(image);
            }
        });
        fileMenuFilter.add(filter);
        menuBar.add(fileMenuFilter);

        JMenu fileMenuCoding = new JMenu("Coding");
        fileMenuCoding.setFont(font);

        JMenuItem coder = new JMenuItem("Кодирование");
        coder.setFont(font);
        coder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coder coder1 = new Coder();
            }
        });
        fileMenuCoding.add(coder);
        menuBar.add(fileMenuCoding);

        JMenu fileMenuBin = new JMenu("Binarization");
        fileMenuBin.setFont(font);

        JMenuItem ocuItem = createJMenuItem("Оцу", image);
        fileMenuBin.add(ocuItem);


        JMenuItem niblItem = new JMenuItem("Ниблэк");
        niblItem.setFont(font);
        niblItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (image == null) {
                    JOptionPane.showMessageDialog(frame, "no image");
                    return;
                }
                niblack(image);
            }
        });
        fileMenuBin.add(niblItem);

        menuBar.add(fileMenuBin);

        JMenu fileMenuMor = new JMenu("Morphology");
        fileMenuMor.setFont(font);

        JMenuItem morItem = new JMenuItem("задать структурирующий элемент");
        morItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (image == null) {
                    JOptionPane.showMessageDialog(frame, "no image");
                    return;
                }
                morphology(image);
            }
        });
        fileMenuMor.add(morItem);


        menuBar.add(fileMenuMor);

        return menuBar;

    }


    private JMenuItem createJMenuItem(String choice, BufferedImage image) {
        JMenuItem newItem = new JMenuItem(choice);
        newItem.setFont(font);
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createWindow(choice, image);
            }
        });
        return newItem;
    }

    private void createHist(int[] hist, String name) {
        Frame histFrame = new Frame(name + "гистограмма");
        histFrame.setSize(1024, 500);
        histFrame.add(new DrawingComponent(hist), BorderLayout.CENTER);
        histFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                histFrame.setVisible(false);
            }
        });
        histFrame.setVisible(true);
    }

    private void createWindow(String choice, BufferedImage orig) {
        if (orig == null) {
            JOptionPane.showMessageDialog(frame, "no image");
            return;
        }

        JFrame nFrame = new JFrame(choice);
        nFrame.setSize(new Dimension(800, 900));
        BufferedImage image = selectHandling(choice, Handling.fit(orig));
        JLabel label = new JLabel(new ImageIcon(Handling.fit2(image)));
        JMenuBar menuBar = createBar(image);
        nFrame.add(label);
        nFrame.setJMenuBar(menuBar);
        nFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                nFrame.setVisible(false);
            }
        });
        nFrame.setVisible(true);

    }

    private BufferedImage selectHandling(String choice, BufferedImage orig) {
        switch (choice) {
            case "Усреднение":
                return Handling.usred(orig);
            case "Коррекция":
                return Handling.correction(orig);
            case "Десатурация":
                return Handling.desaturation(orig);
            case "Градация по минимуму":
                return Handling.minGradation(orig);
            case "Градация по максимуму":
                return Handling.maxGradation(orig);
            case "Синий канал":
                return Handling.blueChannel(orig);
            case "Красный канал":
                return Handling.redChannel(orig);
            case "Зеленый канал":
                return Handling.greenChannel(orig);
            case "Эквализация":
                BufferedImage image;
                createHist(Handling.buildHist(orig), "оригинальная ");
                image = Handling.equalization(orig);
                createHist(Handling.buildHist(image), "эквализованная ");
                return image;
            case "Оцу":
                return Handling.binarizationOcu(orig);
            default:
                return orig;
        }
    }


    class DrawingComponent extends JPanel {
        int[] hist;

        DrawingComponent(int[] hist) {
            this.hist = hist;
        }


        @Override
        protected void paintComponent(Graphics gh) {
            Graphics2D drp = (Graphics2D) gh;
            int maxHist = 500;
            for (int i = 0; i < 255; i++)
                if (hist[i] > maxHist)
                    maxHist = hist[i];

            for (int i = 0; i < 255; i++) {
                if (hist[i] != 0) {
                    hist[i] = hist[i] * 500 / maxHist;
                    drp.drawRect(i * 4, 500 - hist[i], 4, hist[i]);
                }
            }
        }

    }


    private JPanel leftPanelGen() {
        final JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEtchedBorder());
        uploadImage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("image", "jpg", "png", "gif");
                fc.setDialogTitle("Select image file");
                fc.setFileFilter(filter);
                int okCheck = fc.showOpenDialog(null);
                if (okCheck == fc.APPROVE_OPTION) {
                    try {
                        img = ImageIO.read(fc.getSelectedFile());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    JLabel label = new JLabel(new ImageIcon(Handling.fit(img)));
                    leftPanel.removeAll();
                    leftPanel.add(label);
                    leftPanel.revalidate();
                    JMenuBar menuBar = createBar(img);
                    frame.setJMenuBar(menuBar);
                }

            }

        });
        return leftPanel;
    }


    private void morphology(BufferedImage image) {

        JFrame setSizeF = new JFrame("Размерность");
        setSizeF.setSize(new Dimension(130, 170));
        setSizeF.getContentPane().setLayout(null);

        JLabel label1 = new JLabel("width:");
        label1.setBounds(5, 10, 50, 25);
        JLabel label2 = new JLabel("height:");
        label2.setBounds(5, 40, 50, 25);

        JTextField widthField = new JTextField();
        widthField.setBounds(60, 10, 30, 25);
        JTextField heightField = new JTextField();
        heightField.setBounds(60, 40, 30, 25);

        JButton insert = new JButton("OK");
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int width = Integer.valueOf(widthField.getText());
                    int height = Integer.valueOf(heightField.getText());
                    if (width < 2 || height < 2) throw new DimensionException("The number is less than 2");
                    setFilter2(width, height, image);
                    setSizeF.setVisible(false);

                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(setSizeF, "not a number");
                } catch (DimensionException e1) {
                    JOptionPane.showMessageDialog(setSizeF, e1.getMessage());
                }

            }
        });

        setSizeF.getContentPane().add(label1);
        setSizeF.getContentPane().add(label2);
        setSizeF.getContentPane().add(widthField);
        setSizeF.getContentPane().add(heightField);

        insert.setBounds(10, 90, 80, 25);
        setSizeF.getContentPane().add(insert);
        setSizeF.setVisible(true);

    }

    class DimensionException extends Exception {
        public DimensionException(String errorMessage) {
            super(errorMessage);
        }
    }

    private void setFilter2(int width, int height, BufferedImage image) {


        double[][] filter = new double[width][height];
        JTextField[][] fields = new JTextField[width][height];

        JFrame setFilter = new JFrame("Матрица");
        int w = width * 40 + 80;
        int h = height * 30 + 120;
        setFilter.setSize(new Dimension(w, h));
        setFilter.getContentPane().setLayout(null);
        setFilter.setVisible(true);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                JTextField field = new JTextField();
                field.setBounds(40 + i * 40, 30 + j * 30, 40, 30);
                fields[i][j] = field;
                setFilter.getContentPane().add(fields[i][j]);
            }
        }

        JButton erButton = new JButton("ерозия");
        erButton.setBounds(w / 2, h - 60, 80, 30);
        erButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        try {
                            filter[i][j] = Double.valueOf(fields[i][j].getText());
                        } catch (NumberFormatException e1) {
                            JOptionPane.showMessageDialog(setFilter, "not a number");
                            return;
                        }
                    }

                }
                try {
                    erosian(image, filter);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        setFilter.getContentPane().add(erButton);

        JButton dilButton = new JButton("дилатация");
        dilButton.setBounds(0, h - 60, 80, 30);
        dilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        try {
                            filter[i][j] = Double.valueOf(fields[i][j].getText());
                        } catch (NumberFormatException e1) {
                            JOptionPane.showMessageDialog(setFilter, "not a number");
                            return;
                        }
                    }

                }
                try {
                    dilatation(image, filter);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        setFilter.getContentPane().add(dilButton);

    }

    public static void writeImageToJPG(File file, BufferedImage bufferedImage)
            throws IOException {
        ImageIO.write(bufferedImage, "jpg", file);
    }

    private void dilatation(BufferedImage image1, double[][] element) throws IOException {
        image1 = Handling.binarizationOcu(image1);
        int width = element[0].length;
        int height = element.length;
        int w;
        int h;
        w = width / 2 + 1;
        h = height / 2 + 1;
        if (width == 1)
            w = 0;
        if (height == 1)
            h = 0;
        double[][] mas;
        BufferedImage result1 = new BufferedImage(image1.getWidth(), image1.getHeight(), image1.getType());
        int width_im = image1.getWidth();
        int height_im = image1.getHeight();

        mas = Handling.toMatrix(image1);

        double[][] av = new double[image1.getWidth()][image1.getHeight()];
        int i;
        int j;
        int p;
        int u;
        for (int y = 0; y < width_im; y++) {
            for (int v = 0; v < height_im; v++) {
                Color newColor = new Color((int) 255, (int) 255, (int) 255);
                result1.setRGB(y, v, newColor.getRGB());
            }
        }
        for (int y = 0; y <= (width_im - width); y++) {
            for (int v = 0; v <= (height_im - height); v++) {
                u = 0;
                for (i = y; i < (y + width); i++) {
                    p = 0;
                    for (j = v; j < v + height; j++) { //добавление пикселей
                        if (((mas[i][j] == 0) && (element[u][p] == 1.0))) {
                            av[y + w][v + h] = 0.0;
                            i = y + width;
                            break;
                        } else {
                            av[y + w][v + h] = 255.0;
                            p++;
                        }

                    }
                    u++;
                }

                Color newColor = new Color((int) av[y + w][v + h], (int) av[y + w][v + h], (int) av[y + w][v + h]);
                result1.setRGB(y + w, v + h, newColor.getRGB());
            }
        }
        createWindow("дилатация", result1);
        File f = new File("/home/dina/new");
        writeImageToJPG(f, result1);


    }


    private void erosian(BufferedImage image1, double[][] element) throws IOException {
        image1 = Handling.binarizationOcu(image1);
        int width = element[0].length;
        int height = element.length;
        int w;
        int h;
        w = (int) width / 2 + 1;
        h = (int) height / 2 + 1;
        if (width == 1)
            w = 0;
        if (height == 1)
            h = 0;
        double[][] mas;
        BufferedImage result1 = new BufferedImage(image1.getWidth(), image1.getHeight(), image1.getType());
        int width_im = image1.getWidth();
        int height_im = image1.getHeight();

        mas = Handling.toMatrix(image1);

        double[][] av = new double[image1.getWidth()][image1.getHeight()];
        int i;
        int j;
        int p;
        int u;
        for (int y = 0; y < width_im; y++) {
            for (int v = 0; v < height_im; v++) {
                Color newColor = new Color(255, 255, 255);
                result1.setRGB(y, v, newColor.getRGB());
            }
        }
        for (int y = 0; y <= (width_im - width); y++) {
            for (int v = 0; v <= (height_im - height); v++) {

                u = 0;
                for (i = y; i < (y + width); i++) {
                    p = 0;
                    for (j = v; j < v + height; j++) {
                        if (((mas[i][j] == 255))) {
                            av[y + w][v + h] = 255;
                            i = y + width;
                            break;

                        } else {
                            p++;
                        }
                    }
                    u++;
                }


                Color newColor = new Color((int) av[y + w][v + h], (int) av[y + w][v + h], (int) av[y + w][v + h]);
                result1.setRGB(y + w, v + h, newColor.getRGB());
            }
        }
        File f = new File("/home/dina/new");
        writeImageToJPG(f, result1);
        createWindow("эрозия", result1);
    }


    private void niblack(BufferedImage orig) {
        JFrame setSizeF = new JFrame("Размерность");
        setSizeF.setSize(new Dimension(130, 170));
        setSizeF.getContentPane().setLayout(null);

        JLabel label1 = new JLabel("w:");
        label1.setBounds(5, 10, 50, 25);
        JLabel label2 = new JLabel("k:");
        label2.setBounds(5, 40, 50, 25);

        JTextField widthField = new JTextField();
        widthField.setBounds(60, 10, 30, 25);
        JTextField heightField = new JTextField();
        heightField.setBounds(60, 40, 30, 25);

        JButton insert = new JButton("OK");
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int width = Integer.valueOf(widthField.getText());
                    double height = Double.valueOf(heightField.getText());
                    if (width < 1) throw new DimensionException("The number is less than 1");
                    createWindow("Ниблэк", Handling.binarizationNiblack(orig, width, height));

                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(setSizeF, "not a number");
                } catch (DimensionException e1) {
                    JOptionPane.showMessageDialog(setSizeF, e1.getMessage());
                }

            }
        });

        setSizeF.getContentPane().add(label1);
        setSizeF.getContentPane().add(label2);
        setSizeF.getContentPane().add(widthField);
        setSizeF.getContentPane().add(heightField);

        insert.setBounds(10, 90, 80, 25);
        setSizeF.getContentPane().add(insert);
        setSizeF.setVisible(true);
    }

    private void filtration(BufferedImage image) {

        JFrame setSizeF = new JFrame("Размерность");
        setSizeF.setSize(new Dimension(130, 170));
        setSizeF.getContentPane().setLayout(null);

        JLabel label1 = new JLabel("width:");
        label1.setBounds(5, 10, 50, 25);
        JLabel label2 = new JLabel("height:");
        label2.setBounds(5, 40, 50, 25);

        JTextField widthField = new JTextField();
        widthField.setBounds(60, 10, 30, 25);
        JTextField heightField = new JTextField();
        heightField.setBounds(60, 40, 30, 25);

        JButton insert = new JButton("OK");
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int width = Integer.valueOf(widthField.getText());
                    int height = Integer.valueOf(heightField.getText());
                    if (width < 2 || height < 2) throw new DimensionException("The number is less than 2");
                    setFilter(width, height, image);
                    setSizeF.setVisible(false);

                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(setSizeF, "not a number");
                } catch (DimensionException e1) {
                    JOptionPane.showMessageDialog(setSizeF, e1.getMessage());
                }

            }
        });

        setSizeF.getContentPane().add(label1);
        setSizeF.getContentPane().add(label2);
        setSizeF.getContentPane().add(widthField);
        setSizeF.getContentPane().add(heightField);

        insert.setBounds(10, 90, 80, 25);
        setSizeF.getContentPane().add(insert);
        setSizeF.setVisible(true);

    }


    private void setFilter(int width, int height, BufferedImage image) {


        double[][] filter = new double[width][height];
        JTextField[][] fields = new JTextField[width][height];

        JFrame setFilter = new JFrame("Матрица");
        int w = width * 40 + 80;
        int h = height * 30 + 120;
        setFilter.setSize(new Dimension(w, h));
        setFilter.getContentPane().setLayout(null);
        setFilter.setVisible(true);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                JTextField field = new JTextField();
                field.setBounds(40 + i * 40, 30 + j * 30, 40, 30);
                fields[i][j] = field;
                setFilter.getContentPane().add(fields[i][j]);
            }
        }

        JButton okButton = new JButton("OK");
        okButton.setBounds(w / 2 - 40, h - 60, 80, 30);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        try {
                            filter[i][j] = Double.valueOf(fields[i][j].getText());
                        } catch (NumberFormatException e1) {
                            JOptionPane.showMessageDialog(setFilter, "not a number");
                            return;
                        }
                    }

                }
                setFilter.setVisible(false);

                mult(image, filter);

            }
        });
        setFilter.getContentPane().add(okButton);


    }

    private void mult(BufferedImage orig, double[][] matrix) {

        int width = orig.getWidth();
        int height = orig.getHeight();
        double div = 0;

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                div += Math.abs(matrix[i][j]);


        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int w = matrix.length / 2;
        int h = matrix[0].length / 2;

        if (matrix.length % 2 == 0) {
            double[][] modMatrix = new double[matrix.length + 1][matrix[0].length];
            System.arraycopy(matrix, 0, modMatrix, 0, matrix.length);
            matrix = modMatrix;
        }

        if (matrix[0].length % 2 == 0) {
            double[][] modMatrix = new double[matrix.length][matrix[0].length + 1];
            for (int i = 0; i < matrix.length; i++)
                System.arraycopy(matrix[i], 0, modMatrix[i], 0, matrix[i].length);
            matrix = modMatrix;
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int red = 0;
                int green = 0;
                int blue = 0;
                Color c;
                int x = 0;
                int xMax = 0;
                int y = 0;
                int yMax = 0;

                if (i < w)
                    x = -i;
                else x = -w;
                if (j < h)
                    y = -j;
                else y = -h;
                if (i >= width - w)
                    xMax = 0;
                else xMax = w;
                if (j >= height - h)
                    yMax = 0;
                else yMax = h;


                for (int m = x; m <= xMax; m++) {
                    for (int n = y; n <= yMax; n++) {
                        c = new Color(orig.getRGB(i + m, j + n));
                        red += c.getRed() * matrix[m + w][n + h];
                        green += c.getGreen() * matrix[m + w][n + h];
                        blue += c.getBlue() * matrix[m + w][n + h];

                    }

                }

                red = (int) Math.round(red / div);
                green = (int) Math.round(green / div);
                blue = (int) Math.round(blue / div);
                if (red < 0) red = 0;
                else if (red > 255) red = 255;
                if (green < 0) green = 0;
                else if (green > 255) green = 255;
                if (blue < 0) blue = 0;
                else if (blue > 255) blue = 255;

                image.setRGB(i, j, new Color(red, green, blue).getRGB());
            }
        }
        createWindow("Фильтр", image);

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WindowApp myApp = new WindowApp();
            }
        });
    }
}