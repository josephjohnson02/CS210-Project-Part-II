package com.palettemaker;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ColorUtils {

    public static List<Color> generateScheme(
            Color base,
            String scheme
    ) {

        List<Color> colors = new ArrayList<>();

        double hue = base.getHue();

        switch (scheme) {

            case "Complementary":

                colors.add(base);

                colors.add(
                        Color.hsb(
                                (hue + 180) % 360,
                                base.getSaturation(),
                                base.getBrightness()
                        )
                );

                break;

            case "Analogous":

                colors.add(base);

                colors.add(
                        Color.hsb(
                                (hue + 30) % 360,
                                base.getSaturation(),
                                base.getBrightness()
                        )
                );

                colors.add(
                        Color.hsb(
                                (hue - 30 + 360) % 360,
                                base.getSaturation(),
                                base.getBrightness()
                        )
                );

                break;

            case "Triadic":

                colors.add(base);

                colors.add(
                        Color.hsb(
                                (hue + 120) % 360,
                                base.getSaturation(),
                                base.getBrightness()
                        )
                );

                colors.add(
                        Color.hsb(
                                (hue + 240) % 360,
                                base.getSaturation(),
                                base.getBrightness()
                        )
                );

                break;
        }

        return colors;
    }

    public static String toHex(Color color) {

        return String.format(
                "#%02X%02X%02X",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255)
        );
    }

    public static Color fromHex(String hex) {
        return Color.web(hex);
    }
}