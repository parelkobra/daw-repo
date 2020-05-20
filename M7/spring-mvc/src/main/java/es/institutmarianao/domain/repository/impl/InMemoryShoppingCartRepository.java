package es.institutmarianao.domain.repository.impl;

import es.institutmarianao.domain.Article;
import es.institutmarianao.domain.ShoppingCart;
import es.institutmarianao.domain.repository.ShoppingCartRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryShoppingCartRepository implements ShoppingCartRepository {
    
    public void addArticleToShoppingCart(Article article) {
        ShoppingCart sc = new ShoppingCart();
        sc.addArticleToShoppingCart(article);
    }

    public void decreaseArticleFromShoppingCart(Article article) {
        ShoppingCart sc = new ShoppingCart();
        sc.decreaseArticleFromShoppingCart(article);
    }

    public void removeArticleFromShoppingCart(Article article) {
        ShoppingCart sc = new ShoppingCart();
        sc.removeArticleFromShoppingCart(article);
    }
    
}
