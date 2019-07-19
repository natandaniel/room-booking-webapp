package fifty.shades.of.blush.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(path = "/articles", produces = "application/json")
@CrossOrigin(origins = "*")
public class ArticlesController {

	private ArticleRepository articleRepo;

	public ArticlesController(ArticleRepository articleRepo) {
		this.articleRepo = articleRepo;
	}

	@GetMapping("/recent")
	public ResponseEntity<Iterable<Article>> getRecentArtciles() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		Iterable<Article> articles = articleRepo.findAll(page).getContent();
		return new ResponseEntity<Iterable<Article>>(articles, HttpStatus.OK);
	}

	@GetMapping("/beauty")
	public ResponseEntity<Iterable<Article>> getBeautyArticles() {
		Iterable<Article> articles = articleRepo.findByType("BEAUTY");
		return new ResponseEntity<Iterable<Article>>(articles, HttpStatus.OK);
	}

	@GetMapping("/fashion")
	public ResponseEntity<Iterable<Article>> getFashionArticles() {
		Iterable<Article> articles = articleRepo.findByType("FASHION");
		return new ResponseEntity<Iterable<Article>>(articles, HttpStatus.OK);
	}

	@GetMapping("/travel")
	public ResponseEntity<Iterable<Article>> getTravelArticles() {
		Iterable<Article> articles = articleRepo.findByType("TRAVEL");
		return new ResponseEntity<Iterable<Article>>(articles, HttpStatus.OK);
	}

	@GetMapping("/lifestyle")
	public ResponseEntity<Iterable<Article>> getLifestyleArticles() {
		Iterable<Article> articles = articleRepo.findByType("LIFESTYLE");
		return new ResponseEntity<Iterable<Article>>(articles, HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Article postTaco(@RequestBody Article article) {
		return articleRepo.save(article);
	}

}
