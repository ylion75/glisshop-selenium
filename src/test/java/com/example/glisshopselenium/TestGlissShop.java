package com.example.glisshopselenium;

import com.opencsv.exceptions.CsvValidationException;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class TestGlissShop {
    private static WebDriver driver;

    /*
    private void runTestCreateAccount() {
        TestCreateAccount testCreateAccount = new TestCreateAccount("/home/ylion/workspace/glisshop-selenium/src/test/data/CreateAccountData.csv");

        try {
            testCreateAccount.main();
        } catch (InterruptedException | CsvValidationException | IOException e) {
            e.printStackTrace();
        }
    }

    private void runTestLoggin(){
        TestLoginAction testLoginAction = new TestLoginAction("/home/ylion/workspace/glisshop-selenium/src/test/data/LoginData.csv");

        try {
            testLoginAction.execute();
        } catch (InterruptedException | CsvValidationException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestGlissShop testGlissShop = new TestGlissShop();
        //Account creation test
        testGlissShop.runTestCreateAccount();
        Thread.sleep(2000);
        //Loggin test
        testGlissShop.runTestLoggin();

    }

     */
}

//TODO
// 1. Création de compte
// 2. Connexion
// 3. Déconnexion
// 4. Recherche d'articles
// 5. L'ajout d'un article au panier
// 6. Supprimer article panier
// 7. Commande
// 8. Modification du compte (email, telephone, adresse)
// plugin : cucumber / testng ou allure
// question : cssSelector vs xpath ? checkpoints ?