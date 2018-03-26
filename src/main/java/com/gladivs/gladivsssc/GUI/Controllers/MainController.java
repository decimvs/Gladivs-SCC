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

package com.gladivs.gladivsssc.GUI.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gladivs.gladivsssc.Configuration.Configuration;
import com.gladivs.gladivsssc.Configuration.Constants;
import com.gladivs.gladivsssc.Instances.WindowsInstances;
import com.gladivs.gladivsssc.MainApp;
import com.gladivs.gladivsssc.GUI.MonitorMiniature;
import com.gladivs.gladivsssc.GUI.VBoxMiniatura;
import com.gladivs.gladivsssc.VersionCheck;
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class MainController implements Initializable {
    
    @FXML
    private HBox contenedorImatges;
    @FXML
    private Label lblFolder, lblUpdateInfo, txtSelectWhatMonitors;
    @FXML
    private Button btnAboutUs, btnHelp;
    @FXML
    private CheckMenuItem chmnitCheckForUpdates, chmnitAutohideWindow;
    @FXML
    private Hyperlink hlkDownloadUpdate;
    @FXML
    private MenuItem mnitKeyboardSettings, mnitLangEnglish, mnitLangSpanish, mnitLangCatalan, mnitLangFrench, mnitSettings;
    @FXML
    private Menu mnLangSettings;
    @FXML
    private MenuButton mnbtSettings;
            
    
    private Stage primaryStage;
    private ArrayList<java.awt.Rectangle> screenBounds;
    private File screenshotsSaveDir;
    private String screenshotsSavePath;
    private String updateDownloadLink;
    private ResourceBundle rb;
    private String defaultLanguage;
    private int autohideWindow;
    private Configuration configuration;
    private int checkForUpdates;
    
    //Preferences constants (Deprecated)
    private final String SAVE_PATH = "screenshotsSavePath";
    private final String CHECK_UPDATES = "checkServerForNewUpdates";
    private final String DEFAULT_LANGUAGE = "defaultUserLanguage";
    private final String AUTOHIDE_WINDOW = "autohideWindowWhenTakeScreenshot";
    
    //MainApp class object
    private MainApp mApp;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //Events
        lblFolder.addEventFilter(MouseEvent.MOUSE_RELEASED, onLblFolderMouseButtonReleased);
        btnAboutUs.addEventFilter(ActionEvent.ACTION, onButtonAboutUsActionEvent);
        chmnitCheckForUpdates.addEventHandler(ActionEvent.ACTION, onCheckMenuItemCheckForUpdatesActionEvent);
        mnitLangEnglish.addEventHandler(ActionEvent.ACTION, onLanguageMenuOptionActionPerformed);
        mnitLangSpanish.addEventHandler(ActionEvent.ACTION, onLanguageMenuOptionActionPerformed);
        mnitLangCatalan.addEventHandler(ActionEvent.ACTION, onLanguageMenuOptionActionPerformed);
        mnitLangFrench.addEventHandler(ActionEvent.ACTION, onLanguageMenuOptionActionPerformed);
        btnHelp.addEventHandler(ActionEvent.ACTION, onButtonHelpActionPerformed);
        chmnitAutohideWindow.addEventHandler(ActionEvent.ACTION, onMenuItemAutoHideActionPerformed);
        mnitKeyboardSettings.addEventHandler(ActionEvent.ACTION, onMenuItemKeyboardSettingsActionPerformed);
        mnitSettings.addEventHandler(ActionEvent.ACTION, onSettingsMenuActionEvent);
        
        //Set background color for monitor pane
        contenedorImatges.setStyle("-fx-background-color: #000000;");
    }

    /**
     * Here come method calls that needs to be called after Controller is fully
     * loaded and initialized.
     */
    public void postInicilizationActions()
    {
        //Captura el event quant es pressiona la 'X' de la finestra principal. Evita
        //el tancament i amaga la finestra.
        WindowsInstances.getMainWindow().getStage().setOnCloseRequest(event -> {
            WindowsInstances.getMainWindow().getStage().hide();
        });
        
        //Load user preferences
        if(configuration.getUserValues().get(configuration.NEW_INSTALLATION).getIntValue() == 1)
        {
            //Copy all preferences in the user config file
            copyAllPreferencesOnConfigFile();
        }
        
        //Init variables
        screenBounds = new ArrayList<>();
        screenshotsSaveDir = null;
        screenshotsSavePath = configuration.getUserValues().get(configuration.SAVE_PATH).getStringValue();
        defaultLanguage = configuration.getUserValues().get(configuration.DEFAULT_LANGUAGE).getStringValue();
        autohideWindow = configuration.getUserValues().get(configuration.AUTOHIDE_WINDOW).getIntValue();
        checkForUpdates = configuration.getUserValues().get(configuration.CHECK_UPDATES).getIntValue();
        
        //Setting default language if users has selected one
        if(defaultLanguage != null)
        {
            Locale.setDefault(new Locale(defaultLanguage));
        }
        
        //Load with default locale (set or by user o JVM default if not is set by user)
        this.rb = ResourceBundle.getBundle("bundles.Main");
        
        if(configuration.getUserValues().get(configuration.CHECK_UPDATES).getIntValue() == 1){
            chmnitCheckForUpdates.setSelected(true);
        } else {
            chmnitCheckForUpdates.setSelected(false);
        }
        
        if(configuration.getUserValues().get(configuration.AUTOHIDE_WINDOW).getIntValue() == 0)
        {
            chmnitAutohideWindow.setSelected(false);
        }
        else
        {
            chmnitAutohideWindow.setSelected(true);
        }
        
        //Populate monitor pane
        refreshAllMonitorMiniatures();
        
        //Check for software updates
        checkForUpdates();
        
        //Translate UI
        translateUI();
        
        //Set the tooltips for some UI elements
        setUiElementsToolTips();
        
        //Check if the save path is saved in preferences and load it
        checkSavePathSettings();
    }
    
    /**
     * Translate texts in UI elemnts for the current Locale
     */
    private void translateUI()
    {
        //Set UI strings
        txtSelectWhatMonitors.setText(rb.getString("select_what_monitors_you_want_to_capture"));
        chmnitCheckForUpdates.setText(rb.getString("check_for_updates_menu_option"));
        mnLangSettings.setText(rb.getString("menu_lang_settings_text"));
        chmnitAutohideWindow.setText(rb.getString("autohide_window_menu_option"));
        mnitKeyboardSettings.setText(rb.getString("keyboard_settings_menu_option"));
        
        //Recalculate UI size to arrange all elements inside
        primaryStage.sizeToScene();
    }
    
    private void setUiElementsToolTips()
    {
        btnAboutUs.setTooltip(new Tooltip(rb.getString("button_about_us_tooltip")));
        btnHelp.setTooltip(new Tooltip(rb.getString("button_help_tooltip")));
        mnbtSettings.setTooltip(new Tooltip(rb.getString("menu_button_settings")));
    }
    
    /**
     * Comprueba la ruta de guardado de las capturas
     */
    private void checkSavePathSettings()
    {
        //Verify if there is a screen shots save path saved in prefs. If there is
        //we put it in the path label to inform user
        if(screenshotsSavePath != null){
            File testPath = new File(screenshotsSavePath);
            
            if(testPath.isDirectory()){
                lblFolder.setText(screenshotsSavePath);
                screenshotsSaveDir = testPath;
            } else {
                lblFolder.setText(rb.getString("click_here_to_select_a_destination_folder"));
            }
        } else {
            lblFolder.setText(rb.getString("click_here_to_select_a_destination_folder"));
        }
    }
    
    /**
     * Populates or refreshes all monitor miniatures in monitors pane.
     */
    public void refreshAllMonitorMiniatures()
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();

        //If monitor pane is not empty, clean it.
        if(contenedorImatges.getChildren().size() > 0)
        {
            contenedorImatges.getChildren().clear();
            screenBounds.clear();
        }
        
        for(int j=0; j < gd.length; j++)
        {
            refreshMonitorMiniatures(j);
        }
    }
    
    /**
     * Populates or refreshes the specified monitor
     */
    public void refreshMonitorMiniature(int index)
    {
        if(index >= 0 && index < contenedorImatges.getChildren().size())
        {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] gd = ge.getScreenDevices();
            
            contenedorImatges.getChildren().remove(index);
            screenBounds.remove(index);
            
            refreshMonitorMiniatures(index);
        }
    }
    
    /**
     * Refreshes or pupulates a monitor miniature especified by index
     */
    private void refreshMonitorMiniatures(int index)
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        GraphicsConfiguration gc = gd[index].getDefaultConfiguration();

        java.awt.Rectangle rectangle = new java.awt.Rectangle(gc.getBounds());

        try
        {
            //Obtenir la captura 
            BufferedImage bf = new Robot().createScreenCapture(rectangle);

            //Register screen rectangle in a list
            screenBounds.add(index, gc.getBounds());

            //Get the rectangle index inside the list
            int indexMonitor = screenBounds.indexOf(gc.getBounds());

            //Escriure la imatge amb format per al image viewer.
            WritableImage wr = null;
            if (bf != null)
            {
                wr = new WritableImage(bf.getWidth(), bf.getHeight());
                PixelWriter pw = wr.getPixelWriter();

                for (int x = 0; x < bf.getWidth(); x++) 
                {
                    for (int y = 0; y < bf.getHeight(); y++) 
                    {
                        pw.setArgb(x, y, bf.getRGB(x, y));
                    }
                }
            }

            MonitorMiniature mm = new MonitorMiniature(this, indexMonitor);
            contenedorImatges.getChildren().add(index, mm.createMiniature(wr));

        }   catch (AWTException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method performs a response to screenshot keys when pressed
     */
    public void takeScreenshotButtonActionPerformed()
    {
        //Auto-hide window before take a screenshot
        if(autohideWindow == 1){
            try {
                WindowsInstances.getMainWindow().getStage().hide();
                Thread.sleep(350);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        int bounds = screenBounds.size();
        int miniatures = contenedorImatges.getChildren().size();
        
        if((bounds > 0 && miniatures > 0) && bounds == miniatures)
        {
            int countI = 0;
            
            for(int i=0; i < miniatures; i++){
                if(contenedorImatges.getChildren().get(i) instanceof VBoxMiniatura)
                {
                    VBoxMiniatura miniatura = (VBoxMiniatura) contenedorImatges.getChildren().get(i);
                    
                    if(miniatura.getSelected()){
                        WritableImage wr = takeNewScreenshot(miniatura.getIndexMonitor());
                        
                        if(wr != null)
                        {
                            saveScreenshotToDisk(miniatura.getIndexMonitor(), wr);
                        }
                    }
                }
                countI = i;
            }
            
            if(countI == 0)
            {
                System.out.println("No monitor selected");
            }
        }
        
        //Show window if it's hidden
        if(!WindowsInstances.getMainWindow().getStage().isShowing()) WindowsInstances.getMainWindow().getStage().show();
    }
    
    /**
     * Takes a screenshot from the specified monitor and saves it to disk
    */
    public void takeScreenshotFromMonitor(int monitorIndex)
    {
        //Hide the window before take a screenshot
        if(autohideWindow == 1){
            try {
                WindowsInstances.getMainWindow().getStage().hide();
                Thread.sleep(350);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        int bounds = screenBounds.size();
        int miniatures = contenedorImatges.getChildren().size();

        if((bounds > 0 && miniatures > 0) && bounds == miniatures && (monitorIndex >= 0 && monitorIndex < bounds))
        {
            WritableImage wr = takeNewScreenshot(monitorIndex);

            if(wr != null)
            {
                saveScreenshotToDisk(monitorIndex, wr);
            }
        }

        //Show the window if it's hidden
        if(!WindowsInstances.getMainWindow().getStage().isShowing()) WindowsInstances.getMainWindow().getStage().show();  
    }
    
    /**
     * Takes an screenshot from the specified monitor.
     * @param indexMonitor
     * @return 
     */
    private WritableImage takeNewScreenshot(int indexMonitor)
    {  
        java.awt.Rectangle captureRectangle = screenBounds.get(indexMonitor);

        //Obtenir la captura 
        BufferedImage bf;
        try {
            bf = new Robot().createScreenCapture(captureRectangle);

            //Escriure la imatge amb format per al image viewer.
            WritableImage wr = null;
            if (bf != null)
            {
                wr = new WritableImage(bf.getWidth(), bf.getHeight());
                PixelWriter pw = wr.getPixelWriter();

                for (int x = 0; x < bf.getWidth(); x++) 
                {
                    for (int y = 0; y < bf.getHeight(); y++) 
                    {
                        pw.setArgb(x, y, bf.getRGB(x, y));
                    }
                }
            }

            
            return wr;
            
        } catch (AWTException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Saves an image to disk. The folder path and filename are determined by
     * parameters.
     * @param index
     * @param wr 
     */
    private void saveScreenshotToDisk(int index, WritableImage wr)
    {
        if(screenshotsSaveDir != null && screenshotsSaveDir.isDirectory())
        {
            LocalDateTime localDate = LocalDateTime.now();
            String month;
            String day;
            String hour;
            String minute;
            String second;

            if(localDate.getMonthValue() < 10){ month = "0" + localDate.getMonthValue(); }
            else { month = String.valueOf(localDate.getMonthValue()); }

            if(localDate.getDayOfMonth() < 10){ day = "0" + localDate.getDayOfMonth(); }
            else { day = String.valueOf(localDate.getDayOfMonth()); }

            if(localDate.getHour() < 10){ hour = "0" + localDate.getHour(); }
            else { hour = String.valueOf(localDate.getHour()); }

            if(localDate.getMinute() < 10){ minute = "0" + localDate.getMinute(); }
            else { minute = String.valueOf(localDate.getMinute()); }

            if(localDate.getSecond() < 10){ second = "0" + localDate.getSecond(); }
            else { second = String.valueOf(localDate.getSecond()); }

            String dataString = localDate.getYear() + "-" + month  + "-" + day + "-" + hour + "_" + minute + "_" + second;
            String imageName = "Monitor_" + (index + 1) + "_" + dataString + ".png";

            File fileSave = new File(screenshotsSaveDir, imageName);

            if(fileSave.exists())
            {
                boolean startRename = true;
                int indexRenamed = 0;

                while(startRename)
                {
                    String newData = dataString + "(" + index +")";
                    imageName = "Monitor_" + (indexRenamed + 1) + "_" + newData + ".png";

                    fileSave = new File(screenshotsSaveDir, imageName);

                    if(fileSave.exists())
                    {
                        indexRenamed++;
                    } else {
                        startRename = false;
                    }
                }
            }

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(wr, null), "PNG", fileSave);
                System.out.println("File saved to: " + fileSave.getPath());
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error when write image");
            }
        } else {
            System.out.println("You might select a directory to save screenshots!");
        }
    }
    
    public void checkForUpdates()
    {     
        if(checkForUpdates == 1){
            ObjectMapper mapper = new ObjectMapper();

            try {
                VersionCheck latest = mapper.readValue(new URL("https://updates.gladivs.com/versioncheck/GladivsSSC"), VersionCheck.class);

                if(latest.getLatest_version().equals(Constants.APP_VERSION))
                {
                    lblUpdateInfo.setVisible(true);
                    hlkDownloadUpdate.setVisible(false);
                    lblUpdateInfo.setText(rb.getString("your_app_is_up_to_date"));
                } else {
                    updateDownloadLink = latest.getDownload_link();
                    lblUpdateInfo.setVisible(false);
                    hlkDownloadUpdate.setVisible(true);
                    hlkDownloadUpdate.setText(rb.getString("a_new_version_is_avilable"));
                    hlkDownloadUpdate.addEventFilter(ActionEvent.ACTION, onUpdateDownloadLinkActionPerformed);
                }

            } catch (IOException ex) {
                lblUpdateInfo.setText(rb.getString("error_when_checking_updates"));
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lblUpdateInfo.setVisible(false);
            hlkDownloadUpdate.setVisible(false);
        }
    }
    
    private void changeLocale(String locale)
    {
        Locale.setDefault(new Locale(locale));
        
        configuration.getUserValues().get(configuration.DEFAULT_LANGUAGE).setParameterValue(locale);
        configuration.writeUserParameters();
        defaultLanguage = locale;
    }
    
    private void updateApplicationUIStrings()
    {
        rb = ResourceBundle.getBundle("bundles.Main");
        
        //Update all elements in UI
        translateUI();
        checkForUpdates();
        checkSavePathSettings();
    }
    
    //#########################
    //
    //  GETTERS & SETTERS
    //
    //#########################
    
    public MainApp getMainAppInstance() { return mApp; }
    
    public void setMainAppInstance(MainApp mApp) { this.mApp = mApp; }
    
    public void setConfigurationSettings(Configuration conf) { this.configuration = conf; }
    
    public void setPrimaryStage(Stage ps) { this.primaryStage = ps; }
    
    //#################
    //
    //  EVENTS
    //
    //#################
    
    private EventHandler<MouseEvent> onLblFolderMouseButtonReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.getButton() == MouseButton.PRIMARY){
                DirectoryChooser fc = new DirectoryChooser();
                
                if(screenshotsSaveDir != null && screenshotsSaveDir.isDirectory()){
                    fc.setInitialDirectory(screenshotsSaveDir);
                } else {
                    fc.setInitialDirectory(new File(System.getProperty("user.dir")));
                }
                
                fc.setTitle(rb.getString("select_folder_dialog_title"));
                File file = fc.showDialog(primaryStage);
                
                if (file != null) {
                    configuration.getUserValues().get(configuration.SAVE_PATH).setParameterValue(file.getPath());
                    configuration.writeUserParameters();
                    lblFolder.setText(file.getPath());
                    screenshotsSaveDir = file;
                    screenshotsSavePath = file.getPath();
                }
            }
        }
    };
    
    /**
     * Obrir la finestra de configuració (Settings)
     */
    private EventHandler<ActionEvent> onSettingsMenuActionEvent = (event)-> {
        WindowsInstances.getSettingsDialog().getStage().show();
    };
    
    private EventHandler<ActionEvent> onButtonAboutUsActionEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Parent root;
            try {
                final FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/AboutUs.fxml")
                );
                
                //Load FXML file
                root = (Parent) loader.load();
                
                //Create an instance of AboutUs controller an load version String in label
                AboutUsController aboutUs = loader.getController();
                aboutUs.setMainAppInstance(mApp);
                aboutUs.setApplicationVersionText();
                
                //Configure and show window
                Stage stage = new Stage();
                stage.setTitle(rb.getString("about_us_scene_window_title"));
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/icona_sense_sombra.png")));
                stage.setScene(new Scene(root, 450, 480));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(((Node)event.getSource()).getScene().getWindow());
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    private EventHandler<ActionEvent> onCheckMenuItemCheckForUpdatesActionEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(chmnitCheckForUpdates.isSelected())
            {
                configuration.getUserValues().get(configuration.CHECK_UPDATES).setParameterValue(1);
                checkForUpdates();
                System.out.println("Updates activated");
            } else {
                configuration.getUserValues().get(configuration.CHECK_UPDATES).setParameterValue(0);
                lblUpdateInfo.setVisible(false);
                hlkDownloadUpdate.setVisible(false);
                System.out.println("Updates deactivated");
            }
            
            //write config values
            configuration.writeUserParameters();
        }
    };
    
    private EventHandler<ActionEvent> onUpdateDownloadLinkActionPerformed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Desktop.getDesktop().browse(URI.create(updateDownloadLink));
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    private EventHandler<ActionEvent> onLanguageMenuOptionActionPerformed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            MenuItem mnItem = (MenuItem) event.getSource();
            String locale = null;
            
            switch(mnItem.getId())
            {
                case "mnitLangEnglish":
                    locale = "en";
                    break;
                case "mnitLangSpanish":
                    locale = "es";
                    break;
                case "mnitLangCatalan":
                    locale = "ca";
                    break;
                case "mnitLangFrench":
                    locale = "fr";
                    break;
                default:
                    locale = null;
            }
            
            if(locale != null)
            {
                changeLocale(locale);
                updateApplicationUIStrings();
            }
        }
    };
    
    private EventHandler<ActionEvent> onButtonHelpActionPerformed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String helpLanguage;
            
            if(defaultLanguage.equals("es") || defaultLanguage.equals("en"))
            {
                helpLanguage = defaultLanguage;
            }
            else
            {
                helpLanguage = "en";
            }
            
            String fullUrl = "https://gladivs.com/" + helpLanguage + "/manual/gladivs-ssc-0-6";
            
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URL(fullUrl).toURI());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    
    private EventHandler<ActionEvent> onMenuItemAutoHideActionPerformed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(chmnitAutohideWindow.isSelected())
            {
                chmnitAutohideWindow.setSelected(true);
                configuration.getUserValues().get(configuration.AUTOHIDE_WINDOW).setParameterValue(1);
                autohideWindow = 1;
            }
            else
            {
                chmnitAutohideWindow.setSelected(false);
                configuration.getUserValues().get(configuration.AUTOHIDE_WINDOW).setParameterValue(0);
                autohideWindow = 0;
            }
            
            //Write config parameters
            configuration.writeUserParameters();
        }
    };
    
    private EventHandler<ActionEvent> onMenuItemKeyboardSettingsActionPerformed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try
            {
                //Open the keyboard settings window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CombinationKeysSetting.fxml"));
                Parent parent = (Parent) loader.load();
                Stage stage = new Stage();
                
                CombinationKeysSettingsController ckeySettings = loader.getController();
                ckeySettings.setConfigurationSettings(configuration);
                ckeySettings.setMyStage(stage);
                ckeySettings.postInitializationActions();
                
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/icona_sense_sombra.png")));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.setTitle(rb.getString("ckc_window_title"));
                stage.setScene(new Scene(parent));
                stage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    /**
     * Method for copy settings from 0.5 version to 0.6. I should run one time
     * on the 0.6 version first run
     */
    private void copyAllPreferencesOnConfigFile() 
    {
        Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
        
        String screenshotsSavePath = prefs.get(SAVE_PATH, null);
        String defaultLanguage = prefs.get(DEFAULT_LANGUAGE, "en");
        boolean autohideWindow = prefs.getBoolean(AUTOHIDE_WINDOW, false);
        boolean checkUpdates = prefs.getBoolean(CHECK_UPDATES, true);
        
        //Copy screenshots save path to config file
        if(screenshotsSavePath != null)
        {
            configuration.getUserValues().get(configuration.SAVE_PATH).setParameterValue(screenshotsSavePath);
        }
        
        //Copy default language to config file
        configuration.getUserValues().get(configuration.DEFAULT_LANGUAGE).setParameterValue(defaultLanguage);
        
        //Copy autohide window to config file
        configuration.getUserValues().get(configuration.AUTOHIDE_WINDOW).setParameterValue((autohideWindow) ? 1 : 0);
        
        //Copy check for updates to config file
        configuration.getUserValues().get(configuration.CHECK_UPDATES).setParameterValue((checkUpdates) ? 1 : 0);
        
        //Block rewrite of user settings on first run
        configuration.getUserValues().get(configuration.NEW_INSTALLATION).setParameterValue(0);
        
        //Write config
        configuration.writeUserParameters();
    }
}
