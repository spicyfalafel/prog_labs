package com.itmo.utils;

import com.itmo.collection.DragonForTable;
import com.itmo.collection.dragon.classes.Dragon;
import com.itmo.collection.dragon.classes.DragonType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lombok.SneakyThrows;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class Pain {
    final double HEIGHT_LESS_THAN_WIDTH_KF = 0.7;
    final int MAX_WIDTH_OVAL = 200;
    public final double MIN_DISTANCE = 20;
    final int MIN_WIDTH_OVAL = 30;
    final int MAX_HEIGHT_OVAL = (int) Math.round(MAX_WIDTH_OVAL*HEIGHT_LESS_THAN_WIDTH_KF);
    final int MIN_HEIGHT_OVAL = (int) Math.round(MIN_WIDTH_OVAL*HEIGHT_LESS_THAN_WIDTH_KF);

    private final Canvas canvas;
    private final GraphicsContext gc;
    private static Set<Dragon> drawn = Collections.synchronizedSet(new HashSet<>());

    private final Image fire100 = UIHelper.getImage("/images/fire100.gif", getClass());
    private final Image fire200 = UIHelper.getImage("/images/fire200.gif", getClass());
    public Pain(Canvas canvas) {
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
    }

    private void animate(Dragon d, Color userColor){
        Timeline timeLine = new Timeline();
        timeLine.setCycleCount(10);
        int width = valueToWidth(d.getValue());
        int drX = dragonXToCanvasX(d.getCoordinates().getX()) - width/2;
        long drY = dragonYToCanvasY(d.getCoordinates().getY()) - valueToHeight(d.getValue());

        timeLine.getKeyFrames().add(
                new KeyFrame(Duration.millis(200),
                        t -> {
                            if(width>150){
                                gc.drawImage(fire200, drX, drY);
                            }else{
                                gc.drawImage(fire100, drX, drY);
                            }
                        }
        ));
        timeLine.playFromStart();
        timeLine.setOnFinished( e -> {
            clearGraph();
            drawAgain();
            drawn.add(d);
            drawDragonOnCanvas(d.getCoordinates().getX(), d.getCoordinates().getY(), d.getValue(),
                    userColor);
            int dragonX = dragonXToCanvasX(d.getCoordinates().getX());
            long dragonY = dragonYToCanvasY(d.getCoordinates().getY());
            drawFire(dragonX, dragonY , d.getType(), d.getValue());
            d.getUser().setColor(new double[]{userColor.getRed(), userColor.getGreen(), userColor.getBlue()});
        });
    }

    private void removeAnimation(Dragon d){
        Timeline timeLine = new Timeline();
        timeLine.setCycleCount(10);
        int width = valueToWidth(d.getValue());
        int drX = dragonXToCanvasX(d.getCoordinates().getX()) - width/2;
        long drY = dragonYToCanvasY(d.getCoordinates().getY()) - valueToHeight(d.getValue());

        timeLine.getKeyFrames().add(
                new KeyFrame(Duration.millis(200),
                        t -> {
                            if(width>150){
                                gc.drawImage(fire200, drX, drY);
                            }else{
                                gc.drawImage(fire100, drX, drY);
                            }
                        }
                ));
        timeLine.playFromStart();
        timeLine.setOnFinished( e -> {
            clearGraph();
            drawAgain();
        });
    }

    @SneakyThrows
    public void drawDragonWithoutAnimation(Dragon d, Color userColor){
        if(d==null) return;
        drawDragonOnCanvas(d.getCoordinates().getX(), d.getCoordinates().getY(), d.getValue(), userColor);
        int dragonX = dragonXToCanvasX(d.getCoordinates().getX());
        long dragonY = dragonYToCanvasY(d.getCoordinates().getY());
        drawFire(dragonX, dragonY , d.getType(), d.getValue());
    }

    @SneakyThrows
    public void drawDragonOnCanvas(Dragon d, Color userColor){
        if(d==null) return;
        animate(d, userColor);
    }

    private void drawDragonOnCanvas(int xCenter, long yCenter, float value, Color userColor){
        int x = dragonXToCanvasX(xCenter);
        long y = dragonYToCanvasY(yCenter);
        drawOvalHead(x, y, value, userColor); // body
        drawUshki(x,y,value, userColor.brighter());
    }

    private void drawUshki(int x, long y, float value, Color userColor) {
        double x1, y1, x2, y2, x3, y3;
        int height = valueToHeight(value);
        int width = valueToWidth(value);
        x1 = x;
        y1 =  y - height/2.0;

        x2 =  Math.round(x +  width*0.15);
        y2 =  Math.round(y - height*0.8);

        x3 =  Math.round(x + width*0.1);
        y3 =  Math.round(y- height*0.4);

        gc.setFill(userColor);
        gc.fillPolygon(
                new double[]{x1, x2, x3},
                new double[]{y1, y2, y3},
                3
        );

        gc.fillPolygon(
                new double[]{
                        x1 + width*0.2,
                        x2 + width*0.2,
                        x3 + width*0.2
                },
                new double[]{
                        y1+height*0.1,
                        y2+height*0.1,
                        y3+height*0.1
                },
                3
        );
    }

    private void drawFire(int x, long y, DragonType type, float value){
        Color fireColor = getColorOfType(type);
        int pastbX = x - valueToWidth(value)/2;
        drawOval(pastbX, y, (int) Math.round(valueToHeight(value)*0.66),
                (int) Math.round(valueToHeight(value)*0.66), fireColor);
        drawOval((int) Math.round(pastbX-valueToWidth(value)*0.2),
                y, (int) Math.round(valueToHeight(value)*0.8), (int) Math.round(valueToHeight(value)*0.8),
                fireColor.brighter());
    }

    private void drawOval(double centerX, double centerY, double xDiameter, double yDiameter, Color color){
        gc.setFill(color);
        gc.fillOval(centerX-xDiameter/2, centerY-yDiameter/2, xDiameter, yDiameter);
    }

    private int valueToHeight(float value){
        int height = Math.round(value*0.007f);
        if(height<=MAX_HEIGHT_OVAL && height>=MIN_HEIGHT_OVAL) return height;
        else{
            if(height<=MIN_HEIGHT_OVAL) return MIN_HEIGHT_OVAL;
            else return MAX_HEIGHT_OVAL;
        }
    }
    private int valueToWidth(float value){
        int width = Math.round(value*0.01f);
        if(width<=MAX_WIDTH_OVAL && width>=MIN_WIDTH_OVAL) return width;
        else{
            if(width<=MIN_WIDTH_OVAL) return MIN_WIDTH_OVAL;
            else return MAX_WIDTH_OVAL;
        }
    }
    private Color getColorOfType(DragonType type){
        if(type==null) return Color.BLACK;
        switch (type){
            case AIR:
                return Color.LIGHTSKYBLUE;
            case FIRE:
                return Color.ORANGE;
            case WATER:
                return Color.BLUE;
            case UNDERGROUND:
                return Color.BROWN;
            default:
                return Color.BLACK;
        }
    }

    private void drawOvalHead(int centerX, long centerY, float value, Color userColor){
        drawOval(centerX, centerY, valueToWidth(value), valueToHeight(value), userColor);
        drawOval((int)Math.round(centerX-valueToWidth(value)*0.2),
                (int)Math.round(centerY-valueToHeight(value)*0.2),
                (int)Math.round(valueToWidth(value)*0.2),
                (int)Math.round(valueToWidth(value)*0.2),
                userColor.invert());
    }

    public int dragonXToCanvasX(int xCenter){
        return ((int) canvas.getWidth())/2 + xCenter;
    }

    public long dragonYToCanvasY(long yCenter){
        return ((long) canvas.getHeight())/2 -yCenter;
    }

    private void clearGraph(){
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        drawAxis();
    }

    public int calculateDistance(int dragonX, double mouseX, long dragonY, double mouseY) {
        return (int) Math.sqrt(Math.pow(dragonX-mouseX, 2) + Math.pow(dragonY-mouseY,2));
    }

    public void drawCollection(ObservableList<DragonForTable> dragonsForTable) {
        for (DragonForTable d :
                dragonsForTable) {
            drawDragonWithoutAnimation(d.getDragon(),d.getDragon().getUser().getColor());
        }
    }

    public void drawAgain(){
        clearGraph();
        for(Dragon d : drawn){
            drawDragonWithoutAnimation(d, d.getUser().getColor());
        }
    }

    public void drawAgainWithAnimation(){
        drawn.forEach(d -> drawDragonWithoutAnimation(d, d.getUser().getColor()));
    }
    public void removeDragon(long id){
        AtomicReference<Dragon> dd = new AtomicReference<>();
        boolean removed = drawn.removeIf(d -> {
            dd.set(d);
            return d.getId() == id;
        });
        if(removed){
            removeAnimation(dd.get());
        }
    }


    public void drawAxis() {
        synchronized (gc) {
            gc.setStroke(javafx.scene.paint.Color.BLACK);
            gc.setLineWidth(2);
            double w = canvas.getWidth();
            double h = canvas.getHeight();
            gc.strokeLine(w / 2, 0, w / 2, h);
            gc.strokeLine(0, h / 2, w, h / 2);

            //y arrow
            gc.strokeLine(w / 2, 0, w / 2 - 5, 5);
            gc.strokeLine(w / 2, 0, w / 2 + 5, 5);
            //x arrow
            gc.strokeLine(w, h / 2, w - 5, h / 2 + 5);
            gc.strokeLine(w, h / 2, w - 5, h / 2 - 5);

            drawStepsX((int) w / 15);
            drawStepsY((int) h / 9);
        }
    }

    private void drawStepsX(int step){
        double w = canvas.getWidth(); //1200
        int currentX = (int) w;
        double h = canvas.getHeight();
        int label = ((int) w/2); //600
        while(currentX>0){
            gc.strokeLine(currentX, h/2-5, currentX, h/2+5);
            gc.fillText(Integer.toString(label), currentX, h/2+15);
            currentX-=step;
            label-=step;
        }
    }

    private void drawStepsY(int step){
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        int currentY = 0;
        int label = ((int) h/2);
        while(currentY<w){
            gc.strokeLine(w/2-5, currentY, w/2+5, currentY);
            gc.fillText(
                    Integer.toString(label),
                    w/2+5, currentY-3
            );
            currentY+=step;
            label-=step;
        }
    }
}