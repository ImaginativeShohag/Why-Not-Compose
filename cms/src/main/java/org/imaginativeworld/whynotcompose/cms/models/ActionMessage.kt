package org.imaginativeworld.whynotcompose.cms.models

interface ViewAction

data class ActionMessage(
    val message: String,
    val action: ViewAction? = null
)
