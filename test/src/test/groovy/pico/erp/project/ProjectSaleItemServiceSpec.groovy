package pico.erp.project

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.item.ItemId
import pico.erp.project.sale.item.ProjectSaleItemExceptions
import pico.erp.project.sale.item.ProjectSaleItemId
import pico.erp.project.sale.item.ProjectSaleItemRequests
import pico.erp.project.sale.item.ProjectSaleItemService
import pico.erp.shared.IntegrationConfiguration
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

  def projectId = ProjectId.from("sample-project1")

  def itemId = ItemId.from("item-1")

  def id = ProjectSaleItemId.from("sale-item-1")

  def unknownId = ProjectSaleItemId.from("unknown")

  def setup() {
    projectSaleItemService.create(
      new ProjectSaleItemRequests.CreateRequest(
        id: id,
        projectId: projectId,
        itemId: itemId,
        unitPrice: 20000
      )
    )
  }

  def "존재 - 아이디로 확인"() {
    when:
    def exists = projectSaleItemService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = projectSaleItemService.exists(unknownId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def saleItem = projectSaleItemService.get(id)

    then:
    saleItem.id == id
    saleItem.projectId == projectId
    saleItem.unitPrice == 20000
    saleItem.itemId == itemId
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    projectSaleItemService.get(unknownId)

    then:
    thrown(ProjectSaleItemExceptions.NotFoundException)
  }


  def "조회 - 프로젝트 아이디로 조회"() {
    when:

    def saleItems = projectSaleItemService.getAll(projectId)
    def saleItem = saleItems[0]

    then:
    saleItems.size() == 1
    saleItem.unitPrice == 20000
    saleItem.itemId == itemId
  }

  def "삭제 - 삭제"() {
    when:
    projectSaleItemService.delete(
      new ProjectSaleItemRequests.DeleteRequest(
        id: id
      )
    )
    def saleItems = projectSaleItemService.getAll(projectId)

    then:
    saleItems.size() == 0
  }

  def "수정 - 수정"() {
    when:
    projectSaleItemService.update(
      new ProjectSaleItemRequests.UpdateRequest(
        id: id,
        unitPrice: 10000
      )
    )
    def saleItems = projectSaleItemService.getAll(projectId)
    def saleItem = projectSaleItemService.get(id)

    then:
    saleItems.size() == 1
    saleItem.unitPrice == 10000
    saleItem.itemId == itemId
  }
}
