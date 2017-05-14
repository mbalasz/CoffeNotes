package com.example.mateusz.coffeenotes;

enum CoffeeType {
  ESPRESSO("Espresso"),
  LATTE("Latte"),
  FLAT_WHITE("Flat white");

  private String coffeeType;

  CoffeeType(String coffeeType) {
    this.coffeeType = coffeeType;
  }

  @Override
  public String toString() {
    return coffeeType;
  }
}
