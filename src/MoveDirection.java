public class MoveDirection {
  
  public int xstart, ystart;
  public int[] scanDir, moveDir;
  
  public MoveDirection(int x, int y, int[] s, int[] m) {
    xstart = x;
    ystart = y;
    scanDir = s;
    moveDir = m;
  }
  
  public static MoveDirection LEFT_MOVE = new MoveDirection(1, 0,
    new int[]{1, 1},  new int[]{-1,0} );
  public static MoveDirection RIGHT_MOVE = new MoveDirection(-2, 0,
    new int[]{-1,1},  new int[]{1,0}  );
  public static MoveDirection UP_MOVE = new MoveDirection(0, 1,
    new int[]{1,1},  new int[]{0,-1}   );
  public static MoveDirection DOWN_MOVE = new MoveDirection(0, -2,
    new int[]{1,-1},  new int[]{0,1} );
  
}

