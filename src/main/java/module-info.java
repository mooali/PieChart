module ch.bfh.PieChart {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires kotlin.stdlib;

    opens ch.bfh.PieChart.views to javafx.fxml;
    opens ch.bfh.PieChart.controllers to javafx.fxml;

    exports ch.bfh.PieChart;
}