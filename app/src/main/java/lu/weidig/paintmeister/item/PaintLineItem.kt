package lu.weidig.paintmeister.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractExpandableHeaderItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.ExpandableViewHolder
import lu.weidig.paintmeister.R
import lu.weidig.paintmeister.data.entity.PaintLine
import lu.weidig.paintmeister.item.PaintLineItem.PaintLineItemViewHolder

data class PaintLineItem(private val paintLine: PaintLine, private var mHeader: ManufacturerItem) :
    AbstractExpandableHeaderItem<
            PaintLineItemViewHolder, PaintItem>() {
    init {
        isExpanded = false
    }

    override fun getExpansionLevel(): Int {
        return 1
    }

    override fun getLayoutRes(): Int {
        return R.layout.recyclerview_paint_line_item
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<*>>):
            PaintLineItemViewHolder {
        return PaintLineItemViewHolder(view, adapter)
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<*>>, holder: PaintLineItemViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        holder.paintLineName.text = paintLine.name
    }

    inner class PaintLineItemViewHolder(view: View, adapter: FlexibleAdapter<*>) :
        ExpandableViewHolder(view, adapter, true) {
        val paintLineName: TextView = view.findViewById(R.id.paintLineName)
        val dropdownicon: ImageView = view.findViewById(R.id.dropdown_icon_paint_line)
        var expandedd = false

        init {
            view.setOnClickListener {
                if (expandedd) {
                    dropdownicon.setImageResource(R.drawable.ic_dropdown_closed)
                    expandedd = false
                } else {
                    dropdownicon.setImageResource(R.drawable.ic_dropdown_open)
                    expandedd = true
                }
                toggleExpansion()
            }
        }
    }
}