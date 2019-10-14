public class AMovementDisplayTile {
  
  public ATile dipslayTile;
  public boolean isMoving, isMovingVertically;
  public int distanceToMove;
  
  public AMovementDisplayTile(ATile display, boolean isMoving, boolean isMovingVertically, int distance) {
    this.dipslayTile = display;
    this.isMoving = isMoving;
    this.isMovingVertically = isMovingVertically;
    this.distanceToMove = distance;
  }
  
}
