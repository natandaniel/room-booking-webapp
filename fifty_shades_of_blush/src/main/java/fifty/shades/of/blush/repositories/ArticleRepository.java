package fifty.shades.of.blush.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import fifty.shades.of.blush.entities.Article;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

	Iterable<Article> findByTypeOrderByCreatedAtDesc(String type);
	
	Iterable<Article> findTop1ByTypeOrderByCreatedAtDesc(String type);

	Iterable<Article> findTop2ByTypeOrderByCreatedAtDesc(String type);
}