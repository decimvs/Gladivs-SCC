/*
 * Copyright (C) 2016 Guillermo Espert Carrasquer [gespert@yahoo.es]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.gladivs.gladivsssc.GUI;

import com.gladivs.gladivsssc.GUI.Controllers.MainController;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author gespe
 */
public class MonitorMiniature {
    
    private MainController myParent;
    private int monitorIndex;
    
    private VBoxMiniatura vb;
    private HBox recOptions;
    
    private double openX, openY;
    private double openWidth, openHeight;
    
    //Option area effects variables
    private Timer timerIn, timerOut;
    
    //Option buttons variables
    private ImageView area, screenshot, popup, reload;
    private ResourceBundle btnStyles;
    public Pane pArea, pReload;
    
    public MonitorMiniature(MainController mp, int mi)
    {
        myParent = mp;
        monitorIndex = mi;
        
        //Creació i configuració de la VBox
        vb = new VBoxMiniatura(10);
        vb.setPadding(new Insets(10));
        vb.setAlignment(Pos.CENTER);
        
        //Establiment dels estils visuals per al contenidor VBox
        setVBoxBorderColorStyle("cornflowerblue");
        
        //Declaració d'events
        vb.addEventHandler(MouseEvent.MOUSE_ENTERED, onMouseOverVBox);
        vb.addEventHandler(MouseEvent.MOUSE_EXITED, onMouseOutVBox);
        vb.addEventHandler(MouseEvent.MOUSE_RELEASED, onVBoxMouseReleased);
        
        //Creation of the option areas and configuration
        recOptions = new HBox(15);
        
        //Event declaration for option area
        recOptions.addEventFilter(MouseEvent.MOUSE_ENTERED, onOptionsAreaMouseIn);
        recOptions.addEventFilter(MouseEvent.MOUSE_EXITED, onOptionsAreaMouseOut);
    }
       
