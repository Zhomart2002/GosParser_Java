import gos.logic.Runner;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    public GUI() {
        super("Parser");
        this.setBounds(100, 100, 300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 3, 5, 5));

        JButton okButton = new JButton("Parse");
        container.add(okButton);

        okButton.addActionListener((event) -> {
            okButton.setEnabled(false);

            Thread runner = new Thread(Runner::run);
            runner.start();

            try {
                runner.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            okButton.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Parsing finished", "Output", JOptionPane.PLAIN_MESSAGE);
        });
    }
}
