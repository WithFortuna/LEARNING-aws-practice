package awsGroup.awsPractice.com.sikdan.domain.posts;

import awsGroup.awsPractice.com.sikdan.webController.dto.PostsSaveRequestDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public class PostsRepository {
    @PersistenceContext
    private EntityManager em;


    public Long save(Posts posts) {
        em.persist(posts);
        return posts.getId();
    }

    public Long save(PostsSaveRequestDto requestDto) {
        Posts posts = requestDto.toEntity();
        em.persist(posts);
        return posts.getId();
    }

    public Optional<Posts> findByOptionalId(Long id) {
        Posts findPosts = em.find(Posts.class, id);
        return Optional.ofNullable(findPosts);
    }

    public Posts findOne(Long id) {
        return em.find(Posts.class, id);
    }

    public List<Posts> findAll() {

        return em.createQuery("select p from Posts p", Posts.class)
                .getResultList();
    }

    public void deleteAll() {
        em.createQuery("delete from Posts").executeUpdate(); //delete JPQL이라서 executeUpdate()필요
    }

    public void flush() {
        em.flush();
    }
}
