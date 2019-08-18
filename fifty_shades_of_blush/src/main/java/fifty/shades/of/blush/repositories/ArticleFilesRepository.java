package fifty.shades.of.blush.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fifty.shades.of.blush.entities.ArticleFile;

@Repository
public interface ArticleFilesRepository extends JpaRepository<ArticleFile, String> {

}