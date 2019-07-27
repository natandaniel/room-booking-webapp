package fifty.shades.of.blush.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import fifty.shades.of.blush.entities.ArticleContent;

public interface ArticleContentRepository extends PagingAndSortingRepository<ArticleContent, Long> {

}
