package es.institutmarianao.domain.repository.impl;

import es.institutmarianao.domain.Article;
import es.institutmarianao.domain.repository.ArticleRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryArticleRepository implements ArticleRepository {

    private final List<Article> listOfArticles = new ArrayList<Article>();

    public InMemoryArticleRepository() {
        Article pig = new Article("A0000001", "Big Pig");
        pig.setDescription("Cerdito bonito y apestoso de gran calidad");
        pig.setPrice(23.90);
        pig.setImage("cerdo.jpg");

        Article monkey = new Article("A0000002", "Silly Monkey");
        monkey.setDescription("Simpático monito que hará las delícias de los más pequeños");
        monkey.setPrice(24.90);
        monkey.setImage("mono.jpg");

        Article bear = new Article("A0000003", "Fat Bear");
        bear.setDescription("El oso amoroso que le hará coger el sueño con sólo abrazarlo");
        bear.setPrice(25.00);
        bear.setImage("oso.jpg");

        Article cow = new Article("A0000004", "Vaca Paca");
        cow.setDescription("La vaca más realista del mundo de los peluches. Sólo le falta andar");
        cow.setPrice(23.80);
        cow.setImage("vaca.jpg");

        listOfArticles.add(pig);
        listOfArticles.add(monkey);
        listOfArticles.add(bear);
        listOfArticles.add(cow);
    }

    public List<Article> getAllArticles() {
        return listOfArticles;
    }

    public Article getArticleByReference(String reference) {
        for (Article article : listOfArticles) {
            if (article != null && article.getReference() != null
                    && article.getReference().equals(reference)) {
                return article;
            }
        }
        throw new IllegalArgumentException(
                "No articles with reference=" + reference + " found");
    }
}
