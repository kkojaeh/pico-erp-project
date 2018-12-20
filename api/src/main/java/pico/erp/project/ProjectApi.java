package pico.erp.project;

import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.ApplicationId;
import pico.erp.shared.data.Role;

public final class ProjectApi {

  public final static ApplicationId ID = ApplicationId.from("project");

  @RequiredArgsConstructor
  public enum Roles implements Role {

    PROJECT_MANAGER,

    PROJECT_ACCESSOR;

    @Id
    @Getter
    private final String id = name();

  }
}
