package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class MapView extends JPanel implements MapViewListener{
    private MapModel mapModel;
    private MapController mapController;
    private DrawMap drawMap;

    MapView(){
        this.mapModel = new MapModel();
        this.mapModel.addMapListener(this);
        this.mapController = new MapController(this.mapModel);
        this.drawMap = new DrawMap();

        // JPanel Config
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(600, 500));
        this.add(drawMap, BorderLayout.CENTER);

        // Mouse Listener
        MouseAdapter mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MapView.this.drawMap.mouseClickHandler(e.getPoint());
            }
        };

        this.addMouseListener(mouseListener);
        this.repaint();
        this.revalidate();

    }

    MapModel getMapModel(){
        return this.mapModel;
    }

    @Override
    public void handleMapUpdate(MapEvent e) {
        MapView.this.repaint();
        MapView.this.revalidate();
    }

    private class DrawMap extends JComponent {
        AffineTransform tx;
        Double scale;

        DrawMap(){
            super();
            tx = new AffineTransform();
            scale = (double) this.getWidth() /600;
            tx.scale(scale, scale);
        }

        void mouseClickHandler(Point2D point){
            AffineTransform tx2 = new AffineTransform();
            Double scale2 = (double) this.getWidth() /600;
            tx2.scale(scale2, scale2);

            for(MapTerritory terr: MapView.this.mapModel.getTerritoryList()){
                Shape territoryShape = tx2.createTransformedShape(terr.getShape());
                //Shape territoryShape = terr.getShape();
                if(territoryShape.contains(point)){
                    MapView.this.mapController.actionPerformed(
                            new ActionEvent(MapView.this, ActionEvent.ACTION_PERFORMED, terr.getId()));
                    break;
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw Background
            graphics.setColor(Color.CYAN);
            g.fillRect(0,0, getWidth(), getHeight());

            AffineTransform tx2 = new AffineTransform();
            Double scale2 = (double) this.getWidth() /600;
            tx2.scale(scale2, scale2);


            for(MapTerritory terr: MapView.this.mapModel.getTerritoryList()){
                Shape territoryShape = tx2.createTransformedShape(terr.getShape());
                //Shape territoryShape = terr.getShape();
                if(terr == MapView.this.mapModel.getActiveTerritory()){
                    graphics.setColor(Color.RED);
                    graphics.fill(territoryShape);
                } else {
                    graphics.setColor(getContinentColor(terr.getContinent()));
                    graphics.fill(territoryShape);
                    graphics.setColor(Color.BLACK);
                    graphics.draw(territoryShape);
                }
            }
        }

        private Color getContinentColor(String continent){
            if(continent.equals("northamerica")){
                return Color.YELLOW;
            }
            if(continent.equals("southamerica")){
                return Color.MAGENTA;
            }
            if(continent.equals("europe")){
                return Color.BLUE;
            }
            if(continent.equals("africa")){
                return Color.ORANGE;
            }
            if(continent.equals("asia")){
                return Color.GREEN;
            }
            if(continent.equals("australia")){
                return Color.PINK;
            }

            return Color.WHITE;
        }
    }
}
