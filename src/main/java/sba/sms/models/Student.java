package sba.sms.models;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "student")
public class Student {
@Id
@NonNull
@Column(name="email", length=50, nullable=false)
String email;
@NonNull
@Column(name="name", length=50, nullable=false)
String name;
@NonNull
@Column(name="password", length=50, nullable=false)
String password;

@ToString.Exclude
@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
		CascadeType.REFRESH })
@JoinTable(name = "student_courses", joinColumns = @JoinColumn(name = "student_email"), inverseJoinColumns = @JoinColumn(name = "course_id"))


List<Course> courses = new LinkedList<Course>();

public void addCourse(Course c) {
	courses.add(c);
	c.getStudents().add(this);
}
//hash code() and equals()
@Override
public int hashCode() {
	return Objects.hash(email, name, password,courses);
}
@Override
public boolean equals(Object o) {
    if (o == this)
        return true;
    if (!(o instanceof Student))
        return false;
    Student other = (Student)o;
    boolean emailEquals = (this.email == null && other.email == null)
      || (this.email != null && this.email.equals(other.email));
    boolean nameEquals = (this.name == null && other.name == null)
      || (this.name != null && this.name.equals(other.name));
    boolean passwordEquals = (this.password== null && other.password == null)
    	      || (this.password != null && this.password.equals(other.password));
    return emailEquals && nameEquals && passwordEquals;
}
}
