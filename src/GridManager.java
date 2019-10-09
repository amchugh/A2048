import java.awt.*;

public class GridManager {

    private GridSlot[] slots;

    private int posx,posy,gridSize,gridCount,inside_borders,slotSize;

    private static final Color GRID_BACKGROUND = new Color(0x8A8A8F);
    private static Color BLANK_BACKGROUND_COLOR = new Color(0xB4C2C6);
    
    public GridManager(int gridCount, int inside_borders, int gridSize, int upperx, int uppery) {
        posx = upperx;
        posy = uppery;
        this.gridCount = gridCount;
        this.inside_borders = inside_borders;
        slots = new GridSlot[gridCount*gridCount];
        slotSize = (gridSize - (inside_borders))/(gridCount)-inside_borders;
        this.gridSize = ((slotSize+inside_borders) * gridCount) + inside_borders;
        for (int x = 0; x < gridCount; x++) {
            for (int y = 0; y < gridCount; y++) {
                slots[y*gridCount+x] =
                        new GridSlot(
                                upperx + (inside_borders * (x+1)) + (slotSize * x),
                                uppery + (inside_borders * (y+1)) + (slotSize * y),
                                slotSize
                        );
            }
        }
    }

    public void draw(Graphics g, int[] numbers, ATileDetails[] tiles) {
        // Checksum
        if (numbers.length != slots.length) {
            System.out.println("Not enough numbers");
            System.exit(-1);
            return;
        }
        // First, lay down the base color for blank tiles
        g.setColor(BLANK_BACKGROUND_COLOR);
        g.fillRect(posx,posy,gridSize,gridSize);
        // Draw the individual tiles
        for (int i = 0; i < slots.length; i++) {
            slots[i].draw(g, tiles[numbers[i]]);
        }
        // Draw the borders
        g.setColor(GRID_BACKGROUND);
        for (int i = 0; i < gridCount; i++) {
            // Draw a rectangle vertically and horizontally
            g.fillRect(posx, posy + (inside_borders+slotSize) * i, gridSize, inside_borders);
            g.fillRect(posx + (inside_borders+slotSize) * i, posy, inside_borders, gridSize);
        }
        // Draw the right side border
        g.fillRect(posx, posy+gridSize-inside_borders, gridSize, inside_borders);
        g.fillRect(posx+gridSize-inside_borders, posy, inside_borders, gridSize);
    }

}
