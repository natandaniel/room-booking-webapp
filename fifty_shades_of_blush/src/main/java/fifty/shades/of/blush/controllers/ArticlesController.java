package fifty.shades.of.blush.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fifty.shades.of.blush.entities.Article;
import fifty.shades.of.blush.repositories.ArticleRepository;

@RestController
@RequestMapping(path = "/articles", produces = "application/json")
@CrossOrigin(origins = "*")
public class ArticlesController {

	private ArticleRepository articleRepo;

	public ArticlesController(ArticleRepository articleRepo) {
		this.articleRepo = articleRepo;
	}
	
	@GetMapping("/latest")
	public Resources<ArticleResource> getLatestArticle() {

		PageRequest page = PageRequest.of(0, 1, Sort.by("createdAt").descending());
		Iterable<Article> articles = articleRepo.findAll(page).getContent();

		List<ArticleResource> articleResources = new ArticleResourceAssembler().toResources(articles);
		Resources<ArticleResource> recentResources = new Resources<ArticleResource>(articleResources);

		recentResources.add(linkTo(methodOn(ArticlesController.class).getLatestArticle()).withRel("latest"));
		return recentResources;
	}

	@GetMapping("/recent")
	public Resources<ArticleResource> getRecentArtciles() {

		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		Iterable<Article> articles = articleRepo.findAll(page).getContent();

		List<ArticleResource> articleResources = new ArticleResourceAssembler().toResources(articles);
		Resources<ArticleResource> recentResources = new Resources<ArticleResource>(articleResources);

		recentResources.add(linkTo(methodOn(ArticlesController.class).getRecentArtciles()).withRel("recents"));
		return recentResources;
	}

	@GetMapping("/beauty")
	public Resources<ArticleResource> getBeautyArticles() {
		
		Iterable<Article> articles = articleRepo.findByType("BEAUTY");
		
		List<ArticleResource> articleResources = new ArticleResourceAssembler().toResources(articles);
		Resources<ArticleResource> recentResources = new Resources<ArticleResource>(articleResources);
		
		recentResources.add(linkTo(methodOn(ArticlesController.class).getBeautyArticles()).withRel("beauty"));
		return recentResources;
	}

	@GetMapping("/fashion")
	public Resources<ArticleResource> getFashionArticles() {
		
		Iterable<Article> articles = articleRepo.findByType("FASHION");
		
		List<ArticleResource> articleResources = new ArticleResourceAssembler().toResources(articles);
		Resources<ArticleResource> recentResources = new Resources<ArticleResource>(articleResources);
		
		recentResources.add(linkTo(methodOn(ArticlesController.class).getBeautyArticles()).withRel("fashion"));
		return recentResources;
	}

	@GetMapping("/travel")
	public Resources<ArticleResource> getTravelArticles() {
		Iterable<Article> articles = articleRepo.findByType("TRAVEL");
		
		List<ArticleResource> articleResources = new ArticleResourceAssembler().toResources(articles);
		Resources<ArticleResource> recentResources = new Resources<ArticleResource>(articleResources);
		
		recentResources.add(linkTo(methodOn(ArticlesController.class).getBeautyArticles()).withRel("travel"));
		return recentResources;
	}

	@GetMapping("/lifestyle")
	public Resources<ArticleResource> getLifestyleArticles() {
		Iterable<Article> articles = articleRepo.findByType("LIFESTYLE");
		
		List<ArticleResource> articleResources = new ArticleResourceAssembler().toResources(articles);
		Resources<ArticleResource> recentResources = new Resources<ArticleResource>(articleResources);
		
		recentResources.add(linkTo(methodOn(ArticlesController.class).getBeautyArticles()).withRel("lifestyle"));
		return recentResources;
	}

	@PostMapping(path = "/admin", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Article postTaco(@RequestBody Article article) {
		return articleRepo.save(article);
	}

	@PutMapping("/admin/{articleId}")
	public ResponseEntity<Article> putArticle(@RequestBody Article putArticle) {

		Article article = articleRepo.save(putArticle);
		return new ResponseEntity<>(article, HttpStatus.OK);
	}

	@PatchMapping(path = "/admin/{articleId}", consumes = "application/json")
	public ResponseEntity<Article> patchArticle(@PathVariable("articleId") Long articleId,
			@RequestBody Article patchArticle) {

		Optional<Article> optArticle = articleRepo.findById(articleId);

		if (optArticle.isPresent()) {

			Article article = optArticle.get();

			if (patchArticle.getTitle() != null) {
				article.setTitle(patchArticle.getTitle());
			}
			if (patchArticle.getBody() != null) {
				article.setBody(patchArticle.getBody());
			}
			if (patchArticle.getType() != null) {
				article.setType(patchArticle.getType());
			}

			return new ResponseEntity<Article>(articleRepo.save(article), HttpStatus.OK);
		}

		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

	}

	@DeleteMapping("/admin/{articleId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteArticle(@PathVariable("articleId") Long articleId) {
		try {
			articleRepo.deleteById(articleId);
		} catch (EmptyResultDataAccessException e) {
		}
	}

}
