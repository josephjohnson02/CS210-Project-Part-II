package com.palettemaker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PaletteController {

    @FXML
    private Button complementaryBtn;

    @FXML
    private Button analogousBtn;

    @FXML
    private Button triadicBtn;

    private String selectedScheme;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ComboBox<String> schemeBox;

    @FXML
    private FlowPane suggestionPane;

    @FXML
    private FlowPane palettePane;

    private final List<Color> palette = new ArrayList<>();

    private boolean paletteContains(Color color) {

        String hex = ColorUtils.toHex(color);

        for (Color c : palette) {

            if (ColorUtils.toHex(c).equals(hex)) {
                return true;
            }
        }

        return false;
    }

    @FXML
    public void initialize() {

        selectedScheme = "Complementary";

        colorPicker.setOnAction(e -> {

            if (palette.isEmpty()) {

                palette.clear();
                palette.add(colorPicker.getValue());

                refreshPalette();
                generateSuggestions();
            }
        });

        setSchemeGraphic(
                complementaryBtn,
                "/com/palettemaker/images/complementary.png"
        );

        setSchemeGraphic(
                analogousBtn,
                "/com/palettemaker/images/analogous.png"
        );

        setSchemeGraphic(
                triadicBtn,
                "/com/palettemaker/images/triadic.png"
        );

        highlightSelectedButton(complementaryBtn);
    }

    private void setSchemeGraphic(
            Button button,
            String path
    ) {

        ImageView image = new ImageView(
                new Image(getClass().getResourceAsStream(path))
        );

        image.setFitWidth(105);
        image.setFitHeight(105);

        button.setGraphic(image);
    }

    private void highlightSelectedButton(Button selected) {

        complementaryBtn.getStyleClass().remove("selected-scheme");
        analogousBtn.getStyleClass().remove("selected-scheme");
        triadicBtn.getStyleClass().remove("selected-scheme");

        selected.getStyleClass().add("selected-scheme");
    }

    @FXML
    private void selectComplementary() {

        selectedScheme = "Complementary";

        highlightSelectedButton(complementaryBtn);

        generateSuggestions();
    }

    @FXML
    private void selectAnalogous() {

        selectedScheme = "Analogous";

        highlightSelectedButton(analogousBtn);

        generateSuggestions();
    }

    @FXML
    private void selectTriadic() {

        selectedScheme = "Triadic";

        highlightSelectedButton(triadicBtn);

        generateSuggestions();
    }

    @FXML
    private void generateSuggestions() {

        suggestionPane.getChildren().clear();

        List<Color> suggestions =
                new ArrayList<>();

        for (Color base : palette) {

            suggestions.addAll(
                    ColorUtils.generateScheme(
                            base,
                            selectedScheme
                    )
            );
        }

        for (Color color : suggestions) {

            if (!paletteContains(color)) {

                suggestionPane.getChildren().add(
                        createColorBox(color, true)
                );
            }
        }

        colorPicker.setDisable(!palette.isEmpty());
    }

    private VBox createColorBox(Color color, boolean suggestion) {

        Rectangle rect = new Rectangle(120, 120);
        rect.setArcWidth(15);
        rect.setArcHeight(15);
        rect.setFill(color);

        String hex = ColorUtils.toHex(color);

        Label label = new Label(hex);

        Button actionButton =
                new Button(suggestion ? "Add" : "Remove");

        Button copyButton =
                new Button("Copy");

        actionButton.setOnAction(e -> {

            if (suggestion) {

                if (!paletteContains(color)) {
                    palette.add(color);
                }

            } else {

                palette.remove(color);
            }

            refreshPalette();
            generateSuggestions();
        });

        copyButton.setOnAction(e ->
                copyToClipboard(hex)
        );

        HBox buttons =
                new HBox(10, actionButton, copyButton);

        buttons.setAlignment(Pos.CENTER);

        VBox box =
                new VBox(10, rect, label, buttons);

        box.setAlignment(Pos.CENTER);

        return box;
    }

    private void refreshPalette() {

        palettePane.getChildren().clear();

        for (Color color : palette) {

            palettePane.getChildren().add(
                    createColorBox(color, false)
            );
        }

        if (palette.isEmpty()) {

            colorPicker.setDisable(false);

            suggestionPane.getChildren().clear();
        }
    }

    private void copyToClipboard(String text) {

        Clipboard clipboard = Clipboard.getSystemClipboard();

        ClipboardContent content =
                new ClipboardContent();

        content.putString(text);

        clipboard.setContent(content);
    }

    @FXML
    private void savePalette() {

        FileChooser chooser = new FileChooser();

        chooser.setTitle("Save Palette");

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Palette Maker Files",
                        "*.pltmkr"
                )
        );

        File file = chooser.showSaveDialog(null);

        if (file != null) {

            if (!file.getName().endsWith(".pltmkr")) {

                file = new File(
                        file.getAbsolutePath() + ".pltmkr"
                );
            }

            PaletteFileManager.savePalette(file, palette);
        }
    }

    @FXML
    private void loadPalette() {

        FileChooser chooser = new FileChooser();

        chooser.setTitle("Load Palette");

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Palette Maker Files",
                        "*.pltmkr"
                )
        );

        File file = chooser.showOpenDialog(null);

        if (file != null) {

            palette.clear();

            palette.addAll(
                    PaletteFileManager.loadPalette(file)
            );

            refreshPalette();
            generateSuggestions();
        }
    }
}