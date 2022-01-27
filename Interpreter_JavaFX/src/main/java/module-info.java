module com.example.interpreter_javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.interpreter_javafx to javafx.fxml;
    exports com.example.interpreter_javafx;
}