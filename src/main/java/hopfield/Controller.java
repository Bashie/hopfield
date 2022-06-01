package hopfield;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Controller {
	private static final double PINTA = 1d;
	private static final double VACIO = -1d;
	@FXML
	private ImageView imgResult;
	@FXML
	private GridPane gridPane;
	GraphicsContext graphicsContext2D;
	@FXML
	private JFXButton botonCargar;
	@FXML
	private JFXButton botonEntrenar;
	File dir;
	@FXML
	private ListView<Image> imagenesView;
	private ObservableList<Image> imagenes;
	Matriz pesos;
	@FXML
	private JFXButton botonEncontrar;
	private List<List<Double>> patrones = new ArrayList<>();
	public static int TAMANIO_IMAGEN = 10;
	@FXML
	private JFXButton btnclear;

	private void entrenar() {
		Matriz matrizIdentidad = Matriz.identidad(TAMANIO_IMAGEN * TAMANIO_IMAGEN, TAMANIO_IMAGEN * TAMANIO_IMAGEN);

		pesos = new Matriz(TAMANIO_IMAGEN * TAMANIO_IMAGEN, TAMANIO_IMAGEN * TAMANIO_IMAGEN);
		List<Double> temp;

		temp = Arrays.asList(PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, PINTA, PINTA, VACIO, VACIO, VACIO,
				VACIO, VACIO, VACIO, VACIO, VACIO, PINTA, PINTA, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, PINTA, PINTA, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, PINTA,
				PINTA, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, PINTA, PINTA, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, PINTA, PINTA, VACIO, VACIO, VACIO, VACIO, VACIO,
				VACIO, VACIO, VACIO, PINTA, PINTA, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA);
		patrones.add(temp);

		temp = Arrays.asList(PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, VACIO, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, PINTA, VACIO,
				VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO,
				VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO,
				VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO);
		patrones.add(temp);

		double[][] vectoresPatron = new double[patrones.size()][TAMANIO_IMAGEN * TAMANIO_IMAGEN];

		for (int i = 0; i < patrones.size(); i++) {
			for (int j = 0; j < TAMANIO_IMAGEN * TAMANIO_IMAGEN; j++) {
				vectoresPatron[i][j] = patrones.get(i).get(j);
			}
		}

		Matriz matrizDePatrones = new Matriz(vectoresPatron);
		matrizDePatrones.imprimir();

		Matriz wi;
		for (int i = 0; i < matrizDePatrones.getTamanioFilas(); i++) {
			Matriz tmp = new Matriz(patrones.get(i).toArray(), 1);
			wi = tmp.transponer().multiplicar(tmp).menos(matrizIdentidad);
			pesos = wi.sumar(pesos);
		}
		System.out.println("Pesos:");
		pesos.imprimir();
	}

	private void onBotonCargar() {
		List<Double> temp = Arrays.asList(VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO,
				VACIO, PINTA, PINTA, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, PINTA, PINTA, PINTA, PINTA, VACIO, VACIO, VACIO, VACIO, VACIO, PINTA, PINTA, VACIO, VACIO, PINTA, PINTA, VACIO,
				VACIO, VACIO, VACIO, PINTA, PINTA, VACIO, VACIO, PINTA, PINTA, VACIO, VACIO, VACIO, VACIO, VACIO, PINTA, PINTA, PINTA, PINTA, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, PINTA,
				PINTA, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO);
		
		pintarImagen(temp);

		gridPane.setGridLinesVisible(true);
		patrones.add(temp);

		Image imagenParaAgregar = guardarImagen();
		imgResult.setImage(imagenParaAgregar);
		limpiar();
		entrenar();
		botonEncontrar.setDisable(false);
		botonEncontrar.setStyle("-fx-background-color: #008080");
	}

	private Image guardarImagen() {
		WritableImage imagen = gridPane.snapshot(new SnapshotParameters(), new WritableImage((int) gridPane.getWidth(), (int) gridPane.getHeight()));

		File archivo = new File(dir.getAbsolutePath() + System.getProperty("file.separator") + "Img" + System.nanoTime() + ".png");
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(imagen, null), "png", archivo);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Image imagenParaAgregar = null;
		try {
			imagenParaAgregar = new Image(archivo.toURI().toURL().toString());
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		return imagenParaAgregar;
	}

	private void onBotonBorrar() {
		limpiar();
		imagenesView.getItems().clear();
	}

	private void limpiar() {
		gridPane.getChildren().forEach(n -> {
			if (n instanceof Celda) {
				Celda p = (Celda) n;
				p.setStyle("-fx-background-color:#fff");
				p.setColor(Color.WHITE);
			}

		});
	}

	private void onBotonBuscar() {
		Double[] t = new Double[TAMANIO_IMAGEN * TAMANIO_IMAGEN];
		ArrayList<Double> temp = new ArrayList<>();
		gridPane.getChildren().forEach(n -> {
			if (n instanceof Celda) {
				Celda p = (Celda) n;
				temp.add(p.getColor().equals(Color.WHITE) ? VACIO : PINTA);
			}
		});
		Boolean empty = true;
		for (int i = 0; i < temp.size(); i++) {
			t[i] = temp.get(i);
			empty = empty && (t[i] == VACIO);
		}
		if (empty) {
			//Si esta vacio uso el ejemplo de la lectura complementaria del TP3
			Double[] tmp = { PINTA, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, PINTA, VACIO, VACIO, VACIO, PINTA, VACIO, VACIO, PINTA, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO, VACIO,
					VACIO, VACIO, PINTA, PINTA, VACIO, VACIO, VACIO, PINTA, VACIO, VACIO, VACIO, PINTA, PINTA, PINTA, PINTA, VACIO, VACIO, VACIO, VACIO, VACIO, PINTA, PINTA, VACIO, VACIO, PINTA,
					PINTA, VACIO, VACIO, VACIO, VACIO, PINTA, PINTA, VACIO, VACIO, PINTA, PINTA, VACIO, PINTA, VACIO, VACIO, VACIO, PINTA, PINTA, PINTA, PINTA, VACIO, VACIO, VACIO, VACIO, VACIO,
					VACIO, VACIO, PINTA, PINTA, VACIO, VACIO, PINTA, VACIO, PINTA, VACIO, VACIO, VACIO, PINTA, VACIO, VACIO, PINTA, PINTA, PINTA, VACIO, PINTA, VACIO, VACIO, VACIO, VACIO, VACIO,
					VACIO };
			t = tmp;
		}
		Matriz matrizABuscar = new Matriz(t, TAMANIO_IMAGEN * TAMANIO_IMAGEN);
		Matriz S = matrizABuscar.transponer().multiplicar(pesos);
		S = volverAPintadoOVacio(S);

		Matriz t1, pattern = null;
		limpiar();
		pintarImagen(S.getAsList());
		Image imagen = guardarImagen();
		imagenesView.getItems().add(imagen);
		for (int i = 0; i < 100; i++) {
			t1 = S.multiplicar(pesos);
			t1 = volverAPintadoOVacio(t1);
			limpiar();
			pintarImagen(t1.getAsList());
			imagen = guardarImagen();
			imagenesView.getItems().add(imagen);
			if (compareTo(S, t1)) {
				if (isEncontrado(t1)) {
					pattern = t1;
				}
				break;
			}
			S = t1;
		}
		limpiar();
		if (pattern == null) {
			System.out.println("No se encontro");
		}
	}

	private Boolean isEncontrado(Matriz pattern) {
		for (int i = 0; i < patrones.size(); i++) {
			for (int j = 0, k = 0; j < patrones.get(0).size(); j++) {
				if (pattern.get(0, j) == patrones.get(i).get(j)) {
					k++;
				}
				if (k == pattern.getTamanioColumnas()) {
					return true;
				}
			}
		}
		return false;
	}

	private void pintarImagen(List<Double> temp) {
		gridPane.getChildren().forEach(n -> {
			if (n instanceof Celda) {
				Celda p = (Celda) n;
				if (temp.get(p.getPosx() * TAMANIO_IMAGEN + p.getPosy()) == PINTA) {
					p.setStyle("-fx-background-color:#000");
					p.setColor(Color.BLACK);
				}
			}
		});
	}

	private boolean compareTo(Matriz a, Matriz b) {
		for (int i = 0; i < a.getTamanioFilas(); i++) {
			for (int j = 0; j < a.getTamanioColumnas(); j++) {
				if (a.get(i, j) != b.get(i, j)) {
					return false;
				}
			}
		}
		return true;
	}

	private Matriz volverAPintadoOVacio(Matriz x) {
		for (int i = 0; i < TAMANIO_IMAGEN * TAMANIO_IMAGEN; i++) {
			x.set(0, i, x.get(0, i) >= 0 ? PINTA : VACIO);
		}
		return x;
	}

	@FXML
	void initialize() {

		File f = new File(System.getProperty("user.home"));
		dir = new File(f, "DataHopfield");

		botonEncontrar.setOnMouseClicked(e -> onBotonBuscar());
		botonEncontrar.setDisable(true);
		botonCargar.setOnMouseClicked(e -> onBotonCargar());
		imagenes = FXCollections.observableArrayList();
		imagenesView.setItems(imagenes);
		btnclear.setOnMouseClicked(e -> onBotonBorrar());
		imagenesView.setCellFactory(l -> new ListCell<Image>() {

			@Override
			protected void updateItem(Image item, boolean empty) {
				super.updateItem(item, empty);

				if (empty) {
					setText("");
					setGraphic(null);
				} else {
					setText("");
					setGraphic(new ImageView(item));
				}
			}
		});

		for (int i = 0; i < TAMANIO_IMAGEN; i++) {
			for (int j = 0; j < TAMANIO_IMAGEN; j++) {
				Celda p = new Celda();
				p.setOnMouseClicked(event -> {
					p.setStyle("-fx-background-color:#000");
					p.setColor(Color.BLACK);
				});
				p.setPosx(i);
				p.setPosy(j);
				gridPane.add(p, j, i);
			}
		}
		gridPane.setGridLinesVisible(true);
	}

}
