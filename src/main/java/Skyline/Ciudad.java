package Skyline;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import Edificios.Edificio;
import Punto.Punto;


/*
 Clase fundamental.
 Sirve para hacer la lectura del fichero de entrada que contiene los datos de como
 están situados los edificios en el fichero de entrada. xi, xd, h, siendo. Siendo
 xi la coordenada en X origen del edificio iésimo, xd la coordenada final en X, y h la altura del edificio.
 
 */
public class Ciudad {

	private ArrayList<Edificio> ciudad;

	public Ciudad() {

		/*
		 * Generamos una ciudad de manera aleatoria para hacer pruebas.
		 */
		ciudad = new ArrayList<Edificio>();
		int n = 5;
		metodoRandom(n);
		ciudad = new ArrayList<Edificio>();
	}

	public Edificio getEdificio(int i) {
		return (Edificio) this.ciudad.get(i);
	}

	public void addEdificio(Edificio e) {
		ciudad.add(e);
	}

	public void removeEdificio(int i) {
		ciudad.remove(i);
	}

	public int size() {
		return ciudad.size();
	}

	public LineaHorizonte getLineaHorizonte() {
		// pi y pd, representan los edificios de la izquierda y de la derecha.
		int pi = 0;
		int pd = ciudad.size() - 1;
		return crearLineaHorizonte(pi, pd);
	}

	public LineaHorizonte crearLineaHorizonte(int pi, int pd) {
		LineaHorizonte linea = new LineaHorizonte(); // LineaHorizonte de salida
// Caso base, la ciudad solo tiene un edificio, el perfil es el de ese edificio. 
		if (pi == pd) {
			Edificio edificio = this.getEdificio(pi); // Obtenemos el único edificio y lo guardo en b
// En cada punto guardamos la coordenada X y la altura.
			Punto p1 = new Punto(edificio.getXi(),edificio.getY());
			Punto p2 = new Punto(edificio.getXd(),0);
// Añado los puntos a la línea del horizonte
			linea.addPunto(p1);
			linea.addPunto(p2);
		} else {
// Edificio mitad
			int medio = (pi + pd) / 2;
			Punto a = null, b = null, aux = null;
			linea = lineaHorizonteFussion(this.crearLineaHorizonte(pi, medio),this.crearLineaHorizonte(medio + 1, pd));
		}
		return linea;
	}

	/**
	 * Función encargada de fusionar los dos LineaHorizonte obtenidos por la técnica
	 * divide y vencerás. Es una función muy compleja ya que es la encargada de
	 * decidir si un edificio solapa a otro, si hay edificios contiguos, etc. y
	 * solucionar dichos problemas para que el LineaHorizonte calculado sea el
	 * correcto.
	 */


	public LineaHorizonte lineaHorizonteFussion(LineaHorizonte s1, LineaHorizonte s2) {
		FusionLineaHorizonte lineaHorizonte = new FusionLineaHorizonte(s1,s2);
		while ((!lineaHorizonte.lh1.isEmpty()) && (!lineaHorizonte.lh2.isEmpty())) {
			lineaHorizonte.paux = new Punto();
			lineaHorizonte.p1 = lineaHorizonte.lh1.getPunto(0);
			lineaHorizonte.p2 = lineaHorizonte.lh2.getPunto(0);
			lineaHorizonte.puntoMenor = lineaHorizonte.getMenor();
			if (lineaHorizonte.p1.getX() != lineaHorizonte.p2.getX())
				lineaHorizonte.fusionPuntosDiferentes();
			else
				lineaHorizonte.fusionPuntosIguales();
		}
		lineaHorizonte.fusionOtrosCasos();
		return lineaHorizonte.getSalida();
	}
	/*
	 * Método que carga los edificios que me pasan en el archivo cuyo nombre se
	 * encuentra en "fichero". El formato del fichero nos lo ha dado el profesor en
	 * la clase del 9/3/2020, pocos días antes del estado de alarma.
	 */

	public void cargarEdificios(String fichero) {
		try {
			int xi, y, xd;
			Scanner sr = new Scanner(new File(fichero));
			while (sr.hasNext()) {
				this.addEdificio(new Edificio(sr.nextInt(), sr.nextInt(), sr.nextInt()));
			}
		} catch (Exception e) {
			System.err.println("Error en la carga del edificio: " + e.getMessage());
		}

	}

	public void metodoRandom(int n) {
		int i = 0;
		int xi, y, xd;
		for (i = 0; i < n; i++) {
			xi = (int) (Math.random() * 100);
			y = (int) (Math.random() * 100);
			xd = (int) (xi + (Math.random() * 100));
			this.addEdificio(new Edificio(xi, y, xd));
		}
	}
}
