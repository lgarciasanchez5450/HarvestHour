package Assets.fonts;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FontLoader {
    public static Font load(String path) {
        Font font;
        try {
            InputStream stream = FontLoader.class.getResourceAsStream(path);

            font = Font.createFont(Font.TRUETYPE_FONT,stream);
        } catch (IOException | FontFormatException | NullPointerException e) {
            throw new RuntimeException("Font in path:\""+path+"\"  Could not be found");
        }
        return font;

    }
}
