package ch.bfh.PieChart.controllers;

import ch.bfh.PieChart.models.PieChartCalcu;
import ch.bfh.PieChart.views.CircleSegment;
import ch.bfh.matrix.Matrix;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class PieChartController {
    private final double PIEBIG = 2400.0;


    @FXML Pane pane;

    double h;
    double w;
    double value = 500;

    boolean clicked = false;

    @FXML public void initialize() {

        // TODO: read pie chart data and create its visual representation
        String som = "someclass";
        ArrayList<CircleSegment> circleSegments = new ArrayList<>();
        ArrayList<Matrix> allPoints = new ArrayList<>();

        PieChartCalcu pieChartCalcu = new PieChartCalcu();
        Group group = new Group();

        for(int i = 1; i <= 7; i ++){
            Matrix pos = pieChartCalcu.getPointMatrix(i);
            allPoints.add(pos);
        }

        for(Matrix m : allPoints){
            double mass = 500/PIEBIG;
            Matrix mm = m.multiply(mass);
            CircleSegment segment = new CircleSegment("slice0"+String.valueOf(allPoints.indexOf(m)),false);
            segment.update(mm);
            group.getChildren().add(segment);
            circleSegments.add(segment);
        }


        pane.getChildren().addAll(group);


        Matrix transfo1 = new Matrix(new double[][]{
                {25,150},
                {25,150},
                {25,150}
        });
        Matrix transfo2 = new Matrix(new double[][]{
                {150,55},
                {150,55},
                {150,55}
        });

        Matrix transfo3 = new Matrix(new double[][]{
                {25,-150},
                {25,-150},
                {25,-150}
        });

        Matrix transfo4 = new Matrix(new double[][]{
                {-60,-150},
                {-60,-150},
                {-60,-150}
        });

        Matrix transfo5 = new Matrix(new double[][]{
                {-150,-130},
                {-150,-130},
                {-150,-130}
        });

        Matrix transfo6 = new Matrix(new double[][]{
                {-150,35},
                {-150,35},
                {-150,35}
        });

        Matrix transfo7 = new Matrix(new double[][]{
                {-40,150},
                {-40,150},
                {-40,150}
        });


        circleSegments.get(0).setOnMouseClicked(event -> {
            clicked = clicked ? false:true;
            Matrix matrix = clicked ? allPoints.get(0).multiply(value/2400).add(transfo1.multiply(value/2400)) : allPoints.get(0).multiply(value/2400);
            circleSegments.get(0).update(matrix);
        });

        circleSegments.get(1).setOnMouseClicked(event -> {
            clicked = clicked ? false:true;
            Matrix matrix = clicked ? allPoints.get(1).multiply(value/2400).add(transfo2.multiply(value/2400)) : allPoints.get(1).multiply(value/2400);
            circleSegments.get(1).update(matrix);

        });

        circleSegments.get(2).setOnMouseClicked(event -> {
            clicked = clicked ? false:true;
            Matrix matrix = clicked ? allPoints.get(2).multiply(value/2400).add(transfo3.multiply(value/2400)) : allPoints.get(2).multiply(value/2400);
            circleSegments.get(2).update(matrix);

        });

        circleSegments.get(3).setOnMouseClicked(event -> {
            clicked = clicked ? false:true;
            Matrix matrix = clicked ? allPoints.get(3).multiply(value/2400).add(transfo4.multiply(value/2400)) : allPoints.get(3).multiply(value/2400);
            circleSegments.get(3).update(matrix);
        });

        circleSegments.get(4).setOnMouseClicked(event -> {
            clicked = clicked ? false:true;
            Matrix matrix = clicked ? allPoints.get(4).multiply(value/2400).add(transfo5.multiply(value/2400)) : allPoints.get(4).multiply(value/2400);
            circleSegments.get(4).update(matrix);
        });

        circleSegments.get(5).setOnMouseClicked(event -> {
            clicked = clicked ? false:true;
            Matrix matrix = clicked ? allPoints.get(5).multiply(value/2400).add(transfo6.multiply(value/2400)) : allPoints.get(5).multiply(value/2400);
            circleSegments.get(5).update(matrix);
        });

        circleSegments.get(6).setOnMouseClicked(event -> {
            clicked = clicked ? false:true;
            Matrix matrix = clicked ? allPoints.get(6).multiply(value/2400).add(transfo7.multiply(value/2400)) : allPoints.get(6).multiply(value/2400);
            circleSegments.get(6).update(matrix);
        });


        ChangeListener<Number> paneSizeListener = (observable, oldValue, newValue) -> {
            // this listener is called when the size of the window changes
            // TODO: get the size of the pane and update the visual representation of the pie chart
            double h = pane.getHeight();
            double w = pane.getWidth();
            this.h = h;
            this.w = w;


            circleSegments.get(0).update(allPoints.get(0).multiply((double)newValue/PIEBIG));
            circleSegments.get(1).update(allPoints.get(1).multiply((double)newValue/PIEBIG));
            circleSegments.get(2).update(allPoints.get(2).multiply((double)newValue/PIEBIG));
            circleSegments.get(3).update(allPoints.get(3).multiply((double)newValue/PIEBIG));
            circleSegments.get(4).update(allPoints.get(4).multiply((double)newValue/PIEBIG));
            circleSegments.get(5).update(allPoints.get(5).multiply((double)newValue/PIEBIG));
            circleSegments.get(6).update(allPoints.get(6).multiply((double)newValue/PIEBIG));

            this.value = (double) newValue;

        };

        pane.widthProperty().addListener(paneSizeListener);
        pane.heightProperty().addListener(paneSizeListener);
    }

}
