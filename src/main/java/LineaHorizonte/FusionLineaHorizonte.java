package LineaHorizonte;

public class FusionLineaHorizonte {

	private int prev;
	private int s2y;
	private int s1y;

	public Punto puntoMenor;
	
	public LineaHorizonte lh1;
	public LineaHorizonte lh2;

	public Punto paux;
	public Punto p1, p2;
	
	private LineaHorizonte salida;

	public FusionLineaHorizonte(LineaHorizonte l1, LineaHorizonte l2) {
		this.s1y = -1;
		this.s2y = -1;
		this.prev = -1;
		this.salida = new LineaHorizonte();
		this.lh2 = new LineaHorizonte();
		this.lh1 = new LineaHorizonte();
		this.lh1.setLineaHorizonte(l1.getLineaHorizonte());
		this.lh2.setLineaHorizonte(l2.getLineaHorizonte());
		this.imprimirBanner(this.lh1, this.lh2);
		this.puntoMenor = new Punto();
		this.paux = new Punto();
		this.p1 = new Punto();
		this.p2 = new Punto();
	}
	
	public LineaHorizonte getSalida() {
		return this.salida;
	}
	
	public Punto getMenor (){
        if (this.p1.getX() < this.p2.getX()) return p1;
        else if (this.p1.getX() > this.p2.getX()) return p2;
        else return null;
    }
	
	public void fusionPuntosIguales() {
		if ((this.p1.getY() > this.p2.getY()) && (this.p1.getY() != this.prev)) 
		{
			this.salida.addPunto(this.p1);
			this.prev = this.p1.getY();
		}
		if ((this.p1.getY() <= this.p2.getY()) && (this.p2.getY() != this.prev)) 
		{
				this.salida.addPunto(this.p2);
				this.prev = this.p2.getY();
		}
		this.s1y = this.p1.getY(); // actualizamos la s1y e s2y
		this.s2y = this.p2.getY();
		this.lh1.borrarPunto(0); // eliminamos el punto del s1 y del s2
		this.lh2.borrarPunto(0);
	}

	public void fusionPuntosDiferentes() {
		this.paux.setX(this.puntoMenor.getX());

		if (this.puntoMenor.equals(this.p1))
			this.paux.setY(Math.max(this.puntoMenor.getY(), this.s2y));

		else if (this.puntoMenor.equals(this.p2))
			this.paux.setY(Math.max(this.puntoMenor.getY(), this.s1y));

		if (this.paux.getY() != prev) {
			salida.addPunto(this.paux); // añadimos el punto al LineaHorizonte de salida
			prev = paux.getY(); // actualizamos prev
		}

		if (this.puntoMenor.equals(this.p1)) {
			this.s1y = this.p1.getY();
			this.lh1.borrarPunto(0);
		} 
		else if (this.puntoMenor.equals(this.p2)) {
			this.s2y = this.p2.getY();
			this.lh2.borrarPunto(0);
		}
	}
	
	public void fusionOtrosCasos() {
		while ((!this.lh1.isEmpty())) // si aun nos quedan elementos en el s1
		{
			this.paux = this.lh1.getPunto(0); // guardamos en paux el primer punto

			if (this.paux.getY() != this.prev) // si paux no tiene la misma altura del segmento previo
			{
				this.salida.addPunto(this.paux); // lo añadimos al LineaHorizonte de salida
				this.prev = this.paux.getY(); // y actualizamos prev
			}
			this.lh1.borrarPunto(0); // en cualquier caso eliminamos el punto de s1 (tanto si se añade como si no es
			// valido)
		}
		while ((!this.lh2.isEmpty())) // si aun nos quedan elementos en el s2
		{
			this.paux = this.lh2.getPunto(0); // guardamos en paux el primer punto

			if (this.paux.getY() != prev) // si paux no tiene la misma altura del segmento previo
			{
				this.salida.addPunto(paux); // lo añadimos al LineaHorizonte de salida
				this.prev = this.paux.getY(); // y actualizamos prev
			}
			this.lh2.borrarPunto(0); // en cualquier caso eliminamos el punto de s2 (tanto si se añade como si no es
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