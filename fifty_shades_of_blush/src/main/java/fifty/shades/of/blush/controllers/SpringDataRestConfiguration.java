package fifty.shades.of.blush.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

import fifty.shades.of.blush.entities.Article;

@Configuration
public class SpringDataRestConfiguration {
	
	@Bean
	public ResourceProcessor<PagedResources<Resource<Article>>> recentArticleProcessor(EntityLinks links) {
		return new ResourceProcessor<PagedResources<Resource<Article>>>() {
			@Override
			public PagedResources<Resource<Article>> process(PagedResources<Resource<Article>> resource) {
				resource.add(links.linkFor(Article.class).slash("recent").withRel("recent"));
				return resource;
			}
		};
	}
	
	@Bean
	public ResourceProcessor<PagedResources<Resource<Article>>> beautyArticleProcessor(EntityLinks links) {
		return new ResourceProcessor<PagedResources<Resource<Article>>>() {
			@Override
			public PagedResources<Resource<Article>> process(PagedResources<Resource<Article>> resource) {
				resource.add(links.linkFor(Article.class).slash("beauty").withRel("beauty"));
				return resource;
			}
		};
	}
	
	@Bean
	public ResourceProcessor<PagedResources<Resource<Article>>> recentBeautyArticleProcessor(EntityLinks links) {
		return new ResourceProcessor<PagedResources<Resource<Article>>>() {
			@Override
			public PagedResources<Resource<Article>> process(PagedResources<Resource<Article>> resource) {
				resource.add(links.linkFor(Article.class).slash("beauty").slash("recent").withRel("recentBeauty"));
				return resource;
			}
		};
	}
	
	@Bean
	public ResourceProcessor<PagedResources<Resource<Article>>> fashionArticleProcessor(EntityLinks links) {
		return new ResourceProcessor<PagedResources<Resource<Article>>>() {
			@Override
			public PagedResources<Resource<Article>> process(PagedResources<Resource<Article>> resource) {
				resource.add(links.linkFor(Article.class).slash("fashion").withRel("fashion"));
				return resource;
			}
		};
	}
	
	@Bean
	public ResourceProcessor<PagedResources<Resource<Article>>> recentFashionArticleProcessor(EntityLinks links) {
		return new ResourceProcessor<PagedResources<Resource<Article>>>() {
			@Override
			public PagedResources<Resource<Article>> process(PagedResources<Resource<Article>> resource) {
				resource.add(links.linkFor(Article.class).slash("fashion").slash("recent").withRel("recentFashion"));
				return resource;
			}
		};
	}
	
	@Bean
	public ResourceProcessor<PagedResources<Resource<Article>>> travelArticleProcessor(EntityLinks links) {
		return new ResourceProcessor<PagedResources<Resource<Article>>>() {
			@Override
			public PagedResources<Resource<Article>> process(PagedResources<Resource<Article>> resource) {
				resource.add(links.linkFor(Article.class).slash("travel").withRel("travel"));
				return resource;
			}
		};
	}
	
	@Bean
	public ResourceProcessor<PagedResources<Resource<Article>>> recentTravelArticleProcessor(EntityLinks links) {
		return new ResourceProcessor<PagedResources<Resource<Article>>>() {
			@Override
			public PagedResources<Resource<Article>> process(PagedResources<Resource<Article>> resource) {
				resource.add(links.linkFor(Article.class).slash("travel").slash("recent").withRel("recentTravel"));
				return resource;
			}
		};
	}
	
	@Bean
	public ResourceProcessor<PagedResources<Resource<Article>>> lifestyleArticleProcessor(EntityLinks links) {
		return new ResourceProcessor<PagedResources<Resource<Article>>>() {
			@Override
			public PagedResources<Resource<Article>> process(PagedResources<Resource<Article>> resource) {
				resource.add(links.linkFor(Article.class).slash("lifestyle").withRel("lifestyle"));
				return resource;
			}
		};
	}
	
	@Bean
	public ResourceProcessor<PagedResources<Resource<Article>>> lifestyleRecentArticleProcessor(EntityLinks links) {
		return new ResourceProcessor<PagedResources<Resource<Article>>>() {
			@Override
			public PagedResources<Resource<Article>> process(PagedResources<Resource<Article>> resource) {
				resource.add(links.linkFor(Article.class).slash("lifestyle").slash("recent").withRel("recentLifestyle"));
				return resource;
			}
		};
	}
	
	@Bean
	public ResourceProcessor<PagedResources<Resource<Article>>> createArticleProcessor(EntityLinks links) {
		return new ResourceProcessor<PagedResources<Resource<Article>>>() {
			@Override
			public PagedResources<Resource<Article>> process(PagedResources<Resource<Article>> resource) {
				resource.add(links.linkFor(Article.class).slash("create").withRel("create"));
				return resource;
			}
		};
	}
	
	@Bean
	public ResourceProcessor<PagedResources<Resource<Article>>> deleteArticleProcessor(EntityLinks links) {
		return new ResourceProcessor<PagedResources<Resource<Article>>>() {
			@Override
			public PagedResources<Resource<Article>> process(PagedResources<Resource<Article>> resource) {
				resource.add(links.linkFor(Article.class).slash("delete").withRel("delete"));
				return resource;
			}
		};
	}
	
	@Bean
	public ResourceProcessor<PagedResources<Resource<Article>>> putArticleProcessor(EntityLinks links) {
		return new ResourceProcessor<PagedResources<Resource<Article>>>() {
			@Override
			public PagedResources<Resource<Article>> process(PagedResources<Resource<Article>> resource) {
				resource.add(links.linkFor(Article.class).slash("put").withRel("put"));
				return resource;
			}
		};
	}
}
