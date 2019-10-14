public class ATile {
  
  public ATileDetails details;
  public int destX, destY;
  public int startX, startY;
  
  public ATile(ATileDetails details) {
    this.details = details;
  }
  
  public boolean isEmpty() {
    return this.details.isEmpty;
  }
  
  public boolean canCombineWithOther(ATile other) {
    // If the tile is not empty ...
    if (isEmpty())
      return false; // blank tiles do not combine
    // If the tiles move to the same next number, they can be combined
    if (this.details.testEqualNext(other.details)) {
      return true;
    }
    return false;
  }
  
  public void combine() {
    // We assign the details of this tile to the next tile
    this.details = this.details.next;
  }
  
  public void setOrigin(int startX, int startY) {
    this.startX = startX;
    this.startY = startY;
  }
  
  public void move(int destX, int destY) {
    this.destX = destX;
    this.destY = destY;
  }
  
}
