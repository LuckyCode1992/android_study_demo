package com.justcode.hxl.androidstudydemo.viewpagerfragment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.justcode.hxl.androidstudydemo.R
import com.justcode.hxl.progrect0.core.syntactic_sugar.gone
import com.justcode.hxl.progrect0.core.syntactic_sugar.visible
import kotlinx.android.synthetic.main.activity_viewpager_fragment.*

class ViewpagerFragmentActivity : AppCompatActivity() {

    val fragment1 by lazy {
        Fragment1()
    }
    val fragment2 by lazy {
        Fragment2()
    }
    val fragment3 by lazy {
        Fragment3()
    }
    val fragment4 by lazy {
        Fragment4()
    }
    val viewList: MutableList<BaseFragment> = ArrayList<BaseFragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager_fragment)

        viewList.add(fragment1)
        viewList.add(fragment2)
        viewList.add(fragment3)
        viewList.add(fragment4)

        val fragmentPagerAdapter = FragmentViewPagerAdapter(viewList,supportFragmentManager)
        viewpager.adapter = fragmentPagerAdapter
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
               if (p0==3){
                   rl_title.gone()
               }else{
                   rl_title.visible()
               }
                when(p0){
                    0->{
                        tv_title.text = "微信"
                    }
                    1->{
                        tv_title.text = "通讯录"
                    }
                    2->{
                        tv_title.text = "发现"
                    }
                }
            }

        })


    }
}


class FragmentViewPagerAdapter(var viewList: MutableList<BaseFragment>, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {
    override fun getItem(p0: Int): Fragment {
        return viewList[p0]
    }

    override fun getCount(): Int {
        return viewList.size
    }

}