package lu.weidig.paintmeister.item

import android.view.View
import android.widget.CheckedTextView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractExpandableItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.ExpandableViewHolder
import lu.weidig.paintmeister.R
import lu.weidig.paintmeister.data.entity.Manufacturer

data class ManufacturerItem(private val manufacturer: Manufacturer) : AbstractExpandableItem<
        ManufacturerItem.ManufacturerItemViewHolder, PaintLineItem>() {
    init {
        // Expand at start up
        mExpanded = true
    }

    override fun getLayoutRes(): Int {
        return R.layout.recyclerview_manufacturer_item
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<*>>):
            ManufacturerItemViewHolder {
        return ManufacturerItemViewHolder(view, adapter)
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<*>>, holder: ManufacturerItemViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        holder.name.text = manufacturer.name
        holder.name.isChecked = adapter.isExpanded(position)
    }

    class ManufacturerItemViewHolder(view: View, adapter: FlexibleAdapter<*>) :
        ExpandableViewHolder(view, adapter, true) {
        var name: CheckedTextView = view.findViewById(R.id.manufacturerName)

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