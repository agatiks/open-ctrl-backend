package com.kndzhut.open_control.infra.repository.info

import com.kndzhut.open_control.domain.*
import com.kndzhut.open_control.usecase.info.business.CreateBusinessRequest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
class InfoJDBCOperations(
    val jdbcTemplate: JdbcTemplate
) {
    fun addAllKNO(knoList: List<Kno>) {
        for (kno in knoList) {
            val query = "insert into kno values (${kno.id}, '${kno.name}') " +
                    "on conflict (id) do update set name='${kno.name}'"
            jdbcTemplate.execute(query)
        }
    }

    fun getAllKNO(): List<Kno> {
        val query = "select * from kno"
        return jdbcTemplate.query(query) { rs, _ ->
            Kno(rs.getInt("id"), rs.getString("name"))
        }
    }

    fun addAllMeasures(measures: List<Measure>) {
        for (measure in measures) {
            val query = "insert into measures values (${measure.id}, '${measure.name}', '${measure.knoId}') " +
                    "on conflict (id) do update set name='${measure.name}', kno_id='${measure.knoId}'"
            jdbcTemplate.execute(query)
        }
    }

    fun getAllMeasuresByKno(knoId: Int): MutableList<MeasureDto> {
        val query = "select id, name from measures where kno_id=$knoId"
        return jdbcTemplate.query(query) { rs, _ ->
            MeasureDto(
                id = rs.getInt("id"),
                name = rs.getString("name")
            )
        }
    }

    fun createUser(userId: String, role: Role) {
        val query = "insert into app_user values ('$userId', '${role.name}');" +
                "insert into ${role.name.lowercase()}_user_info (id) values ('$userId')"
        jdbcTemplate.execute(query)
    }

    fun updateInspectionUserInfo(user: InspectionUser) {
        val query = with(user) {
            "update inspection_user_info " +
                    "set kno_id=${knoId?.let { "'$it'" }}, email=${email?.let { "'$it'" }}, " +
                    "first_name=${firstName?.let { "'$it'" }}, surname=${surName?.let { "'$it'" }}, last_name=${lastName?.let { "'$it'" }} " +
                    "where id='${id}'"
        }
        jdbcTemplate.execute(query)
    }

    fun updateBusinessUserInfo(user: BusinessUser) {
        val query = with(user) {
            "update business_user_info " +
                    "set first_name=${firstName?.let { "'$it'" }}, surname=${surName?.let { "'$it'" }}, last_name=${lastName?.let { "'$it'" }}, " +
                    "inn=${inn}, email=${email?.let { "'$it'" }}, snils=${snils} " +
                    "where id='${id}'"
        }
        jdbcTemplate.execute(query)
    }

    fun getUserRole(userId: String): Role {
        val query = "select role from app_user where id='$userId'"
        return jdbcTemplate.query(query) { rs, _ ->
            Role.valueOf(rs.getString("role"))
        }[0]
    }

    fun getInspectionUserInfo(userId: String): InspectionUser {
        val query = "select * from inspection_user_info where id='$userId'"
        return jdbcTemplate.query(query) { rs, _ ->
            InspectionUser(
                id = rs.getString("id"),
                firstName = rs.getString("first_name"),
                surName = rs.getString("surname"),
                lastName = rs.getString("last_name"),
                email = rs.getString("email"),
                knoId = rs.getInt("kno_id")
            )
        }[0]
    }

    fun getBusinessUserInfo(userId: String): BusinessUser {
        val query = "select * from business_user_info where id='$userId'"
        return jdbcTemplate.query(query) { rs, _ ->
            BusinessUser(
                id = rs.getString("id"),
                firstName = rs.getString("first_name"),
                surName = rs.getString("surname"),
                lastName = rs.getString("last_name"),
                email = rs.getString("email"),
                inn = rs.getInt("inn"),
                snils = rs.getInt("snils")
            )
        }[0]
    }

    fun createBusiness(request: CreateBusinessRequest): UUID {
        val addressUUID = UUID.randomUUID()
        val businessUUID = UUID.randomUUID()
        val query = "insert into address values ('$addressUUID', '${request.address}');" +
                "insert into business values ('${businessUUID}','${request.userId}', '${request.type}', '${request.kind}', " +
                "'${request.objectClass}', '${request.activityClass}', '$addressUUID')"
        jdbcTemplate.execute(query)
        return businessUUID
    }

    fun getBusiness(businessId: UUID): Business {
        val query =
            "select * from (business join address on business.address_id=address.id) where business.id='$businessId'"
        return jdbcTemplate.query(query) { rs, _ ->
            Business(
                id = rs.getObject("id") as UUID,
                userId = rs.getString("user_id"),
                type = rs.getString("type"),
                kind = rs.getString("kind"),
                objectClass = rs.getString("object_class"),
                activityClass = rs.getString("activity_class"),
                address = rs.getString("place")
            )
        }[0]
    }

    fun getKNO(knoId: Int): String {
        val query =
            "select name from kno where id=$knoId"
        return jdbcTemplate.query(query) { rs, _ -> rs.getString("name") }[0]
    }

    fun getMeasure(measureId: Int): String {
        val query =
            "select name from measures where id=$measureId"
        return jdbcTemplate.query(query) { rs, _ -> rs.getString("name") }[0]
    }

}

