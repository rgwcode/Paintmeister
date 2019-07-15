package lu.weidig.paintmeister.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
        holder.manufacturerItem.text = manufacturer.name
        if (adapter.isExpanded(position))
            holder.dropDownIcon.setImageResource(R.drawable.ic_dropdown_open)
        else
            holder.dropDownIcon.setImageResource(R.drawable.ic_dropdown_closed)
    }

    class ManufacturerItemViewHolder(view: View, adapter: FlexibleAdapter<*>) :
        ExpandableViewHolder(view, adapter, true) {
        var manufacturerItem: TextView = view.findViewById(R.id.manufacturerName)
        val dropDownIcon: ImageView = view.findViewById(R.id.dropdown_icon_manufacturer)

        init {
            view.setOnClickListener {
                toggleExpansion()
            }
        }

        override fun expandView(position: Int) {
            super.expandView(position)
            dropDownIcon.setImageResource(R.drawable.ic_dropdown_open)
        }

        override fun collapseView(position: Int) {
            super.collapseView(position)
            dropDownIcon.setImageResource(R.drawable.ic_dropdown_closed)
        }
    }
}