//package UI.GUI;
//
//import Roles.Student;
//import Services.StudentService;
//import UI.GUI.MainGUI;
//import Utils.AplicationProprietys;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.VBox;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//public class StudentController extends MainGUI {
//    @FXML
//    Button addButton,updateButton,deleteButton;
//    @FXML
//    VBox addPane, filterPane, updatePane, deletePane;
//    @FXML
//    TextArea Nume, Prenume, Grupa, An, Profesor, Email, Media, numeU, prenumeU, grupaU, anU, profesorU, emailU, mediaU, filterTextNume, filterTextPrenume, filterTextAn, filterTextGrupa, filterTextProf, filterTextMedia;
//    @FXML
//    TableView<Student> table;
//    @FXML
//    TableColumn<Student, String> id, nume, prenume, grupa, an, profesor, email, media;
//    @FXML
//    Label errorLabel;
//    private StudentService service = new StudentService(1l);
//    private ObservableList<Student> data = FXCollections.observableArrayList();
//
//    @FXML
//    public void initialize() {
//        acces(this.path);
//        initList();
//        id.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
//        nume.setCellValueFactory(new PropertyValueFactory<Student, String>("nume"));
//        prenume.setCellValueFactory(new PropertyValueFactory<Student, String>("prenume"));
//        an.setCellValueFactory(new PropertyValueFactory<Student, String>("an"));
//        grupa.setCellValueFactory(new PropertyValueFactory<Student, String>("grupa"));
//        profesor.setCellValueFactory(new PropertyValueFactory<Student, String>("profesor"));
//        email.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
//        media.setCellValueFactory(new PropertyValueFactory<Student, String>("mediaAnual"));
//        filterTextNume.textProperty().addListener(s -> filterAction());
//        filterTextPrenume.textProperty().addListener(s -> filterAction());
//        filterTextAn.textProperty().addListener(s -> filterAction());
//        filterTextGrupa.textProperty().addListener(s -> filterAction());
//        filterTextProf.textProperty().addListener(s -> filterAction());
//        filterTextMedia.textProperty().addListener(s -> filterAction());
//        table.setItems(data);
//
//    }
//
//    public void addButtonAction(ActionEvent actionEvent) {
//        setFalse();
//        addPane.setVisible(true);
//    }
//
//    public void filterButtonAction(ActionEvent actionEvent) {
//        setFalse();
//        filterPane.setVisible(true);
//
//    }
//
//    public void updateButtonAction(ActionEvent actionEvent) {
//        setFalse();
//        try {
//            viewStudent();
//            updatePane.setVisible(true);
//        } catch (NullPointerException e) {
//            errorLabel.setText("Un student trebuie sa fie selectat");
//        }
//    }
//
//    public void deletePaneAction(ActionEvent actionEvent) {
//        setFalse();
//
//        if (table.getSelectionModel().getSelectedItem() != null)
//            deletePane.setVisible(true);
//        else
//            errorLabel.setText("Un student trebuie sa fie selectat");
//
//    }
//
//    public void addAction(ActionEvent actionEvent) {
//        try {
//            service.add(new Student(Nume.getText(), Prenume.getText(), Integer.valueOf(Grupa.getText()), Integer.valueOf(An.getText()),
//                    Profesor.getText(), Email.getText(), Double.valueOf(Media.getText())));
//        } catch (NumberFormatException e) {
//            errorLabel.setText("Campurile an,grupa trebuie sa fie numere");
//        }
//        refresh();
//    }
//
//    public void updateAction(ActionEvent actionEvent) {
//        try {
//            Student student = table.getSelectionModel().getSelectedItem();
//
//            Student student1 = new Student(student.getId(), numeU.getText(), prenumeU.getText(), Integer.valueOf(grupaU.getText()), Integer.valueOf(anU.getText()), profesorU.getText(), emailU.getText(), Double.valueOf(mediaU.getText()));
//            service.update(student1);
//        } catch (NumberFormatException e) {
//            errorLabel.setText("Campurile an,grupa trebuie sa fie numere");
//        }
//        refresh();
//    }
//
//    public void deleteAction(ActionEvent actionEvent) {
//        try {
//            service.delete(table.getSelectionModel().getSelectedItem().getId());
//        } catch (Exception e) {
//            errorLabel.setText("Un student trebuie sa fie selectat");
//        }
//        refresh();
//    }
//
//    public void cancelAction(ActionEvent actionEvent) {
//        setFalse();
//        refresh();
//    }
//
//    public void filterAction() {
//        Predicate<Student> byName = x -> x.getNume().contains(filterTextNume.getText());
//        Predicate<Student> byPrenume = x -> {
//            if (filterTextPrenume.getText() != "")
//                return x.getPrenume().contains(filterTextPrenume.getText());
//            return true;
//        };
//        Predicate<Student> byAn = x -> {
//            if (filterTextAn.getText() != "")
//                return Integer.toString(x.getAn()).contains(filterTextAn.getText());
//            return true;
//        };
//        Predicate<Student> byGrupa = x -> {
//            if (filterTextGrupa.getText() != "")
//                return Integer.toString(x.getGrupa()).contains(filterTextGrupa.getText());
//            return true;
//        };
//        Predicate<Student> byProf = x -> {
//            if (filterTextProf.getText() != "")
//                return x.getProfesor().contains(filterTextProf.getText());
//            return true;
//        };
//        Predicate<Student> byMedia = x -> {
//            if (filterTextMedia.getText() != "")
//                return Double.toString(x.getMediaAnual()).contains(filterTextMedia.getText());
//            return true;
//        };
//        data.setAll(StreamSupport.stream(service.findAll().spliterator(), false).filter(byName.and(byPrenume).and(byAn).and(byGrupa).and(byProf).and(byMedia)).collect(Collectors.toList()));
//    }
//
//    private void initList() {
//        List<Student> list = StreamSupport.stream(service.findAll().spliterator(), false).collect(Collectors.toList());
//        data.addAll(list);
//    }
//
//    private void refresh() {
//        table.getItems().clear();
//        initList();
//    }
//
//    private void setFalse() {
//        errorLabel.setText("");
//        addPane.setVisible(false);
//        filterPane.setVisible(false);
//        updatePane.setVisible(false);
//        deletePane.setVisible(false);
//    }
//
//    private void viewStudent() {
//        Student student = table.getSelectionModel().getSelectedItem();
//        numeU.setText(student.getNume());
//        prenumeU.setText(student.getPrenume());
//        anU.setText(Integer.toString(student.getAn()));
//        grupaU.setText(Integer.toString(student.getGrupa()));
//        profesorU.setText(student.getProfesor());
//        emailU.setText(student.getEmail());
//        mediaU.setText(Double.toString(student.getMediaAnual()));
//    }
//    public void acces(String path){
//        List<Button> list=Arrays.asList(addButton, deleteButton, updateButton);
//        list.stream().forEach(button -> {
//            button.setDisable(Boolean.valueOf(AplicationProprietys.getPath(path,button.getId())));
//        });
//    }
//
//}
