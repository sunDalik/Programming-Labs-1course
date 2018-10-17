import java.awt.*;

public enum Colors {
    Blue(new Color(0, 0, 255)),
    Sapphire(new Color(15, 82, 186)),
    Navy(new Color(0, 0, 128)),
    Cyan(new Color(0, 255, 255)),
    Mint(new Color(152, 255, 152)),
    Emerald(new Color(80, 200, 120));

    private final Color rgbColor;

    Colors(Color rgbColor) {
        this.rgbColor = rgbColor;
    }

    public Color getRgbColor() {
        return this.rgbColor;
    }
}
