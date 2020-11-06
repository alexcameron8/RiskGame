package Main;

import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;

import java.awt.*;
import java.awt.geom.Point2D;

public class MapTerritory {
    private static PathParser pathParser = new PathParser();
    private static AWTPathProducer pathProducer = new AWTPathProducer();

    private String name;
    private String id;
    private Shape territoryShape;
    private String continent;

    MapTerritory(String name, String id, String continent, String path){
        this.name = name;
        this.id = id;
        this.continent = continent;

        // Shape generation
        pathParser.setPathHandler(pathProducer);
        pathParser.parse(path);
        this.territoryShape = pathProducer.getShape();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Shape getShape() {
        return territoryShape;
    }

    public String getContinent(){
        return this.continent;
    }

    public boolean contains(Point2D point){
        if(this.territoryShape.contains(point)){
            return true;
        } else{
            return false;
        }
    }


}
