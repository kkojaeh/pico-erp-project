package pico.erp.project

import kkojaeh.spring.boot.component.SpringBootTestComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.CompanyApplication
import pico.erp.item.ItemApplication
import pico.erp.project.charge.ProjectChargeExceptions
import pico.erp.project.charge.ProjectChargeId
import pico.erp.project.charge.ProjectChargeRequests
import pico.erp.project.charge.ProjectChargeService
import pico.erp.shared.TestParentApplication
import pico.erp.user.UserApplication
import spock.lang.Specification

@SpringBootTest(classes = [ProjectApplication, TestConfig])
@SpringBootTestComponent(parent = TestParentApplication, siblings = [UserApplication, CompanyApplication, ItemApplication])
@Transactional
@Rollback
@ActiveProfiles("test")
class ProjectChargeServiceSpec extends Specification {

  @Autowired
  ProjectService projectService

  @Autowired
  ProjectChargeService projectChargeService

  def projectId = ProjectId.from("sample-project1")

  def id = ProjectChargeId.from("charge-1")

  def unknownId = ProjectChargeId.from("unknown")

  def name = "기초비 목형"

  def unitPrice = 200000

  def quantity = 1

  def setup() {
    projectChargeService.create(
      new ProjectChargeRequests.CreateRequest(
        id: id,
        projectId: projectId,
        name: name,
        unitPrice: unitPrice,
        quantity: quantity
      )
    )
  }

  def "존재 - 아이디로 확인"() {
    when:
    def exists = projectChargeService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = projectChargeService.exists(unknownId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def charge = projectChargeService.get(id)

    then:
    charge.id == id
    charge.projectId == projectId
    charge.name == name
    charge.unitPrice == unitPrice
    charge.quantity == quantity
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    projectChargeService.get(unknownId)

    then:
    thrown(ProjectChargeExceptions.NotFoundException)
  }


  def "조회 - 프로젝트 아이디로 조회"() {
    when:

    def charges = projectChargeService.getAll(projectId)
    def charge = charges[0]

    then:
    charges.size() == 1
    charge.id == id
    charge.projectId == projectId
    charge.name == name
    charge.unitPrice == unitPrice
    charge.quantity == quantity
  }


  def "삭제 - 삭제"() {
    when:
    projectChargeService.delete(
      new ProjectChargeRequests.DeleteRequest(
        id: id
      )
    )
    def charges = projectChargeService.getAll(projectId)

    then:
    charges.size() == 0
  }

  def "수정 - 수정"() {
    when:
    def name = "기초비 목형2"

    def unitPrice = 200000

    def quantity = 2
    projectChargeService.update(
      new ProjectChargeRequests.UpdateRequest(
        id: id,
        name: name,
        unitPrice: unitPrice,
        quantity: quantity
      )
    )
    def charge = projectChargeService.get(ProjectChargeId.from("charge-1"))

    then:
    charge.name == name
    charge.unitPrice == unitPrice
    charge.quantity == quantity
  }

}
