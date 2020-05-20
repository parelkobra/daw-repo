package es.institutmarianao.service;

import es.institutmarianao.domain.Article;

public interface ShoppingCartService {
    public void addArticleToShoppingCart(Article article);
    public void decreaseArticleFromShoppingCart(Article article);
    public void removeArticleFromShoppingCart(Article article);
}
