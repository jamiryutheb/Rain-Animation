import java.awt.*;

import static java.lang.Math.random;

public class Drop {

    int x;
    int y;
    int speed;
    float thickness;
    int distance;
    int len;

    public Drop(){
        distance = (int) (random() * 10);
        x = (int) (random() * Rain.RainPanel.SCREEN_WIDTH);
        y = (int) (random() * Rain.RainPanel.SCREEN_HEIGHT);
        thickness = (float) (random() * distance + 0.5);
        speed = (int) (random() * distance + 2);
        len = (int) (random() * distance + 13);
    }

    public void fall(){
        y = y + 3 * speed;
        speed += 0.5;
        if(y > Rain.RainPanel.SCREEN_HEIGHT)
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