package com.example.mateusz.coffeenotes;

import java.util.UUID;

class BeansType {
  private UUID id;
  private String name;
  private String country;

  BeansType() {
    id = UUID.randomUUID();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}
