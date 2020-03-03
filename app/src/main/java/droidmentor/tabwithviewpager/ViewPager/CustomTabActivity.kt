package droidmentor.tabwithviewpager.ViewPager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.tabs.TabLayout
import droidmentor.tabwithviewpager.Fragment.CallsFragment
import droidmentor.tabwithviewpager.Fragment.ChatFragment
import droidmentor.tabwithviewpager.Fragment.ContactsFragment
import droidmentor.tabwithviewpager.R
import droidmentor.tabwithviewpager.ViewPagerAdapter

class CustomTabActivity : AppCompatActivity() {
    //This is our tablayout
    private var tabLayout: TabLayout? = null
    //This is our viewPager
    private var viewPager: ViewPager? = null
    //Fragments
    var chatFragment: ChatFragment? = null
    var callsFragment: CallsFragment? = null
    var contactsFragment: ContactsFragment? = null
    var tabTitle = arrayOf("Call", "Chat", "Contacts")
    var unreadCount = intArrayOf(0, 5, 0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_without_icon)
        //Initializing viewPager
        viewPager = findViewById<View>(R.id.viewpager) as ViewPager
        viewPager!!.offscreenPageLimit = 3
        setupViewPager(viewPager)
        //Initializing the tablayout
        tabLayout = findViewById<View>(R.id.tablayout) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager)
        viewPager!!.currentItem = 1
        try {
            setupTabIcons()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        viewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                viewPager!!.setCurrentItem(position, false)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        // Associate searchable configuration with the SearchView
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Handle item selection
        return when (item.itemId) {
            R.id.action_status -> {
                Toast.makeText(this, "Home Status Click", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_settings -> {
                Toast.makeText(this, "Home Settings Click", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_with_icon -> {
                val withicon = Intent(this, TabWithIconActivity::class.java)
                startActivity(withicon)
                finish()
                true
            }
            R.id.action_without_icon -> {
                val withouticon = Intent(this, TabWOIconActivity::class.java)
                startActivity(withouticon)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        callsFragment = CallsFragment()
        chatFragment = ChatFragment()
        contactsFragment = ContactsFragment()
        adapter.addFragment(callsFragment!!, "CALLS")
        adapter.addFragment(chatFragment!!, "CHAT")
        adapter.addFragment(contactsFragment!!, "CONTACTS")
        viewPager!!.adapter = adapter
    }

    private fun prepareTabView(pos: Int): View {
        val view = layoutInflater.inflate(R.layout.custom_tab, null)
        val tv_title = view.findViewById<View>(R.id.tv_title) as TextView
        val tv_count = view.findViewById<View>(R.id.tv_count) as TextView
        tv_title.text = tabTitle[pos]
        if (unreadCount[pos] > 0) {
            tv_count.visibility = View.VISIBLE
            tv_count.text = "" + unreadCount[pos]
        } else tv_count.visibility = View.GONE
        return view
    }

    private fun setupTabIcons() {
        for (i in tabTitle.indices) { /*TabLayout.Tab tabitem = tabLayout.newTab();
            tabitem.setCustomView(prepareTabView(i));
            tabLayout.addTab(tabitem);*/
            tabLayout!!.getTabAt(i)!!.customView = prepareTabView(i)
        }
    }
}