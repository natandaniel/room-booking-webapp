package fifty.shades.of.blush.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@GetMapping("/all")
	public ResponseEntity<Iterable<Article>> getAllArticles() {
		Iterable<Article> articles = articleRepo.findAll();
		return new ResponseEntity<Iterable<Article>>(articles, HttpStatus.OK);
	}

}
