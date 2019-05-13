package lu.weidig.paintmeister

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recyclerview_paint_item.*
import lu.weidig.paintmeister.data.entity.Manufacturer
import lu.weidig.paintmeister.data.entity.Paint

class PaintListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<PaintListAdapter.PaintViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var paints = emptyList<Paint>()
    private var manufacturers = emptyList<Manufacturer>()

    inner class PaintViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {
        override val containerView: View? get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaintViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_paint_item, parent, false)
        return PaintViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PaintViewHolder, position: Int) {
        val current = paints[position]
        if (current.color != "") {
            val curColor = Color.parseColor(current.color)

            // Calculate a contrasting color for the text color matching the background color
            val floatArray = FloatArray(3)
            ColorUtils.colorToHSL(curColor, floatArray)

            val contrastingColor: Int
            contrastingColor = if (floatArray[2] >= 0.175) Color.BLACK else Color.WHITE

            holder.paintName.setBackgroundColor(current.color.toColorInt())
            holder.paintName.setTextColor(contrastingColor)
        }

        holder.paintName.text = current.name
    }

    internal fun setPaints(paints: List<Paint>) {
        this.paints = paints
        notifyDataSetChanged()
    }

    internal fun setManufacturers(manufacturers: List<Manufacturer>) {
        this.manufacturers = manufacturers
        notifyDataSetChanged()
    }

    override fun getItemCount() = paints.size
}