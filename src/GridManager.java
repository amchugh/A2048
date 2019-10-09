import java.awt.*;

public class GridManager {

    private GridSlot[] slots;

    private int posx,posy,gridSize;

    private static final Color GRID_BACKGROUND = new Color(0x8A8A8F);

    public GridManager(int gridCount, int inside_borders, int gridSize, int upperx, int uppery) {
        posx = upperx;
        posy = uppery;
        this.gridSize = gridSize;
        slots = new GridSlot[gridCount*gridCount];
        int slotSize = (gridSize - (inside_borders))/(gridCount)-inside_borders;
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

    public void draw(Graphics g, int[] numbers) {
        if (numbers.length != slots.length) {
            System.out.println("Not enough numbers");
            System.exit(-1);
            return;
        }
        g.setColor(GRID_BACKGROUND);
        g.fillRect(posx,posy,gridSize,gridSize);
        for (int i = 0; i < slots.length; i++) {
            slots[i].draw(g, numbers[i]);
        }
    }

}
