module com.palettemaker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.palettemaker to javafx.fxml;
    exports com.palettemaker;
}