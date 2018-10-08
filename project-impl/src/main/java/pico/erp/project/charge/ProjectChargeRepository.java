package pico.erp.project.charge;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import pico.erp.project.ProjectId;

public interface ProjectChargeRepository {

  ProjectCharge create(@NotNull ProjectCharge projectCharge);

  void deleteBy(@NotNull ProjectChargeId id);

  boolean exists(@NotNull ProjectChargeId id);

  Stream<ProjectCharge> findAllBy(@NotNull ProjectId projectId);

  Optional<ProjectCharge> findBy(@NotNull ProjectChargeId id);

  void update(@NotNull ProjectCharge projectCharge);

}
