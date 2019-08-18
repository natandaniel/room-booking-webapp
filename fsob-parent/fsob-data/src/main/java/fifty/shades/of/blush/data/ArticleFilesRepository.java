package fifty.shades.of.blush.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fifty.shades.of.blush.domain.ArticleFile;

@Repository
public interface ArticleFilesRepository extends JpaRepository<ArticleFile, String> {

}