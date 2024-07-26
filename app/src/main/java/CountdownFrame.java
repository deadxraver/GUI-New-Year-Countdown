
import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CountdownFrame extends JFrame {

    protected JLabel countdownLabel;
    protected JLabel textLabel;
    protected int nextYear = LocalDateTime.now().getYear();

    public CountdownFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        countdownLabel = new JLabel();
        String text = new String("До Нового года осталось:".getBytes(), StandardCharsets.UTF_8);
        textLabel = new JLabel(text);
//        textLabel.setFont(Font.);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        add(textLabel, c);
        c.gridx = 0;
        c.gridy = 2;
        add(countdownLabel, c);
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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected long convertToSeconds(LocalDateTime countdownTill) {
        return countdownTill.toEpochSecond(ZoneOffset.ofHours(3));
    }

}
