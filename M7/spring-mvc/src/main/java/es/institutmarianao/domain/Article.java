/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.institutmarianao.domain;

import org.springframework.core.io.Resource;

/**
 *
 * @author Toni
 */
public class Article {

    String reference;
    String name;
    String description;
    Double price;
    String image;

    public Article(String reference, String name) {
        this.reference = reference;
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Article)) {
            return false;
        }
        Article theOtherArticle = (Article) obj;
        if (reference == null) {
            return false;
        }
        return reference.equals(theOtherArticle.getReference());
    }

    @Override
    public int hashCode() {
        return reference.hashCode();
    }
}
