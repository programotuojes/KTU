package edu.ktu.ds.lab3.gui;

import edu.ktu.ds.lab3.demo.Car;
import edu.ktu.ds.lab3.demo.CarsGenerator;
import edu.ktu.ds.lab3.demo.SimpleBenchmark;
import edu.ktu.ds.lab3.utils.HashType;
import edu.ktu.ds.lab3.utils.ParsableHashMap;
import edu.ktu.ds.lab3.utils.ParsableMap;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Lab3 langas su JavaFX
 * <pre>
 *                    BorderLayout
 * |---------------------(Top)--------------------|
 * |                     menu                     |
 * |------------(Center)------------|---(Right)---|
 * |                                |--paneRight--|
 * |                                |             |
 * |                                |             |
 * |                                |    |
 * |             table              |             |
 * |                                |             |
 * |                                |             |
 * |                                |-------------|
 * |                                | paneButtons |
 * |--------------------------------|-------------|
 * |--------------------(Bottom)------------------|
 * |----------------panParam12Events--------------|
 * |-----------|-----------|----------------------|
 * | panParam1 | panParam2 |       taEvents       |
 * |-----------|-----------|----------------------|
 * </pre>
 *
 * @author darius.matulis@ktu.lt
 */
public class MainWindow extends BorderPane implements EventHandler<ActionEvent> {

    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("edu.ktu.ds.lab3.gui.messages");
    private static final int TF_WIDTH = 200;
    private static final int TF_WIDTH_SMALLER = 80;
    private static final double SPACING = 5.0;
    private static final Insets INSETS = new Insets(SPACING);
    private static final double SPACING_SMALLER = 2.0;
    private static final Insets INSETS_SMALLER = new Insets(SPACING_SMALLER);

    private final ComboBox cmbCollisionTypes = new ComboBox();
    private final ComboBox cmbHashFunctions = new ComboBox();
    private final TextArea taInput = new TextArea();
    private final GridPane paneParam12Events = new GridPane();
    private final GridPane paneRight = new GridPane();
    private final TextArea taEvents = new TextArea();
    private Panels paneParam1, paneParam2, paneButtons;
    private MapTable<String[], String> table;
    private MainWindowMenu mainWindowMenu;
    private final Stage stage;

    private ParsableMap<String, Car> map;
    private int sizeOfInitialSubSet, sizeOfGenSet, colWidth, initialCapacity;
    private float loadFactor;
    private HashType ht = HashType.DIVISION;
    private final CarsGenerator carsGenerator = new CarsGenerator();

    public MainWindow(Stage stage) {
        this.stage = stage;
        initComponents();
    }

