package GUI;


import Entity.Student;
import Exceptions.ValidationException;
import Services.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentController {
    @FXML
    VBox addPane,deletePane,updatePane,filterPane;
    @FXML
    TableView<Student> table;
    @FXML
    TableColumn<Long,Student> id;
    @FXML
    TableColumn<Integer,Student> grupa,an;
    @FXML
    TableColumn<String,Student> nume,email;
    @FXML
    TextArea Year,Group,Nume,Email,YearU,GroupU,NumeU,EmailU,filterTextAn,filterTextNume,filterTextGrupa;
    @FXML
    Label errorLabel;
    StudentService service;
    ObservableList<Student> data= FXCollections.observableArrayList();

    private Long disciplina=1l;

    public Long getDisciplina() {
        return disciplina;
    }

    public StudentController(Properties prop,Long disciplina) {
        this.disciplina = disciplina;
        service=new StudentService(prop,disciplina);
    }

    public void setDisciplina(Long disciplina) {
        this.disciplina = disciplina;
        service.setDisciplina(disciplina);
        refresh();
    }

    @FXML
    public void initialize() throws IOException {
        Properties props=new Properties();
        props.load(new FileInputStream("F:\\workshop\\MPP\\GradesManager\\src\\main\\resources\\connUtils.properties"));
        service=new StudentService(props,disciplina);
        System.out.println(StreamSupport.stream(service.findAll().spliterator(),false).collect(Collectors.toList()));
        data.addAll(StreamSupport.stream(service.findAll().spliterator(),false).collect(Collectors.toList()));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        an.setCellValueFactory(new PropertyValueFactory<>("an"));
        grupa.setCellValueFactory(new PropertyValueFactory<>("grupa"));
        nume.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        table.setItems(data);
        filterTextNume.textProperty().addListener(s -> filter());
        filterTextAn.textProperty().addListener(s -> filter());
        filterTextGrupa.textProperty().addListener(s -> filter());

    }


    public void addButtonAction(ActionEvent actionEvent) {
        setFalse();
        addPane.setVisible(true);
    }

    public void updateButtonAction(ActionEvent actionEvent) {
        if(table.getSelectionModel().getSelectedItem()==null){
            errorLabel.setText("Un student trebuie selectat");
        }
        else{
            errorLabel.setText("");

        setFalse();
        updatePane.setVisible(true);}
    }

    public void filterButtonAction(ActionEvent actionEvent) {
        setFalse();
        filterPane.setVisible(true);
    }

    public void deletePaneAction(ActionEvent actionEvent) {
        if(table.getSelectionModel().getSelectedItem()==null){
            errorLabel.setText("Un student trebuie selectat");
        }
        else{
            errorLabel.setText("");
        setFalse();
        deletePane.setVisible(true);}
    }

    public void addAction(ActionEvent actionEvent) {
       try {
           service.add(new Student(service.getLastID(),Integer.parseInt(Group.getText()),Integer.parseInt(Year.getText()),Email.getText(),Nume.getText()));
       }catch (ValidationException ex){
           errorLabel.setText(ex.getMessage());
       }

        refresh();
    }

    public void updateAction(ActionEvent actionEvent) {
        try {
            Student student=table.getSelectionModel().getSelectedItem();
            service.update(new Student(student.getId(),Integer.valueOf(GroupU.getText()),Integer.valueOf(YearU.getText()),EmailU.getText(),NumeU.getText()));
        }catch (ValidationException ex){
            errorLabel.setText(ex.getMessage());
        }

        refresh();
    }

    public void deleteAction(ActionEvent actionEvent) {
        service.delete(table.getSelectionModel().getSelectedItem().getId());
        refresh();

    }

    public void cancelAction(ActionEvent actionEvent) {
        setFalse();
    }

    private void filter(){
        data.setAll(service.filter(filterTextGrupa.getText(),filterTextAn.getText(),filterTextNume.getText()));
    }

    private void setFalse(){
        addPane.setVisible(false);
        updatePane.setVisible(false);
        deletePane.setVisible(false);
        filterPane.setVisible(false);
    }

    public void display(MouseEvent mouseEvent) {
        Student student=table.getSelectionModel().getSelectedItem();
        if(student!=null){
            NumeU.setText(student.getNume());
            EmailU.setText(student.getEmail());
            YearU.setText(Integer.toString(student.getAn()));
            GroupU.setText(Integer.toString(student.getGrupa()));
        }}

    private void refresh(){
        data.setAll(StreamSupport.stream(service.findAll().spliterator(),false).collect(Collectors.toList()));
    }
}
