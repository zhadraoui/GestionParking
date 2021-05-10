module org.gi.groupe5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires mysql.connector.java;
    requires org.jetbrains.annotations;
    requires java.desktop;
    requires tess4j;

    exports org.gi.groupe5.ControllersViews;
    opens org.gi.groupe5.ControllersViews to  javafx.fxml, javafx.controls, javafx.base, javafx.graphics;

    exports org.gi.groupe5.dao;
    opens org.gi.groupe5.dao to  javafx.fxml, javafx.controls, javafx.base, javafx.graphics;

    exports org.gi.groupe5.Models;
    opens org.gi.groupe5.Models to  javafx.fxml, javafx.controls, javafx.base, javafx.graphics;

    exports org.gi.groupe5;
    opens org.gi.groupe5 to  javafx.fxml, javafx.controls, javafx.base, javafx.graphics;

    exports org.gi.groupe5.manager;
    opens org.gi.groupe5.manager to javafx.fxml, javafx.controls, javafx.base, javafx.graphics;

}
