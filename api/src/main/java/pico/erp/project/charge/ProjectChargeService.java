package pico.erp.project.charge;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.project.ProjectId;
import pico.erp.project.charge.ProjectChargeRequests.CreateRequest;
import pico.erp.project.charge.ProjectChargeRequests.DeleteRequest;
import pico.erp.project.charge.ProjectChargeRequests.UpdateRequest;

public interface ProjectChargeService {

  ProjectChargeData create(@Valid CreateRequest request);

  void delete(@Valid DeleteRequest request);

  boolean exists(@NotNull ProjectChargeId id);

  ProjectChargeData get(@NotNull ProjectChargeId id);

  List<ProjectChargeData> getAll(ProjectId projectId);

  void update(@Valid UpdateRequest request);


}
