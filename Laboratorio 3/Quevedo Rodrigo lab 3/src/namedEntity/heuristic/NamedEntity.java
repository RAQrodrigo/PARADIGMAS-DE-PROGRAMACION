package namedEntity.heuristic;

import namedEntity.Tema.*;

import java.io.Serializable;
/*Esta clase modela la nocion de entidad nombrada*/

import org.apache.hadoop.shaded.org.checkerframework.checker.units.qual.g;

public class NamedEntity  implements Serializable{
	String name;
	Tema subClassTema;
	Integer frequency; 
	String clase;
	String subclass;

	
	public NamedEntity(String name, Tema tema, String clase) {
		super();
		this.name = name;
		this.subClassTema = tema;
		this.frequency = 1;
		this.clase = clase;
		this.subclass = tema.getSubclass();
	}
	
	public String getSubclass() {
		return this.subclass;
	} 

	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getClase() {
		return this.clase;
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

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public void incFrequency() {
		this.frequency++;
	}
	public String getSubclassTema() {
		return this.subClassTema.getTema();
	}

	@Override
	public String toString() {
		return "ObjectNamedEntity [name=" + name + ", frequency=" + frequency + "]";
	} 
	
	public void prettyPrint(){
		System.out.println("Name: " + this.getName() + ", Frecuency: " + this.getFrequency() + ", Class: " + this.getClase()+ ", Subclass: " + this.getSubclass() + ", SubclassTema: " + this.getSubclassTema() );
	}
	
	
}



