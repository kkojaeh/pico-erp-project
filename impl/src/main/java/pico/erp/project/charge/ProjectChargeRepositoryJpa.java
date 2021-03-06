package pico.erp.project.charge;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.project.ProjectId;

@Repository
interface ProjectChargeEntityRepository extends
  CrudRepository<ProjectChargeEntity, ProjectChargeId> {

  @Query("SELECT pc FROM ProjectCharge pc WHERE pc.projectId = :projectId")
  Stream<ProjectChargeEntity> findAllBy(
    @Param("projectId") ProjectId projectId);

}

@Repository
@Transactional
public class ProjectChargeRepositoryJpa implements ProjectChargeRepository {

  @Autowired
  private ProjectChargeEntityRepository repository;

  @Autowired
  private ProjectChargeMapper mapper;

  @Override
  public ProjectCharge create(ProjectCharge projectCharge) {
    val entity = mapper.entity(projectCharge);
    val created = repository.save(entity);
    return mapper.domain(created);
  }

  @Override
  public void deleteBy(ProjectChargeId id) {
    repository.deleteById(id);
  }

  @Override
  public boolean exists(ProjectChargeId id) {
    return repository.existsById(id);
  }

  @Override
  public Stream<ProjectCharge> findAllBy(ProjectId projectId) {
    return repository.findAllBy(projectId)
      .map(mapper::domain);
  }

  @Override
  public Optional<ProjectCharge> findBy(ProjectChargeId id) {
    return repository.findById(id)
      .map(mapper::domain);
  }

  @Override
  public void update(ProjectCharge projectCharge) {
    val entity = repository.findById(projectCharge.getId()).get();
    mapper.pass(mapper.entity(projectCharge), entity);
    repository.save(entity);
  }
}
