import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Timer extends JPanel {
    private int remainingSeconds = 120;
    private JLabel timerLabel;
    private javax.swing.Timer timer;

    public Timer() {
        setPreferredSize(new Dimension(200, (8 + 1) * 80));
        setBackground(Color.BLACK);

        timerLabel = new JLabel(formatTime(remainingSeconds));
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setForeground(Color.WHITE);
        add(timerLabel, BorderLayout.NORTH);

        startTimer();
    }

    private void startTimer() {
        timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingSeconds--;
                if (remainingSeconds >= 0) {
                    timerLabel.setText(formatTime(remainingSeconds));
                } else {
                    stopTimer();
                }
            }
        });
        timer.start();
    }

    private void stopTimer() {
        timer.stop();

        //.....
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }
}
