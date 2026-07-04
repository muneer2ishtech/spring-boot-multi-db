package fi.ishtech.practice.springboot.multidb.entity;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity class for sample table
 *
 * @author Muneer Ahmed Syed
 */
@Entity
@Table
@Data
public class GoldSample implements Serializable {

	@Serial
	private static final long serialVersionUID = 8065613371183549099L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 255, unique = true)
	private String sampleName;

	@Column(columnDefinition = "TEXT")
	private String sampleDescription;

}