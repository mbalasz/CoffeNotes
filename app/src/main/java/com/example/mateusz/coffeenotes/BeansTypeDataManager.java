package com.example.mateusz.coffeenotes;

import java.util.ArrayList;
import java.util.List;

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

  public List<BeansType> getBeansTypeList() {
    return beansTypeList;
  }
}
