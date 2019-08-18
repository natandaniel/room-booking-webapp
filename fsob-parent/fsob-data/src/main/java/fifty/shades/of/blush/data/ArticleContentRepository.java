package fifty.shades.of.blush.data;

import org.springframework.data.repository.PagingAndSortingRepository;

import fifty.shades.of.blush.domain.ArticleContent;

public interface ArticleContentRepository extends PagingAndSortingRepository<ArticleContent, Long> {

}
