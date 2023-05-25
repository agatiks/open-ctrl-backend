package com.kndzhut.open_control.infra.clients.google_sheets

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.kndzhut.open_control.domain.AppointmentStartDto
import com.kndzhut.open_control.domain.Measure
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class GoogleSheetsClient {
    companion object {
        private const val APPLICATION_NAME = "Google Sheets API Java Quickstart"
        private val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()
        private const val TOKENS_DIRECTORY_PATH = "tokens"
        private val SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY)
        private const val CREDENTIALS_FILE_PATH = "/client_secret.json"
        private val APPOINTMENTS_PAGES = mapOf(
            "Главархив" to 8,
            "ГИН" to 19,
            "ДЗН" to 1,
            "МОСКОМВЕТ" to 2,
            "ДТиУ" to 3,
            "ДТСЗН" to 4,
            "ОАТИ" to 5,
            "МАДИ" to 6,
            "ДТиРДТ" to 7,
            "ГОЧСиПБ" to 9,
            "МЖИ" to 10,
            "Депкульт" to 11,
            "ДЭПР" to 13,
            "МОСПРИРОДА" to 14,
            "МОСГОРНАСЛЕДИЕ" to 15,
            "МОСКОМСТРОЙИНВЕСТ" to 16,
            "МОСГОСТРОЙНАДЗОР" to 18,
            "ДЖКХ" to 12,
            "ДОНМ" to 17,
        )
    }

    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {
        // Load client secrets.
        val input = this.javaClass.getResourceAsStream(CREDENTIALS_FILE_PATH)
            ?: throw FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH)
        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(input))

        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES
        )
            .setDataStoreFactory(FileDataStoreFactory(File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

    private fun getService(): Sheets {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        return Sheets.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
            .setApplicationName(APPLICATION_NAME)
            .build()
    }

    fun getValues(service: Sheets, spreadsheetId: String, range: String):
            MutableList<MutableList<Any>> {
        return service.spreadsheets()
            .values()[spreadsheetId, range]
            .execute().getValues()
    }

    fun getKnoList(): MutableList<Pair<Int, String>> {
        val service = getService()
        val spreadsheetId = "1fh3Kh9sOzzeVABehJot_b4wKercRrIaQThiKB2N8SB4"
        val range = "КНО!A2:B"
        val values = getValues(service, spreadsheetId, range)
        val knoList = mutableListOf<Pair<Int, String>>()
        for (row in values) {
            if (row.isNotEmpty())
                knoList.add(Pair((row[0] as String).toInt(), row[1] as String))
        }
        //println(knoList)
        return knoList
    }

    fun getMeasuresList(): MutableList<Measure> {
        val service = getService()
        val spreadsheetId = "1fh3Kh9sOzzeVABehJot_b4wKercRrIaQThiKB2N8SB4"
        val range = "КНО!A2:C"
        val values = getValues(service, spreadsheetId, range)
        //println(values)
        val measures = mutableListOf<Measure>()
        var currId = 0
        values.mapIndexed { id, row ->
            //print(row[0].javaClass)
            if (row[0] != "")
                currId = (row[0] as String).toInt()
            measures.add(
                Measure(
                    id = id,
                    knoId = currId,
                    name = row[2] as String
                )
            )
        }
        //println(measures)
        return measures
    }

    fun getAppointments(): MutableList<AppointmentStartDto> {
        val service = getService()
        val spreadsheetId = "194HDtajEpOLftvudMuaYowFExjHhlxrCWtSeGhbGOT8"
        val appointments = mutableListOf<AppointmentStartDto>()
        for (page in APPOINTMENTS_PAGES.keys) {
            val range = "$page!A2:H"
            val values = getValues(service, spreadsheetId, range)
            var isTimes = false
            val knoId = APPOINTMENTS_PAGES[page] ?: 0
            for (row in values) {
                if (row.isEmpty()) continue
                if (row[0] == "Июнь") {
                    isTimes = true
                    continue
                }
                if (!isTimes) continue
                val dates = row.filter { it != "" }
                for (pairNum in 0 until dates.size / 3) {
                    appointments.add(
                        AppointmentStartDto(
                            id = UUID.randomUUID(),
                            timestamp = getDateTime(dates[pairNum * 2], dates[pairNum * 2 + 1]),
                            knoId = knoId
                        )
                    )
                }
            }
        }
        return appointments
    }

    /**
     * day in format: "mm/dd/yyyy"
     * time in format: "hh:mm-hh:mm"
     */
    private fun getDateTime(day: Any, time: Any): Timestamp {
        val timeString = "${day as String} ${(time as String).substringBefore("-")}"
        //println(timeString)
        val format = DateTimeFormatter.ofPattern("M/d/yyyy H:mm")
        return Timestamp.valueOf(LocalDateTime.parse(timeString, format))
    }
}

