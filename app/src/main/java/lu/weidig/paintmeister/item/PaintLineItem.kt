package lu.weidig.paintmeister.item

import android.view.View
import android.widget.CheckedTextView
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
        // Keep collapsed at start up
        mExpanded = false
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
        holder.name.text = paintLine.name
        holder.name.isChecked = adapter.isExpanded(position)
    }

    inner class PaintLineItemViewHolder(view: View, adapter: FlexibleAdapter<*>) :
        ExpandableViewHolder(view, adapter, true) {
        val name: CheckedTextView = view.findViewById(R.id.paintLineName)

        init {
            view.setOnClickListener {
                toggleExpansion()
            }
        }

        override fun expandView(position: Int) {
            super.expandView(position)
            name.isChecked = true
        }

        override fun collapseView(position: Int) {
            super.collapseView(position)
            name.isChecked = false
        }
    }
}