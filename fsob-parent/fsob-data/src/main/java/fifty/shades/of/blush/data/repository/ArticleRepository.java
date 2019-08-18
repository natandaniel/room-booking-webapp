package fifty.shades.of.blush.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import fifty.shades.of.blush.domain.Article;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

	Iterable<Article> findByCategoryOrderByCreatedAtDesc(String Category);
	
	Iterable<Article> findTop1ByCategoryOrderByCreatedAtDesc(String Category);

	Iterable<Article> findTop2ByCategoryOrderByCreatedAtDesc(String Category);
}