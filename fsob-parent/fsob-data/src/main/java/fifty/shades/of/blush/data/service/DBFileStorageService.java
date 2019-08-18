package fifty.shades.of.blush.data.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import fifty.shades.of.blush.data.exception.FileStorageException;
import fifty.shades.of.blush.data.exception.MyFileNotFoundException;
import fifty.shades.of.blush.data.repository.ArticleFilesRepository;
import fifty.shades.of.blush.domain.Article;
import fifty.shades.of.blush.domain.ArticleFile;

@Service
public class DBFileStorageService {

	@Autowired
	private ArticleFilesRepository articleFilesRepo;

	public ArticleFile storeFile(MultipartFile articleFile, Article article) throws Exception {
		// Normalize file name
		String fileName = StringUtils.cleanPath(articleFile.getOriginalFilename());
		
		Optional<ArticleFile> optArticleFile = articleFilesRepo.findByFileName(fileName);
		
		if(optArticleFile.get() != null) {
			throw new Exception("Cannot upload same file");
		}

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			ArticleFile dbFile = new ArticleFile(fileName, articleFile.getContentType(), articleFile.getBytes(),
					article);

			return articleFilesRepo.save(dbFile);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public ArticleFile getFile(String fileId) throws MyFileNotFoundException {
		return articleFilesRepo.findById(fileId)
				.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
	}
}