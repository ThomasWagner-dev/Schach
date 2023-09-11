import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Timer extends JPanel {
    private int remainingSeconds1 = 120;
    private int remainingSeconds2 = 120;
    private JLabel timerLabel1;
    private JLabel timerLabel2;
    private javax.swing.Timer timer1;
    private javax.swing.Timer timer2;
    private boolean isTimer1Active = true;

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
        timer1 = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingSeconds1--;
                if (remainingSeconds1 >= 0) {
                    timerLabel1.setText(formatTime(remainingSeconds1));
                } else {
                    stopTimer1(); // Aggiunto per fermare il timer1
                    isTimer1Active = false;
                    startTimer2();
                }
            }
        });
        timer1.start();
    }

    public void startTimer2() {
        timer2 = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingSeconds2--;
                if (remainingSeconds2 >= 0) {
                    timerLabel2.setText(formatTime(remainingSeconds2));
                } else {
                    stopTimer2(); // Aggiunto per fermare il timer2
                    isTimer1Active = true;
                    startTimer1();
                }
            }
        });

        timer2.start();
    }

    public String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    public void stopTimers() {
        if (isTimer1Active) {
            stopTimer1();
        } else {
            stopTimer2();
        }
    }

    public void stopTimer1() {
        if (timer1 != null && timer1.isRunning()) {
            timer1.stop();
        }
    }

    public void stopTimer2() {
        if (timer2 != null && timer2.isRunning()) {
            timer2.stop();
        }
    }

    public boolean isTimer1Active() {
        return isTimer1Active;
    }

    public void setTimer1Active(boolean timer1Active) {
        isTimer1Active = timer1Active;
    }
}
