package com.project.chambaapp.api_services

import android.content.Context
import android.content.SharedPreferences

object WorkerSingleton {
    private const val PREFS_NAME = "WorkerPrefs"
    private const val KEY_WORKER_ID = "worker_id"
    private const val KEY_WORKER_JOBS = "worker_jobs"
    private const val KEY_WORKER_STATE = "worker_state"

    // Función para obtener SharedPreferences
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Métodos para establecer y obtener el worker_id
    fun setWorkerId(context: Context, id: Long?) {
        getSharedPreferences(context).edit().putLong(KEY_WORKER_ID, id ?: 0L).apply()
    }

    fun getWorkerId(context: Context): Long? {
        val id = getSharedPreferences(context).getLong(KEY_WORKER_ID, 0L)
        return if (id == 0L) null else id
    }

    // Métodos para establecer y obtener worker_jobs
    fun setWorkerJobs(context: Context, jobs: Array<Long>) {
        val jobsStringSet = jobs.map { it.toString() }.toSet()
        getSharedPreferences(context).edit().putStringSet(KEY_WORKER_JOBS, jobsStringSet).apply()
    }

    fun getWorkerJobs(context: Context): Array<Long> {
        val jobsStringSet = getSharedPreferences(context).getStringSet(KEY_WORKER_JOBS, emptySet())
        return jobsStringSet?.map { it.toLong() }?.toTypedArray() ?: emptyArray()
    }

    fun setWorkerState(context: Context, state: Short) {
        getSharedPreferences(context).edit().putInt(KEY_WORKER_STATE, state.toInt() ?: 0).apply()
    }

    fun getWorkerState(context: Context): Int {
        val state = getSharedPreferences(context).getInt(KEY_WORKER_STATE, 0)
        return if (state == 0) 0 else state
    }

    // Método para limpiar los datos
    fun clear(context: Context) {
        getSharedPreferences(context).edit().clear().apply()
    }

    // Método para establecer todos los datos de una vez
    fun setAll(context: Context, id: Long, jobs: Array<Long>) {
        setWorkerId(context, id)
        setWorkerJobs(context, jobs)
    }
}