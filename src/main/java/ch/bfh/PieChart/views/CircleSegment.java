package ch.bfh.PieChart.views;

import ch.bfh.matrix.Matrix;

import javafx.scene.shape.*;

// A circle segment to be visualized in a JavaFX-pane.
public class CircleSegment extends Path {

    boolean largeArc = false;

    // Creates a circle segment with given style class.
    // The segment is initially invisible and will become visible after a call to one
    // of the update methods.
    // The flag 'largeArc' has to be true when the segment is more than a half circle.
    public CircleSegment(String styleClass, boolean largeArc) {
        super();
        getStyleClass().add(styleClass);
        this.largeArc = largeArc;
    }

    // Updates the visual representation of the circle segment with given positions
    // of the center and the start and end points of the arc.
    public void update(
            double centerX, double centerY,
            double startX, double startY,
            double endX, double endY
    ) {
        double r = Math.sqrt(Math.pow(startX - centerX, 2.0) + Math.pow(startY - centerY, 2.0));
        getElements().clear();
        getElements().add(new MoveTo(centerX, centerY));
        getElements().add(new LineTo(startX, startY));
        getElements().add(new ArcTo(r, r, 0, endX, endY, largeArc, false));
        getElements().add(new ClosePath());
    }

    // Updates the visual representation of the circle segment with positions given
    // in a 3x2 matrix. Line 0 ist the center positions, line 1 the start of the arc
    // and line 2 the end of the arc.
    public void update(Matrix pos) {
        update (pos.get(0,0), pos.get(0,1),
                pos.get(1,0), pos.get(1,1),
                pos.get(2,0), pos.get(2,1)
        );
    }

}
