package pico.erp.project

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.CompanyId
import pico.erp.project.charge.ProjectChargeId
import pico.erp.project.charge.ProjectChargeRequests
import pico.erp.project.charge.ProjectChargeService
import pico.erp.shared.IntegrationConfiguration
import pico.erp.shared.data.Contact
import pico.erp.user.UserId
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class ProjectChargeServiceSpec extends Specification {

  @Autowired
  ProjectService projectService

  @Autowired
  ProjectChargeService projectChargeService


  def setup() {
    projectService.create(new ProjectRequests.CreateRequest(id: ProjectId.from("P1"), name: "프로젝트1",
      customerId: CompanyId.from("CUST2"),
      managerId: UserId.from("ysh"),
      customerManagerContact: new Contact(name: "고객 회사 담당자", email: "manager@company.com", telephoneNumber: "+821011111111", mobilePhoneNumber: "+821011111111", faxNumber: "+821011111111")))
  }

  def "청구 내용 추가"() {
    when:
    projectChargeService.create(
      new ProjectChargeRequests.CreateRequest(
        id: ProjectChargeId.from("charge-1"),
        projectId: ProjectId.from("P1"),
        name: "기초비 목형",
        unitPrice: 200000,
        quantity: 1
      )
    )
    def charges = projectChargeService.getAll(ProjectId.from("P1"))
    def charge = projectChargeService.get(ProjectChargeId.from("charge-1"))

    then:
    charges.size() == 1
    charge.unitPrice == 200000
    charge.quantity == 1
    charge.name == "기초비 목형"
  }

  def "청구 내용 삭제"() {
    when:
    projectChargeService.create(
      new ProjectChargeRequests.CreateRequest(
        id: ProjectChargeId.from("charge-1"),
        projectId: ProjectId.from("P1"),
        name: "기초비 목형",
        unitPrice: 200000,
        quantity: 1
      )
    )
    projectChargeService.delete(
      new ProjectChargeRequests.DeleteRequest(
        id: ProjectChargeId.from("charge-1")
      )
    )
    def charges = projectChargeService.getAll(ProjectId.from("P1"))

    then:
    charges.size() == 0
  }

  def "청구 내용 수정"() {
    when:
    projectChargeService.create(
      new ProjectChargeRequests.CreateRequest(
        id: ProjectChargeId.from("charge-1"),
        projectId: ProjectId.from("P1"),
        name: "기초비 목형",
        unitPrice: 200000,
        quantity: 1
      )
    )
    projectChargeService.update(
      new ProjectChargeRequests.UpdateRequest(
        id: ProjectChargeId.from("charge-1"),
        name: "기초비 목형값",
        unitPrice: 100000,
        quantity: 2
      )
    )
    def charge = projectChargeService.get(ProjectChargeId.from("charge-1"))

    then:
    charge.unitPrice == 100000
    charge.quantity == 2
    charge.name == "기초비 목형값"
  }

}
