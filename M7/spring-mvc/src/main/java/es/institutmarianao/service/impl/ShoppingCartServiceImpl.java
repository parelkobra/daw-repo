package es.institutmarianao.service.impl;

import es.institutmarianao.domain.Article;
import es.institutmarianao.domain.repository.ShoppingCartRepository;
import es.institutmarianao.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    
    @Autowired
    private ShoppingCartRepository shoppingCartRepository; 

    public void addArticleToShoppingCart(Article article) {
        shoppingCartRepository.addArticleToShoppingCart(article);
    }

    public void decreaseArticleFromShoppingCart(Article article) {
        shoppingCartRepository.decreaseArticleFromShoppingCart(article);
    }

    public void removeArticleFromShoppingCart(Article article) {
        shoppingCartRepository.removeArticleFromShoppingCart(article);
    }
    
}
