// StudentManagementSystemFX: JavaFX application for managing student information.

package dev.roundrobin.inc.studentmanagement;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class StudentManagementSystemFX extends Application {

    // ObservableList to store student data for TableView
    private final ObservableList<Student> students = FXCollections.observableArrayList();
    // TableView to display student data
    private final TableView<Student> tableView = new TableView<>();

    // Application entry point
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Management System");

        // GridPane layout for the UI
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        // TextFields for user input
        TextField nameField = new TextField();
        TextField rollNumberField = new TextField();
        TextField gradeField = new TextField();

        // Button to add a new student
        Button addButton = new Button("Add Student");
        addButton.setOnAction(e -> {
            if (validateInput(nameField, rollNumberField, gradeField)) {
                addStudent(nameField.getText(), Integer.parseInt(rollNumberField.getText()), gradeField.getText());
                clearFields(nameField, rollNumberField, gradeField);
            }
        });

        // Button to remove a selected student
        Button removeButton = new Button("Remove Student");
        removeButton.setOnAction(e -> removeStudent());

        // Table View configuration
        tableView.setItems(students);
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Student, Integer> rollNumberColumn = new TableColumn<>("Roll Number");
        rollNumberColumn.setCellValueFactory(cellData -> cellData.getValue().rollNumberProperty().asObject());

        TableColumn<Student, Character> gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setCellValueFactory(cellData -> cellData.getValue().gradeProperty());

        tableView.getColumns().addAll(nameColumn, rollNumberColumn, gradeColumn);

        // Buttons for saving/loading student data
        Button saveButton = new Button("Save to File");
        saveButton.setOnAction(e -> saveToFile());

        Button loadButton = new Button("Load from File");
        loadButton.setOnAction(e -> loadFromFile());

        // Adding UI elements to the GridPane
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Roll Number:"), 0, 1);
        grid.add(rollNumberField, 1, 1);
        grid.add(new Label("Grade:"), 0, 2);
        grid.add(gradeField, 1, 2);
        grid.add(addButton, 2, 0);
        grid.add(removeButton, 2, 1);
        grid.add(tableView, 0, 3, 3, 1);
        grid.add(saveButton, 0, 4);
        grid.add(loadButton, 1, 4);

        // Scene creation
        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    // Method to validate user input
    private boolean validateInput(TextField nameField, TextField rollNumberField, TextField gradeField) {
        if (nameField.getText().isEmpty() || rollNumberField.getText().isEmpty() || gradeField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Validation Error");
            alert.setHeaderText("Please fill in all fields");
            alert.setContentText("Name, Roll Number, and Grade fields cannot be empty.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    // Method to add a student to the ObservableList
    private void addStudent(String name, int rollNumber, String grade) {
        students.add(new Student(name, rollNumber, grade.charAt(0)));
    }

    // Method to remove a student from the TableView
    private void removeStudent() {
        Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            // Confirmation dialog before removal
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Removal");
            alert.setHeaderText("Remove Student");
            alert.setContentText("Are you sure you want to remove the selected student?\n\n" + selectedStudent);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                students.remove(selectedStudent);
            }
        } else {
            // Warning if no student is selected for removal
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Student Selected");
            alert.setContentText("Please select a student to remove.");
            alert.showAndWait();
        }
    }

    // Method to save student data to a file
    private void saveToFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Student Data");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized Files", "*.ser"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                // Writing the ArrayList of students to the file
                oos.writeObject(new ArrayList<>(students));
            } catch (IOException e) {
                // Handling errors during file writing
                System.out.println(e.getMessage());
            }
        }
    }

    // Method to load student data from a file
    private void loadFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Student Data");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized Files", "*.ser"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                // Setting the ObservableList of students with the loaded data
                students.setAll((ArrayList<Student>) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                // Handling errors during file reading
                System.out.println(e.getMessage());
            }
        }
    }

    // Method to clear text fields
    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
}

// Serializable class representing a Student
class Student implements Serializable {
    private transient SimpleStringProperty name;
    private transient SimpleIntegerProperty rollNumber;
    private transient SimpleObjectProperty<Character> grade;

    // Constructor
    public Student(String name, int rollNumber, char grade) {
        this.name = new SimpleStringProperty(name);
        this.rollNumber = new SimpleIntegerProperty(rollNumber);
        this.grade = new SimpleObjectProperty<>(grade);
    }

    // Getters for JavaFX properties
    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleIntegerProperty rollNumberProperty() {
        return rollNumber;
    }

    public SimpleObjectProperty<Character> gradeProperty() {
        return grade;
    }

    // Other getters
    public String getName() {
        return name.get();
    }

    public int getRollNumber() {
        return rollNumber.get();
    }

    public char getGrade() {
        return grade.get();
    }

    // Serialization methods
    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(name.get());
        out.writeInt(rollNumber.get());
        out.writeObject(grade.get());
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        // Initialize the transient properties
        name = new SimpleStringProperty();
        rollNumber = new SimpleIntegerProperty();
        grade = new SimpleObjectProperty<>();
        // Set the values of the properties
        name.set((String) in.readObject());
        rollNumber.set(in.readInt());
        grade.set((Character) in.readObject());
    }

    // toString method for display purposes
    @Override
    public String toString() {
        return "Name: " + getName() + ", Roll Number: " + getRollNumber() + ", Grade: " + getGrade();
    }
}
