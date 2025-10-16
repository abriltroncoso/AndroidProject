package ar.edu.unicen.seminario.ddl.data.DTO

import ar.edu.unicen.seminario.ddl.data.DTO.RequirementsDTO
import ar.edu.unicen.seminario.ddl.models.PlatformInfo
import com.google.gson.annotations.SerializedName

class PlatformInfoDTO (
    @SerializedName("platform") val platform: PlatformDTO,
    @SerializedName("released_at") val releasedAt: String,
    @SerializedName("requirements_") val requirementsEn: RequirementsDTO?,
){

    fun toPlatformInfo(): PlatformInfo {
        return PlatformInfo(
            platform = platform.toPlatform(),
            releasedAt = releasedAt,
            requirementsEn = requirementsEn?.toRequirements()
        )

    }
}