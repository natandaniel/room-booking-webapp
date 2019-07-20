package fifty.shades.of.blush.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fifty.shades.of.blush.entities.Article;
import fifty.shades.of.blush.repositories.ArticleRepository;

@RestController
@CrossOrigin(origins = "*")
public class LifestyleArticlesController {

	private ArticleRepository articleRepo;

	public LifestyleArticlesController(ArticleRepository articleRepo) {
		this.articleRepo = articleRepo;
	}

	@GetMapping("/api/articles/lifestyle")
	public Resources<ArticleResource> getLifestyleArticles() {
		Iterable<Article> articles = articleRepo.findByType("LIFESTYLE");

		List<ArticleResource> articleResources = new ArticleResourceAssembler().toResources(articles);
		Resources<ArticleResource> recentResources = new Resources<ArticleResource>(articleResources);

		recentResources
				.add(linkTo(methodOn(LifestyleArticlesController.class).getLifestyleArticles()).withRel("lifestyle"));

		return recentResources;
	}
}