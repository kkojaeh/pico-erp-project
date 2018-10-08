package pico.erp.project;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.project.ProjectRequests.CreateRequest;
import pico.erp.project.ProjectRequests.DeleteRequest;
import pico.erp.project.ProjectRequests.UpdateRequest;

public interface ProjectService {

  ProjectData create(@Valid CreateRequest request);

  void delete(@NotNull DeleteRequest request);

  boolean exists(@NotNull ProjectId id);

  ProjectData get(@NotNull ProjectId id);

  void update(@Valid UpdateRequest request);

}
