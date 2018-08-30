package pico.erp.project;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.project.ProjectChargeRequests.CreateRequest;
import pico.erp.project.ProjectChargeRequests.DeleteRequest;
import pico.erp.project.ProjectChargeRequests.UpdateRequest;
import pico.erp.project.data.ProjectChargeData;
import pico.erp.project.data.ProjectChargeId;
import pico.erp.project.data.ProjectId;

public interface ProjectChargeService {

  ProjectChargeData create(@Valid CreateRequest request);

  void delete(@Valid DeleteRequest request);

  boolean exists(@NotNull ProjectChargeId id);

  ProjectChargeData get(@NotNull ProjectChargeId id);

  List<ProjectChargeData> getAll(ProjectId projectId);

  void update(@Valid UpdateRequest request);


}
