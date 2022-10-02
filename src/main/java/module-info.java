module com.example.projres {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;



    opens com.example.projres to javafx.fxml;
    exports com.example.projres;
}