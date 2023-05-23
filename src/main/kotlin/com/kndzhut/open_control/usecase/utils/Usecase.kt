package com.kndzhut.open_control.usecase.utils

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

interface UseCase<Rq: Request, Rs: Response, Er: Error> {
    fun execute(request: Rq): UseCaseResult<Rs, Er>
}

class UseCaseResult<Success, Error> private constructor(
    private val success: Success? = null, private val error: Error? = null
)
 {
     companion object {
         fun <SuccessT, ErrorT> success(success: SuccessT): UseCaseResult<SuccessT, ErrorT> = UseCaseResult(success = success)

         fun <SuccessT, ErrorT> error(error: ErrorT): UseCaseResult<SuccessT, ErrorT> = UseCaseResult(error = error)
     }


     fun <T> fold(
        errorHandler: (Error) -> T = { throw IllegalStateException("There is not error result handler provided result=$it") },
        successHandler: (Success) -> T
    ): T {
        return when {
            success != null -> successHandler(success)
            error != null -> errorHandler(error)
            else -> throw java.lang.IllegalStateException()
        }
    }

}

fun UseCaseResult<*, *>.toResponseEntity(): ResponseEntity<*> = fold(
    errorHandler = { ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(it) },
    successHandler = { ResponseEntity.ok(it) }
)

class EmptyRequest(): Request
class EmptyError(): Error


interface Request
interface Response
interface Error