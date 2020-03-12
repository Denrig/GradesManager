package GUI;

import Entity.Category;
import Entity.Grade;

import Entity.Student;
import Exceptions.ValidationException;

import Services.CategoryService;
import Services.GradeService;
import Services.StudentService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class GradeController {
    @FXML
    VBox inputPane;
    @FXML
    TableColumn<Grade, Long> id;
    @FXML
    TableColumn<Grade, String> nume,categoria;
    @FXML
    TableColumn<Grade, LocalDate>  incarcata;
    @FXML
    TableColumn<Grade, Double> nota;
    @FXML
    TableView<Grade> table;
    @FXML
    ComboBox<Student> studentSelector;
    @FXML
    ComboBox<Category> categorySelector;
    @FXML
    TextField notaSelector;
    @FXML
    Label errorLabel;
    @FXML
    Button execute;
    @FXML
    DatePicker datePicker;
    @FXML
    Text labelDate;
    @FXML
    ToggleButton filterButton;

    private ObservableList<Student> students=FXCollections.observableArrayList();
    private ObservableList<Grade> data = FXCollections.observableArrayList();
    private GradeService gradeService;
    private StudentService studentService;
    private CategoryService categoryService;
    private Long disciplina=1l;

    public GradeController(Properties prop,Long disciplina) {
        this.disciplina = disciplina;
        this.gradeService=new GradeService(prop,disciplina);
        this.studentService=new StudentService(prop,disciplina);
        this.categoryService=new CategoryService(disciplina,prop);
    }

    public Long getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Long disciplina) {
        this.disciplina = disciplina;
        gradeService.setDisciplina(disciplina);
        studentService.setDisciplina(disciplina);
        referesh();
    }

    @FXML
    public void initialize() throws IOException {
        InputStream stream=MainGUI.class.getResourceAsStream("connUtils.properties");
        Properties props=new Properties();
        props.load(new FileInputStream("F:\\workshop\\MPP\\GradesManager\\src\\main\\resources\\connUtils.properties"));

        gradeService=new GradeService(props,disciplina);
        studentService=new StudentService(props,disciplina);
        categoryService=new CategoryService(disciplina,props);

        students.setAll(StreamSupport.stream(studentService.findAll().spliterator(),false).collect(Collectors.toList()));
        categorySelector.setItems(FXCollections.observableArrayList(StreamSupport.stream(categoryService.findAll().spliterator(),false).collect(Collectors.toList())));

        studentSelector.setItems(students);

        notaSelector.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    notaSelector.setText(newValue.replaceAll("[^0-9.]", ""));
                }
            }
        });

        id.setCellValueFactory(new PropertyValueFactory<Grade, Long>("id"));
        nume.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Grade, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Grade, String> param) {
               return  new ReadOnlyObjectWrapper(studentService.findOne(param.getValue().getStudentID()).getNume());
            }
        });
        nota.setCellValueFactory(new PropertyValueFactory<Grade, Double>("grade"));
        incarcata.setCellValueFactory(new PropertyValueFactory<Grade, LocalDate>("uploaded"));
        categoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Grade, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Grade, String> param) {
                return  new ReadOnlyObjectWrapper(categoryService.findOne(param.getValue().getCategory()).getName());
            }
        });

        referesh();
    }

    public void addButtonAction(ActionEvent actionEvent) {
            referesh();
            inputPane.setVisible(true);
            execute.setVisible(true);
            execute.onActionProperty().setValue(this::addAction);
    }

    public void deletePaneAction(ActionEvent actionEvent) {
        Grade grade=table.getSelectionModel().getSelectedItem();
        if(grade!=null){
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Sunteti sigur ca vreti sa stergeti nota studentului?");
            alert.setContentText(table.getSelectionModel().getSelectedItem().toString());
            Optional<ButtonType> result=alert.showAndWait();
            if(result.get()==ButtonType.OK){
                gradeService.delete(table.getSelectionModel().getSelectedItem().getId());
                errorLabel.setText("Nota a fost stearsa");
            }
            else {
                errorLabel.setText("Nota nu a fost stearsa");
            }
        }
        else{
            errorLabel.setText("O nota trebuie sa fie selectata");
        }
        referesh();
    }

    public void filterButtonAction(ActionEvent actionEvent) {
        referesh();
        if(filterButton.isSelected()){
                errorLabel.setText("Filtrarea este activa");
            inputPane.setVisible(true);
            datePicker.setVisible(true);
            labelDate.setVisible(true);
            categorySelector.onActionProperty().setValue(x->filterAction());
            studentSelector.onActionProperty().setValue(x->filterAction());
            notaSelector.textProperty().addListener(x->filterAction());
            datePicker.getEditor().textProperty().addListener(x->filterAction());
            }
            else {
                errorLabel.setText("");
                inputPane.setVisible(false);
                datePicker.setVisible(false);
                labelDate.setVisible(false);
                categorySelector.onActionProperty().setValue(null);
                studentSelector.onActionProperty().setValue(null);
            }

    }

    public void updateButtonAction(ActionEvent actionEvent) {

        Grade grade=table.getSelectionModel().getSelectedItem();
        if(grade!=null){
            inputPane.setVisible(true);
            execute.onActionProperty().setValue(this::updateAction);
            execute.setVisible(true);
            viewGrade(null);
            table.onMouseClickedProperty().setValue(this::viewGrade);
        }
        else{
            errorLabel.setText("O nota trebuie sa fie selectata");
        }
    }

    public void addAction(ActionEvent actionEvent) {
        try {
            gradeService.add(new Grade(studentSelector.getValue().getId(),categorySelector.getValue().getId(),Double.parseDouble(notaSelector.getText()),LocalDate.now()));
            errorLabel.setText("Nota salvata.");
        }catch (ValidationException ex){
            errorLabel.setText(ex.getMessage());
        }
    referesh();
    }


    public void filterAction() {
        Predicate<Grade> byCategory=x->{
            if(categorySelector.getValue()!=null && filterButton.isSelected())
                return x.getCategory()==categorySelector.getValue().getId();
            return true;
        };
        Predicate<Grade> byStudent=x->{
            if(studentSelector.getValue()!=null && filterButton.isSelected())
                return x.getStudentID()==studentSelector.getValue().getId();
            return true;
        };
        Predicate<Grade> byGrade=x->{
            if(!notaSelector.getText().equals("") && filterButton.isSelected())
                return x.getGrade()==Double.parseDouble(notaSelector.getText());
            return true;
        };
        Predicate<Grade> byDate=x->{
            if(datePicker.getValue()!=null && filterButton.isSelected())
                return x.getUploaded().equals(datePicker.getValue());
            return true;
        };

        data.setAll(StreamSupport.stream(gradeService.findAll().spliterator(),false).filter(byCategory.and(byStudent).and(byGrade).and(byDate)).collect(Collectors.toList()));
    }

    public void updateAction(ActionEvent actionEvent) {
        try {

            gradeService.update(new Grade(table.getSelectionModel().getSelectedItem().getId(),studentSelector.getValue().getId(),categorySelector.getValue().getId(),Double.parseDouble(notaSelector.getText()),LocalDate.now()));
            errorLabel.setText("Nota salvata.");
        }catch (ValidationException ex){
            errorLabel.setText(ex.getMessage());
        }
        referesh();
    }

    private void referesh() {
        data.setAll(StreamSupport.stream(gradeService.findAll().spliterator(), false).collect(Collectors.toList()));
        students.setAll(StreamSupport.stream(studentService.findAll().spliterator(),false).collect(Collectors.toList()));
        categorySelector.setItems(FXCollections.observableArrayList(StreamSupport.stream(categoryService.findAll().spliterator(),false).collect(Collectors.toList())));
        table.setItems(data);
        studentSelector.setValue(null);
        notaSelector.setText("");
        categorySelector.setValue(null);
        datePicker.setValue(null);
        table.onMouseClickedProperty().setValue(null);

    }

    public void viewGrade(MouseEvent mouseEvent) {
        try {
            Grade grade = gradeService.findOne(table.getSelectionModel().getSelectedItem().getId());
            categorySelector.setValue(categoryService.findOne(grade.getCategory()));
            studentSelector.getSelectionModel().select(studentService.findOne(grade.getStudentID()));
            notaSelector.setText(Double.toString(grade.getGrade()));
        } catch (Exception e) {
        }
    }



}
