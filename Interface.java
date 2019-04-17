import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ComboBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ToggleGroup;

public class Interface extends Application{
	
	private RadioButton namnSortering;
	private RadioButton vardeSortering;
	
	private TextArea textArea;
	
	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		
		Label rubrik = new Label("Värdesaker");
		Label ny = new Label("Ny:");
		Label sortering = new Label("Sortering");
		
		textArea = new TextArea();
		textArea.setPrefHeight(300);
		
		ObservableList<String> val = FXCollections.observableArrayList("Smycke", "Aktie", "Apparat");
		ComboBox<String> comboBox = new ComboBox<>(val);
		comboBox.setPromptText("Välj värdesak:");
		
		Button visa = new Button("Visa");
		Button borsKrasch = new Button("Börskrasch");
		
		ToggleGroup group = new ToggleGroup();
		namnSortering = new RadioButton("Namn");
		namnSortering.setToggleGroup(group);
		vardeSortering = new RadioButton("Värde");
		vardeSortering.setToggleGroup(group);
		
		rubrik.setStyle("-fx-font-weight: bold");
		ny.setStyle("-fx-font-weight: bold");
		sortering.setStyle("-fx-font-weight: bold");
		visa.setStyle("-fx-font-weight: bold");
		borsKrasch.setStyle("-fx-font-weight: bold");
		namnSortering.setStyle("-fx-font-weight: bold");
		vardeSortering.setStyle("-fx-font-weight: bold");
		
		
		FlowPane top = new FlowPane();
		top.setAlignment(Pos.CENTER);
		top.setPadding(new Insets(5,0,5,0));
		root.setTop(top);
		top.getChildren().add(rubrik);
		
		HBox left = new HBox();
		root.setLeft(left);
		left.getChildren().add(textArea);
		VBox rightInLeft = new VBox();
		rightInLeft.setSpacing(10);
		rightInLeft.setPadding(new Insets(10,10,0,10));
		left.getChildren().add(rightInLeft);
		rightInLeft.getChildren().add(sortering);
		rightInLeft.getChildren().add(namnSortering);
		rightInLeft.getChildren().add(vardeSortering);
		
		HBox bottom = new HBox();
		HBox bottomCentering = new HBox();
		bottomCentering.setSpacing(10);
		bottom.setAlignment(Pos.CENTER);
		bottom.setPadding(new Insets(10,0,10,0));
		root.setBottom(bottom);
		bottom.getChildren().add(bottomCentering);
		bottomCentering.getChildren().add(ny);
		bottomCentering.getChildren().add(comboBox);
		bottomCentering.getChildren().add(visa);
		bottomCentering.getChildren().add(borsKrasch);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main (String[] args) {
		Application.launch(args);
	}
}