    private void initComponents() {
        // Formuojamas mėlynos spalvos skydelis (dešinėje pusėje)

        // Užpildomi ComboBox'ai
        cmbCollisionTypes.setItems(FXCollections.observableArrayList(
                MESSAGES.getString("cmbCollisionType1"),
                MESSAGES.getString("cmbCollisionType2"),
                MESSAGES.getString("cmbCollisionType3"),
                MESSAGES.getString("cmbCollisionType4")));
        cmbCollisionTypes.setOnAction(this);
        cmbCollisionTypes.getSelectionModel().select(0);
        cmbHashFunctions.setItems(FXCollections.observableArrayList(
                MESSAGES.getString("cmbHashFunction1"),
                MESSAGES.getString("cmbHashFunction2"),
                MESSAGES.getString("cmbHashFunction3"),
                MESSAGES.getString("cmbHashFunction4")));
        cmbHashFunctions.setOnAction(this);
        cmbHashFunctions.getSelectionModel().select(0);

        // Formuojamas mygtukų tinklelis (pilkas). Naudojama klasė Panels.
        paneButtons = new Panels(
                new String[]{
                    MESSAGES.getString("button1"),
                    MESSAGES.getString("button2"),
                    MESSAGES.getString("button3"),
                    MESSAGES.getString("button4")}, 1, 4);
        paneButtons.getButtons().forEach((btn) -> btn.setOnAction(this));
        IntStream.of(1, 3).forEach(p -> paneButtons.getButtons().get(p).setDisable(true));

        // Viskas sudedama į mėlynos spalvos skydelį 
        GridPane.setVgrow(taInput, Priority.ALWAYS);
        taInput.setPrefWidth(TF_WIDTH);
        cmbCollisionTypes.setPrefWidth(TF_WIDTH);
        cmbHashFunctions.setPrefWidth(TF_WIDTH);
        Stream.of(new Label(MESSAGES.getString("border1")),
                cmbCollisionTypes,
                new Label(MESSAGES.getString("border2")),
                cmbHashFunctions,
                new Label(MESSAGES.getString("border3")),
                taInput,
                paneButtons
        ).forEach(n -> paneRight.addColumn(0, n));
        paneRight.setHgap(SPACING);
        paneRight.setVgap(SPACING);
        paneRight.setPadding(INSETS);

        // Formuojama pirmoji parametrų lentelė (šviesiai žalia). Naudojama klasė Panels.
        paneParam1 = new Panels(
                new String[]{
                    MESSAGES.getString("lblParam11"),
                    MESSAGES.getString("lblParam12"),
                    MESSAGES.getString("lblParam13"),
                    MESSAGES.getString("lblParam14"),
                    MESSAGES.getString("lblParam15"),
                    MESSAGES.getString("lblParam16"),
                    MESSAGES.getString("lblParam17")},
                new String[]{
                    MESSAGES.getString("tfParam11"),
                    MESSAGES.getString("tfParam12"),
                    MESSAGES.getString("tfParam13"),
                    MESSAGES.getString("tfParam14"),
                    MESSAGES.getString("tfParam15"),
                    MESSAGES.getString("tfParam16"),
                    MESSAGES.getString("tfParam17")}, TF_WIDTH_SMALLER);

        // Formuojama antroji parametrų lentelė (gelsva). Naudojama klasė Panels.
        paneParam2 = new Panels(
                new String[]{
                    MESSAGES.getString("lblParam21"),
                    MESSAGES.getString("lblParam22"),
                    MESSAGES.getString("lblParam23"),
                    MESSAGES.getString("lblParam24"),
                    MESSAGES.getString("lblParam25"),
                    MESSAGES.getString("lblParam26"),
                    MESSAGES.getString("lblParam27")},
                new String[]{
                    MESSAGES.getString("tfParam21"),
                    MESSAGES.getString("tfParam22"),
                    MESSAGES.getString("tfParam23"),
                    MESSAGES.getString("tfParam24"),
                    MESSAGES.getString("tfParam25"),
                    MESSAGES.getString("tfParam26"),
                    MESSAGES.getString("tfParam27")}, TF_WIDTH_SMALLER);

        // Dviejų lentelių skydeliai sudedami į šviesiai pilką skydelį
        paneParam12Events.setPadding(new Insets(SPACING_SMALLER, SPACING, SPACING, SPACING));
        paneParam12Events.setVgap(SPACING_SMALLER);
        paneParam12Events.setHgap(SPACING);
        paneParam12Events.add(new Label(MESSAGES.getString("border4")), 0, 0);
        GridPane.setHgrow(taEvents, Priority.ALWAYS);
        paneParam12Events.addRow(1, paneParam1, paneParam2, taEvents);
        paneParam12Events.add(new Label(MESSAGES.getString("border6")), 2, 0);

        // Sukuriama lentelė, sukuriamas trūkstamas metodas
        table = new MapTable<String[], String>() {
            @Override
            public ObservableValue<String> returnValue(TableColumn.CellDataFeatures<String[], String> p) {
                int index = Integer.parseInt(p.getTableColumn().getId());
                return new SimpleStringProperty(index < p.getValue().length ? p.getValue()[index] : "");
            }
        };

        // Sukuriamas meniu
        mainWindowMenu = new MainWindowMenu() {
            @Override
            public void handle(ActionEvent ae) {
                Region region = (Region) taEvents.lookup(".content");
                region.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

                try {
                    Object source = ae.getSource();
                    KsGui.setFormatStartOfLine(true);
                    if (source.equals(mainWindowMenu.getMenus().get(0).getItems().get(0))) {
                        fileChooseMenu();
                    } else if (source.equals(mainWindowMenu.getMenus().get(0).getItems().get(1))) {
                        KsGui.ounerr(taEvents, MESSAGES.getString("notImplemented"));
                    } else if (source.equals(mainWindowMenu.getMenus().get(0).getItems().get(3))) {
                        System.exit(0);
                    } else if (source.equals(mainWindowMenu.getMenus().get(1).getItems().get(0))) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initStyle(StageStyle.UTILITY);
                        alert.setTitle(MESSAGES.getString("menuItem21"));
                        alert.setHeaderText(MESSAGES.getString("author"));
                        alert.showAndWait();
                    }
                } catch (ValidationException e) {
                    KsGui.ounerr(taEvents, e.getMessage());
                } catch (Exception e) {
                    KsGui.ounerr(taEvents, MESSAGES.getString("systemError"));
                    e.printStackTrace(System.out);
                }
                KsGui.setFormatStartOfLine(false);
            }
        };

