package introsde.rest.ehealth.model;

import introsde.rest.ehealth.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * The persistent class for the "MeasureType" database table.
 * 
 */
@Entity
@Table(name="MeasureType")
@NamedQuery(name="MeasureType.findAll", query="SELECT m FROM MeasureType m")
@XmlRootElement
public class MeasureType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_measuretype")
	@TableGenerator(name="sqlite_measuretype", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="idMeasureDef")
	@Column(name="idMeasureDef")
	private int idMeasureDef;

	@Column(name="measureType")
	private String measureType;

	public MeasureType() {
	}

	public int getIdMeasureDef() {
		return this.idMeasureDef;
	}

	public void setIdMeasureDef(int idMeasureDef) {
		this.idMeasureDef = idMeasureDef;
	}

	public String getMeasureType() {
		return this.measureType;
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}
	
	
}
