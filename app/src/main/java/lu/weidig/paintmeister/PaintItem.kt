package lu.weidig.paintmeister

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.toColorInt
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractSectionableItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import lu.weidig.paintmeister.data.entity.Paint

class PaintItem(private val paint: Paint, header: PaintLineItem?) :
    AbstractSectionableItem<PaintItem
    .PaintItemViewHolder, PaintLineItem>(header) {
    override fun equals(other: Any?): Boolean {
        if (other is PaintItem) {
            return paint.id == other.paint.id
        }
        return false
    }

    override fun hashCode(): Int {
        return paint.id!!.hashCode()
    }

    override fun getLayoutRes(): Int {
        return R.layout.recyclerview_paint_item
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<*>>):
            PaintItemViewHolder {
        return PaintItemViewHolder(view, adapter)
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<*>>, holder: PaintItemViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        holder.paintName.text = paint.name + paint.id
        if (paint.color != "") {
            val curColor = Color.parseColor(paint.color)

            // Calculate a contrasting color for the text color matching the background color
            val floatArray = FloatArray(3)
            ColorUtils.colorToHSL(curColor, floatArray)

            val contrastingColor: Int
            contrastingColor = if (floatArray[2] >= 0.175) Color.BLACK else Color.WHITE
            holder.paintName.setBackgroundColor(paint.color.toColorInt())
            holder.paintName.setTextColor(contrastingColor)
        } else {
            holder.paintName.setBackgroundColor(Color.WHITE)
            holder.paintName.setTextColor(Color.BLACK)
        }

    }

    inner class PaintItemViewHolder(view: View, adapter: FlexibleAdapter<*>) :
        FlexibleViewHolder(view, adapter) {
        var paintName: TextView = view.findViewById(R.id.paintName)
    }
}