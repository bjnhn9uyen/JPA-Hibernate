package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Every persistent entity class must have at least the @Entity annotation. By default, Hibernate maps this class to a table called 'message' or
 * generates the table 'message' (if the table does not exist). You can override the table name with the JPA @Table annotation.
 */
@Entity
@Table(name = "MESSAGES")
public class Message {

	/**
	 * Every persistent entity class must have an identifier attribute annotated with @Id. Hibernate maps this attribute to a column named ID. The
	 * identifier attribute of a persistent class allows the application to access the database identity—the primary key value—of a persistent
	 * instance.
	 */
	@Id
	/**
	 * The @GeneratedValue annotation enables automatic generation of IDs. By default, Hibernate will generate a table called hibernate_sequence to
	 * provide the next number for the ID sequence (just in case you may have forgotten to add the AutoIncrement feature to your table's PK). Using
	 * generation strategy "IDENTITY" to enforce using the AutoIncrement feature available in MySql and avoid creating hibernate_sequence table.
	 */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/** You can override the column name with the JPA @Column annotation */
	@Column(name = "MESSAGE_ID")
	private Long id;

	@Column(name = "MESSAGE_TEXT")
	private String text;

	public Message() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
