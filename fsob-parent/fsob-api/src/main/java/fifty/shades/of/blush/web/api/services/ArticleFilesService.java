package fifty.shades.of.blush.web.api.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fifty.shades.of.blush.data.exception.FileStorageException;
import fifty.shades.of.blush.data.exception.MyFileNotFoundException;
import fifty.shades.of.blush.data.exception.ResourceNotFoundException;
import fifty.shades.of.blush.data.repository.ArticleRepository;
import fifty.shades.of.blush.data.service.DBFileStorageService;
import fifty.shades.of.blush.domain.Article;
import fifty.shades.of.blush.domain.ArticleFile;
import fifty.shades.of.blush.web.api.UploadFileResponse;

@Service
public class ArticleFilesService {

	@Autowired
	ArticleRepository articles;

	@Autowired
	private DBFileStorageService DBFileStorageService;

	public UploadFileResponse uploadFile(MultipartFile file, Long articleId) throws Exception {

		Article article = articles.findById(articleId)
				.orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));

		ArticleFile articleFile = DBFileStorageService.storeFile(file, article);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(articleFile.getId()).toUriString();

		return new UploadFileResponse(articleFile.getFileName(), fileDownloadUri, file.getContentType(),
				file.getSize());
	}

	public List<UploadFileResponse> uploadMultipleFiles(MultipartFile[] files, Long articleId)
			throws FileStorageException {

		return Arrays.asList(files).stream().map(file -> {
			try {
				return uploadFile(file, articleId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
	}

	public ResponseEntity<ByteArrayResource> downloadFile(String fileId) throws MyFileNotFoundException {

		ArticleFile dbFile = DBFileStorageService.getFile(fileId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
				.body(new ByteArrayResource(dbFile.getData()));
	}

}
