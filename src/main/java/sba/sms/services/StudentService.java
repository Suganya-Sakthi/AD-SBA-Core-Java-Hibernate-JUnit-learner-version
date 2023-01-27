package sba.sms.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sba.sms.utils.HibernateUtil;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;

public class StudentService implements StudentI  {

	@Override
	public List<Student> getAllStudents() {	
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Student> students = null;
		try {
			
			students = session.createQuery("from Student", Student.class).getResultList();
		} catch (HibernateException ex) {
			ex.printStackTrace();

		} finally {
			session.close();
		}
		return students;
	}

	@Override
	public void createStudent(Student student) {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {

			tx = session.beginTransaction();
			session.merge(student);
			tx.commit();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
		
	}

	@Override
	public Student getStudentByEmail(String email) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Student s = null;
		try {
			s = session.createQuery("from Student where email = :email", Student.class).setParameter("email", email).getSingleResult();
		} catch (HibernateException ex) {
			ex.printStackTrace();

		} finally {
			session.close();
		}
		return s;
	}

	@Override
	public boolean validateStudent(String email, String password) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		boolean b = false;
		try {
			Student s = session.createQuery("from Student where email =:email", Student.class)
					.setParameter("email", email).getSingleResult();
			if (s.getEmail().equalsIgnoreCase(email) && s.getPassword().equals(password)) {
				b = true;
			} else {
				b = false;
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();
			
		} finally {
			session.close();
		}
		return b;

	}

	@Override
	public void registerStudentToCourse(String email, int courseId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {	
			Student s=session.get(Student.class,email);
			Course c=session.get(Course.class, courseId);
			tx=session.beginTransaction();
						
			//To eliminate duplicate records
			List<Course> courses =s.getCourses();
			if(!courses.contains(c)) {
			s.addCourse(c);
			session.merge(s);
			tx.commit();
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
		
	}

	@Override
	public List<Course> getStudentCourses(String email) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Course> courses=new LinkedList<>();
		try {	
			
			Student s =session.createQuery("from Student where email=:email",Student.class).setParameter("email", email).getSingleResult();
			courses =  s.getCourses();	
			
		} catch (HibernateException ex) {
			ex.printStackTrace();

		} finally {
			session.close();
		}
		return courses;
	}

}
