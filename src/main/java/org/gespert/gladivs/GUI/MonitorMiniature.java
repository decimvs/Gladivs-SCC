/*
 * Copyright (C) 2016 - 2018 Guillermo Espert Carrasquer [gespert@yahoo.es]
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

package org.gespert.gladivs.GUI;

import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.gespert.gladivs.Instances.SettingsInstance;
import org.gespert.gladivs.Instances.Windows;
import org.gespert.gladivs.Screenshots.CaptureRegion;
import org.gespert.gladivs.Screenshots.MonitorData;
import org.gespert.gladivs.Screenshots.TakeScreenshot;

/**
 *
 * @author gespe
 */
public class MonitorMiniature {

    private MonitorData monitorData;
    
    private VBoxMiniatura vb;
    private HBox recOptions;
    private ImageView monitorScreenshot;
    
    private double openX, openY;
    private double openWidth, openHeight;
    
    //Option area effects variables
    private Timer timerIn, timerOut;
    
    //Option buttons variables
    private ImageView area, screenshot, popup, reload;
    private ResourceBundle btnStyles;
    private Pane pArea, pReload;
    private ResourceBundle rb;
    
    public MonitorMiniature(MonitorData md)
    {
        //Load with default locale (set or by user o JVM default if not is set by user)
        this.rb = ResourceBundle.getBundle("bundles.Main");

        monitorData = md;
        
        //Creació i configuració de la VBox
        vb = new VBoxMiniatura(10, md);
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
       
    public VBoxMiniatura createMiniature()
    {
        double miniatureMaxHeight = 200;
        double optionAreaMaxHeight = 45;
        
        monitorScreenshot = new ImageView();
        monitorScreenshot.setImage(SwingFXUtils.toFXImage(monitorData.getScreenImage(), null));
        monitorScreenshot.setPreserveRatio(true);
        monitorScreenshot.setFitHeight(miniatureMaxHeight); //Totes les miniatures tenen la mateixa altura i es redimensionen en amplària automàticament.
        
        //Calculate option rectangle dimensions
        openX = monitorScreenshot.getX();
        openY = monitorScreenshot.getY() + (miniatureMaxHeight - optionAreaMaxHeight);
        openWidth = monitorScreenshot.getBoundsInLocal().getWidth();
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
        
        //Button select area -------------------------------->
        area = new ImageView();
        area.setId("btnArea");
        area.setImage(new Image(getClass().getResourceAsStream("/icons/selArea_50px.png")));
        area.setFitWidth(25);
        area.setFitHeight(25);
        area.setStyle(btnStyles.getString("btnAreaNormal"));

        //Tooltip del select area
        Tooltip areaTT = new Tooltip(rb.getString("main_window_area_button_tooltip"));
        
        pArea = new Pane();
        pArea.setMaxSize(25, 25);
        pArea.getChildren().add(area);
        pArea.addEventFilter(MouseEvent.MOUSE_ENTERED, onOptionAreaButtonEnter);
        pArea.addEventFilter(MouseEvent.MOUSE_EXITED, onOptionsAreaButtonExit);
        pArea.addEventFilter(MouseEvent.MOUSE_PRESSED, onOptionAreaButtonPressed);
        pArea.addEventFilter(MouseEvent.MOUSE_RELEASED, onAreaButtonMouseReleased);
        Tooltip.install(pArea, areaTT);
        recOptions.getChildren().add(pArea);

        //Button take screenshot ------------------------->
        screenshot = new ImageView();
        screenshot.setId("btnScreenshot");
        screenshot.setImage(new Image(getClass().getResourceAsStream("/icons/takeScreenshot_50px.png")));
        screenshot.setFitWidth(30);
        screenshot.setFitHeight(30);
        screenshot.setStyle(btnStyles.getString("btnScreenshotNormal"));

        //Tooltip del botó screenshot
        Tooltip screenshotTT = new Tooltip(rb.getString("main_window_screenshot_button_tooltip"));

        Pane pScreenshot = new Pane();
        pScreenshot.setMaxSize(30, 30);
        pScreenshot.getChildren().add(screenshot);
        pScreenshot.addEventFilter(MouseEvent.MOUSE_ENTERED, onOptionAreaButtonEnter);
        pScreenshot.addEventFilter(MouseEvent.MOUSE_EXITED, onOptionsAreaButtonExit);
        pScreenshot.addEventFilter(MouseEvent.MOUSE_PRESSED, onOptionAreaButtonPressed);
        pScreenshot.addEventFilter(MouseEvent.MOUSE_RELEASED, onAreaButtonMouseReleased);
        Tooltip.install(pScreenshot, screenshotTT);
        recOptions.getChildren().add(pScreenshot);

        //Button open popup ----------------------------->
        popup = new ImageView();
        popup.setId("btnPopup");
        popup.setImage(new Image(getClass().getResourceAsStream("/icons/openPopup_50px.png")));
        popup.setFitWidth(30);
        popup.setFitHeight(30);
        popup.setStyle(btnStyles.getString("btnPopupNormal"));

        //Tooltip del popup button
        Tooltip popupTT = new Tooltip(rb.getString("main_window_popup_button_tooltip"));

        Pane pPopup = new Pane();
        pPopup.setMaxSize(30, 30);
        pPopup.getChildren().add(popup);
        pPopup.addEventFilter(MouseEvent.MOUSE_ENTERED, onOptionAreaButtonEnter);
        pPopup.addEventFilter(MouseEvent.MOUSE_EXITED, onOptionsAreaButtonExit);
        pPopup.addEventFilter(MouseEvent.MOUSE_PRESSED, onOptionAreaButtonPressed);
        pPopup.addEventFilter(MouseEvent.MOUSE_RELEASED, onAreaButtonMouseReleased);
        Tooltip.install(pPopup, popupTT);
        recOptions.getChildren().add(pPopup);

        //Button reload monitor ------------------------->
        reload = new ImageView();
        reload.setId("btnReload");
        reload.setImage(new Image(getClass().getResourceAsStream("/icons/reload_50px.png")));
        reload.setFitWidth(25);
        reload.setFitHeight(25);
        reload.setStyle(btnStyles.getString("btnReloadNormal"));

        //Tooltip del reload monitor
        Tooltip reloadTT = new Tooltip(rb.getString("main_window_reload_button_tooltip"));
        
        pReload = new Pane();
        pReload.setMaxSize(25, 25);
        pReload.getChildren().add(reload);
        pReload.addEventFilter(MouseEvent.MOUSE_ENTERED, onOptionAreaButtonEnter);
        pReload.addEventFilter(MouseEvent.MOUSE_EXITED, onOptionsAreaButtonExit);
        pReload.addEventFilter(MouseEvent.MOUSE_PRESSED, onOptionAreaButtonPressed);
        pReload.addEventFilter(MouseEvent.MOUSE_RELEASED, onAreaButtonMouseReleased);
        Tooltip.install(pReload, reloadTT);
        recOptions.getChildren().add(pReload);
        
        //--> Create a container for miniature and options area
        Pane pane = new Pane();
        pane.getChildren().add(monitorScreenshot);
        pane.getChildren().add(recOptions);
        
        
        
        //Afegim el ImageView i el nom del monitor al VBox
        vb.getChildren().add(pane);
        vb.getChildren().add(new Label("Monitor: " + (monitorData.getMonitorIndex())));
        
        return vb;
    }
    
    public boolean getSelected(){
        return vb.getSelected();
    }
    
    public MonitorData getMonitorData(){
        return vb.getMonitorData();
    }
    
    public VBoxMiniatura getMiniature(){
        return vb;
    }

    /**
     * Pren una captura del monitor i la desa a la ruta definida
     * a la finestra
     */
    private void takeQuickScreenshot()
    {
        autocloseMainWindow();
        
        TakeScreenshot.DoAndSave(monitorData);
        
        autocloseMainWindow();
    }

    private void takeScreenshot()
    {
        autocloseMainWindow();

        TakeScreenshot.Do(monitorData);

        autocloseMainWindow();
    }

    /**
     * Recarrega la previsualització del monitor
     */
    private void reloadMiniature()
    {
        monitorScreenshot.setImage(SwingFXUtils.toFXImage(monitorData.getScreenImage(), null));
    }

    /**
     * Captura una regió del monitor
     */
    private void captureRegion()
    {
        autocloseMainWindow();
        
        CaptureRegion.Do(monitorData);
        
        autocloseMainWindow();
    }
    
    private void autocloseMainWindow()
    {
        boolean hidding = false;
        
        if(SettingsInstance.getGeneralSettings().getAutohideMainWindow() && Windows.getMainWindow().getStage().isShowing())
        {
            Windows.getMainWindow().getStage().hide();
            try {
                Thread.sleep(350);
            } catch (InterruptedException ex) {
                Logger.getLogger(MonitorMiniature.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            hidding = true;
        }
        
        if(SettingsInstance.getGeneralSettings().getAutohideMainWindow() && !Windows.getMainWindow().getStage().isShowing() && !hidding)
        {
            Windows.getMainWindow().getStage().show();
        }
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
                        captureRegion();
                        break;
                    case "btnScreenshot":
                        takeQuickScreenshot();
                        break;
                    case "btnPopup":
                        takeScreenshot();
                        break;
                    case "btnReload":
                        reloadMiniature();
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
