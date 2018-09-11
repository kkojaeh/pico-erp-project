package pico.erp.project

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.data.CompanyId
import pico.erp.item.data.ItemId
import pico.erp.project.data.ProjectId
import pico.erp.project.sale.item.ProjectSaleItemRequests
import pico.erp.project.sale.item.ProjectSaleItemService
import pico.erp.project.sale.item.data.ProjectSaleItemId
import pico.erp.shared.IntegrationConfiguration
import pico.erp.shared.data.Contact
import pico.erp.user.data.UserId
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class ProjectSaleItemServiceSpec extends Specification {

  @Autowired
  ProjectSaleItemService projectSaleItemService

  @Autowired
  ProjectService projectService

  def setup() {
    projectService.create(new ProjectRequests.CreateRequest(id: ProjectId.from("P1"), name: "프로젝트1",
      customerId: CompanyId.from("CUST2"),
      managerId: UserId.from("ysh"),
      customerManagerContact: new Contact(name: "고객 회사 담당자", email: "manager@company.com", telephoneNumber: "+821011111111", mobilePhoneNumber: "+821011111111", faxNumber: "+821011111111")))
  }

  def "판매 상품 추가"() {
    when:
    projectSaleItemService.create(
      new ProjectSaleItemRequests.CreateRequest(
        projectId: ProjectId.from("P1"),
        id: ProjectSaleItemId.from("sale-item-1"),
        itemId: ItemId.from("item-1"),
        unitPrice: 20000
      )
    )
    def saleItems = projectSaleItemService.getAll(ProjectId.from("P1"))
    def saleItem = projectSaleItemService.get(ProjectSaleItemId.from("sale-item-1"))

    then:
    saleItems.size() == 1
    saleItem.unitPrice == 20000
    saleItem.itemId == ItemId.from("item-1")
  }

  def "판매 상품 삭제"() {
    when:
    projectSaleItemService.create(
      new ProjectSaleItemRequests.CreateRequest(
        projectId: ProjectId.from("P1"),
        id: ProjectSaleItemId.from("sale-item-1"),
        itemId: ItemId.from("item-1"),
        unitPrice: 20000
      )
    )
    projectSaleItemService.delete(
      new ProjectSaleItemRequests.DeleteRequest(
        id: ProjectSaleItemId.from("sale-item-1")
      )
    )
    def saleItems = projectSaleItemService.getAll(ProjectId.from("P1"))

    then:
    saleItems.size() == 0
  }

  def "판매 상품 수정"() {
    when:
    projectSaleItemService.create(
      new ProjectSaleItemRequests.CreateRequest(
        projectId: ProjectId.from("P1"),
        id: ProjectSaleItemId.from("sale-item-1"),
        itemId: ItemId.from("item-1"),
        unitPrice: 20000
      )
    )
    projectSaleItemService.update(
      new ProjectSaleItemRequests.UpdateRequest(
        id: ProjectSaleItemId.from("sale-item-1"),
        unitPrice: 10000
      )
    )
    def saleItems = projectSaleItemService.getAll(ProjectId.from("P1"))
    def saleItem = projectSaleItemService.get(ProjectSaleItemId.from("sale-item-1"))

    then:
    saleItems.size() == 1
    saleItem.unitPrice == 10000
    saleItem.itemId == ItemId.from("item-1")
  }
}
