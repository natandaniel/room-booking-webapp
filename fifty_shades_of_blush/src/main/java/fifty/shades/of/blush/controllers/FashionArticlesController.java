package fifty.shades.of.blush.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fifty.shades.of.blush.entities.Article;
import fifty.shades.of.blush.repositories.ArticleRepository;

@RestController
@RequestMapping(path = "/api/articles", produces = "application/hal+json")
@CrossOrigin(origins = "*")
public class FashionArticlesController {
	
	private final String FASHION = "FASHION";
	private final String RECENT = "recent";

	private ArticleRepository articleRepo;

	public FashionArticlesController(ArticleRepository articleRepo) {
		this.articleRepo = articleRepo;
	}

	@GetMapping("/fashion")
	public Resources<ArticleResource> getFashionArticles() {

		Iterable<Article> articles = articleRepo.findByType(FASHION);

		List<ArticleResource> articleResources = new ArticleResourceAssembler().toResources(articles);
		Resources<ArticleResource> recentResources = new Resources<ArticleResource>(articleResources);

		recentResources.add(linkTo(methodOn(FashionArticlesController.class).getFashionArticles()).withRel("fashion"));

		return recentResources;
	}
	
	@GetMapping("/fashion/recent")
	public Resources<ArticleResource> getRecentFashionArticles() {

		Iterable<Article> articles = articleRepo.findTop2ByTypeOrderByCreatedAtDesc(FASHION);

		List<ArticleResource> articleResources = new ArticleResourceAssembler().toResources(articles);
		Resources<ArticleResource> recentResources = new Resources<ArticleResource>(articleResources);

		recentResources.add(linkTo(methodOn(FashionArticlesController.class).getRecentFashionArticles()).withRel(RECENT));

		return recentResources;
	}
}
