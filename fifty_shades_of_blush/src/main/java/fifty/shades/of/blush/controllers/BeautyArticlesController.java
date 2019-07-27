package fifty.shades.of.blush.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fifty.shades.of.blush.entities.Article;
import fifty.shades.of.blush.repositories.ArticleRepository;

@RestController
@RequestMapping(path = "/api/articles", produces = "application/hal+json")
@CrossOrigin(origins = "*")
public class BeautyArticlesController {

	private final String BEAUTY = "BEAUTY";
	private final String RECENT = "recent";
	private final String LATEST = "latest";

	private ArticleRepository articleRepo;

	public BeautyArticlesController(ArticleRepository articleRepo) {
		this.articleRepo = articleRepo;
	}

	@GetMapping("/beauty")
	public ResponseEntity<Resources<ArticleResource>> getBeautyArticles() {

		Iterable<Article> articles = articleRepo.findByTypeOrderByCreatedAtDesc(BEAUTY);

		List<ArticleResource> articleResources = new ArticleResourceAssembler().toResources(articles);
		Resources<ArticleResource> recentResources = new Resources<ArticleResource>(articleResources);

		recentResources.add(linkTo(methodOn(BeautyArticlesController.class).getBeautyArticles()).withRel(RECENT));

		return new ResponseEntity<>(recentResources, HttpStatus.OK);
	}

	@GetMapping("/beauty/recent")
	public ResponseEntity<Resources<ArticleResource>> getRecentBeautyArticles() {

		Iterable<Article> articles = articleRepo.findTop2ByTypeOrderByCreatedAtDesc(BEAUTY);

		List<ArticleResource> articleResources = new ArticleResourceAssembler().toResources(articles);
		Resources<ArticleResource> recentResources = new Resources<ArticleResource>(articleResources);

		recentResources.add(linkTo(methodOn(BeautyArticlesController.class).getRecentBeautyArticles()).withRel(RECENT));

		return new ResponseEntity<>(recentResources, HttpStatus.OK);
	}
	
	@GetMapping("/beauty/latest")
	public ResponseEntity<Resources<ArticleResource>> getLatestBeautyArticle() {

		Iterable<Article> articles = articleRepo.findTop1ByTypeOrderByCreatedAtDesc(BEAUTY);

		List<ArticleResource> articleResources = new ArticleResourceAssembler().toResources(articles);
		Resources<ArticleResource> recentResources = new Resources<ArticleResource>(articleResources);

		recentResources.add(linkTo(methodOn(BeautyArticlesController.class).getLatestBeautyArticle()).withRel(LATEST));

		return new ResponseEntity<>(recentResources, HttpStatus.OK);
	}

}
