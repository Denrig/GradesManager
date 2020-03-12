package GUI;

import Entity.Grade;
import Entity.Student;
import Services.GradeService;
import Services.StudentService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FinalGradesController {
   private Long disciplina;
   private GradeService gradeService;
   private StudentService studentService;
   ObservableList<Student> grades= FXCollections.observableArrayList();
    @FXML
    TableView<Student> table;
    @FXML
    TableColumn<Student,String> student;
    @FXML
    TableColumn<Student,Double> nota;
    @FXML
    TextField search;
    @FXML
    public void initialize(){
        grades.setAll(StreamSupport.stream(studentService.findAll().spliterator(),false).collect(Collectors.toList()));
        student.setCellValueFactory(new PropertyValueFactory<>("nume"));
        nota.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Student, Double> param) {
                return  new ReadOnlyObjectWrapper(gradeService.getFinalGrade(param.getValue(),disciplina));
            }
        });
        table.setItems(grades);

    }
    public FinalGradesController(Properties properties, Long disciplina){
        this.disciplina=disciplina;
        gradeService=new GradeService(properties,disciplina);
        studentService=new StudentService(properties,disciplina);
    }



    public void refresh(ActionEvent actionEvent) {
        grades.setAll(StreamSupport.stream(studentService.findAll().spliterator(),false).collect(Collectors.toList()));
    }
}
