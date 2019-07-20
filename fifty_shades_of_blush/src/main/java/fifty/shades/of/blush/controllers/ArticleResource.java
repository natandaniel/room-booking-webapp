package fifty.shades.of.blush.controllers;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import fifty.shades.of.blush.entities.Article;
import lombok.Getter;

public class ArticleResource extends ResourceSupport {

	@Getter
	private final String type;

	@Getter
	private final String title;

	@Getter
	private final String subtitle;

	@Getter
	private final String body;

	@Getter
	private final Date createdAt;

	public ArticleResource(Article article) {
		this.type = article.getType();
		this.title = article.getTitle();
		this.subtitle = article.getSubtitle();
		this.body = article.getBody();
		this.createdAt = article.getCreatedAt();
	}
}
