package Main.Map;

import Main.RiskModel;
import Map.DecorativeShape;
import Map.Territory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Map View component. Displays the selectable game map to the user.
 *
 * @author Benjamin Munro
 */
public class MapView extends JPanel implements MapViewListener{

    public static final String TERRITORY_NAME_TOGGLE_ACTION = "territoryName";
    public static final String TROOP_DOTS_TOGGLE_ACTION = "troopDots";
    public static final String TROOP_COUNT_TOGGLE_ACTION = "troopCount";
    public static final String PLAYER_TERRITORY_COLOUR_TOGGLE_ACTION = "playerTerritoryColor";

    private MapModel mapModel;
    private MapController mapController;
    private DrawMap drawMap;

    /**
     * Create a new MapView
     * @param riskModel Game model containing map to represent.
     */
    public MapView(RiskModel riskModel){
        this.mapModel = new MapModel(riskModel);
        this.mapModel.addMapListener(this);
        this.mapController = new MapController(this.mapModel);
        this.drawMap = new DrawMap();

        // JPanel Config
        this.setLayout(new BorderLayout());
        this.add(drawMap, BorderLayout.CENTER);
        this.add(new ConfigBar(), BorderLayout.PAGE_END);

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
    public MapModel getMapModel(){
        return this.mapModel;
    }

    /**
     * Handle events from MapModel
     * @param e MapModel event.
     */
    @Override
    public void handleMapUpdate(MapEvent e) {
        if(e instanceof MapTerritoryEvent || e instanceof MapRedrawEvent){
            MapView.this.repaint();
            MapView.this.revalidate();
        }
    }

    private class ConfigBar extends JPanel {
        ConfigBar(){
            this.setLayout(new FlowLayout());

            JCheckBox territoryNames = new JCheckBox("Show Territory Names");
            territoryNames.setActionCommand(TERRITORY_NAME_TOGGLE_ACTION);
            territoryNames.addItemListener(MapView.this.mapController);

            JCheckBox troopDots = new JCheckBox("Show Troop Dots");
            troopDots.setActionCommand(TROOP_DOTS_TOGGLE_ACTION);
            troopDots.addItemListener(MapView.this.mapController);

            JCheckBox troopCount = new JCheckBox("Show Troop Count");
            troopCount.setActionCommand(TROOP_COUNT_TOGGLE_ACTION);
            troopCount.addItemListener(MapView.this.mapController);
            troopCount.setSelected(true);

            JCheckBox playerTerritoryColor = new JCheckBox("Player Territory Color");
            playerTerritoryColor.setActionCommand(PLAYER_TERRITORY_COLOUR_TOGGLE_ACTION);
            playerTerritoryColor.addItemListener(MapView.this.mapController);
            playerTerritoryColor.setSelected(true);


            this.add(territoryNames);
            this.add(troopDots);
            this.add(troopCount);
            this.add(playerTerritoryColor);
        }
    }

    /**
     * Map component
     */
    private class DrawMap extends JComponent {
        private static final int STANDARD_MAP_WIDTH = 600;
        private static final int STANDARD_MAP_HEIGHT = 400;
        private static final int SOLDIER_SIZE = 7;
        private static final double ZOOM = 1.0;

        /**
         * Create new DrawMap
         */
        DrawMap(){
            super();
        }

        double calculateScale(){
            return Math.min((this.getWidth() / (double) STANDARD_MAP_WIDTH) * ZOOM, (this.getHeight() / (double) STANDARD_MAP_HEIGHT) * ZOOM);
        }

        Color complementaryColor(Color source){
            return new Color(255-source.getRed(), 255-source.getGreen(), 255-source.getBlue());
        }

        int calculateXTransform(){
            return (this.getWidth() - (int) (STANDARD_MAP_WIDTH * calculateScale())) / 2;
        }

        int calculateYTransform(){
            return (this.getHeight() - (int) (STANDARD_MAP_HEIGHT * calculateScale())) / 2;
        }

        /**
         * Handle mouse click events on the map.
         * Creates a new action containing the ID of the territory clicked.
         * @param point
         */
        void mouseClickHandler(Point2D point){
            AffineTransform tx2 = new AffineTransform();
            Double scale = calculateScale();
            tx2.translate(calculateXTransform(), calculateYTransform());
            tx2.scale(scale, scale);

            for(Territory terr: MapView.this.mapModel.getTerritoryList()){
                Shape territoryShape = tx2.createTransformedShape(terr.getShape());
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
            Double scale = calculateScale();
            tx2.translate(calculateXTransform(), calculateYTransform());
            tx2.scale(scale, scale);

            // Draw decorative shapes
            for(DecorativeShape decorativeShape: MapView.this.getMapModel().getDecorativeShapes()){
                graphics.setColor(new Color(
                        decorativeShape.getColor().get(0),
                        decorativeShape.getColor().get(1),
                        decorativeShape.getColor().get(2)
                ));
                Shape transformedShape = tx2.createTransformedShape(decorativeShape.getShape());
                graphics.fill(transformedShape);
            }


            // Draw water crossing lines
            graphics.setColor(complementaryColor(MapView.this.mapModel.getBackgroundColor()));
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
                    Color terrColor;
                    if(MapView.this.getMapModel().isPlayerTerritoryColorVisible()){
                        terrColor = terr.getOwner().getPlayerColor();
                    } else {
                        terrColor = new Color(
                                terr.getContinent().getColor().get(0),
                                terr.getContinent().getColor().get(1),
                                terr.getContinent().getColor().get(2));
                    }
                    graphics.setColor(terrColor);
                    graphics.fill(territoryShape);
                    graphics.setColor(Color.GRAY);
                    graphics.draw(territoryShape);
                }
            }

            // Draw troops
            if(MapView.this.getMapModel().isTroopDotsVisible()){
                for(Territory terr: MapView.this.mapModel.getTerritoryList()){
                    if(terr == MapView.this.mapModel.getActiveTerritory()){
                        graphics.setColor(Color.WHITE);
                    } else {
                        if(MapView.this.getMapModel().isPlayerTerritoryColorVisible()){
                            graphics.setColor(Color.BLACK);
                        } else {
                            graphics.setColor(terr.getOwner().getPlayerColor().darker());
                        }
                    }
                    for(Point2D pos: terr.getSoldierPositions()){
                        int x, y;
                        x = (int) (pos.getX()*scale - SOLDIER_SIZE/2);
                        y = (int) (pos.getY()*scale - SOLDIER_SIZE/2);
                        graphics.fillOval(x, y, SOLDIER_SIZE, SOLDIER_SIZE);
                    }
                }
            }

            // Draw territory names and troop count
            if(MapView.this.getMapModel().isTerritoryNamesVisible() || MapView.this.getMapModel().isTroopCountVisible()){
                for(Territory terr: MapView.this.mapModel.getTerritoryList()){
                    Shape territoryShape = tx2.createTransformedShape(terr.getShape());
                    graphics.setFont(new Font("default", Font.BOLD, 14));
                    FontMetrics fontMetrics = graphics.getFontMetrics();
                    ArrayList<String> nameArr = new ArrayList<>();
                    if(MapView.this.getMapModel().isTerritoryNamesVisible()){
                        nameArr.addAll(Arrays.asList(terr.getName().split(" ")));
                    }
                    if(MapView.this.getMapModel().isTroopCountVisible()){
                        nameArr.add("(" + terr.getSoldiers() + ")");
                    }

                    graphics.setColor(Color.BLACK);

                    for(int i = 0; i < nameArr.size(); i++){
                        float centerX = (float) territoryShape.getBounds2D().getCenterX() - fontMetrics.stringWidth(nameArr.get(i)) / 2;
                        float centerY = (float) territoryShape.getBounds2D().getCenterY() - fontMetrics.getHeight() + fontMetrics.getAscent() + i*fontMetrics.getAscent();
                        graphics.drawString(nameArr.get(i), centerX, centerY);
                    }

                }
            }
        }
    }
}
