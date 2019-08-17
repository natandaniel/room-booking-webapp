package fifty.shades.of.blush.controllers;

import lombok.Getter;

public class ArticleDTO {

	@Getter
	private String title;

	@Getter
	private String subtitle;

	@Getter
	private String category;

	@Getter
	private String body;

	@Getter
	private String image;

	public ArticleDTO() {
	};

}
