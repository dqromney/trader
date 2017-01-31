package iag1.com.types;

/**
 * Direction enumerator.
 *
 * Created by dqromney on 11/6/15.
 */
public enum Direction {
    UP('↑', "&uarr;", "#555"),
    DOWN('↓', "&darr;", "#555"),
    EVEN('|', "|", "#555"),
    UNDEFINED(' ', " ", "#555");

    private Character directionChar;
    private String escapedHtml;
    private String directionRgbColor;

    Direction(Character directionChar, String escapedHtml, String directionRgbColor) {
        this.directionChar = directionChar;
        this.escapedHtml = escapedHtml;
        this.directionRgbColor = directionRgbColor;
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

    public String getEscapedHtml() {
        return escapedHtml;
    }

    public void setEscapedHtml(String escapedHtml) {
        this.escapedHtml = escapedHtml;
    }

    public String getDirectionRgbColor() {
        return directionRgbColor;
    }

    public void setDirectionRgbColor(String directionRgbColor) {
        this.directionRgbColor = directionRgbColor;
    }
}
