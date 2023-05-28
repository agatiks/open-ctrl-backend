package com.kndzhut.open_control.infra.repository.info

import com.kndzhut.open_control.domain.*
import com.kndzhut.open_control.usecase.info.business.CreateBusinessRequest
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InfoRepository(
    private val infoJDBCOperations: InfoJDBCOperations
) {
    fun addAllKno(knoList: List<Kno>) = infoJDBCOperations.addAllKNO(knoList)
    fun getAllKno(): List<Kno> = infoJDBCOperations.getAllKNO()
    fun addAllMeasures(measures: List<Measure>) = infoJDBCOperations.addAllMeasures(measures)
    fun getAllMeasuresByKno(knoId: Int): List<MeasureDto> = infoJDBCOperations.getAllMeasuresByKno(knoId)
    fun createUser(userId: String, role: Role) =
        infoJDBCOperations.createUser(userId, role)

    fun updateInspectionUserInfo(user: InspectionUser) =
        infoJDBCOperations.updateInspectionUserInfo(user)

    fun updateBusinessUserInfo(user: BusinessUser) =
        infoJDBCOperations.updateBusinessUserInfo(user)

    fun getUserRoleInfo(userId: String): Role =
        infoJDBCOperations.getUserRole(userId)

    fun getInspectionUserInfo(userId: String): InspectionUser =
        infoJDBCOperations.getInspectionUserInfo(userId)

    fun getBusinessUserInfo(userId: String): BusinessUser =
        infoJDBCOperations.getBusinessUserInfo(userId)

    fun createBusiness(request: CreateBusinessRequest): UUID =
        infoJDBCOperations.createBusiness(request)

    fun getBusiness(businessId: UUID): Business =
        infoJDBCOperations.getBusiness(businessId)
}