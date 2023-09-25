import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Timer extends JPanel {
    private int remainingSeconds1 = 120;
    private int remainingSeconds2 = 120;
    private JLabel timerLabel1;
    private JLabel timerLabel2;
    public boolean isTimer1Active = true;
    private javax.swing.Timer timer1 = new javax.swing.Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            remainingSeconds1--;
            if (remainingSeconds1 >= 0) {
                timerLabel1.setText(formatTime(remainingSeconds1));
            }
        }
    });
    private javax.swing.Timer timer2 = new javax.swing.Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            remainingSeconds2--;
            if (remainingSeconds2 >= 0) {
                timerLabel2.setText(formatTime(remainingSeconds2));
            }
        }
    });

    public Timer() {
        setPreferredSize(new Dimension(400, 80));
        setBackground(Color.BLACK);

        timerLabel1 = new JLabel(formatTime(remainingSeconds1));
        timerLabel1.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel1.setForeground(Color.WHITE);

        timerLabel2 = new JLabel(formatTime(remainingSeconds2));
        timerLabel2.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel2.setForeground(Color.WHITE);

        add(timerLabel1);
        add(timerLabel2);

        startTimer1();
    }

    public void startTimer1() {
        timer2.stop();
        isTimer1Active = true;
        timer1.start();
    }

    public void startTimer2() {
        timer1.stop();
        isTimer1Active = false;
        timer2.start();
    }

    public String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    public void stopTimers() {
        if (isTimer1Active) {
            timer1.stop();
        } else {
            timer2.stop();
        }
    }
}
