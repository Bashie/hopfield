package hopfield;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Matriz {

	private double[][] mat;

	private int col, fila;

	public Matriz(int m, int n) {
		this.col = m;
		this.fila = n;
		mat = new double[m][n];
	}

	public void imprimir(NumberFormat format) {
		int width = 2;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < fila; j++) {
				String s = format.format(mat[i][j]); 
				int padding = Math. max(1, width - s.length());
				for (int k = 0; k < padding; k++) {
					buffer.append(' ');
				}
				buffer.append(s);
			}
			System.out.println(buffer);
			buffer = new StringBuffer();
		}
	}

	public double[][] getArray() {
		return mat;
	}

	public List<Double> getAsList() {
		List<Double> resultado = new ArrayList<>();
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < fila; j++) {
				resultado.add(mat[i][j]);
			}
		}
		return resultado;
	}
	
	public Matriz sumar(Matriz B) {
		controlarDimensiones(B);
		Matriz X = new Matriz(col, fila);
		double[][] C = X.getArray();
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < fila; j++) {
				C[i][j] = mat[i][j] + B.mat[i][j];
			}
		}
		return X;
	}

	private void controlarDimensiones(Matriz B) {
		if (B.col != col || B.fila != fila) {
			throw new IllegalArgumentException("Las dimenciones son distintas.");
		}
	}

	public Matriz getMatriz(int i0, int i1, int j0, int j1) {
		Matriz X = new Matriz(i1 - i0 + 1, j1 - j0 + 1);
		double[][] B = X.getArray();
		try {
			for (int i = i0; i <= i1; i++) {
				for (int j = j0; j <= j1; j++) {
					B[i - i0][j - j0] = mat[i][j];
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Indices incorrectos");
		}
		return X;
	}

	public Matriz(double[][] A) {
		col = A.length;
		fila = A[0].length;
		for (int i = 0; i < col; i++) {
			if (A[i].length != fila) {
				throw new IllegalArgumentException("Todas las filas tienen que tener el mismo largo");
			}
		}
		this.mat = A;
	}

	public Matriz(double vals[], int m) {
		this.col = m;
		fila = (m != 0 ? vals.length / m : 0);
		if (m * fila != vals.length) {
			throw new IllegalArgumentException("El largo debe ser m.");
		}
		mat = new double[m][fila];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < fila; j++) {
				mat[i][j] = vals[i + j * m];
			}
		}
	}

	public int getTamanioFilas() {
		return col;
	}

	public int getTamanioColumnas() {
		return fila;
	}

	public double get(int i, int j) {
		return mat[i][j];
	}

	public void set(int i, int j, double s) {
		mat[i][j] = s;
	}

	public void imprimir() {
		DecimalFormat format = new DecimalFormat();
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		format.setMinimumIntegerDigits(1);
		format.setGroupingUsed(false);
		imprimir(format);
	}

	public static Matriz identidad(int m, int n) {
		Matriz A = new Matriz(m, n);
		double[][] X = A.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X[i][j] = (i == j ? 1.0 : 0.0);
			}
		}
		return A;
	}

	public Matriz multiplicar(Matriz B) {
		if (B.col != fila) {
			throw new IllegalArgumentException("Las dimensiones son distintas");
		}
		Matriz X = new Matriz(col, B.fila);
		double[][] C = X.getArray();
		double[] Bcolj = new double[fila];
		for (int j = 0; j < B.fila; j++) {
			for (int k = 0; k < fila; k++) {
				Bcolj[k] = B.mat[k][j];
			}
			for (int i = 0; i < col; i++) {
				double[] Arowi = mat[i];
				double s = 0;
				for (int k = 0; k < fila; k++) {
					s += Arowi[k] * Bcolj[k];
				}
				C[i][j] = s;
			}
		}
		return X;
	}

	public Matriz transponer() {
		Matriz X = new Matriz(fila, col);
		double[][] C = X.getArray();
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < fila; j++) {
				C[j][i] = mat[i][j];
			}
		}
		return X;
	}

	public Matriz menos(Matriz B) {
		controlarDimensiones(B);
		Matriz X = new Matriz(col, fila);
		double[][] C = X.getArray();
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < fila; j++) {
				C[i][j] = mat[i][j] - B.mat[i][j];
			}
		}
		return X;
	}
}
