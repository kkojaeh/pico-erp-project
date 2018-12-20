package pico.erp.project

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.CompanyId
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
class ProjectServiceSpec extends Specification {

  @Autowired
  ProjectService projectService

  def id = ProjectId.from("test")

  def unknownId = ProjectId.from("unknown")

  def name = "테스트 프로젝트"

  def customerId = CompanyId.from("CUST2")

  def managerId = UserId.from("ysh")

  def customerManagerContact = new Contact(
    name: "고객 회사 담당자",
    email: "manager@company.com",
    telephoneNumber: "+821011111111",
    mobilePhoneNumber: "+821011111111",
    faxNumber: "+821011111111"
  )

  def setup() {
    projectService.create(
      new ProjectRequests.CreateRequest(
        id: id,
        name: name,
        customerId: customerId,
        managerId: managerId,
        customerManagerContact: customerManagerContact
      )
    )
  }

  def "존재 - 아이디로 확인"() {
    when:
    def exists = projectService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = projectService.exists(unknownId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def project = projectService.get(id)

    then:
    project.id == id
    project.name == name
    project.managerId == managerId
    project.customerId == customerId
    project.customerManagerContact.name == customerManagerContact.name
    project.customerManagerContact.email == customerManagerContact.email
    project.customerManagerContact.telephoneNumber == customerManagerContact.telephoneNumber
    project.customerManagerContact.mobilePhoneNumber == customerManagerContact.mobilePhoneNumber
    project.customerManagerContact.faxNumber == customerManagerContact.faxNumber
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    projectService.get(unknownId)

    then:
    thrown(ProjectExceptions.NotFoundException)
  }

  def "수정 - 수정"() {
    when:
    def name = "테스트 프로젝트2"

    def customerId = CompanyId.from("CUST1")

    def managerId = UserId.from("kjh")

    def customerManagerContact = new Contact(
      name: "고객 회사 담당자2",
      email: "manager2@company.com",
      telephoneNumber: "+821011111112",
      mobilePhoneNumber: "+821011111112",
      faxNumber: "+821011111112"
    )

    projectService.update(
      new ProjectRequests.UpdateRequest(
        id: id,
        name: name,
        customerId: customerId,
        managerId: managerId,
        customerManagerContact: customerManagerContact
      )
    )

    def project = projectService.get(id)

    then:
    project.id == id
    project.name == name
    project.managerId == managerId
    project.customerId == customerId
    project.customerManagerContact.name == customerManagerContact.name
    project.customerManagerContact.email == customerManagerContact.email
    project.customerManagerContact.telephoneNumber == customerManagerContact.telephoneNumber
    project.customerManagerContact.mobilePhoneNumber == customerManagerContact.mobilePhoneNumber
    project.customerManagerContact.faxNumber == customerManagerContact.faxNumber
  }

}
