module com.example.unmess {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.feather;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.unmess to javafx.fxml;
    exports com.example.unmess;
    exports com.example.unmess.core;
    exports com.example.unmess.engine;
    exports com.example.unmess.model;
    exports com.example.unmess.ui;
}