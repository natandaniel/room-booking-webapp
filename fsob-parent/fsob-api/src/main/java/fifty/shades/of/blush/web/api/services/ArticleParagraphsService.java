package fifty.shades.of.blush.web.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fifty.shades.of.blush.data.exception.ResourceNotFoundException;
import fifty.shades.of.blush.data.repository.ArticleParagraphsRepository;
import fifty.shades.of.blush.data.repository.ArticleRepository;
import fifty.shades.of.blush.domain.Article;
import fifty.shades.of.blush.domain.ArticleParagraph;

@Service
public class ArticleParagraphsService {

	@Autowired
	ArticleRepository artRepo;

	@Autowired
	ArticleParagraphsRepository artParagraphsRepo;

	public ArticleParagraph createParagraph(String content, Long articleId) {

		Article article = artRepo.findById(articleId)
				.orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));

		ArticleParagraph newparagraph = new ArticleParagraph(article, content);

		return artParagraphsRepo.save(newparagraph);
	}

}
