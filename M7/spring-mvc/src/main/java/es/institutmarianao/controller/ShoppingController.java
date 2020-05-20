package es.institutmarianao.controller;

import es.institutmarianao.domain.Article;
import es.institutmarianao.domain.ShoppingCart;
import es.institutmarianao.service.ArticleService;
import java.io.IOException;
import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
/* Busca un ModelAttribute llamado "shopingCart" para inicializarlo y ponerlo en sesión */
@SessionAttributes("shoppingCart")
public class ShoppingController {
    
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showCatalog()
            throws ServletException, IOException {
        ModelAndView modelview = new ModelAndView("catalog");
        modelview.getModelMap().addAttribute("articles", articleService.getAllArticles());
        return modelview;
    }

    @RequestMapping(value = "/addToCart", method = RequestMethod.GET)
    public String addToCart(@ModelAttribute("shoppingCart") ShoppingCart sc, @RequestParam("reference") String reference) {
        Article article = articleService.getArticleByReference(reference);
        sc.addArticleToShoppingCart(article);
        return "redirect:/";
    }

    @RequestMapping(value = "/decreaseFromCart", method = RequestMethod.GET)
    public String decreaseFromCart(@ModelAttribute("shoppingCart") ShoppingCart sc, @RequestParam("reference") String reference) {
        Article article = articleService.getArticleByReference(reference);
        sc.decreaseArticleFromShoppingCart(article);
        return "redirect:/";
    }

    @RequestMapping(value = "/removeFromCart", method = RequestMethod.GET)
    public String removeFromCart(@ModelAttribute("shoppingCart") ShoppingCart sc, @RequestParam("reference") String reference) {
        Article article = articleService.getArticleByReference(reference);
        sc.removeArticleFromShoppingCart(article);
        return "redirect:/";
    }

    @RequestMapping(value = "/secured/shopping", method = RequestMethod.GET)
    public ModelAndView shopping()
            throws ServletException, IOException {
        ModelAndView modelview = new ModelAndView("shopping");
        return modelview;
    }
    
   /* Inicializa el carrito */
   @ModelAttribute("shoppingCart")
   public ShoppingCart setUpShoppingCart() {
      return new ShoppingCart();
   }
}
