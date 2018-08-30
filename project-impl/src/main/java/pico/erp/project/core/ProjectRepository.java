package pico.erp.project.core;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import pico.erp.project.data.ProjectId;
import pico.erp.project.domain.Project;

@Repository
public interface ProjectRepository {

  Project create(@NotNull Project project);

  void deleteBy(@NotNull ProjectId id);

  boolean exists(@NotNull ProjectId id);

  Optional<Project> findBy(@NotNull ProjectId id);

  void update(@NotNull Project project);

}
