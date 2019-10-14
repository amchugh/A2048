import java.awt.*;

public class ATileDetails {
  
  public String displayValue;
  public Color backgroundColor;
  public boolean isEmpty;
  public ATileDetails next;
  
  public ATileDetails(String name, Color color) {
    backgroundColor = color;
    displayValue = name;
    isEmpty = false;
    next = null;
  }
  
  public ATileDetails(String name, Color color, ATileDetails nextTile) {
    backgroundColor = color;
    displayValue = name;
    isEmpty = false;
    next = nextTile;
  }
  
  public ATileDetails() {
    backgroundColor = null;
    displayValue = null;
    isEmpty = true;
    next = null;
  }
  
  @Override
  public boolean equals(Object obj) {
    // Test to make sure they're not the same object
    if (this == obj)
      return true;
    // Test to make sure they're of the same class
    if(obj == null || obj.getClass()!= this.getClass())
      return false;
    // Cast to type and compare members
    ATileDetails o = (ATileDetails)obj;
    if (o.isEmpty == this.isEmpty && o.displayValue == this.displayValue && o.backgroundColor == this.backgroundColor)
      return true;
    // Else, they're not the same
    return false;
  }
  
  public boolean testEqualNext(ATileDetails other) {
    if (other.next == null || this.next == null)
      return false;
    if (other.next == this.next)
      return true;
    return false;
  }
  
}
