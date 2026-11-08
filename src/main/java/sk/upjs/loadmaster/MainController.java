package sk.upjs.loadmaster;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {

    // --- výber vozidla ---
    @FXML private ComboBox<Truck> truckComboBox;
    @FXML private Label truckInfoLabel;

    // --- formulár na pridanie krabice ---
    @FXML private TextField nameField;
    @FXML private TextField lengthField;
    @FXML private TextField widthField;
    @FXML private TextField heightField;
    @FXML private TextField weightField;
    @FXML private TextField stopField;
    @FXML private CheckBox stackableCheckBox;

    // --- tabuľka nákladu ---
    @FXML private TableView<Box> boxTable;
    @FXML private TableColumn<Box, String> nameColumn;
    @FXML private TableColumn<Box, Integer> lengthColumn;
    @FXML private TableColumn<Box, Integer> widthColumn;
    @FXML private TableColumn<Box, Integer> heightColumn;
    @FXML private TableColumn<Box, Double> weightColumn;
    @FXML private TableColumn<Box, Integer> stopColumn;
    @FXML private TableColumn<Box, String> stackableColumn;

    // --- súhrn ---
    @FXML private Label countLabel;
    @FXML private Label weightLabel;
    @FXML private Label volumeLabel;

    private ObservableList<Box> boxes = FXCollections.observableArrayList();

    // Spustí sa automaticky po načítaní FXML
    @FXML
    private void initialize() {
        // Prednastavené typy vozidiel
        truckComboBox.getItems().add(new Truck("Dodávka do 3,5 t", 420, 205, 190, 1200));
        truckComboBox.getItems().add(new Truck("Nákladné auto 12 t", 720, 245, 260, 5500));
        truckComboBox.getItems().add(new Truck("Kamión s návesom 13,6 m", 1360, 245, 270, 24000));
        truckComboBox.getSelectionModel().select(0);

        // Prepojenie stĺpcov s getrami v triede Box (napr. "length" -> getLength())
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        widthColumn.setCellValueFactory(new PropertyValueFactory<>("width"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        stopColumn.setCellValueFactory(new PropertyValueFactory<>("stop"));
        stackableColumn.setCellValueFactory(new PropertyValueFactory<>("stackableText"));

        boxTable.setItems(boxes);
        stackableCheckBox.setSelected(true);
        stopField.setText("1");

        updateSummary();
    }

    @FXML
    private void onTruckChanged() {
        updateSummary();
    }

    @FXML
    private void onAddBox() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            showError("Zadaj názov krabice.");
            return;
        }

        int length;
        int width;
        int height;
        double weight;
        int stop;
        try {
            length = Integer.parseInt(lengthField.getText().trim());
            width = Integer.parseInt(widthField.getText().trim());
            height = Integer.parseInt(heightField.getText().trim());
            weight = Double.parseDouble(weightField.getText().trim().replace(',', '.'));
            stop = Integer.parseInt(stopField.getText().trim());
        } catch (NumberFormatException e) {
            showError("Rozmery, hmotnosť a zastávka musia byť čísla.");
            return;
        }

        if (length <= 0 || width <= 0 || height <= 0 || weight <= 0 || stop <= 0) {
            showError("Všetky čísla musia byť väčšie ako 0.");
            return;
        }

        // Kontrola, či sa krabica vôbec zmestí do vozidla (zatiaľ bez otáčania)
        Truck truck = truckComboBox.getValue();
        if (length > truck.getLength() || width > truck.getWidth() || height > truck.getHeight()) {
            showError("Krabica je väčšia ako nákladný priestor vozidla.");
            return;
        }

        boxes.add(new Box(name, length, width, height, weight, stop, stackableCheckBox.isSelected()));
        clearForm();
        updateSummary();
    }

    @FXML
    private void onRemoveBox() {
        Box selected = boxTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Najprv vyber krabicu v tabuľke.");
            return;
        }
        boxes.remove(selected);
        updateSummary();
    }

    @FXML
    private void onClearAll() {
        boxes.clear();
        updateSummary();
    }

    // Prepočíta súhrn: počet, celková hmotnosť a objem oproti kapacite vozidla
    private void updateSummary() {
        Truck truck = truckComboBox.getValue();

        double totalWeight = 0;
        double totalVolume = 0;
        for (int i = 0; i < boxes.size(); i++) {
            totalWeight = totalWeight + boxes.get(i).getWeight();
            totalVolume = totalVolume + boxes.get(i).getVolume();
        }

        double weightPercent = totalWeight / truck.getMaxWeight() * 100;
        double volumePercent = totalVolume / truck.getVolume() * 100;

        truckInfoLabel.setText(String.format("Priestor: %d × %d × %d cm  |  objem %.1f m³  |  nosnosť %.0f kg",
                truck.getLength(), truck.getWidth(), truck.getHeight(), truck.getVolume(), truck.getMaxWeight()));
        countLabel.setText("Počet kusov: " + boxes.size());
        weightLabel.setText(String.format("Hmotnosť: %.1f / %.0f kg (%.1f %%)", totalWeight, truck.getMaxWeight(), weightPercent));
        volumeLabel.setText(String.format("Objem: %.2f / %.1f m³ (%.1f %%)", totalVolume, truck.getVolume(), volumePercent));

        // Červená farba, ak je vozidlo preťažené.
        // Najprv triedu "warning" odstránime, aby sa nepridala viackrát.
        weightLabel.getStyleClass().removeAll("warning");
        volumeLabel.getStyleClass().removeAll("warning");
        if (totalWeight > truck.getMaxWeight()) {
            weightLabel.getStyleClass().add("warning");
        }
        if (totalVolume > truck.getVolume()) {
            volumeLabel.getStyleClass().add("warning");
        }
    }

    private void clearForm() {
        nameField.clear();
        lengthField.clear();
        widthField.clear();
        heightField.clear();
        weightField.clear();
        nameField.requestFocus();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Chyba");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
