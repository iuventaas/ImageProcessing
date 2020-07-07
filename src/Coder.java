import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Coder {

    JFrame codingFrame = new JFrame("Кодирование");
    BufferedImage img;
    private JButton codeBtn;
    private JButton decodeBtn;
    Font font = new Font("Verdana", Font.PLAIN, 11);

    JTextField alphabet;
    JTextField toDecodeStart;
    JTextField toDecodeEnd;
    JTextField toCode;


    public Coder() {

        JLabel label1 = new JLabel("Алфавит:");
        label1.setBounds(5, 10, 120, 25);
        JLabel label2 = new JLabel("Закодировать:");
        label2.setBounds(5, 40, 120, 25);
        JLabel label3 = new JLabel("Раскодировать:");
        label3.setBounds(5, 70, 120, 25);

        alphabet = new JTextField();
        toCode = new JTextField();
        toDecodeStart = new JTextField();
        toDecodeEnd = new JTextField();

        alphabet.setBounds(130, 10, 250, 25);
        toCode.setBounds(130, 40, 250, 25);
        toDecodeStart.setBounds(130, 70, 250, 25);

        codingFrame.getContentPane().add(label1);
        codingFrame.getContentPane().add(label2);
        codingFrame.getContentPane().add(label3);
        codingFrame.getContentPane().add(alphabet);
        codingFrame.getContentPane().add(toCode);
        codingFrame.getContentPane().add(toDecodeStart);

        codeBtn = new JButton("code");
        decodeBtn = new JButton("decode");


        codeBtn.setBounds(30, 120, 100, 25);
        decodeBtn.setBounds(150, 120, 100, 25);
        codingFrame.getContentPane().add(decodeBtn);
        codingFrame.getContentPane().add(codeBtn);

        codingFrame.setSize(new Dimension(600, 200));
        codingFrame.getContentPane().add(leftPanelGen());
        codingFrame.getContentPane().setLayout(null);
        codingFrame.setVisible(true);

    }

    private JPanel leftPanelGen() {
        final JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEtchedBorder());
        codeBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String toCodeStr = toCode.getText();
                String alphabetStr = alphabet.getText() + "!";

                if (alphabetStr.isEmpty()) {
                    JOptionPane.showMessageDialog(codingFrame, "пустой алфавит");
                    return;
                }
                if (toCodeStr.isEmpty()) {
                    JOptionPane.showMessageDialog(codingFrame, "пустая строка");
                    return;
                }
                if (toCodeStr.contains("!")) {
                    JOptionPane.showMessageDialog(codingFrame, "! это стоп символ");
                    return;
                }
                if (!check(alphabetStr, toCodeStr)) {
                    JOptionPane.showMessageDialog(codingFrame, "алфавит не включает все символы");
                    return;
                } else {
                    double[] res = code(alphabetStr, toCodeStr + "!");
                    JTextArea result = new JTextArea("[" + res[0] + "; " + res[1] + ")");
                    JFrame resFrame = new JFrame("результат кодировки");
                    resFrame.add(result);
                    resFrame.setSize(new Dimension(300, 100));
                    resFrame.setVisible(true);



                }

            }

        });

        decodeBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                double start;
                String alphabetStr;
                try {
                    start = Double.valueOf(toDecodeStart.getText());
                    alphabetStr = alphabet.getText() + "!";

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(codingFrame, "not a number");
                    return;
                }

                if (start < 0 || start > 1) {
                    JOptionPane.showMessageDialog(codingFrame, "число должно попадать в [0;1]");
                    return;
                }

                StringBuffer res = decode(alphabetStr, start);
                JTextArea result = new JTextArea(res.toString());
                JFrame resFrame = new JFrame("результат кодировки");
                resFrame.add(result);
                resFrame.setSize(new Dimension(300, 100));
                resFrame.setVisible(true);

            }

        });
        return leftPanel;
    }


    private boolean check(String alphabet, String strToCode) {

        ArrayList<Character> chAlphabet = toChars(alphabet);

        for (int i = 0; i < strToCode.length(); i++) {
            char ch = strToCode.charAt(i);
            if (!chAlphabet.contains(ch))
                return false;
        }
        return true;
    }

    private StringBuffer decode(String alphabet, double value) {
        StringBuffer res = new StringBuffer();
        ArrayList<Character> chAlphabet = toChars(alphabet);
        double n = 1 / (double) chAlphabet.size();
        res = decodeR(chAlphabet, res, value, n);
        return res;
    }

    private StringBuffer decodeR( ArrayList<Character> chAlphabet, StringBuffer res, double value, double n){
        int k = (int) Math.floor(value / n);
        if (chAlphabet.get(k).equals('!'))
            return res;
        res.append(chAlphabet.get(k));
        double codeN = (value - k * n) / n;
        decodeR(chAlphabet, res, codeN, n);
        return res;
    }

    private ArrayList<Character> toChars(String str) {
        ArrayList<Character> charList = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!charList.contains(ch))
                charList.add(ch);
        }

        return charList;
    }

    private double[] code(String alphabet, String strToCode) {
        ArrayList<Character> chAlphabet = toChars(alphabet);
        double n = 1 / (double) chAlphabet.size();
        double[] interval = new double[2];
        interval[0] = 0;
        interval[1] = 1;
        for (int i = 0; i < strToCode.length(); i++) {
            char ch = strToCode.charAt(i);
            int k = chAlphabet.indexOf(ch);
            double newHigh = interval[0] + (interval[1] - interval[0]) * (k + 1) * n;
            double newLow = interval[0] + (interval[1] - interval[0]) * k * n;
            interval[0] = newLow;
            interval[1] = newHigh;
        }

        return interval;
    }
}
