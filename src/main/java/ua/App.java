package ua;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Random;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        try {
            emf = Persistence.createEntityManagerFactory("JPAStudents");
            em = emf.createEntityManager();
            try {
                Group group1 = new Group("Course-1");
                Group group2 = new Group("Course-2");
                initGroup(group1);
                initGroup(group2);
                Query query = em.createNamedQuery("getAll", Group.class);
                List<Group> groups = (List<Group>) query.getResultList();
                for (Group group: groups){
                    System.out.println("Thre are " + group.getStudents().size() + " Students in " + group.getName());
                    for (Student student: group.getStudents()){
                        System.out.println(student);
                    }
                }
            } finally {
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void initGroup(Group group) {
        String[] names = {"Viktoriya", "Vasyl", "Karina", "Diana", "Dmytro", "Roman", "Kristina", "Artyr", "Afrodita", "Alexander"};
        int[] ages = {23, 24, 25, 25, 27, 28, 29, 30, 31, 32};
        for (int i = 0; i < new Random().nextInt(10); i++) {
            Random rn = new Random();
            group.addStudent(new Student(names[rn.nextInt(10)], ages[rn.nextInt(10)]));
        }
        em.getTransaction().begin();
        try{
            em.persist(group);
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
        }
    }
}
