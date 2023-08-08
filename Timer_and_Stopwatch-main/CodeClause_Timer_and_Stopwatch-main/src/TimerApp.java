import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerApp extends JFrame {
    private JLabel timerLabel;
    private Timer timer;
    private int countdown;
    private boolean isPaused;

    public TimerApp() {
        setTitle("Timer App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(300, 150);
        setLocationRelativeTo(null);

        timerLabel = new JLabel("Enter time in seconds:");
        JTextField inputField = new JTextField(10);
        JButton startButton = new JButton("Start");
        JButton pauseButton = new JButton("Pause");
        JButton resumeButton = new JButton("Resume");
        JButton stopButton = new JButton("Stop");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) {
                    try {
                        countdown = Integer.parseInt(inputField.getText());
                        timerLabel.setText(formatTime(countdown));
                        timer = new Timer(1000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (countdown > 0) {
                                    countdown--;
                                    timerLabel.setText(formatTime(countdown));
                                } else {
                                    timer.stop();
                                    JOptionPane.showMessageDialog(TimerApp.this, "Time's up!");
                                }
                            }
                        });
                        timer.start();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(TimerApp.this, "Invalid input! Please enter a valid integer.");
                    }
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer != null && !isPaused) {
                    timer.stop();
                    isPaused = true;
                }
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer != null && isPaused) {
                    timer.start();
                    isPaused = false;
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer != null) {
                    timer.stop();
                    countdown = 0;
                    timerLabel.setText(formatTime(countdown));
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(timerLabel);
        panel.add(inputField);
        panel.add(startButton);
        panel.add(pauseButton);
        panel.add(resumeButton);
        panel.add(stopButton);
        add(panel);

        setVisible(true);
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TimerApp();
            }
        });
    }
}
