package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class RiskGameMap extends JPanel {
    private Point2D mouseClickPosition;
    private ArrayList<MapTerritory> territories;

    RiskGameMap(){
        MapImport mapImport = new MapImport();
        this.mouseClickPosition = new Point2D.Double(0,0);
        this.territories = mapImport.getTerritories();

        // JPanel Config
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(600, 500));
        this.add(new DrawMap(), BorderLayout.CENTER);

        // Mouse Listener
        MouseAdapter mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseClickPosition = e.getPoint();
                RiskGameMap.this.repaint();
                RiskGameMap.this.revalidate();
            }
        };

        this.addMouseListener(mouseListener);
        this.repaint();
        this.revalidate();

    }

    public Point2D getMouseClickPosition() {
        return mouseClickPosition;
    }

    public ArrayList<MapTerritory> getTerritories() {
        return territories;
    }

    private class DrawMap extends JComponent {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw Background
            graphics.setColor(Color.CYAN);
            g.fillRect(0,0, getWidth(), getHeight());

            AffineTransform tx = new AffineTransform();

            Double scale = Double.valueOf(this.getWidth())/600;
            tx.scale(scale, scale);

            for(MapTerritory terr: RiskGameMap.this.getTerritories()){
                Shape territoryShape = tx.createTransformedShape(terr.getShape());
                if(territoryShape.contains(RiskGameMap.this.getMouseClickPosition())){
                    graphics.setColor(Color.RED);
                    graphics.fill(territoryShape);
                    System.out.println(terr.getName());
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
