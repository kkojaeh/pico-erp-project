package pico.erp.project;

import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.data.Role;

@RequiredArgsConstructor
public enum ROLE implements Role {

  PROJECT_MANAGER,

  PROJECT_ACCESSOR;

  @Id
  @Getter
  private final String id = name();

}
