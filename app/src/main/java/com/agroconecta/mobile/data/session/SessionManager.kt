package com.agroconecta.mobile.data.session

object SessionManager {

    private var _session: UserSession? = null

    val session: UserSession?
        get() = _session

    fun saveSession(session: UserSession) {
        _session = session
    }

    fun clearSession() {
        _session = null
    }

    fun isLoggedIn(): Boolean = _session != null

    fun currentRole(): UserRole? = _session?.role
}