    public VBox createMiniature(WritableImage wr)
    {
        int monitorNumber = monitorIndex;
        double miniatureMaxHeight = 200;
        double optionAreaMaxHeight = 45;
        
        ImageView iw = new ImageView();
        iw.setImage(wr);
        iw.setPreserveRatio(true);
        iw.setFitHeight(miniatureMaxHeight); //Totes les miniatures tenen la mateixa altura i es redimensionen en amplària automàticament.
        
        //Calculate option rectangle dimensions
        openX = iw.getX();
        openY = iw.getY() + (miniatureMaxHeight - optionAreaMaxHeight);
        openWidth = iw.getBoundsInLocal().getWidth();
        openHeight = optionAreaMaxHeight;        
        
        //Draw options area
        recOptions.setOpacity(0);
        recOptions.setPrefSize(openWidth, openHeight);
        recOptions.setLayoutX(openX);
        recOptions.setLayoutY(openY);
        recOptions.setAlignment(Pos.CENTER_RIGHT);
        recOptions.setPadding(new Insets(5,5,5,5));
        recOptions.setStyle(
                "-fx-background-color: #A9ABA9;" +
                "-fx-effect: innershadow(gaussian, #6a6b6a, 25, 0.1, 0, 7);"
        );
        
        //--> Create option icons and add in options area
        btnStyles = ResourceBundle.getBundle("styles.monitorMiniatureButtons");
        
        //Button select area
        area = new ImageView();
        area.setId("btnArea");
        area.setImage(new Image(getClass().getResourceAsStream("/icons/selArea_50px.png")));
        area.setFitWidth(25);
        area.setFitHeight(25);
        area.setStyle(btnStyles.getString("btnAreaNormal"));
        
        pArea = new Pane();
        pArea.setMaxSize(25, 25);
        pArea.getChildren().add(area);
        pArea.addEventFilter(MouseEvent.MOUSE_ENTERED, onOptionAreaButtonEnter);
        pArea.addEventFilter(MouseEvent.MOUSE_EXITED, onOptionsAreaButtonExit);
        pArea.addEventFilter(MouseEvent.MOUSE_PRESSED, onOptionAreaButtonPressed);
        //recOptions.getChildren().add(pArea);
        
        //Button select reload
        reload = new ImageView();
        reload.setId("btnReload");
        reload.setImage(new Image(getClass().getResourceAsStream("/icons/reload_50px.png")));
        reload.setFitWidth(25);
        reload.setFitHeight(25);
        reload.setStyle(btnStyles.getString("btnReloadNormal"));
        
        pReload = new Pane();
        pReload.setMaxSize(25, 25);
        pReload.getChildren().add(reload);
        pReload.addEventFilter(MouseEvent.MOUSE_ENTERED, onOptionAreaButtonEnter);
        pReload.addEventFilter(MouseEvent.MOUSE_EXITED, onOptionsAreaButtonExit);
        pReload.addEventFilter(MouseEvent.MOUSE_PRESSED, onOptionAreaButtonPressed);
        pReload.addEventFilter(MouseEvent.MOUSE_RELEASED, onAreaButtonMouseReleased);
        recOptions.getChildren().add(pReload);
        
        //Button take screenshot
        screenshot = new ImageView();
        screenshot.setId("btnScreenshot");
        screenshot.setImage(new Image(getClass().getResourceAsStream("/icons/takeScreenshot_50px.png")));
        screenshot.setFitWidth(30);
        screenshot.setFitHeight(30);
        screenshot.setStyle(btnStyles.getString("btnScreenshotNormal"));
        
        Pane pScreenshot = new Pane();
        pScreenshot.setMaxSize(30, 30);
        pScreenshot.getChildren().add(screenshot);
        pScreenshot.addEventFilter(MouseEvent.MOUSE_ENTERED, onOptionAreaButtonEnter);
        pScreenshot.addEventFilter(MouseEvent.MOUSE_EXITED, onOptionsAreaButtonExit);
        pScreenshot.addEventFilter(MouseEvent.MOUSE_PRESSED, onOptionAreaButtonPressed);
        pScreenshot.addEventFilter(MouseEvent.MOUSE_RELEASED, onAreaButtonMouseReleased);
        recOptions.getChildren().add(pScreenshot);
        
        //Button open popup
        popup = new ImageView();
        popup.setId("btnPopup");
        popup.setImage(new Image(getClass().getResourceAsStream("/icons/openPopup_50px.png")));
        popup.setFitWidth(30);
        popup.setFitHeight(30);
        popup.setStyle(btnStyles.getString("btnPopupNormal"));
        
        Pane pPopup = new Pane();
        pPopup.setMaxSize(30, 30);
        pPopup.getChildren().add(popup);
        pPopup.addEventFilter(MouseEvent.MOUSE_ENTERED, onOptionAreaButtonEnter);
        pPopup.addEventFilter(MouseEvent.MOUSE_EXITED, onOptionsAreaButtonExit);
        pPopup.addEventFilter(MouseEvent.MOUSE_PRESSED, onOptionAreaButtonPressed);
        //recOptions.getChildren().add(pPopup);
        
        //--> Create a container for miniature and options area
        Pane pane = new Pane();
        pane.getChildren().add(iw);
        pane.getChildren().add(recOptions);
        
        
        
        //Afegim el ImageView i el nom del monitor al VBox
        vb.getChildren().add(pane);
        vb.getChildren().add(new Label("Monitor: " + (monitorNumber+1)));
        vb.setIndexMonitor(monitorNumber);
        
        return vb;
    }
    
    public boolean getSelected(){
        return vb.getSelected();
    }
    
    public int getIndexMonitor(){
        return vb.getIndexMonitor();
    }
    
    public VBoxMiniatura getMiniature(){
        return vb;
    }
    
    private void setVBoxBorderColorStyle(String color)
    {
        vb.setStyle(
            "-fx-border-width: 4px;" + 
            "-fx-border-color: " + color + ";" +
            "-fx-border-radius: 1;" +
            "-fx-background-color: #FFFFFF;" +
            "-fx-background-insets: 3px;"
        );
    }
    
    private void setNormalStyleForButtons(MouseEvent ev)
    {
        Pane p = (Pane) ev.getSource();
        ImageView iw = (ImageView) p.getChildren().get(0);

        switch(iw.getId())
        {
            case "btnArea":
                area.setStyle(btnStyles.getString("btnAreaNormal"));
                break;
            case "btnScreenshot":
                screenshot.setStyle(btnStyles.getString("btnScreenshotNormal"));
                break;
            case "btnPopup":
                popup.setStyle(btnStyles.getString("btnPopupNormal"));
                break;
            case "btnReload":
                reload.setStyle(btnStyles.getString("btnReloadNormal"));
                break;
            default:
                break;
        }
    }
 
