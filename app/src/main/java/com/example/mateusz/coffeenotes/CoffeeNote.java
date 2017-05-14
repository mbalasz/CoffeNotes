package com.example.mateusz.coffeenotes;

class CoffeeNote {
  private CoffeeType coffeeType;
  private BeansType beansType;
  private double weight;
  private double grinderSettings;

  void setCoffeeType(CoffeeType coffeeType) {
    this.coffeeType = coffeeType;
  }

  void setBeansType(BeansType beansType) {
    this.beansType = beansType;
  }

  public BeansType getBeansType() {
    return beansType;
  }

  void setWeight(double weight) {
    this.weight = weight;
  }

  void setGrinderSettings(double grinderSettings) {
    this.grinderSettings = grinderSettings;
  }


  static class Builder {
    private CoffeeNote coffeeNote;

    Builder() {
      coffeeNote = new CoffeeNote();
    }

    Builder setCoffeeType(CoffeeType coffeeType) {
      coffeeNote.setCoffeeType(coffeeType);
      return this;
    }

    Builder setWeight(double weight) {
      coffeeNote.setWeight(weight);
      return this;
    }

    Builder setGrinderSettings(double grinderSettings) {
      coffeeNote.setGrinderSettings(grinderSettings);
      return this;
    }
  }
}
