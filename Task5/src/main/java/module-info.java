module dev.roundrobin.inc.studentmanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.roundrobin.inc.studentmanagement to javafx.fxml;
    exports dev.roundrobin.inc.studentmanagement;
}