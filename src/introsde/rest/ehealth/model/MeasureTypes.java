package introsde.rest.ehealth.model;

import java.io.Serializable;
import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import introsde.rest.ehealth.dao.LifeCoachDao;


/**
 * The persistent class for the "MeasureType" database table.
 * 
 */
@Entity
@Table(name = "MeasureType")
@XmlRootElement(name = "MeasureTypes")
public class MeasureTypes implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "measureType")
	private List<String> measureType;

	public List<String> getMeasureType() {return this.measureType;}
	public void setMeasureType(List<String> measureType) {this.measureType = measureType;}

	public static MeasureTypes getTypeList()
	{
		MeasureTypes res = new MeasureTypes();

		EntityManager em = LifeCoachDao.instance.createEntityManager();		
		res.setMeasureType(em.createQuery("SELECT m.measureType FROM MeasureType m", String.class).getResultList());
		LifeCoachDao.instance.closeConnections(em);

		return res;
	}


}
