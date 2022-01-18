package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

data class ProjectPreview(
    override val id: Long,
    val name: String,
    val image: String //Todo mit Robin reden, wer daraus ein Image macht
): Identifiable
