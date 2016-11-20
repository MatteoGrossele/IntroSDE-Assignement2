package introsde.rest.ehealth.model;

import introsde.rest.ehealth.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.OneToOne;

/**
 * The persistent class for the "Measure" database table.
 * 
 */
@Entity
@Table(name = "Measure")
@NamedQuery(name = "Measure.findAll", query = "SELECT m FROM Measure m")
@XmlRootElement(name="Measure")
public class Measure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_measure")
	@TableGenerator(name="sqlite_measure", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="idMeasure")
	@Column(name = "idMeasure")
	private int idMeasure;

	@Column(name = "mid")
	private int mid;

	@Column(name = "idPerson")
	private int idPerson;

	@Column(name = "value")
	private String value;

	@Temporal(TemporalType.DATE)
	@Column(name="date")
	private Date date;

	@Column(name = "type")
	private String type;
	
	/*@OneToMany
	@JoinColumn(name = "idMeasureDef", referencedColumnName = "idMeasureDef", insertable = false, updatable = false)
	private MeasureType measureType;
	*/
	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson", insertable = false, updatable = false)
	private Person person;

	public Measure() {
	}

	public String getDate() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        // Get the date today using Calendar object.
		return df.format(this.date);
	}
	public void setDate(String d) throws ParseException{
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		this.date = format.parse(d);
	}

	public int getIdMeasure() {
		return this.idMeasure;
	}

	public void setIdMeasure(int idMeasure) {
		this.idMeasure = idMeasure;
	}

	public int getMid() {
		return this.mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/*public MeasureType getMeasureType() {
		return measureType;
	}

	public void setMeasureType(MeasureType param) {
		this.measureType = param;
	}
*/
	public void setType(String type){this.type = type;}
	public String getType(){return this.type;}

	public void setIdPerson(int idPerson){this.idPerson = idPerson;}
	public int getIdPerson(){return this.idPerson;}

	// we make this transient for JAXB to avoid and infinite loop on serialization
	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	// Database operations
	// Notice that, for this example, we create and destroy and entityManager on each operation. 
	// How would you change the DAO to not having to create the entity manager every time? 
	public static Measure getMeasureById(int measureId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Measure p = em.find(Measure.class, measureId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}

	public static List<Measure> getHealthProfile(int personId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		String query = "SELECT m FROM Measure m WHERE m.idPerson=" + personId + " AND m.date IN (SELECT MAX(m2.date) FROM Measure m2 WHERE m2.idPerson=" + personId+" AND m2.mid=m.mid)";
		List<Measure> healthprofile = em.createQuery(query, Measure.class).getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return healthprofile;
	}

	public static List<Measure> getMeasureHistorybyPersonIdType(int personId,String measureType) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		String query = "SELECT m FROM Measure m WHERE m.idPerson = "+personId+" AND m.type = \"" + measureType + "\"";
		List<Measure> history = em.createQuery(query, Measure.class).getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return history;
	}

	public static List<Measure> getMeasureHistorybyPersonIdTypeMid(int personId,String measureType,int mid) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		String query = "SELECT m FROM Measure m WHERE m.idPerson = "+personId+" AND m.type = \"" + measureType + "\" AND m.mid = "+mid+"" ;
		List<Measure> history = em.createQuery(query, Measure.class).getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return history;
	}

	
	public static List<Measure> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<Measure> list = em.createNamedQuery("Measure.findAll", Measure.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static Measure saveMeasure(Measure p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static Measure updateMeasure(Measure p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeMeasure(Measure p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
