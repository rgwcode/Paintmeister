package lu.weidig.paintmeister.item

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.toColorInt
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractSectionableItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import lu.weidig.paintmeister.R
import lu.weidig.paintmeister.data.entity.Paint

data class PaintItem(private val paint: Paint, private var header: PaintLineItem?) :
    AbstractSectionableItem<PaintItem.PaintItemViewHolder, PaintLineItem>(header) {
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
        holder.paintName.text = paint.name + " " + paint.numOwned
        val curColor = Color.parseColor(paint.color)

        // Calculate a contrasting color for the text color matching the background color
        val floatArray = FloatArray(3)
        ColorUtils.colorToHSL(curColor, floatArray)

        val contrastingColor: Int
        contrastingColor = if (floatArray[2] >= 0.175) Color.BLACK else Color.WHITE
        holder.paintName.setTextColor(contrastingColor)
        holder.addButton.setColorFilter(contrastingColor)
        holder.removeButton.setColorFilter(contrastingColor)
        holder.removeButton.tag = paint.id
        holder.addButton.tag = paint.id

        if (paint.metallic) {
            val gradient = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(0xff, curColor, 0xff)
            )
            // Not really deprecated, alternative won't work for SDK < 15
            @Suppress("DEPRECATION")
            holder.itemView.setBackgroundDrawable(gradient)

        } else {
            holder.itemView.setBackgroundColor(paint.color.toColorInt())
        }
    }

    inner class PaintItemViewHolder(view: View, adapter: FlexibleAdapter<*>) :
        FlexibleViewHolder(view, adapter) {
        val paintName: TextView = view.findViewById(R.id.paintName)
        val addButton: ImageButton = view.findViewById(R.id.add_paint_button)
        val removeButton: ImageButton = view.findViewById(R.id.remove_paint_button)
    }
}