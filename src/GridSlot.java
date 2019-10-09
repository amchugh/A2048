import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class GridSlot {

    int x, y, size;

    private static Color BACKGROUND_COLOR = new Color(0xE8FAFF);
    private static Color TEXT_COLOR = new Color(0x444949);
    private static int TEXT_SIZE = 30;

    public GridSlot(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void draw(Graphics g, ATileDetails tile) {
        // Test to see if the slot is blank
        if (!tile.doDisplay) {
            // We wont draw anything if the tile doesnt have a display
            return;
        }
        // Fill the background
        g.setColor(tile.backgroundColor);
        drawBackground(g);
        // Draw the number
        g.setColor(TEXT_COLOR);
        Font f = new Font("Courier", Font.PLAIN,TEXT_SIZE);
        g.setFont(f);
        String s = tile.displayValue;
        FontRenderContext frc = new FontRenderContext(new AffineTransform(), false, false);
        Rectangle2D r = f.getStringBounds(s, frc);
        g.drawString(s, x + size/2 - (int)r.getWidth()/2, y + size/2 +(int)r.getHeight()/4);
    }

    private void drawBackground(Graphics g) {
        g.fillRect(x,y,size,size);
    }

}