    private EventHandler<MouseEvent> onAreaButtonMouseReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                setNormalStyleForButtons(event);
                
                Pane p = (Pane) event.getSource();
                ImageView iw = (ImageView) p.getChildren().get(0);

                switch(iw.getId())
                {
                    case "btnArea":
                        break;
                    case "btnScreenshot":
                        //Call to the main controller's method to perform a screenshot from 
                        //the monitor that corresponds with this monitor miniature.
                        myParent.takeScreenshotFromMonitor(monitorIndex);
                        break;
                    case "btnPopup":
                        break;
                    case "btnReload":
                        myParent.refreshMonitorMiniature(monitorIndex);
                        break;
                    default:
                        break;
                }
                
                event.consume();
            }
        }
    };
    
    private EventHandler<MouseEvent> onOptionAreaButtonPressed = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent ev) {
            ev.consume();
            
            Pane p = (Pane) ev.getSource();
            ImageView iw = (ImageView) p.getChildren().get(0);
            
            recOptions.setOpacity(1);
            
            switch(iw.getId())
            {
                case "btnArea":
                    area.setStyle(btnStyles.getString("btnAreaPressed"));
                    break;
                case "btnScreenshot":
                    screenshot.setStyle(btnStyles.getString("btnScreenshotPressed"));
                    break;
                case "btnPopup":
                    popup.setStyle(btnStyles.getString("btnPopupPressed"));
                    break;
                case "btnReload":
                    reload.setStyle(btnStyles.getString("btnReloadPressed"));
                    break;
                default:
                    break;
            }
        }
    };
    
    private EventHandler<MouseEvent> onOptionAreaButtonEnter = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent ev) {
            Pane p = (Pane) ev.getSource();
            ImageView iw = (ImageView) p.getChildren().get(0);
            
            recOptions.setOpacity(1);
            
            switch(iw.getId())
            {
                case "btnArea":
                    area.setStyle(btnStyles.getString("btnAreaHover"));
                    break;
                case "btnScreenshot":
                    screenshot.setStyle(btnStyles.getString("btnScreenshotHover"));
                    break;
                case "btnPopup":
                    popup.setStyle(btnStyles.getString("btnPopupHover"));
                    break;
                case "btnReload":
                    reload.setStyle(btnStyles.getString("btnReloadHover"));
                    break;
                default:
                    break;
            }
        }
    };
    
    private EventHandler<MouseEvent> onOptionsAreaButtonExit = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent ev) {
            setNormalStyleForButtons(ev);
        }
    };
    
    private EventHandler<MouseEvent> onOptionsAreaMouseIn = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            
            event.consume();
            
            if(timerOut != null)
            {
                timerOut.cancel();
                timerOut = null;
            }
            
            timerIn = new Timer();
            timerIn.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    
                    if(recOptions.getOpacity() <= 0.90) 
                    {
                        recOptions.setOpacity(recOptions.getOpacity() + 0.1);
                    }
                    else
                    {
                        timerIn.cancel();
                    }
                }
            }, 0, 100);
        }
    };
    
    private EventHandler<MouseEvent> onOptionsAreaMouseOut = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            
            if(timerIn != null){
                timerIn.cancel();
                timerIn = null;
            }
            
            timerOut = new Timer();
            timerOut.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    
                    if(recOptions.getOpacity() <= 1 && recOptions.getOpacity() > 0) 
                    {
                        recOptions.setOpacity(recOptions.getOpacity() - 0.15);
                    }
                    else
                    {
                        timerOut.cancel();
                    }
                }
            }, 0, 100);       
        }
    };
    
    private EventHandler<MouseEvent> onVBoxMouseReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.getButton() == MouseButton.PRIMARY){
                if(!vb.getSelected()){
                    vb.setSelected(true);
                    setVBoxBorderColorStyle("forestgreen");
                } else {
                    vb.setSelected(false);
                    setVBoxBorderColorStyle("cornflowerblue");
                }
            }
        }
    };
    
    private EventHandler<MouseEvent> onMouseOverVBox = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            if(!vb.getSelected()){
                setVBoxBorderColorStyle("darkorange");
            }
        }
        
    };
    
    private EventHandler<MouseEvent> onMouseOutVBox = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(!vb.getSelected()){
                setVBoxBorderColorStyle("cornflowerblue");
            }
        }
    };
}
