package com.palettemaker;

import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PaletteFileManager {

    public static void savePalette(
            File file,
            List<Color> palette
    ) {

        try (PrintWriter writer =
                     new PrintWriter(file + ".pltmkr")) {

            for (Color color : palette) {

                writer.println(
                        ColorUtils.toHex(color)
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Color> loadPalette(File file) {

        List<Color> colors = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file)
                     )) {

            String line;

            while ((line = reader.readLine()) != null) {

                colors.add(
                        ColorUtils.fromHex(line)
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return colors;
    }
}