package lu.weidig.paintmeister.item

import android.view.View
import android.widget.TextView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractExpandableHeaderItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.ExpandableViewHolder
import lu.weidig.paintmeister.R
import lu.weidig.paintmeister.data.entity.Manufacturer
import lu.weidig.paintmeister.item.ManufacturerItem.ManufacturerItemViewHolder

data class ManufacturerItem(private val manufacturer: Manufacturer) : AbstractExpandableHeaderItem<
        ManufacturerItemViewHolder, PaintLineItem>() {
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
    }

    inner class ManufacturerItemViewHolder(view: View, adapter: FlexibleAdapter<*>) :
        ExpandableViewHolder(view, adapter, true) {
        var manufacturerItem: TextView = view.findViewById(R.id.manufacturerName)

        init {
            view.setOnClickListener { toggleExpansion() }
        }
    }
}