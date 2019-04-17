// Anton Sandström 960726-4430
// Karl Jonsson 980226-8293

abstract class Vardesaker {
	private String namn;

	public abstract double nyttVarde();

	public abstract String toString();

	public Vardesaker(String namn) {
		this.namn = namn;
	}

	public String getNamn() {
		return namn;
	}

	public double getRealVarde() {
		return nyttVarde() * 1.25;
	}
}

class Smycken extends Vardesaker {
	private int adelstenar;
	private double varde;
	private boolean guld;

	public Smycken(String namn, int adelstenar, boolean guld) {
		super(namn);
		this.adelstenar = adelstenar;
		this.guld = guld;
	}

	@Override
	public double nyttVarde() {
		if (guld) {
			varde = 2000 + (adelstenar * 500);
		}

		else if (!guld) {
			varde = 700 + (adelstenar * 500);
		}

		return varde;
	}

	@Override
	public String toString() {
		String metall = null;
		if (guld) {
			metall = "guld";
		} else if (!guld) {
			metall = "silver";
		}
		return getNamn() + " av " + metall + " med " + adelstenar + " adelstenar," + " värd " + getRealVarde() + " kr.";
	}
}

class Aktie extends Vardesaker {
	private double kurs;
	private int antal;

	public Aktie(String namn, double kurs, int antal) {
		super(namn);
		this.kurs = kurs;
		this.antal = antal;
	}

	@Override
	public double nyttVarde() {
		return antal * kurs;
	}

	@Override
	public String toString() {
		return getNamn() + " aktien har kursen: " + kurs + " du äger: " + antal + " st. Totalt värde är: "
				+ getRealVarde();
	}

	public void setNyKurs(double nyKurs) {
		kurs = nyKurs;
	}
}

class Apparat extends Vardesaker {
	private double inkopPris;
	private double slitage;

	public Apparat(String namn, double inkop, int slitage) {
		super(namn);
		inkopPris = inkop;
		this.slitage = slitage;
	}

	@Override
	public double nyttVarde() {
		return inkopPris * (slitage / 10);
	}

	@Override
	public String toString() {
		return getNamn() + " köptes för: " + inkopPris + " och har ett slitage på: " + slitage + " värd nu: "
				+ getRealVarde();
	}
}
