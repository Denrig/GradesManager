package GUI;

import Entity.Category;
import Entity.Discipline;
import Entity.Grade;
import Entity.Semester;
import Exceptions.ValidationException;
import Services.CategoryService;
import Services.DisciplineService;
import Services.SemesterService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DisciplineController {
    @FXML
    Label errorLabel;
    @FXML
    public void initialize() throws IOException {
    Properties props=new Properties();
    props.load(new FileInputStream("F:\\workshop\\MPP\\GradesManager\\src\\main\\resources\\connUtils.properties"));
    disciplineService=new DisciplineService(props);
    semesterService=new SemesterService(props);
    categoryService=new CategoryService(1l,props);

    disciplines.setAll(StreamSupport.stream(disciplineService.findAll().spliterator(),false).collect(Collectors.toList()));

    idDisciplina.setCellValueFactory(new PropertyValueFactory<>("id"));
    numeDisciplina.setCellValueFactory(new PropertyValueFactory<>("name"));
    disciplinaSemestru.setCellValueFactory(new PropertyValueFactory<>("semester"));
    tabelDisciplina.setItems(disciplines);
    tabelDisciplina.onMouseClickedProperty().setValue(this::observer);

    semestruSelector.setItems(FXCollections.observableArrayList(StreamSupport.stream(semesterService.findAll().spliterator(),false).collect(Collectors.toList())));

    categories.setAll(StreamSupport.stream(categoryService.findAll().spliterator(),false).collect(Collectors.toList()));

    catId.setCellValueFactory(new PropertyValueFactory<>("id"));
    catNume.setCellValueFactory(new PropertyValueFactory<>("name"));
    catProcent.setCellValueFactory(new PropertyValueFactory<>("percentage"));
    catDis.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Category, String>, ObservableValue<String>>() {
      @Override
      public ObservableValue<String> call(TableColumn.CellDataFeatures<Category, String> param) {
       return  new ReadOnlyObjectWrapper(disciplineService.findOne(param.getValue().getDisciplinaID()).getName());
      }
     });

    tabelCat.setItems(categories);

    }

    private void observer(MouseEvent mouseEvent){
     if(tabelDisciplina.getSelectionModel().getSelectedItem()==null){
      tabelDisciplina.getSelectionModel().select(1);
       categoryService.setDisciplina(tabelDisciplina.getSelectionModel().getSelectedItem().getId());

     }else
      categoryService.setDisciplina(tabelDisciplina.getSelectionModel().getSelectedItem().getId());
     refreshCat();
    }



    //CLASS
    @FXML
    VBox inputPane;
    @FXML
    TableView<Discipline> tabelDisciplina;
    @FXML
    TableColumn<Discipline,Long> idDisciplina,disciplinaSemestru;
    @FXML
    TableColumn<Discipline,String> numeDisciplina;
    @FXML
    TextField numeInputDisciplina;
    @FXML
    ComboBox<Semester> semestruSelector;
    @FXML
    Button disciplinaExecute;

    private DisciplineService disciplineService;
    private SemesterService semesterService;
    private ObservableList<Discipline> disciplines= FXCollections.observableArrayList();

    public void disciplinaAdd(ActionEvent actionEvent) {
      numeInputDisciplina.setText("");
      semestruSelector.setValue(null);
           disciplinaExecute.onActionProperty().setValue(this::addAction);
           inputPane.setVisible(true);
           disciplinaExecute.setVisible(true);
    }

    public void disciplinaDelete(ActionEvent actionEvent) {
     Discipline grade=tabelDisciplina.getSelectionModel().getSelectedItem();
     if(grade!=null) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setHeaderText("Sunteti sigur ca vreti sa stergeti nota studentului?");
      alert.setContentText(tabelDisciplina.getSelectionModel().getSelectedItem().toString());
      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK)
      {disciplineService.delete(tabelDisciplina.getSelectionModel().getSelectedItem().getId());
       errorLabel.setText("Disciplina stearsa");
      }
      else
       errorLabel.setText("Disciplina nu a fost stearsa");
     }
     else {
      errorLabel.setText("O disciplina trebuie sa fie selectat");
     }
     refreshDisc();

    }

    public void disciplinaUpd(ActionEvent actionEvent) {
       Discipline discipline=tabelDisciplina.getSelectionModel().getSelectedItem();
       if(discipline!=null){
          inputPane.setVisible(true);
          disciplinaExecute.onActionProperty().setValue(x->updateAction(x));
         tabelDisciplina.onMouseClickedProperty().setValue(this::viewDisc);
         viewDisc(null);
         disciplinaExecute.setVisible(true);
       }
       else {
        errorLabel.setText("O disciplina trebuie sa fie selectat");
       }
    }

    private void updateAction(ActionEvent actionEvent){
     try {
       disciplineService.update(new Discipline(tabelDisciplina.getSelectionModel().getSelectedItem().getId(),semestruSelector.getValue().getId(),numeInputDisciplina.getText()));
       errorLabel.setText("Disciplina schimbata cu succes");

         }
     catch (ValidationException ex){

             errorLabel.setText(ex.getMessage());
                                    }
     refreshDisc();
    }

    private void viewDisc(MouseEvent mouseEvent){
     Discipline discipline=tabelDisciplina.getSelectionModel().getSelectedItem();
         numeInputDisciplina.setText(discipline.getName());
         semestruSelector.setValue(semesterService.findOne(discipline.getSemester()));
    }

    private void addAction(ActionEvent actionEvent){
        try {
         disciplineService.add(new Discipline(semestruSelector.getValue().getId(),numeInputDisciplina.getText()));
         errorLabel.setText("Disciplina incarcata cu succes");
         refreshDisc();
        }
        catch (ValidationException ex){
         errorLabel.setText(ex.getMessage());
        }
    }

    public void refreshDisc(){
     disciplines.setAll(StreamSupport.stream(disciplineService.findAll().spliterator(),false).collect(Collectors.toList()));
     inputPane.setVisible(false);
     disciplinaExecute.setVisible(false);
     disciplinaExecute.onActionProperty().setValue(null);
     tabelDisciplina.onMouseClickedProperty().setValue(this::observer);
    }

   //CATEGORY
   @FXML
   VBox inputPaneCat;
 @FXML
 TableView<Category> tabelCat;
 @FXML
 TableColumn<Category,Long> catId;
 @FXML
 TableColumn<Category,String> catNume,catDis;
 @FXML
 TableColumn<Category,Integer> catProcent;
 @FXML
 TextField catInputNume,catInputNota;
 @FXML
 Button catExecute;


  private CategoryService categoryService;
  private ObservableList<Category> categories=FXCollections.observableArrayList();

    public void catAdd(ActionEvent actionEvent) {
       catInputNota.setText("");
       catInputNume.setText("");
       inputPaneCat.setVisible(true);
       catExecute.onActionProperty().setValue(this::addActionCat);
       catExecute.setVisible(true);
    }

    private void addActionCat(ActionEvent actionEvent) {
     try {
        categoryService.add(new Category(catInputNume.getText(),Integer.valueOf(catInputNota.getText()),tabelDisciplina.getSelectionModel().getSelectedItem().getId()));

      }catch (ValidationException ex){
      errorLabel.setText(ex.getMessage());
     }
     catch (NullPointerException ex){
      errorLabel.setText("Selectati o disciplina");
     }
     refreshCat();
 }

    public void catDelete(ActionEvent actionEvent) {
  Category grade=tabelCat.getSelectionModel().getSelectedItem();
  if(grade!=null) {
   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
   alert.setHeaderText("Sunteti sigur ca vreti sa stergeti nota studentului?");
   alert.setContentText(tabelCat.getSelectionModel().getSelectedItem().toString());
   Optional<ButtonType> result = alert.showAndWait();
   if (result.get() == ButtonType.OK)
   {categoryService.delete(tabelCat.getSelectionModel().getSelectedItem().getId());
    errorLabel.setText("Categoria stearsa");
   }
   else
    errorLabel.setText("Categoria nu a fost stearsa");
  }
  else {
   errorLabel.setText("O categorie trebuie sa fie selectat");
  }
  refreshCat();
    }

    public void catUpd(ActionEvent actionEvent) {
     Category discipline=tabelCat.getSelectionModel().getSelectedItem();
     if(discipline!=null){
      inputPaneCat.setVisible(true);

      catExecute.onActionProperty().setValue(this::updateActionCat);
      tabelCat.onMouseClickedProperty().setValue(this::viewCat);
      viewCat(null);
      catExecute.setVisible(true);
     }
     else {
      errorLabel.setText("O categorie trebuie sa fie selectat");
     }
    }

    private void viewCat(MouseEvent mouseEvent) {
  Category discipline=tabelCat.getSelectionModel().getSelectedItem();
  catInputNume.setText(discipline.getName());
  catInputNota.setText(Integer.toString(discipline.getPercentage()));
 }

     private void updateActionCat(ActionEvent actionEvent) {
  try {

   categoryService.update(new Category(tabelCat.getSelectionModel().getSelectedItem().getId(),catInputNume.getText(),Integer.valueOf(catInputNota.getText()),tabelDisciplina.getSelectionModel().getSelectedItem().getId()));

  }catch (ValidationException ex){
   errorLabel.setText(ex.getMessage());
  }
  catch (NullPointerException ex){
   errorLabel.setText("Selectati o disciplina");
  }
  refreshCat();
 }

     private void refreshCat(){
     catExecute.onActionProperty().setValue(null);
     inputPaneCat.setVisible(false);
     catExecute.setVisible(false);
     categories.setAll(StreamSupport.stream(categoryService.findAll().spliterator(),false).collect(Collectors.toList()));

    }
}
