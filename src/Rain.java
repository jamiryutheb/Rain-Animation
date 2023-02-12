
import javax.swing.*;
import java.awt.*;
import static java.lang.Math.random;

public class Rain extends JFrame {

    public Rain(){
        this.add(new RainPanel());
        this.setTitle("Rain");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    private static class RainPanel extends JPanel implements Runnable {

        boolean rain;
        static int width = 1200;
        static int height = 900;
        Drop[] drops;

        public RainPanel(){

            this.setPreferredSize(new Dimension(width, height));
            this.setBackground(new Color(0xA7A7B8));

            drops = new Drop[700];
            for (int i = 0; i < drops.length ; i++) drops[i] = new Drop();
            rain = true;
            new Thread(this).start();
        }

        @Override
        public void run() {
            System.out.println("running...");
            while (rain){
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Drop drop : drops) drop.fall();
                repaint();
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(0x08666A));
            for (Drop drop : drops) drop.show(g);
        }

        private static class Drop {

            int x;
            int y;
            int speed;
            float thickness;
            int distance;
            int len;

            public Drop(){
                distance = (int) (random() * 10);
                x = (int) (random() * width);
                y = (int) (random() * height);
                thickness = (float) (random() * distance + 0.5);
                speed = (int) (random() * distance + 2);
                len = (int) (random() * distance + 13);
            }

            public void fall(){
                y = y + 3 * speed;
                speed += 0.5;
                if(y > height)
                    y = 0;
            }

            public void show(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                g2.drawLine(this.x, this.y, this.x, this.y + len);
            }

            /**
             * Re-maps a number from one range to another.
             * @// FIXME: 12/13/2020 : map function from p5 library
             * @param n        Variable to be mapped to X*
             * @param start1   Minimum value of n
             * @param stop1    Maximum value of n
             * @param start2   Minimum value of X
             * @param stop2    Maximum value of X
             * @return         Remapped number
             * */

            int map (int n, int start1, int stop1, int start2, int stop2) {

                final int new_wal = (n - start1) / (stop1 - start1) * (stop2 - start2) + start2;
                if (start2 < stop2) {
                    return constrain(new_wal, start2, stop2);
                } else {
                    return constrain(new_wal, stop2, start2);
                }
            };

            public int constrain(int n, int low, int high) {
                return Math.max(Math.min(n, high), low);
            };

        }
    }
}