package net.omisoft.aborovskoy.umoriliviper.app.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrendingResult(
    @SerializedName("total_count") var totalCount: String? = null,
    @SerializedName("incomplete_results") var incompleteResults: String? = null,
    @SerializedName("items") var items: List<TrendingRepo>? = null,
) : Parcelable