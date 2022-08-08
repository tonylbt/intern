package com.android.tony.kotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.tony.kotlin.R
import com.android.tony.kotlin.grammar.GrammarArray
import com.android.tony.kotlin.grammar.GrammarBasics
import com.android.tony.kotlin.grammar.GrammarBasics.UpdateListener
import com.android.tony.kotlin.grammar.bean.WeeklyBean

class TabTwoFragment : Fragment() {
    private lateinit var mPageTitle : TextView
    private lateinit var mBtn1 : Button
    private lateinit var mTextContent : TextView
    private lateinit var mCurrentContent : String

    private lateinit var basicsGrammar: GrammarBasics
    private lateinit var mUpdateListener : UpdateListener

    private lateinit var arrayGrammar : GrammarArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_tab_two_layout, container, false)

        initView(view)
        initData()

        return view
    }

    private fun initView(view : View) {
        mPageTitle = view.findViewById(R.id.tv_page_two_title)
        val idx : Int = arguments?.getInt(CURRENT_FRAGMENT_INDEX) ?: 1
        mPageTitle.text = "———— This is Page $idx$idx$idx$idx ————"
        mBtn1 = view.findViewById(R.id.btn_page_two_1)
        mBtn1.setOnClickListener(mOnClickListener)
        view.findViewById<Button>(R.id.btn_page_two_2).setOnClickListener(mOnClickListener)
        view.findViewById<Button>(R.id.btn_page_two_3).setOnClickListener(mOnClickListener)
        mTextContent = view.findViewById(R.id.tv_page_two_content)
    }

    private fun initData() {
        mUpdateListener = object : UpdateListener {
            override fun onUpdateContent(content: String) {
                mCurrentContent += content
                mTextContent.text = mCurrentContent
            }
        }
        basicsGrammar = GrammarBasics()
        basicsGrammar.setUpdateListener(mUpdateListener)

        arrayGrammar = GrammarArray()
    }

    private val mOnClickListener = View.OnClickListener {
        if (it.id == R.id.btn_page_two_1) {
            mCurrentContent = ""
            basicsGrammar.mainEntrance()
        } else if (it.id == R.id.btn_page_two_2) {
            var weeklyBean = WeeklyBean("Friday")
            weeklyBean.main()
        } else if (it.id == R.id.btn_page_two_3) {
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val CURRENT_FRAGMENT_INDEX = "current_index"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): TabTwoFragment {
            return TabTwoFragment().apply {
                arguments = Bundle().apply {
                    putInt(CURRENT_FRAGMENT_INDEX, sectionNumber)
                }
            }
        }
    }
}