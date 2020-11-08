package Main;

import Map.Territory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Map View component. Displays the selectable game map to the user.
 *
 * @author Benjamin Munro
 */
public class MapView extends JPanel implements MapViewListener{
    private MapModel mapModel;
    private MapController mapController;
    private DrawMap drawMap;

    /**
     * Create a new MapView
     * @param riskModel Game model containing map to represent.
     */
    MapView(RiskModel riskModel){
        this.mapModel = new MapModel(riskModel);
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

    /**
     * Get the model of the game map.
     * @return Model of the game map.
     */
    MapModel getMapModel(){
        return this.mapModel;
    }

    /**
     * Handle events from MapModel
     * @param e MapModel event.
     */
    @Override
    public void handleMapUpdate(MapEvent e) {
        MapView.this.repaint();
        MapView.this.revalidate();
    }

    /**
     * Map component
     */
    private class DrawMap extends JComponent {
        private static final int STANDARD_MAP_WIDTH = 600;
        AffineTransform tx;
        Double scale;

        /**
         * Create new DrawMap
         */
        DrawMap(){
            super();
            tx = new AffineTransform();
            scale = (double) this.getWidth() /600;
            tx.scale(scale, scale);
        }

        /**
         * Handle mouse click events on the map.
         * Creates a new action containing the ID of the territory clicked.
         * @param point
         */
        void mouseClickHandler(Point2D point){
            AffineTransform tx2 = new AffineTransform();
            Double scale2 = (double) this.getWidth() / STANDARD_MAP_WIDTH;
            tx2.scale(scale2, scale2);

            for(Territory terr: MapView.this.mapModel.getTerritoryList()){
                Shape territoryShape = tx2.createTransformedShape(terr.getShape());
                //Shape territoryShape = terr.getShape();
                if(territoryShape.contains(point)){
                    MapView.this.mapController.actionPerformed(
                            new ActionEvent(MapView.this, ActionEvent.ACTION_PERFORMED, terr.getId()));
                    break;
                }
            }
        }

        /**
         * Override DrawMap paintComponent method to draw map from shapes.
         * @param g Graphics object of component
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw Background
            graphics.setColor(MapView.this.mapModel.getBackgroundColor());
            graphics.fillRect(0,0, getWidth(), getHeight());

            AffineTransform tx2 = new AffineTransform();
            Double scale2 = (double) this.getWidth() /600;
            tx2.scale(scale2, scale2);


            // Draw water crossing lines
            graphics.setColor(Color.BLACK);
            graphics.setStroke(new BasicStroke(2));
            for(Shape waterCrossing: MapView.this.getMapModel().getWaterCrossings()){
                Shape waterCrossingScaledShape = tx2.createTransformedShape(waterCrossing);
                graphics.draw(waterCrossingScaledShape);
            }

            // Draw territories
            graphics.setStroke(new BasicStroke(1));
            for(Territory terr: MapView.this.mapModel.getTerritoryList()){
                Shape territoryShape = tx2.createTransformedShape(terr.getShape());
                if(terr == MapView.this.mapModel.getActiveTerritory()){
                    graphics.setColor(Color.RED);
                    graphics.fill(territoryShape);
                } else {
                    Color terrColor = new Color(
                            terr.getContinent().getColor().get(0),
                            terr.getContinent().getColor().get(1),
                            terr.getContinent().getColor().get(2));
                    graphics.setColor(terrColor);
                    graphics.fill(territoryShape);
                    graphics.setColor(Color.BLACK);
                    graphics.draw(territoryShape);
                }
            }

            // Draw territory names
            for(Territory terr: MapView.this.mapModel.getTerritoryList()){
                Shape territoryShape = tx2.createTransformedShape(terr.getShape());
                graphics.setFont(new Font("default", Font.BOLD, 14));
                FontMetrics fontMetrics = graphics.getFontMetrics();
                String[] nameArr = terr.getName().split(" ");
                graphics.setColor(Color.BLACK);

                for(int i = 0; i < nameArr.length; i++){
                    float centerX = (float) territoryShape.getBounds2D().getCenterX() - fontMetrics.stringWidth(nameArr[i]) / 2;
                    float centerY = (float) territoryShape.getBounds2D().getCenterY() - fontMetrics.getHeight() + fontMetrics.getAscent() + i*fontMetrics.getAscent();
                    graphics.drawString(nameArr[i], centerX, centerY);
                }

            }
        }
    }
}
