package namedEntity.heuristic;

import namedEntity.Tema.Tema;

/*Esta clase modela la nocion de entidad nombrada*/

public class NamedEntity {
	private String name;
	private Tema subClassTema;
	private Integer frequency; //cuantas veces ocurre
	private String clase;
    String subclass;
	
	
	public NamedEntity(String name, Tema tema, String clase) {
		super();
		this.name = name;
		this.subClassTema = tema;
		this.frequency = 1;
		this.clase = clase;
		this.subclass = tema.getSubclass();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return name;
	}

	public void setCategory(String name) {
		this.name = name;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public void incFrequency() {
		this.frequency++;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getSubClassTema() {
		return this.subClassTema.getTema();
	}


	@Override
	public String toString() {
		return "ObjectNamedEntity [name=" + name + ", frequency=" + frequency + "]";
	}
	public void prettyPrint(){
		System.out.println( "Entity: " + this.getName() + ",   Frequency: " + this.getFrequency() + ",   Class: " + this.getClase() + ",   Subclass: " + getSubClassTema());
	}
	
	
}



