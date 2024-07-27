import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CountdownFrame extends JFrame {

    protected JLabel countdownLabel;
    protected JLabel textLabel;
    protected int nextYear = LocalDateTime.now().getYear() + 1;
    protected JCheckBox checkBox;
    protected JLabel funLabel;

    public CountdownFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        funLabel = new JLabel(new String("ВЕСЕЛУХА:".getBytes(), StandardCharsets.UTF_8));
        countdownLabel = new JLabel();
        funLabel.setForeground(Color.RED);
        String text = new String("До Нового года осталось:".getBytes(), StandardCharsets.UTF_8);
        textLabel = new JLabel(text);
        checkBox = new JCheckBox();
        checkBox.setSelected(false);
//        textLabel.setFont(Font.);
        setLayout(new GridBagLayout());
        addComponents();
        configureListeners();
        Thread r = new Thread(this::updateCountdown);
        r.start();

        pack();
        setBounds(700, 200, getWidth() + 50, getWidth() + 50);
    }

    protected void updateCountdown() {
        while (true) {
            String text = "";
            LocalDateTime countdownTill = LocalDateTime.of(nextYear, 1, 1, 0, 0);
            long secondsLeft = convertToSeconds(countdownTill) - convertToSeconds(LocalDateTime.now());
            if (secondsLeft <= 0 && secondsLeft >= -10) {
                text = String.format(new String("С НОВЫМ %d ГОДОМ".getBytes(), StandardCharsets.UTF_8), nextYear);
                textLabel.setText("");
            } else if (secondsLeft < -10) {
                nextYear++;
                textLabel.setText(new String("До Нового года осталось:".getBytes(), StandardCharsets.UTF_8));
            } else {
                text = Long.toString(secondsLeft);
            }
            countdownLabel.setText(text);
            countdownLabel.setForeground(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void addComponents() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        add(funLabel, c);
        c.gridx = 3;
        c.gridy = 0;
        add(checkBox, c);
        c.gridx = 0;
        c.gridy = 1;
        add(textLabel, c);
        c.gridx = 0;
        c.gridy = 2;
        add(countdownLabel, c);
    }

    protected long convertToSeconds(LocalDateTime countdownTill) {
        return countdownTill.toEpochSecond(ZoneOffset.ofHours(3));
    }

    protected void configureListeners() {
        checkBox.addActionListener(e -> {
            if (checkBox.isSelected()) {
                try {
                    MusicManager.getInstance().play();
                } catch (JavaLayerException ex) {
                    throw new RuntimeException(ex);
                }
            } else MusicManager.getInstance().stop();
        });
    }

}
