import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

/*
We need a few different objects

We need the background
the score display
the keyboard listener
the slots with numbers
the game logic
 */

public class UserGame {

    public static void main(String[] args) {
        new UserGame().run();
    }

    // Some global defaults
    private static final Dimension DEFAULT_SIZE = new Dimension(600,400);
    private static final int UPDATES_PER_SECOND = 20;
    private static final Color BACKGROUND_COLOR = new Color(0xB8B8B8);
    private static final int OUTSIDE_OFFSET = 40;
    private static final int INSIDE_OFFSET = 10;

    private ADisplay display;
    private GridManager gm;
    private AGameUserController controller;

    private Random random;
    private static final int SEED = 1;

    private int[] grid;
    private static final int gridCount = 4;

    public UserGame() {
        display = new ADisplay(DEFAULT_SIZE);
        display.setVisible(true);

        random = new Random(SEED);

        int[] s = getGridSize();
        gm = new GridManager(gridCount, INSIDE_OFFSET, s[0], s[1], s[2]);
        controller = new AGameUserController();

        display.addKeyListener(controller);

        setupGame();
    }

    private int[] getGridSize() {
        int[] r = new int[3];
        if (DEFAULT_SIZE.width < DEFAULT_SIZE.height) {
            r[0] = DEFAULT_SIZE.width - OUTSIDE_OFFSET;
        } else {
            r[0] = DEFAULT_SIZE.height - OUTSIDE_OFFSET;
        }
        r[1] = (DEFAULT_SIZE.width - r[0]) / 2;
        r[2] = (DEFAULT_SIZE.height - r[0]) / 2;
        return r;
    }

    // Sets up the original game state
    private void setupGame() {
        grid = new int[gridCount * gridCount];
        // Now add random tiles
        addRandomTile();
        addRandomTile();
    }

    public void handleDraw() {
        BufferStrategy b = display.canvas.getBufferStrategy();
        if (b == null) {
            display.canvas.createBufferStrategy(2);
            b = display.canvas.getBufferStrategy();
        }

        Graphics g = b.getDrawGraphics();

        draw(g);

        g.dispose();
        b.show();
    }

    private void draw(Graphics g) {
        // Draw the background
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0,0,DEFAULT_SIZE.width,DEFAULT_SIZE.height);
        gm.draw(g, grid);
    }

    private void update() {
        AGameUserController.ViableControls s = controller.getSelected();
        if (s != AGameUserController.ViableControls.NONE) {
            // We want to move in a direction
            if (s == AGameUserController.ViableControls.DOWN) {
                moveWithDirection(0, gridCount-2, new int[] {1, -1}, new int[] {0, 1});
            }

            if (s == AGameUserController.ViableControls.UP) {
                moveWithDirection(0, 1, new int[] {1, 1}, new int[] {0, -1});
            }

            if (s == AGameUserController.ViableControls.LEFT) {
                moveWithDirection(1, 0, new int[] {1, 1}, new int[] {-1, 0});
            }

            if (s == AGameUserController.ViableControls.RIGHT) {
                moveWithDirection(gridCount-2, 0, new int[] {-1, 1}, new int[] {1, 0});
            }


            // After every move we need to add a tile
            addRandomTile();
        }
    }

    private void moveWithDirection(int xstart, int ystart, int[] scanDirection, int[] direction) {
        boolean[] hascombined = new boolean[gridCount];
        for (int y = ystart; (y >= 0) && (y < gridCount); y+=scanDirection[1]) {
            for (int x = xstart; (x>=0) && (x < gridCount); x+=scanDirection[0] ) {
                int curr_pos = getSequentialSpaceFromXY(x,y);
                if (grid[curr_pos] == 0) {
                    // We don't move zeroes.
                } else {
                    // Now we need to scan all the way down vertically
                    boolean move = false;
                    int[] vec = {x, y};
                    vec = addVectors(vec, direction);
                    for ( ; (vec[0] >= 0) && (vec[0] < gridCount) && (vec[1] >= 0) && (vec[1] < gridCount);
                          vec = addVectors(vec, direction)) {
                        int testPos = getSequentialSpaceFromXY(vec[0], vec[1]);
                        if (grid[testPos] == grid[curr_pos]) {
                            // We can combine.
                            // But first, make sure we haven't already combined in this column
                            if (!hascombined[x]) {
                                grid[curr_pos] = 0;
                                grid[testPos]++;
                                hascombined[x] = true;
                            } else { // we belong in the space above
                                vec = addVectors(vec, direction, true);
                                int toPos = getSequentialSpaceFromXY(vec[0], vec[1]);
                                moveTiles(curr_pos, toPos);
                            }
                            move = true;
                            break;
                        } else if (grid[testPos] != 0) {
                            // We belong in the space above
                            vec = addVectors(vec, direction, true);
                            int toPos = getSequentialSpaceFromXY(vec[0], vec[1]);
                            moveTiles(curr_pos, toPos);
                            move = true;
                            break;
                        }
                    }
                    // If we made it here, then we need to move to the bottom
                    if (!move) {
                        vec = addVectors(vec, direction, true); // Move before the out-of-bounds
                        int target = getSequentialSpaceFromXY(vec[0], vec[1]);
                        moveTiles(curr_pos, target);
                    }
                }
            }
        }
    }

    private int[] addVectors(int[] v1, int[] v2, boolean reverse) {
        if (v1.length != v2.length) {
            return null;
        } else {
            int[] r = new int[v1.length];
            for (int i = 0; i < v1.length; i++) {
                r[i] = v1[i] + v2[i] * (reverse?-1:1);
            }
            return r;
        }
    }

    private int[] addVectors(int[] v1, int[] v2) {
        return addVectors(v1,v2,false);
    }

    private void moveTiles(int fromPos, int toPos) {
        if (fromPos == toPos)
            return;
        grid[toPos] = grid[fromPos];
        grid[fromPos] = 0;
    }

    private int getSequentialSpaceFromXY(int x, int y) {
        return x + y*gridCount;
    }

    /**
     * @return whether the game should continue
     */
    private boolean addRandomTile() {
        int zero_count = 0;
        for (int i : grid) {
            if (i==0) {
                zero_count++;
            }
        }
        if (zero_count == 0) {
            return false;
        }
        int index = random.nextInt(zero_count);
        for (int i = 0; i < grid.length; i++) {
            if (index == zero_count) {
                grid[i] = 1;
                return true;
            } else {
                zero_count--;
            }
        }
        return false;
    }

    public void run() {
        System.out.println("started");

        // Get the time since our last update
        long pastUpdateTime = System.nanoTime();
        double delayTime = updateDelayTime(UPDATES_PER_SECOND);

        while (true) {

            // If the time since out last update is greater than the time per update, ... update
            if (System.nanoTime() - pastUpdateTime > delayTime) {
                pastUpdateTime += delayTime;
                update();
            }

            handleDraw();
        }
    }

    private final double updateDelayTime(int _updates_per_second) {
        return Math.pow(10, 9) / _updates_per_second;
    }

}