        // Formuojamas Lab3 langas
        // ..viršuje, dešinėje, centre ir apačioje talpiname objektus..
        setTop(mainWindowMenu);

        VBox vboxTable = new VBox();
        vboxTable.setPadding(INSETS_SMALLER);
        VBox.setVgrow(table, Priority.ALWAYS);
        vboxTable.getChildren().addAll(new Label(MESSAGES.getString("border5")), table);
        setRight(paneRight);
        setCenter(vboxTable);
        setBottom(paneParam12Events);
        appearance();
    }

    /**
     * Kosmetika
     */
    private void appearance() {
        cmbCollisionTypes.setStyle(Panels.STYLE_COMMON);
        cmbHashFunctions.setStyle(Panels.STYLE_COMMON);
        paneParam1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        paneParam2.setBackground(new Background(new BackgroundFill(Color.KHAKI, CornerRadii.EMPTY, Insets.EMPTY)));
        paneRight.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        paneButtons.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        taInput.setFont(Font.font("Monospaced", 12));
        taInput.setDisable(true);
        taEvents.setEditable(false);
        taEvents.setFont(Font.font("Monospaced", 12));
        // Antra parametrų lentelė (gelsva) bus neredaguojama
        paneParam2.getTfOfTable().forEach(c -> {
            c.setEditable(false);
            c.setMouseTransparent(true);
        });
    }

    @Override
    public void handle(ActionEvent event) {
        KsGui.setFormatStartOfLine(true);
        Platform.runLater(() -> {
            try {
                System.gc();
                System.gc();
                System.gc();
                Region region = (Region) taEvents.lookup(".content");
                region.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

                Object source = event.getSource();
                if (source instanceof Button) {
                    handleButtons(source);
                } else if (source instanceof ComboBox && (source.equals(cmbCollisionTypes) || source.equals(cmbHashFunctions))) {
                    IntStream.of(1, 3).forEach(p -> paneButtons.getButtons().get(p).setDisable(true));
                }
            } catch (ValidationException e) {
                KsGui.ounerr(taEvents, MESSAGES.getString(e.getMessage()), e.getValue());
            } catch (UnsupportedOperationException e) {
                KsGui.ounerr(taEvents, e.getMessage());
            } catch (Exception e) {
                KsGui.ounerr(taEvents, MESSAGES.getString("systemError"));
                e.printStackTrace(System.out);
            }
        });
    }

    private void handleButtons(Object source) {
        if (source.equals(paneButtons.getButtons().get(0))) {
            mapGeneration(null);
        } else if (source.equals(paneButtons.getButtons().get(1))) {
            mapPut();
        } else if (source.equals(paneButtons.getButtons().get(2))) {
            mapEfficiency();
        } else if (source.equals(paneButtons.getButtons().get(3))) {
            KsGui.ounerr(taEvents, MESSAGES.getString("notImplemented"));
        }
    }

    private void mapGeneration(String filePath) {
        // Išjungiami 2 ir 4 mygtukai
        IntStream.of(1, 3).forEach(p -> paneButtons.getButtons().get(p).setDisable(true));
        // Duomenų nuskaitymas iš parametrų lentelės (žalios)
        readMapParameters();
        // Sukuriamas tuščias atvaizdis priklausomai nuo kolizijų tipo
        createMap();
        // Jei failas nenurodytas - generuojami automobiliai ir talpinami atvaizdyje
        if (filePath == null) {
            Car[] carsArray = carsGenerator.generateShuffleCarsAndIds(sizeOfGenSet, sizeOfInitialSubSet);
            for (Car c : carsArray) {
                map.put(
                        carsGenerator.getCarId(), //raktas
                        c);
            }
            KsGui.ounArgs(taEvents, MESSAGES.getString("mapPuts"), map.size());
        } else { // Jei failas nurodytas skaitoma iš failo
            map.load(filePath);
            if (map.isEmpty()) {
                KsGui.ounerr(taEvents, MESSAGES.getString("fileWasNotReadOrEmpty"), filePath);
            } else {
                KsGui.ou(taEvents, MESSAGES.getString("fileWasRead"), filePath);
            }
        }

        // Atvaizdis rodomas lentelėje
        table.formTable(map.getMaxChainSize() * 2 + 1, colWidth);
        String[][] modelList = map.getModelList(paneParam1.getTfOfTable().get(5).getText());
        table.getItems().clear();
        table.setItems(FXCollections.observableArrayList(modelList));
        // Atnaujinamai maišos lentelės parametrai (geltona lentelė)
        updateHashtableParameters(false);
        // Įjungiami 2 ir 4 mygtukai
        IntStream.of(1, 3).forEach(p -> paneButtons.getButtons().get(p).setDisable(false));
    }

    private void mapPut() {
        Car car = carsGenerator.getCar();
        map.put(
                carsGenerator.getCarId(), // Raktas
                car);
        table.formTable(map.getMaxChainSize() * 2 + 1, colWidth);
        String[][] modelList = map.getModelList(paneParam1.getTfOfTable().get(5).getText());
        table.setItems(FXCollections.observableArrayList(modelList));
        updateHashtableParameters(true);
        KsGui.oun(taEvents, car, MESSAGES.getString("mapPut"));
    }

    private void mapEfficiency() {
        KsGui.oun(taEvents, "", MESSAGES.getString("benchmark"));
        paneRight.setDisable(true);
        mainWindowMenu.setDisable(true);

        BlockingQueue<String> resultsLogger = new SynchronousQueue<>();
        Semaphore semaphore = new Semaphore(-1);
        SimpleBenchmark simpleBenchmark = new SimpleBenchmark(resultsLogger, semaphore);

        // Ši gija paima rezultatus iš greitaveikos tyrimo gijos ir išveda 
        // juos i taEvents. Gija baigia darbą kai gaunama FINISH_COMMAND     
        new Thread(() -> {
            try {
                String result;
                while (!(result = resultsLogger.take())
                        .equals(SimpleBenchmark.FINISH_COMMAND)) {
                    KsGui.ou(taEvents, result);
                    semaphore.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            semaphore.release();
            paneRight.setDisable(false);
            mainWindowMenu.setDisable(false);
        }, "Greitaveikos_rezultatu_gija").start();

        //Šioje gijoje atliekamas greitaveikos tyrimas
        new Thread(simpleBenchmark::startBenchmark, "Greitaveikos_tyrimo_gija").start();
    }

    private void readMapParameters() {
        int i = 0;
        List<TextField> tfs = paneParam1.getTfOfTable();

        sizeOfInitialSubSet = notNegativeNumberVerifier(tfs.get(i++), "badSizeOfInitialSubSet");
        sizeOfGenSet = notNegativeNumberVerifier(tfs.get(i++), "badSizeOfGenSet");
        initialCapacity = notNegativeNumberVerifier(tfs.get(i++), "badInitialCapacity");
        loadFactor = loadFactorVerifier(tfs.get(i++));
        colWidth = notNegativeNumberVerifier(tfs.get(i), "badColumnWidth");

        switch (cmbHashFunctions.getSelectionModel().getSelectedIndex()) {
            case 0:
                ht = HashType.DIVISION;
                break;
            case 1:
                ht = HashType.MULTIPLICATION;
                break;
            case 2:
                ht = HashType.JCF7;
                break;
            case 3:
                ht = HashType.JCF8;
                break;
            default:
                ht = HashType.DIVISION;
                break;
        }
    }

    private void createMap() {
        switch (cmbCollisionTypes.getSelectionModel().getSelectedIndex()) {
            case 0:
                map = new ParsableHashMap<>(String::new, Car::new, initialCapacity, loadFactor, ht);
                break;
            // ...
            // Programuojant kitus kolizijų sprendimo metodus reikia papildyti switch sakinį
            default:
                IntStream.of(1, 3).forEach(p -> paneButtons.getButtons().get(p).setDisable(true));
                throw new ValidationException("notImplemented");
        }
    }

    /**
     * Atnaujina parametrus antroje lentelėje. Taip pat tikrina ar pasikeitė
     * parametro reikšmė nuo praeito karto. Jei pasikeitė - spalvina jo reikšmę
     * rožine spalva
     *
     * @param colorize ar spalvinti parametrų reikšmes rožine spalva
     */
    private void updateHashtableParameters(boolean colorize) {
        String[] parameters = new String[]{
            String.valueOf(map.size()),
            String.valueOf(map.getTableCapacity()),
            String.valueOf(map.getMaxChainSize()),
            String.valueOf(map.getRehashesCounter()),
            String.valueOf(map.getLastUpdatedChain()),
            // Užimtų maišos lentelės elementų skaičius procentais
            String.format("%3.2f", (double) map.getChainsCounter() / map.getTableCapacity() * 100) + "%"
        // .. naujus parametrus tęsiame čia ..
        };
        for (int i = 0; i < parameters.length; i++) {
            String str = paneParam2.getTfOfTable().get(i).getText();
            if ((!str.equals(parameters[i]) && !str.equals("") && colorize)) {
                paneParam2.getTfOfTable().get(i).setStyle(Panels.STYLE_PINK);
            } else {
                paneParam2.getTfOfTable().get(i).setStyle(Panels.STYLE_COMMON);
            }
            paneParam2.getTfOfTable().get(i).setText(parameters[i]);
        }
    }

    private void fileChooseMenu() {
        FileChooser fc = new FileChooser();
        // Papildoma mūsų sukurtais filtrais
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );
        fc.setTitle((MESSAGES.getString("menuItem11")));
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = fc.showOpenDialog(stage);
        if (file != null) {
            mapGeneration(file.getAbsolutePath());
        }
    }

    /**
     * Skirtas TextField objekte įvedamo skaičiaus tikrinimui. Tikrinama ar
     * įvestas neneigiamas skaičius
     */
    private int notNegativeNumberVerifier(TextField tf, String errorMessage) {
        int result;
        try {
            result = Integer.parseInt(tf.getText());
            tf.setStyle(result < 0 ? Panels.STYLE_ERROR : Panels.STYLE_COMMON);
            if (result < 0) {
                throw new ValidationException(errorMessage, tf.getText());
            }
        } catch (NumberFormatException e) {
            tf.setStyle(Panels.STYLE_ERROR);
            throw new ValidationException(errorMessage, tf.getText());
        }

        return result;
    }

    /**
     * Skirtas apkrovimo faktoriaus tikrinimui. Tikrinama ar įvestas skaičius
     * (0,1]
     */
    private float loadFactorVerifier(TextField tf) {
        float factor;
        try {
            factor = Float.parseFloat(tf.getText());
            tf.setStyle(factor <= 0.0 || factor > 1.0 ? Panels.STYLE_ERROR : Panels.STYLE_COMMON);
            if (factor <= 0.0 || factor > 1.0) {
                throw new ValidationException("badLoadFactor", tf.getText());
            }
        } catch (NumberFormatException e) {
            tf.setStyle(Panels.STYLE_ERROR);
            throw new ValidationException("badLoadFactor", tf.getText());
        }
        return factor;
    }

    public static void createAndShowGui(Stage stage) {
        Platform.runLater(() -> {
            Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
            MainWindow window = new MainWindow(stage);
            stage.setScene(new Scene(window, 1300, 700));
            //stage.setMaximized(true);
            stage.setTitle(MESSAGES.getString("title"));
            stage.getIcons().add(new Image("file:" + MESSAGES.getString("icon")));
            stage.setOnCloseRequest(e -> System.exit(0));
            stage.show();
        });
    }
}
