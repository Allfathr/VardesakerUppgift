// Anton Sandström 960726-4430
// Karl Jonsson 980226-8293

import java.util.*;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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
		textArea.setEditable(false);
		
		ObservableList<String> val = FXCollections.observableArrayList("Smycke", "Aktie", "Apparat");
		ComboBox<String> comboBox = new ComboBox<>(val);
		
		Button visa = new Button("Visa");
		Button borsKrasch = new Button("Börskrasch");
		
		ToggleGroup group = new ToggleGroup();
		namnSortering = new RadioButton("Namn");
		namnSortering.setToggleGroup(group);
		namnSortering.setSelected(true);
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
		borsKrasch.setOnAction(new ExchangeHandler());
		comboBox.setOnAction(new NewHandler());
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Sakregister");
}
	class SmyckenAlert extends Alert {
		private TextField nameField = new TextField();
		private TextField stoneField = new TextField();
		private CheckBox box = new CheckBox("Guld");

		SmyckenAlert() {
			super(AlertType.CONFIRMATION);

			GridPane grid = new GridPane();
			grid.addRow(0, new Label("Typ:"), nameField);
			grid.addRow(1,  new Label("Adelsstenar:"), stoneField);
			grid.addRow(2, box);

			getDialogPane().setContent(grid);
		}
		public String getNamn() {
			return nameField.getText();
		}

		public int getStenar() {
			return Integer.parseInt(stoneField.getText());
		}

		public boolean isGuld() {
			return box.isSelected();
		}
	}
	
	class AktieAlert extends Alert {
		private TextField nameField = new TextField();
		private TextField antalField = new TextField();
		private TextField kurs = new TextField();
		
		AktieAlert() {
			super(AlertType.CONFIRMATION);
			GridPane grid = new GridPane();
			grid.addRow(0, new Label("Namn:"), nameField);
			grid.addRow(1,  new Label("Antal:"), antalField);
			grid.addRow(2, new Label("Kurs:"), kurs);

			getDialogPane().setContent(grid);
		}
		
		public String getNamn() {
			return nameField.getText();
		}
		
		public int getAntal() {
			return Integer.parseInt(antalField.getText());
		}
		
		public double getKurs() {
			return Double.parseDouble(kurs.getText());
		}
	}
	
	class ApparatAlert extends Alert {
		private TextField nameField = new TextField();
		private TextField priceField = new TextField();
		private TextField slitage = new TextField();
		
		ApparatAlert() {
			super(AlertType.CONFIRMATION);
			GridPane grid = new GridPane();
			grid.addRow(0, new Label("Typ av föremål:"), nameField);
			grid.addRow(1,  new Label("Pris:"), priceField);
			grid.addRow(2, new Label("Slitage"), slitage);

			getDialogPane().setContent(grid);

		}
		
		public String getNamn() {
			return nameField.getText();
		}
		
		public double getPris() {
			return Double.parseDouble(priceField.getText());
		}
		
		public int getSlitage() {
			return Integer.parseInt(slitage.getText());
		}
	}
	
	class NewHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				ComboBox cb = (ComboBox) event.getSource();
				String choice = (String) cb.getValue();
				
				if (choice.equalsIgnoreCase("smycke")) {
					SmyckenAlert sa = new SmyckenAlert();
					Optional<ButtonType> result = sa.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.OK) {
						String namn = sa.getNamn();
						if (namn.trim().isEmpty()) {
							Alert msg = new Alert(AlertType.ERROR, "Tomt namn!");
							msg.showAndWait();
						}
					}
					Smycken s = new Smycken(sa.getNamn(), sa.getStenar(), sa.isGuld());
					allaSaker.add(s);

				}

				else if (choice.equalsIgnoreCase("aktie")) {
					AktieAlert aa = new AktieAlert();
					Optional<ButtonType> result = aa.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.OK) {
						String namn = aa.getNamn();
						if (namn.trim().isEmpty()) {
							Alert msg = new Alert(AlertType.ERROR, "Tomt namn!");
							msg.showAndWait();
						}
					}
					Aktie a = new Aktie(aa.getNamn(), aa.getKurs(), aa.getAntal());
					allaSaker.add(a);
				}

				else if (choice.equalsIgnoreCase("apparat")) {
					ApparatAlert appAl = new ApparatAlert();
					Optional<ButtonType> result = appAl.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.OK) {
						String namn = appAl.getNamn();
						if (namn.trim().isEmpty()) {
							Alert msg = new Alert(AlertType.ERROR, "Tomt namn!");
							msg.showAndWait();
						}
					}
					Apparat a = new Apparat(appAl.getNamn(), appAl.getPris(), appAl.getSlitage());
					allaSaker.add(a);
				}
						
			} catch (NumberFormatException e) {
				Alert msg = new Alert(AlertType.ERROR);
				msg.setContentText("Fel inmatning!");
				msg.showAndWait();
			}
		}
	}
	
	class ShowHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			textArea.setText("");		
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
					textArea.appendText("\n");
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
