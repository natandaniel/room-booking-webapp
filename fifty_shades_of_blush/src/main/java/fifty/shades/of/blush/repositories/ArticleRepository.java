package fifty.shades.of.blush.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fifty.shades.of.blush.entities.Article;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

}
