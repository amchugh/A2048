import java.awt.*;

public class ATileDetails {
  
  public String displayValue;
  public Color backgroundColor;
  public boolean doDisplay;
  
  public ATileDetails(String name, Color color) {
    backgroundColor = color;
    displayValue = name;
    doDisplay = true;
  }
  
  public ATileDetails() {
    backgroundColor = null;
    displayValue = null;
    doDisplay = false;
  }
  
}
