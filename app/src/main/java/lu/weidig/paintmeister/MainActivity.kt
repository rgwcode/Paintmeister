package lu.weidig.paintmeister

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IHeader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import lu.weidig.paintmeister.data.PaintmeisterRoomDatabase
import lu.weidig.paintmeister.data.entity.Manufacturer
import lu.weidig.paintmeister.data.entity.Paint
import lu.weidig.paintmeister.data.entity.PaintLine
import lu.weidig.paintmeister.data.viewmodel.PaintListViewModel
import lu.weidig.paintmeister.item.ManufacturerItem
import lu.weidig.paintmeister.item.PaintItem
import lu.weidig.paintmeister.item.PaintLineItem

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var paintListViewModel: PaintListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
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

        //val adapter = PaintListAdapter(this)
        paintListViewModel = ViewModelProviders.of(this).get(PaintListViewModel::class.java)
        val manufacturerList = ArrayList<IHeader<ManufacturerItem.ManufacturerItemViewHolder>>()

        val manufacturerItem = ManufacturerItem(
            Manufacturer(
                id = 1,
                name = "Manufacturer1"
            )
        )
        val manufacturerItem2 = ManufacturerItem(
            Manufacturer(
                id = 2,
                name = "Manufacturer2"
            )
        )

        manufacturerList.add(manufacturerItem)
        manufacturerList.add(manufacturerItem2)

        val paintLineHeaderItem =
            PaintLineItem(
                PaintLine(id = 1, name = "Paintline1"),
                manufacturerItem
            )
        val paintLineHeaderItem2 =
            PaintLineItem(
                PaintLine(id = 2, name = "Paintline2"),
                manufacturerItem
            )
        val paintLineHeaderItem3 =
            PaintLineItem(
                PaintLine(id = 3, name = "Paintline3"),
                manufacturerItem2
            )
        val paintLineHeaderItem4 =
            PaintLineItem(
                PaintLine(id = 4, name = "Paintline4"),
                manufacturerItem2
            )

        for (i in 0..20) {
            paintLineHeaderItem2.addSubItem(
                PaintItem(
                    Paint(id = i.toLong(), name = "Test" + i),
                    paintLineHeaderItem2
                )
            )
        }
        for (i in 21..40) {
            paintLineHeaderItem3.addSubItem(
                PaintItem(
                    Paint(id = i.toLong(), name = "Test" + i),
                    paintLineHeaderItem3
                )
            )
        }
        for (i in 41..60) {
            paintLineHeaderItem4.addSubItem(
                PaintItem(
                    Paint(id = i.toLong(), name = "Test" + i),
                    paintLineHeaderItem4
                )
            )
        }

        manufacturerItem.addSubItem(paintLineHeaderItem)
        manufacturerItem.addSubItem(paintLineHeaderItem2)
        manufacturerItem2.addSubItem(paintLineHeaderItem3)
        manufacturerItem2.addSubItem(paintLineHeaderItem4)

        val adapter = FlexibleAdapter(manufacturerList)
        adapter.setDisplayHeadersAtStartUp(true)
        adapter.setStickyHeaders(true)
        adapter.isAutoCollapseOnExpand = false
        paintRecyclerView.adapter = adapter

        paintRecyclerView.layoutManager = LinearLayoutManager(this)
        paintListViewModel.paints.observe(
            this,
            Observer { paints ->
                paints?.let {
                    for (paint in paints) {
                        paintLineHeaderItem.addSubItem(
                            PaintItem(
                                paint,
                                paintLineHeaderItem
                            )
                        )
                    }
                }
            }
        )

        fab.setOnClickListener()
        {
            val db = PaintmeisterRoomDatabase.getDatabase(this, GlobalScope)
            GlobalScope.launch {
                db.clearAllTables()
            }
        }
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
