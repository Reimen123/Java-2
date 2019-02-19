package com.javaguru.shoppinglist;

import java.math.BigDecimal;
import java.util.Scanner;

public class CreateProductAction implements Action {

    private static final String ACTION_NAME = "Create Product";
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 32;

    private final ProductService productService;

    public CreateProductAction(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter product name:");
        String name = scanner.nextLine();
        isNameContainsNeededAmount(name);
        System.out.println("Enter product price: ");
        String price = scanner.nextLine();
        checkPrice(price);
        System.out.println("Enter discount:" );
        String discount = scanner.nextLine();
        isDiscountValid(discount);
        System.out.println("Enter description: ");
        String description = scanner.nextLine();
        Product product = new Product();
        product.setName(name);
        product.setPrice(new BigDecimal(price));

        try {
            Long response = productService.create(product);
            System.out.println("Response: " + response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return ACTION_NAME;
    }

    private void checkPrice(String prise) {
        Scanner scanner = new Scanner(System.in);
        if (!isPricePositiveNumber(prise)) {
            System.out.println("Error, Prise cant be negative ");
            scanner.nextLine();
        }
    }

    private boolean isPricePositiveNumber(String prise) {
        if (prise == null) {
            return false;
        }
        int length = prise.length();
        if (length == 0) {
            return false;
        }
        if (prise.charAt(0) == '-') {
            return false;
        }
        for (int i = 0; i < length; i++) {
            char c = prise.charAt(i);
            boolean isDigit = (c >= '0' && c <= '9' || c == ',' || c == '.');
            if (!isDigit) {
                return false;
            }
        }
        return true;
    }

    private void isDiscountValid(String prise) {
        Scanner scanner = new Scanner(System.in);
        long checkAmount = prise.chars().sum();
        if (checkAmount > 100) {
            System.out.println("Error, discount cant be more than 100%");
            scanner.nextLine();
        }
    }

    private void isNameContainsNeededAmount(String name) {
        Scanner scanner = new Scanner(System.in);
        long lettersCount = name.chars().count();
        if (lettersCount < MIN_NAME_LENGTH) {
            System.out.println("Error, name is too small, please try another one");
            scanner.nextLine();
        } else if (lettersCount > MAX_NAME_LENGTH) {
            System.out.println("Error, name is too big, please try another one");
            scanner.nextLine();
        }
    }


}

