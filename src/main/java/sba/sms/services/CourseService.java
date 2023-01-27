package sba.sms.services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

public class CourseService implements CourseI  {

	@Override
	public void createCourse(Course course) {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {

			tx = session.beginTransaction();
			session.merge(course);
			tx.commit();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
		
	}

	@Override
	public Course getCourseById(int courseId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Course s = null;
		try {
			s = session.createQuery("from Course where Id = :courseId",Course.class)
					.setParameter("courseId", courseId).getSingleResult();
		} catch (HibernateException ex) {
			ex.printStackTrace();

		} finally {
			session.close();
		}
		return s;
	}

	@Override
	public List<Course> getAllCourses() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Course> course = null;
		try {
			course = session.createQuery("from Course", Course.class).getResultList();
		} catch (HibernateException ex) {
			ex.printStackTrace();

		} finally {
			session.close();
		}
		return course;
	}

}
