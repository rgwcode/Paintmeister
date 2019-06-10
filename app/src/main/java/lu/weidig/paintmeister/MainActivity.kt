package lu.weidig.paintmeister

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import lu.weidig.paintmeister.data.PaintmeisterRoomDatabase
import lu.weidig.paintmeister.data.viewmodel.PaintListViewModel
import lu.weidig.paintmeister.item.ManufacturerItem
import lu.weidig.paintmeister.item.PaintItem
import lu.weidig.paintmeister.item.PaintLineItem

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var paintListViewModel: PaintListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setUpNavigationDrawer()
        setUpRecyclerView()
    }

    fun dumpDatabase(view: View) {
        val db = PaintmeisterRoomDatabase.getDatabase(this, GlobalScope)
        GlobalScope.launch {
            db.clearAllTables()
        }
    }

    fun setUpRecyclerView() {
        val manufacturerList = ArrayList<IFlexible<ManufacturerItem.ManufacturerItemViewHolder>>()
        val adapter = FlexibleAdapter(manufacturerList)

        paintListViewModel = ViewModelProviders.of(this).get(PaintListViewModel::class.java)
        adapter.setDisplayHeadersAtStartUp(true)
        adapter.setStickyHeaders(true)
        adapter.isAutoCollapseOnExpand = false

        paintRecyclerView.adapter = adapter
        paintRecyclerView.layoutManager = LinearLayoutManager(this)

        paintListViewModel.fullDepthManufacturers.observe(
            this,
            Observer { fullDepthManufacturers ->
                fullDepthManufacturers?.let {
                    for (fullDepthManufacturer in fullDepthManufacturers) {
                        val manufacturerItem = ManufacturerItem(fullDepthManufacturer.manufacturer)
                        manufacturerList.add(manufacturerItem)
                        for (paintLine in fullDepthManufacturer.paintLinesByName) {
                            val paintLineItem = PaintLineItem(paintLine.paintLine, manufacturerItem)
                            manufacturerItem.addSubItem(paintLineItem)
                            for (paint in paintLine.paintsByName) {
                                paintLineItem.addSubItem(
                                    PaintItem(
                                        paint,
                                        paintLineItem
                                    )
                                )
                            }

                        }
                    }
                    adapter.updateDataSet(manufacturerList)
                }
            }
        )
    }

    fun setUpNavigationDrawer() {
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
