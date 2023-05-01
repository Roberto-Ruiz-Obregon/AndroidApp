package com.example.kotlin.robertoruizapp.framework.view.activities

import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document

/**
 * Creates the interface for ClickListener and has the onClick method
 *
 */
interface CursoClickListener {
    /**
     * Add the parameter of [Document] to the onClick method
     *
     * @param document [Document] object
     */
    fun onClick(document: Document)
}