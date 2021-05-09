package Skyline;

import Dominio.Punto;

public class FusionLineaHorizonte {

	private int prev;
	private int s2y;
	private int s1y;

	private Punto puntoMenor;
	
	private LineaHorizonte lh1;
	private LineaHorizonte lh2;

	private Punto paux;
	private Punto p1, p2;
	
	private int a1x;
	private int a2x;
	private int a1y;
	private int a2y;
	
	private LineaHorizonte salida;
	
	private int countLH1;
	private int countLH2;

	public FusionLineaHorizonte(LineaHorizonte l1, LineaHorizonte l2) {
		this.s1y = -1;
		this.s2y = -1;
		this.prev = -1;
		this.salida = new LineaHorizonte();
		this.lh2 = l2;
		this.lh1 = l1;
		this.imprimirBanner(this.lh1, this.lh2);
		
		this.countLH1 = this.lh1.size();
		this.countLH2 = this.lh2.size();
		
		while (!(countLH1==0) && !(countLH2==0)) {
			this.paux = new Punto();
			this.p1 = this.lh1.getPunto(0);
			this.p2 = this.lh2.getPunto(0);
			this.getVariables();
			this.puntoMenor = this.getMenor();
			if (a1x != a2x)
				this.fusionPuntosDiferentes();
			else
				this.fusionPuntosIguales();
		}
		this.fusionOtrosCasos();
	}
	
	public LineaHorizonte getSalida() {
		return this.salida;
	}
	
	public Punto getMenor (){
        if (a1x<a2x) return this.p1;
        else if (a1x>a2x) return this.p2;
        else return null;
    }
	
	public void fusionPuntosIguales() {
		if ((a1x > a2x) && (a1x != this.prev)) 
		{
			this.salida.addPunto(this.p1);
			this.prev = a2y;
		}
		if ((a1y <= a2y) && (a2y != this.prev)) 
		{
				this.salida.addPunto(this.p2);
				this.prev = a2y;
		}
		this.s1y = a1y; // actualizamos la s1y e s2y
		this.s2y = a2y;
		this.lh1.borrarPunto(0); // eliminamos el punto del s1 y del s2
		this.lh2.borrarPunto(0);
		this.countLH1-=1;
		this.countLH2-=1;
	}
	
	private void getVariables() {
		this.a1x = this.p1.getX();
		this.a2x = this.p2.getX();
		this.a1y = this.p1.getY();
		this.a2y = this.p2.getY();
	}

	public void fusionPuntosDiferentes() {
		int menorx = this.puntoMenor.getX();
		int menory = this.puntoMenor.getY();
		
		this.paux.setX(menorx);

		if (this.puntoMenor.equals(this.p1))
			this.paux.setY(Math.max(menory, this.s2y));

		else if (this.puntoMenor.equals(this.p2))
			this.paux.setY(Math.max(menory, this.s1y));

		if (this.paux.getY() != prev) {
			salida.addPunto(this.paux); // añadimos el punto al LineaHorizonte de salida
			prev = paux.getY(); // actualizamos prev
		}

		if (this.puntoMenor.equals(this.p1)) {
			this.s1y = a1y;
			this.lh1.borrarPunto(0);
			this.countLH1-=1;
			
		} 
		else if (this.puntoMenor.equals(this.p2)) {
			this.s2y = a2y;
			this.lh2.borrarPunto(0);
			this.countLH2-=1;
		}
	}
	
	public void fusionOtrosCasos() {
		int auxy = 0;
		while (!(countLH1==0)) // si aun nos quedan elementos en el s1
		{
			this.paux = this.lh1.getPunto(0); // guardamos en paux el primer punto
			auxy = this.paux.getY();
			if (auxy != this.prev) // si paux no tiene la misma altura del segmento previo
			{
				this.salida.addPunto(this.paux); // lo añadimos al LineaHorizonte de salida
				this.prev = auxy; // y actualizamos prev
			}
			this.lh1.borrarPunto(0);
			this.countLH1-=1;// en cualquier caso eliminamos el punto de s1 (tanto si se añade como si no es
			// valido)
		}
		while (!(countLH2==0)) // si aun nos quedan elementos en el s2
		{
			this.paux = this.lh2.getPunto(0); // guardamos en paux el primer punto
			auxy = this.paux.getY();
			if (auxy != prev) // si paux no tiene la misma altura del segmento previo
			{
				this.salida.addPunto(paux); // lo añadimos al LineaHorizonte de salida
				this.prev = auxy; // y actualizamos prev
			}
			this.lh2.borrarPunto(0);
			this.countLH2-=1;// en cualquier caso eliminamos el punto de s2 (tanto si se añade como si no es
			// valido)
		}
	}
	
	private void imprimirBanner(LineaHorizonte s1, LineaHorizonte s2) {
		System.out.println("==== S1 ====");
		s1.imprimir();
		System.out.println("==== S2 ====");
		s2.imprimir();
		System.out.println("\n");
	}
}