package GUI;


import Entity.Discipline;
import Services.DisciplineService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainGUI extends Application {
    private static Properties props=new Properties();
    private GradeController gradeController;
    private StudentController studentController;
    private FinalGradesController finalGradesController;
    private static DisciplineService disciplineService;
    @FXML
    AnchorPane studentPane,gradesPane,classPane,finalPane;
    @FXML
    ComboBox<Discipline> disciplinaSelector;
@FXML
    Label label;
    @FXML
    public void initialize(){
        try {
            disciplinaSelector.setItems(FXCollections.observableArrayList(StreamSupport.stream(disciplineService.findAll().spliterator(),false).collect(Collectors.toList())));
            disciplinaSelector.getSelectionModel().selectFirst();
            studentController=new StudentController(props,disciplinaSelector.getValue().getId());
            gradeController=new GradeController(props,disciplinaSelector.getValue().getId());
            finalGradesController=new FinalGradesController(props,disciplinaSelector.getValue().getId());


            FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXML/ViewGrade.fxml"));
            loader.setController(gradeController);
            Parent parent=loader.load();
            gradesPane.getChildren().setAll(parent.getChildrenUnmodifiable());

            loader=new FXMLLoader(getClass().getResource("/FXML/ViewStudent.fxml"));
            loader.setController(studentController);
            parent=loader.load();
            studentPane.getChildren().setAll(parent.getChildrenUnmodifiable());

            loader=new FXMLLoader(getClass().getResource("/FXML/FinalGradesView.fxml"));
            loader.setController(finalGradesController);
            parent=loader.load();
            finalPane.getChildren().setAll(parent.getChildrenUnmodifiable());

            loader=new FXMLLoader(getClass().getResource("/FXML/ViewDiscipline.fxml"));
            parent=loader.load();
            classPane.getChildren().setAll(parent.getChildrenUnmodifiable());





        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        props.load(new FileInputStream("F:\\workshop\\MPP\\GradesManager\\src\\main\\resources\\connUtils.properties"));
        disciplineService=new DisciplineService(props);
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("/FXML/ViewMenu.fxml"));

        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void changeDis(ActionEvent actionEvent) {
        if(disciplinaSelector.getValue()!=null){
            studentController.setDisciplina(disciplinaSelector.getValue().getId());
            gradeController.setDisciplina(disciplinaSelector.getValue().getId());
        }
    }

    public void hideCombo(Event event) {
        disciplinaSelector.setDisable(true);
        disciplinaSelector.setVisible(false);
    label.setText("");
    }

    public void showCombo(Event event) {
        try {
            disciplinaSelector.setDisable(false);
            disciplinaSelector.setVisible(true);
            label.setText("Selectati o disciplina->>>>");
        }catch (Exception e){

        }

    }
}
