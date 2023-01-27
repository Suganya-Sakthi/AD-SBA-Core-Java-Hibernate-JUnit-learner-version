package sba.sms.models;
import java.util.LinkedList;
import java.util.List;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "course")
public class Course {	
	@Id
	@NonNull
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	@NonNull
	@Column(name="name", length=50, nullable=false)
	String name;
	@NonNull
	@Column(name="instructor", length=50, nullable=false)
	String instructor;
	@ToString.Exclude
	@ManyToMany(mappedBy="courses",fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	List<Student> students = new LinkedList<>();
	
	public Course(String name, String instructor) {
		this.name=name;
		this.instructor=instructor;
	}
	
	
}
