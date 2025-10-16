package ar.edu.unicen.seminario.ddl.data.DTO

import ar.edu.unicen.seminario.ddl.models.Requirements
import com.google.gson.annotations.SerializedName

class RequirementsDTO (
    @SerializedName("minimum") val minimum: String,
    @SerializedName("recommended") val recommended: String,
){

    fun toRequirements(): Requirements {
        return Requirements(
            minimum = minimum,
            recommended = recommended
        )
    }
}