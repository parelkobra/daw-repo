package es.institutmarianao.domain.repository;

import es.institutmarianao.domain.Article;

public interface ShoppingCartRepository {
    public void addArticleToShoppingCart(Article article);
    public void decreaseArticleFromShoppingCart(Article article);
    public void removeArticleFromShoppingCart(Article article);
}
