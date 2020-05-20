package es.institutmarianao.domain.repository;

import es.institutmarianao.domain.Article;
import java.util.List;

public interface ArticleRepository {
    List<Article> getAllArticles();
    Article getArticleByReference(String reference);  
}
