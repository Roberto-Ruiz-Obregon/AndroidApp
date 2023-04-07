package com.example.kotlin.robertoruizapp.framework.view.activities

import com.example.kotlin.robertoruizapp.data.network.model.Document

interface CursoClickListener {
    fun onClick(document: Document)
}