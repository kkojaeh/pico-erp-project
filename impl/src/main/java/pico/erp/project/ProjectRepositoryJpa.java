package pico.erp.project;

import java.util.Optional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
interface ProjectEntityRepository extends CrudRepository<ProjectEntity, ProjectId> {

}

@Repository
@Transactional
public class ProjectRepositoryJpa implements ProjectRepository {

  @Autowired
  private ProjectEntityRepository repository;

  @Autowired
  private ProjectMapper mapper;


  @Override
  public Project create(Project project) {
    val entity = mapper.entity(project);
    val created = repository.save(entity);
    return mapper.domain(created);
  }

  @Override
  public void deleteBy(ProjectId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(ProjectId id) {
    return repository.exists(id);
  }

  @Override
  public Optional<Project> findBy(ProjectId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::domain);
  }

  @Override
  public void update(Project project) {
    val entity = repository.findOne(project.getId());
    mapper.pass(mapper.entity(project), entity);
    repository.save(entity);
  }
}
