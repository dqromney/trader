package iag1.com.types;

/**
 * Direction enumerator.
 *
 * Created by dqromney on 11/6/15.
 */
public enum Direction {
    UP('^', "#555"),
    DOWN('v', "#555"),
    EVEN('|', "#555"),
    UNDEFINED(' ', "#555");

    private Character directionChar;
    private String directionRgbColor;

    Direction(char pDirectionChar, String pDirectionRgbColor) {
        this.directionChar = pDirectionChar;
        this.directionRgbColor = pDirectionRgbColor;
    }

   // -----------------------------------------------------------------
   // Access methods
   // -----------------------------------------------------------------

    public Character getDirectionChar() {
        return directionChar;
    }

    public void setDirectionChar(Character directionChar) {
        this.directionChar = directionChar;
    }

    public String getDirectionRgbColor() {
        return directionRgbColor;
    }

    public void setDirectionRgbColor(String directionRgbColor) {
        this.directionRgbColor = directionRgbColor;
    }
}
