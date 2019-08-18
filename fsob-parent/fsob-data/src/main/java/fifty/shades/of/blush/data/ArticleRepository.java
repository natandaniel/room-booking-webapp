package fifty.shades.of.blush.data;

import org.springframework.data.repository.PagingAndSortingRepository;

import fifty.shades.of.blush.domain.Article;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

	Iterable<Article> findByTypeOrderByCreatedAtDesc(String type);
	
	Iterable<Article> findTop1ByTypeOrderByCreatedAtDesc(String type);

	Iterable<Article> findTop2ByTypeOrderByCreatedAtDesc(String type);
}