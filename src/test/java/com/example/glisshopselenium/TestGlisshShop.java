package com.example.glisshopselenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestGlisshShop {
    private WebDriver driver;

    public void launchBrowser(){
        System.setProperty("webdriver.gecko.driver", "/home/ylion/selenium/geckodriver");
        driver = new FirefoxDriver();
        driver.get("https://www.glisshop.com/");
    }

    public static void main(String[] args) {
        TestGlisshShop testGlisshShop = new TestGlisshShop();
        testGlisshShop.launchBrowser();
    }
}

//TODO
// 1. Création de compte
// 2. Connexion
// 3. Recherche d'articles
// 4. Commande
// 5. Déconnexion
// 6. L'ajout d'un article au panier
// 7. Supprimer article panier
// 8. Modification du compte (email, telephone, adresse)