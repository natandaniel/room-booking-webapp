package fifty.shades.of.blush.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fifty.shades.of.blush.entities.Article;
import fifty.shades.of.blush.repositories.ArticleRepository;

@RestController
@CrossOrigin(origins = "*")
public class RecentArticlesController {

	private ArticleRepository articleRepo;

	public RecentArticlesController(ArticleRepository articleRepo) {
		this.articleRepo = articleRepo;
	}

	@Autowired
	EntityLinks entityLinks;

	@GetMapping(path = "/api/articles/recent", produces = "application/hal+json")
	public Resources<ArticleResource> getLatestArticles() {

		PageRequest page = PageRequest.of(0, 2, Sort.by("createdAt").descending());
		Iterable<Article> articles = articleRepo.findAll(page).getContent();

		List<ArticleResource> articleResources = new ArticleResourceAssembler().toResources(articles);
		Resources<ArticleResource> recentResources = new Resources<ArticleResource>(articleResources);

		recentResources.add(linkTo(methodOn(RecentArticlesController.class).getLatestArticles()).withRel("recent"));

		return recentResources;
	}
}
