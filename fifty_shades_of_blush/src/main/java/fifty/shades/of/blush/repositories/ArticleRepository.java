package fifty.shades.of.blush.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import fifty.shades.of.blush.entities.Article;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
	
	Iterable<Article> findByType(String type);

}
