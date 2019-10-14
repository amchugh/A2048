import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class A2048GameManager {
  
  private int gameSize;
  private ATile[] grid;
  private Random random;
  private static final int SEED = 1;
  
  private static ATileDetails[] tileDetails;
  private static Color[] tileColors = {
    new Color(0xF4F4D7),
    new Color(0xEEF4A9),
    new Color(0xEFF48A),
    new Color(0xF4E522),
    new Color(0xF4C71E),
    new Color(0xF4A414),
    new Color(0xF4740E),
    new Color(0xF4370D),
    new Color(0xF40C9B),
    new Color(0xA410F4),
    new Color(0x0A55F4),
    new Color(0x0CB4F4),
    new Color(0x16F4C7),
  };
  
  public A2048GameManager(int gameSize) {
    this.random = new Random(SEED);
    this.gameSize = gameSize;
    initializeTileDetails();
    initializeGrid();
  }
  
  private void initializeTileDetails() {
    // Create the tiles used to play the game
    tileDetails = new ATileDetails[tileColors.length + 1];
    tileDetails[0] = new ATileDetails(); // create the blank tile
    for (int i = tileColors.length - 2; i >= 0; i-- ) {
      tileDetails[i + 1] = new ATileDetails(String.valueOf((int)Math.pow(2, i+1)), tileColors[i], tileDetails[i+2]);
    }
    tileDetails[tileDetails.length - 1] = new ATileDetails("Max Value",tileColors[tileColors.length-1]);
  }
  
  private void initializeGrid() {
    grid = new ATile[gameSize*gameSize];
    for (int i = 0; i < grid.length; i++) {
      grid[i] = new ATile(tileDetails[0]); // Create blank tiles
    }
    addRandomTile();
    addRandomTile();
  }
  
  public void update(MoveDirection direction) {
    if (moveWithDirection(direction)) {
      addRandomTile();
    }
  }
  
  private void addRandomTile() {
    ArrayList<ATile> zeroes = new ArrayList<>();
    for (ATile t : grid) {
      if (t.isEmpty()) {
        zeroes.add(t);
      }
    }
    if (zeroes.size()==0) {
      System.out.println("Failed to place");
      return;
    }
    int index = random.nextInt(zeroes.size());
    zeroes.get(index).details = tileDetails[1];
  }
  
  private boolean moveWithDirection(MoveDirection direction) {
    int xstart, ystart;
    if (direction.xstart < 0) {
      xstart = gameSize + direction.xstart;
    } else {
      xstart = direction.xstart;
    }
    if (direction.ystart < 0) {
      ystart = gameSize + direction.ystart;
    } else {
      ystart = direction.ystart;
    }
    boolean r = false;
    int dir = 1;
    if (direction.moveDir[0] == 0) {
      r = true;
      dir = direction.moveDir[1];
    } else {
      dir = direction.moveDir[0];
    }
    return moveWithDirection(xstart, ystart, direction.scanDir, dir, r);
  }
  
  private boolean moveWithDirection(int xstart, int ystart, int[] scanDirection, int moveDirection, boolean isVertical) {
    boolean hasMoved = false;
    for (int y = ystart; (y >= 0) && (y < gameSize); y += scanDirection[1]) {   // iterating through all places in bounds
      for (int x = xstart; (x >= 0) && (x < gameSize); x += scanDirection[0]) { // iterating through all places in bounds
        int curr_index = getIndex(x,y); // This is the tile index we are testing.
        ATile tile = grid[curr_index];  // This is the tile object
        // Test to make sure it's not an empty tile
        if (!tile.isEmpty()) {
          // Otherwise, we're going to move it until it combines.
          if (isVertical) {
            tile.setOrigin(x, y);
            // We are going to edit the Y value
            for (int targety = y + moveDirection; targety >= 0 && targety < gameSize; targety += moveDirection) {
              // Lets look at the new tile.
              int test_index = getIndex(x, targety);
              ATile test_tile = grid[test_index];
              // If it's empty, we'll move there.
              if (test_tile.isEmpty()) {
                swap(curr_index, test_index);
                curr_index = test_index;
                hasMoved = true;
              } else {
                // If it's not empty, we're done.
                // But first, see if we should combine.
                if (test_tile.canCombineWithOther(tile)) {
                  test_tile.combine();
                  erase(curr_index);
                  hasMoved = true;
                }
                break;
              }
            }
          } else {
            // We are going to edit the X value
            // We are going to edit the Y value
            for (int targetx = x + moveDirection; targetx >= 0 && targetx < gameSize; targetx += moveDirection) {
              // Lets look at the new tile.
              int test_index = getIndex(targetx, y);
              ATile test_tile = grid[test_index];
              if (test_tile.isEmpty()) {
                swap(curr_index, test_index);
                curr_index = test_index;
                hasMoved = true;
              } else {
                // If it's not empty, we're done.
                // But first, see if we should combine.
                if (test_tile.canCombineWithOther(tile)) {
                  test_tile.combine();
                  erase(curr_index);
                  hasMoved = true;
                }
                break;
              }
            }
          }
        }
        // Otherwise, we do nothing.
      }
    }
    return hasMoved;
  }
  
  private void swap(int fromIndex, int toIndex) {
    ATile temp = grid[toIndex];
    grid[toIndex] = grid[fromIndex];
    grid[fromIndex] = temp;
  }
  
  private void erase(int index) {
    grid[index] = new ATile(tileDetails[0]);
  }
  
  public int getIndex(int x, int y) {
    return x + y * gameSize;
  }
  
  public boolean doesValidMoveExists() {
    // todo implement
    return true;
  }
  
  public ATile[] getGridDetails() {
    return grid;
  }
  
}
