package aritzh.ld27.util.console.commands;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class FormatHelper {

    public static final String BLACK = "\\0";
    public static final String DRED = "\\1";
    public static final String RED = "\\2";
    public static final String PINK = "\\3";
    public static final String TEAL = "\\4";
    public static final String DGREEN = "\\5";
    public static final String BGREEN = "\\6";
    public static final String CYAN = "\\7";
    public static final String DBLUE = "\\8";
    public static final String PURPLE = "\\9";
    public static final String BLUE = "\\a";
    public static final String BGRAY = "\\b";
    public static final String GRAY = "\\c";
    public static final String OLIVE = "\\d";
    public static final String YELLOW = "\\e";
    public static final String WHITE = "\\f";

    public static final String ITALIC = "\\i";
    public static final String BOLD = "\\n";
    public static final String UNDERLINE = "\\u";

    public static final String RESET = "\\r";

    /**
     * Formats the input string using HTML.
     * <p/>
     * The format is base on some tokens, that mark the beginning of a style, and a reset token, that resets the last style <br />
     * Note: Styles are automatically closed at the end of the string, so there is no need to spam "\r"s at the end
     * <br />
     * The format is as follows:
     * <ol>
     * <li> Colors (These override last color token)
     * <ol>
     * <li>"&#92;0" -> From now on, color is black</li>
     * <li>"&#92;1" -> From now on, color is dark red</li>
     * <li>"&#92;2" -> From now on, color is red</li>
     * <li>"&#92;3" -> From now on, color is pink</li>
     * <li>"&#92;4" -> From now on, color is teal</li>
     * <li>"&#92;5" -> From now on, color is dark green</li>
     * <li>"&#92;6" -> From now on, color is bright green</li>
     * <li>"&#92;7" -> From now on, color is cyan</li>
     * <li>"&#92;8" -> From now on, color is dark blue</li>
     * <li>"&#92;9" -> From now on, color is purple</li>
     * <li>"&#92;a" -> From now on, color is blue</li>
     * <li>"&#92;b" -> From now on, color is bright gray</li>
     * <li>"&#92;c" -> From now on, color is gray</li>
     * <li>"&#92;d" -> From now on, color is olive</li>
     * <li>"&#92;e" -> From now on, color is yellow</li>
     * <li>"&#92;f" -> From now on, color is white</li>
     * </ol>
     * </li>
     * <li> Styles (These stack)
     * <ol>
     * <li>"&#92;i" -> From now on, style is italic</li>
     * <li>"&#92;n" -> From now on, style is </li>
     * <li>"&#92;u" -> From now on, style is </li>
     * </ol>
     * </li>
     * <li> Other
     * <ol>
     * <li>"&#92;r" -> Resets last token (removes its effects)</li>
     * </ol>
     * </li>
     * </ol>
     * <br />
     * So, for example, the string <br />
     * {@code FormatHelper.format("Hello, &#92;&#92;5&#92;&#92;iworld!&#92;&#92;rHow are you?")}<br />
     * would result in<br />
     * {@code <html><font style="font-weight:normal">Hello, <font color="#008000"><font style="font-style:italic">world!</font>How are you?</font></font></html>}
     *
     * @param input The string to be formatted
     * @return The formatted string
     */
    public static String format(String input) {
        if (input == null) throw new IllegalArgumentException("Input string must not be null");
        if (!input.contains("\\")) return input;

        String ret = "<html><font style=\"font-weight:normal\">";

        char[] chars = input.toCharArray();
        int formatLevels = 0;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c != '\\') {
                if (c == '<') ret += "&lt;";
                else if (c == '>') ret += "&gt;";
                else if (c == '\n') ret += "<br />";
                else ret += c;
            } else if (i + 1 != chars.length) {
                char f = Character.toLowerCase(chars[++i]);
                if (f == 'r') {
                    ret += "</font>";
                    formatLevels--;
                    continue;
                } else formatLevels++;

                if (f == '0') ret += "<font color=\"#000000\">"; // Black
                else if (f == '1') ret += "<font color=\"#800000\">"; // Dark red
                else if (f == '2') ret += "<font color=\"#FF0000\">"; // Red
                else if (f == '3') ret += "<font color=\"#FF00FF\">"; // Pink
                else if (f == '4') ret += "<font color=\"#008080\">"; // Teal
                else if (f == '5') ret += "<font color=\"#008000\">"; // Dark Green
                else if (f == '6') ret += "<font color=\"#00FF00\">"; // Bright green
                else if (f == '7') ret += "<font color=\"#00FFFF\">"; // Cyan
                else if (f == '8') ret += "<font color=\"#000080\">"; // Dark blue
                else if (f == '9') ret += "<font color=\"#800080\">"; // Purple
                else if (f == 'a') ret += "<font color=\"#0000FF\">"; // Blue
                else if (f == 'b') ret += "<font color=\"#C0C0C0\">"; // Light gray (25%)
                else if (f == 'c') ret += "<font color=\"#808080\">"; // Gray (50%)
                else if (f == 'd') ret += "<font color=\"#808000\">"; // Dark yellow (olive)
                else if (f == 'e') ret += "<font color=\"#FFFF00\">"; // Yellow
                else if (f == 'f') ret += "<font color=\"#FFFFFF\">"; // White
                else if (f == 'i') ret += "<font style=\"font-style:italic\">"; // Italic
                else if (f == 'n') ret += "<font style=\"font-weight:bold\">"; // Bold
                else if (f == 'u') ret += "<font style=\"text-decoration:underline\">"; // Underline
            }
        }

        for (; formatLevels + 1 > 0; formatLevels--) {
            ret += "</font>";
        }

        return ret += "</html>";
    }
}
