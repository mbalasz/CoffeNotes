package com.example.mateusz.coffeenotes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BeansTypeDataManager {
  private static BeansTypeDataManager instance;

  private List<BeansType> beansTypeList;

  private BeansTypeDataManager() {
    beansTypeList = new ArrayList<>();

    for (int i = 0; i < 10; ++i) {
      BeansType beansType = new BeansType();
      beansType.setName("Type #" + Integer.toString(i));
      beansType.setCountry("Country #" + Integer.toString(i));
      beansTypeList.add(beansType);
    }
  }

  public static BeansTypeDataManager getInstance() {
    if (instance == null) {
      instance = new BeansTypeDataManager();
    }
    return instance;
  }

  public BeansType getBeansTypeById(UUID id) {
    for (BeansType beansType : beansTypeList) {
      if (beansType.getId().equals(id)) {
        return beansType;
      }
    }
    return null;
  }

  public List<BeansType> getBeansTypeList() {
    return beansTypeList;
  }
}
