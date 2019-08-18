package fifty.shades.of.blush.web.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fifty.shades.of.blush.data.exception.FileStorageException;
import fifty.shades.of.blush.data.exception.MyFileNotFoundException;
import fifty.shades.of.blush.data.exception.ResourceNotFoundException;
import fifty.shades.of.blush.data.repository.ArticleRepository;
import fifty.shades.of.blush.data.service.DBFileStorageService;
import fifty.shades.of.blush.domain.Article;
import fifty.shades.of.blush.domain.ArticleFile;

@RestController
@RequestMapping(path = "/api/articles")
@CrossOrigin(origins = "*")
public class ArticleFilesController {
	
	@Autowired
	ArticleRepository articles;

    private static final Logger logger = LoggerFactory.getLogger(ArticleFilesController.class);

    @Autowired
    private DBFileStorageService DBFileStorageService;

    @PostMapping("/uploadFile/{articleId}")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("articleId") Long articleId) throws FileStorageException {
        
    	Article article = articles.findById(articleId).orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
    	
    	ArticleFile articleFile = DBFileStorageService.storeFile(file, article);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(articleFile.getId())
                .toUriString();

        return new UploadFileResponse(articleFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles/{articleId}")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("articleId") Long articleId) {
    	
    	return Arrays.asList(files)
                .stream()
                .map(file -> {
					try {
						return uploadFile(file, articleId);
					} catch (FileStorageException e) {
						e.printStackTrace();
					}
					return null;
				})
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileId) throws MyFileNotFoundException {
        
    	ArticleFile dbFile = DBFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

}