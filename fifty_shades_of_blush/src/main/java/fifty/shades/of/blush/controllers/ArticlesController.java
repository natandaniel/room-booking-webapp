package fifty.shades.of.blush.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fifty.shades.of.blush.entities.Article;
import fifty.shades.of.blush.repositories.ArticleRepository;

@RestController
@RequestMapping(path = "/api/articles", produces = "application/hal+json")
@CrossOrigin(origins = "*")
public class ArticlesController {

	private ArticleRepository articleRepo;

	public ArticlesController(ArticleRepository articleRepo) {
		this.articleRepo = articleRepo;
	}

	@Autowired
	EntityLinks entityLinks;

	@GetMapping("/recent")
	public Resources<ArticleResource> getLatestArticles() {

		PageRequest page = PageRequest.of(0, 2, Sort.by("createdAt").descending());
		Iterable<Article> articles = articleRepo.findAll(page).getContent();

		List<ArticleResource> articleResources = new ArticleResourceAssembler().toResources(articles);
		Resources<ArticleResource> recentResources = new Resources<ArticleResource>(articleResources);

		recentResources.add(linkTo(methodOn(ArticlesController.class).getLatestArticles()).withRel("recent"));

		return recentResources;
	}
	
	@PostMapping(path="/create", consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ArticleResource createArticle(@RequestBody Article createdArticle) {
		
		createdArticle.setCreatedAt(new Date());
		createdArticle.setUpdatedAt(new Date());

		Article article = articleRepo.save(createdArticle);

		ArticleResource articleResource = new ArticleResourceAssembler().instantiateResource(article);

		articleResource.add(linkTo(methodOn(ArticlesController.class).createArticle(article)).withRel("create"));

		return articleResource;
	}
}
