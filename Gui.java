// Anton Sandström ansa6928
// Karl Jonsson

import java.util.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Gui extends Application{
	private TextArea textArea;
	private RadioButton namnSortering;
	private RadioButton vardeSortering;
	ArrayList<Vardesaker> allaSaker = new ArrayList<>();
	
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
		rightInLeft.getChildren().addAll(sortering, namnSortering, vardeSortering);
		
		HBox bottom = new HBox();
		HBox bottomCentering = new HBox();
		bottomCentering.setSpacing(10);
		bottom.setAlignment(Pos.CENTER);
		bottom.setPadding(new Insets(10,0,10,0));
		root.setBottom(bottom);
		bottom.getChildren().add(bottomCentering);
		bottomCentering.getChildren().addAll(ny, comboBox, visa, borsKrasch);
		
		visa.setOnAction(new ShowHandler());
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
}
	
	class NewHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			
		}
	}
	
	class ShowHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			textArea.setText("");
			textArea.setText("This is working");
			
			if (namnSortering.isSelected()) {
				allaSaker.sort(new NameComparator());
				for (Vardesaker sak : allaSaker) {
					textArea.appendText(sak.toString());
					textArea.appendText("\n");
					
				}
				
			}
			
			else if (vardeSortering.isSelected()) {
				allaSaker.sort(new VardeComparator());
				for (Vardesaker sak : allaSaker) {
					textArea.appendText(sak.toString());
					textArea.appendText("\n)");
				}
			}
		}
	}
	
	class ExchangeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			for (Vardesaker sak : allaSaker) {
				if (sak instanceof Aktie) {
					Aktie a = (Aktie) sak;
					a.setNyKurs(0);
				}
			}
		}
	}
	
	class NameComparator implements Comparator<Vardesaker> {
		public int compare (Vardesaker v1, Vardesaker v2) {
			return v1.getNamn().compareTo(v2.getNamn());
		}
	}
	
	class VardeComparator implements Comparator<Vardesaker> {
		public int compare (Vardesaker v1, Vardesaker v2) {
			return Double.compare(v2.getRealVarde(), v1.getRealVarde());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
