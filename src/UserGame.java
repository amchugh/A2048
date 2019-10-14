import java.awt.*;
import java.awt.image.BufferStrategy;

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
  private static final Dimension DEFAULT_SIZE = new Dimension(600, 400);
  private static final int UPDATES_PER_SECOND = 20;
  private static final Color BACKGROUND_COLOR = new Color(0xB8B8B8);
  private static final int OUTSIDE_OFFSET = 40;
  private static final int INSIDE_OFFSET = 10;
  private static final float MOVEMENT_SPEED = 5f;
  
  private ADisplay display;
  private GridManager dm;
  private AGameUserController controller;
  private A2048GameManager gm;
  
  private static final int gridCount = 4;
  
  public UserGame() {
    display = new ADisplay(DEFAULT_SIZE);
    display.setVisible(true);
    
    int[] s = getGridSize();
    dm = new GridManager(gridCount, INSIDE_OFFSET, s[0], s[1], s[2]);
    controller = new AGameUserController();
    gm = new A2048GameManager(gridCount);
    
    display.addKeyListener(controller);
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
    g.fillRect(0, 0, DEFAULT_SIZE.width, DEFAULT_SIZE.height);
    // Draw the grid
    dm.draw(g, gm.getGridDetails());
  }
  
  private void update() {
    MoveDirection dir = controller.getSelected();
    if (dir != null) {
      gm.update(dir);
      if (!gm.doesValidMoveExists()) {
        // todo gameover
      }
    }
  }
  
  /**
  private int[] moveWithDirection(int[] gridToMove, MoveDirection direction) {
    return moveWithDirection(gridToMove, direction.xstart, direction.ystart, direction.scanDir, direction.moveDir);
  }
  
  private int[] moveWithDirection(int[] gridToMove, int xstart, int ystart, int[] scanDirection, int[] direction) {
    for (int y = ystart; (y >= 0) && (y < gridCount); y += scanDirection[1]) {
      for (int x = xstart; (x >= 0) && (x < gridCount); x += scanDirection[0]) {
        int curr_pos = getSequentialSpaceFromXY(x, y);
        if (gridToMove[curr_pos] == 0) {
          // We don't move zeroes.
        } else {
          // Now we need to scan all the way across
          boolean move = false;
          int[] vec = {x, y};
          vec = addVectors(vec, direction);
          for (; (vec[0] >= 0) && (vec[0] < gridCount) && (vec[1] >= 0) && (vec[1] < gridCount);
               vec = addVectors(vec, direction)) {
            int testPos = getSequentialSpaceFromXY(vec[0], vec[1]);
            if (gridToMove[testPos] == gridToMove[curr_pos]) {
              // We can combine.
              // But first, make sure we haven't already combined in this column
              gridToMove[curr_pos] = 0;
              gridToMove[testPos]++;
              move = true;
              break;
            } else if (gridToMove[testPos] != 0) {
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
    return gridToMove;
  }
  
  
  private int[] addVectors(int[] v1, int[] v2, boolean reverse) {
    if (v1.length != v2.length) {
      return null;
    } else {
      int[] r = new int[v1.length];
      for (int i = 0; i < v1.length; i++) {
        r[i] = v1[i] + v2[i] * (reverse ? -1 : 1);
      }
      return r;
    }
  }
  
  private int[] addVectors(int[] v1, int[] v2) {
    return addVectors(v1, v2, false);
  }
  
  private void moveTiles(int fromPos, int toPos) {
    if (fromPos == toPos)
      return;
    grid[toPos] = grid[fromPos];
    grid[fromPos] = 0;
  }
  
  private int getSequentialSpaceFromXY(int x, int y) {
    return x + y * gridCount;
  }
  
  private void addRandomTile() {
    int zero_count = 0;
    for (int i : grid) {
      if (i == 0) {
        zero_count++;
      }
    }
    if (zero_count == 0) {
      return;
    }
    int index = random.nextInt(zero_count + 1); // is exclusive top cap
    for (int i = 0; i < grid.length; i++) {
      if (grid[i] == 0) {
        if (index == zero_count) {
          grid[i] = 1;
          return;
        } else {
          zero_count--;
        }
      }
    }
  }
   */
   
  public void run() {
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