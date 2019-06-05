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
import lu.weidig.paintmeister.data.entity.Paint
import lu.weidig.paintmeister.data.entity.PaintLine
import lu.weidig.paintmeister.data.viewmodel.PaintListViewModel

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
        val paintlinelist = ArrayList<IHeader<PaintLineItem.PaintLineItemViewHolder>>()
        val paintLineHeaderItem = PaintLineItem(PaintLine(id = 1, name = "Paintline1"))
        val paintLineHeaderItem2 = PaintLineItem(PaintLine(id = 2, name = "Paintline2"))

        for (i in 0..20) {
            paintLineHeaderItem2.addSubItem(
                PaintItem(
                    Paint(name = "Test" + i),
                    paintLineHeaderItem2
                )
            )
        }
        paintlinelist.add(paintLineHeaderItem)
        paintlinelist.add(paintLineHeaderItem2)

        val adapter = FlexibleAdapter(paintlinelist)
        adapter.setDisplayHeadersAtStartUp(true)
        adapter.setStickyHeaders(true)
        adapter.stickyHeaderElevation = 50
        adapter.isAutoCollapseOnExpand = false
        paintRecyclerView.adapter = adapter

        paintRecyclerView.layoutManager = LinearLayoutManager(this)
        paintListViewModel.paints.observe(
            this,
            Observer { paints ->
                paints?.let {
                    for (paint in paints) {
                        paintLineHeaderItem.addSubItem(PaintItem(paint, paintLineHeaderItem))
                    }
                    adapter.updateDataSet(paintlinelist)
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
