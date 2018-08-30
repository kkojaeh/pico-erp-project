package pico.erp.project;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pico.erp.shared.data.Menu;
import pico.erp.shared.data.MenuCategory;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MENU implements Menu {

  PROJECT_MANAGEMENT("/project", "fas fa-tasks", MenuCategory.CUSTOMER);

  String url;

  String icon;

  MenuCategory category;

  public String getId() {
    return name();
  }

}