package awsGroup.awsPractice.com.sikdan.domain.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    //Create
    public User save(User user) {
        em.persist(user);
        return user;
    }

    //Read
    public Optional<User> findByOptionalId(Long id) {
        User findUser = em.find(User.class, id);
        return Optional.ofNullable(findUser);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {

        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public Optional<User> findByEmail(String email) {
        List<User> users = em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();


        return users.stream().findFirst();
    }

    //Delete
    public void deleteAll() {
        em.createQuery("delete from User").executeUpdate(); //delete JPQL이라서 executeUpdate()필요
    }

    public void flush() {
        em.flush();
    }

    public void delete(User user) {
        em.find(User.class, user.getId());
        em.remove(user); //엔티티를 영속성컨텍스트에 올려놓고 삭제해야함
    }
}
