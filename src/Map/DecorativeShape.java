package Map;

import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;

import java.awt.*;
import java.util.ArrayList;

public class DecorativeShape {
    private static PathParser pathParser = new PathParser();
    private static AWTPathProducer pathProducer = new AWTPathProducer();
    private Shape decorativeShape;
    private ArrayList<Integer> color = new ArrayList<>(3);

    DecorativeShape(String pathData, ArrayList<Integer> color) {
        pathParser.setPathHandler(pathProducer);
        pathParser.parse(pathData);
        this.decorativeShape = pathProducer.getShape();
        this.color = color;
    }

    public Shape getShape() {
        return decorativeShape;
    }

    public ArrayList<Integer> getColor() {
        return color;
    }
}
