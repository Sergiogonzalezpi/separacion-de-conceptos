package Dominio;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class LineaHorizonte {

	private ArrayList<Punto> LineaHorizonte;

	/*
	 * Constructor sin par�metros
	 */
	public LineaHorizonte() {
		LineaHorizonte = new ArrayList<Punto>();
	}
	public void setLineaHorizonte(ArrayList<Punto> lh) {
		this.LineaHorizonte = lh;
	}
	
	public ArrayList<Punto> getLineaHorizonte(){
		return this.LineaHorizonte;
	}

	/*
	 * m�todo que devuelve un objeto de la clase Punto
	 */
	public Punto getPunto(int i) {
		return (Punto) this.LineaHorizonte.get(i);
	}

	// A�ado un punto a la l�nea del horizonte
	public void addPunto(Punto p) {
		LineaHorizonte.add(p);
	}

	// m�todo que borra un punto de la l�nea del horizonte
	public void borrarPunto(int i) {
		LineaHorizonte.remove(i);
	}

	public int size() {
		return LineaHorizonte.size();
	}

	// m�todo que me dice si la l�nea del horizonte est� o no vac�a
	public boolean isEmpty() {
		return LineaHorizonte.isEmpty();
	}
	

	/*
	 * M�todo al que le pasamos una serie de par�metros para poder guardar la linea
	 * del horizonte resultante despu�s de haber resuelto el ejercicio mediante la
	 * t�cnica de divide y vencer�s.
	 */

	public void guardaLineaHorizonte(String fichero) {
		try {
			FileWriter fileWriter = new FileWriter(fichero);
			PrintWriter out = new PrintWriter(fileWriter);
			for (int i = 0; i < this.size(); i++) {
				out.println(getPunto(i).getX() + " " + getPunto(i).getY());
			}
			out.close();
		} catch (Exception e) {
			System.err.println("Error en la carga del edificio: " + e.getMessage());
		}
	}

	public void imprimir() {

		for (int i = 0; i < LineaHorizonte.size(); i++) {
			System.out.println(cadena(i));
		}
	}

	public String cadena(int i) {
		Punto p = LineaHorizonte.get(i);
		int x = p.getX();
		int y = p.getY();
		return p.toString();
	}
}
