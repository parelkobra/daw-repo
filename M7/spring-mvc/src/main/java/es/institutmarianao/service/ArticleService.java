package es.institutmarianao.service;

import es.institutmarianao.domain.Article;
import java.util.List;

public interface ArticleService {
    List<Article> getAllArticles();
    Article getArticleByReference(String reference);  
}
