package es.institutmarianao.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Map<Article, Integer> articles;

    public ShoppingCart() {
        articles = new HashMap<Article, Integer>();
    }

    public Map<Article, Integer> getArticles() {
        return articles;
    }

    public void setArticles(Map<Article, Integer> articles) {
        this.articles = articles;
    }

    public void addArticleToShoppingCart(Article article) {
        int quantity = 1;
        if (this.articles.containsKey(article)) {
            quantity += articles.get(article);
        }
        articles.put(article, quantity);
    }

    public void decreaseArticleFromShoppingCart(Article article) {
        if (this.articles.containsKey(article)) {
            int quantity = articles.get(article);
            if (quantity > 1) {
                articles.put(article, --quantity);
            } else {
                articles.remove(article);
            }
        }
    }

    public void removeArticleFromShoppingCart(Article article) {
        if (this.articles.containsKey(article)) {
            articles.remove(article);
        }
    }
    
    public double getTotalPrice() {
        double totalPrice = 0;
        for (Map.Entry<Article,Integer> articleQuantity : articles.entrySet()) {
            totalPrice += articleQuantity.getKey().getPrice() * articleQuantity.getValue();
        }
        return totalPrice;
    }
}
