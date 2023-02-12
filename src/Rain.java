
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Rain extends JFrame {

    private static final long REFRESH_INTERVAL_MS = 25;

    public Rain(){
        this.add(new RainPanel());
        this.setTitle("Rain");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    static class RainPanel extends JPanel implements Runnable {

        boolean rain;
        static final int SCREEN_WIDTH = 1050;
        static final int SCREEN_HEIGHT = 700;
        Drop[] drops;
        private Object redrawLock = new Object();
        BufferedImage bgImage;
        public RainPanel(){

            try {
                bgImage = ImageIO.read(new File("src\\image\\bg.jpg"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
//            this.setBackground(new Color(0xA7A7B8));

            drops = new Drop[700];
            for (int i = 0; i < drops.length ; i++) drops[i] = new Drop();
            rain = true;
            new Thread(this).start();
        }

        @Override
        public void run() {
            System.out.println("running...");
            while (rain){
                long durationMs = redraw();
                try {
                    Thread.sleep(Math.max(0, REFRESH_INTERVAL_MS - durationMs));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        private long redraw() {

            long t = System.currentTimeMillis();

            // perform changes to model
            for (Drop drop : drops)
                drop.fall();


            repaint();
            waitForPaint();

            // return time taken to do redraw in ms
            return System.currentTimeMillis() - t;
        }

        private void waitForPaint() {
            try {
                synchronized (redrawLock) {
                    redrawLock.wait();
                }
            } catch (InterruptedException ignored) {
            }
        }

        private void resume() {
            synchronized (redrawLock) {
                redrawLock.notify();
            }
        }


        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bgImage, 0, 0, 1200, 700, null);
            resume();
            g.setColor(new Color(0x08666A));
            for (Drop drop : drops) drop.show(g);
        }


    }
}