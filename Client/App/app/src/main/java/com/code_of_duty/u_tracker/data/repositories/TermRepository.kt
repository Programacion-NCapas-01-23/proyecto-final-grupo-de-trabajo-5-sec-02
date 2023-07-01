package com.code_of_duty.u_tracker.data.repositories

import com.code_of_duty.u_tracker.data.network.SafeApiRequest
import com.code_of_duty.u_tracker.data.network.UtrackerApiClient
import com.code_of_duty.u_tracker.data.network.response.IdealTermResponse
import com.code_of_duty.u_tracker.data.network.response.PersonalTermResponse
import javax.inject.Inject

class TermRepository @Inject constructor(
    private val apiClient: UtrackerApiClient
    ): SafeApiRequest() {

        suspend fun getIdealTerms(token: String): List<IdealTermResponse> {
            return apiRequest {
                apiClient.getPensum(token)
            }
        }

        suspend fun getPersonalTerms(token:String): List<PersonalTermResponse> {
            return apiRequest {
                apiClient.getPersonalTerms(token)
            }
        }
}
