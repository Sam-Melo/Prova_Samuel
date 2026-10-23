module com.samuel.provasamuel {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.samuel.provasamuel to javafx.fxml;
    exports com.samuel.provasamuel;
}
