package fifty.shades.of.blush.web.api;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import fifty.shades.of.blush.domain.Article;

public class ArticleResourceAssembler extends ResourceAssemblerSupport<Article, ArticleResource> {

	public ArticleResourceAssembler() {
		super(ArticlesController.class, ArticleResource.class);
	}

	@Override
	protected ArticleResource instantiateResource(Article article) {
		return new ArticleResource(article);
	}

	@Override
	public ArticleResource toResource(Article article) {
		return createResourceWithId(article.getId(), article);
	}

}
