package fifty.shades.of.blush.repositories;

import org.springframework.data.repository.CrudRepository;

import fifty.shades.of.blush.entities.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {

}
