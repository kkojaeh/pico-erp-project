package pico.erp.project.jpa;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.project.charge.ProjectCharge;
import pico.erp.project.charge.ProjectChargeRepository;
import pico.erp.project.charge.data.ProjectChargeId;
import pico.erp.project.data.ProjectId;

@Repository
interface ProjectChargeEntityRepository extends
  CrudRepository<ProjectChargeEntity, ProjectChargeId> {

  @Query("SELECT pc FROM ProjectCharge pc JOIN pc.project p WHERE p.id = :projectId")
  Stream<ProjectChargeEntity> findAllBy(
    @Param("projectId") ProjectId projectId);

}

@Repository
@Transactional
public class ProjectChargeRepositoryJpa implements ProjectChargeRepository {

  @Autowired
  private ProjectChargeEntityRepository repository;

  @Autowired
  private ProjectJpaMapper mapper;

  @Override
  public ProjectCharge create(ProjectCharge projectCharge) {
    val entity = mapper.map(projectCharge);
    val created = repository.save(entity);
    return mapper.map(created);
  }

  @Override
  public void deleteBy(ProjectChargeId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(ProjectChargeId id) {
    return repository.exists(id);
  }

  @Override
  public Stream<ProjectCharge> findAllBy(ProjectId projectId) {
    return repository.findAllBy(projectId)
      .map(mapper::map);
  }

  @Override
  public Optional<ProjectCharge> findBy(ProjectChargeId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::map);
  }

  @Override
  public void update(ProjectCharge projectCharge) {
    val entity = repository.findOne(projectCharge.getId());
    mapper.pass(mapper.map(projectCharge), entity);
    repository.save(entity);
  }
}
