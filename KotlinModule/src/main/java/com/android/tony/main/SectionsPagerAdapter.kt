package com.android.tony.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.tony.kotlin.R
import com.android.tony.kotlin.ui.main.TabOneFragment
import com.android.tony.kotlin.ui.main.TabTwoFragment

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val TAB_TITLES = arrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3
    )

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        val pageIdx = position + 1
        if (position == 0) {
            return TabOneFragment.newInstance(pageIdx)
        } else {
            return TabTwoFragment.newInstance(pageIdx)
//            return PlaceholderFragment.newInstance(position + 1)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
//        return 2
        return TAB_TITLES.size
    }

//    @StringRes
//    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
//    private final Context mContext;
//
//    public SectionsPagerAdapter(Context context, FragmentManager fm) {
//        super(fm);
//        mContext = context;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        // getItem is called to instantiate the fragment for the given page.
//        // Return a PlaceholderFragment (defined as a static inner class below).
//        return PlaceholderFragment.newInstance(position + 1);
//    }
//
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mContext.getResources().getString(TAB_TITLES[position]);
//    }
//
//    @Override
//    public int getCount() {
//        // Show 2 total pages.
//        return 2;
//    }

}