package fifty.shades.of.blush.web.api;

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
	}

	@Override
	public String toString() {
		return "ArticleDTO [title=" + title + ", subtitle=" + subtitle + ", category=" + category + ", body=" + body
				+ ", image=" + image + "]";
	};
	
	

}
